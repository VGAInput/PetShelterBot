package edu.group5.petshelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.entity.Dog;
import edu.group5.petshelterbot.entity.Owner;
import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.service.CatService;
import edu.group5.petshelterbot.service.DogService;
import edu.group5.petshelterbot.service.OwnerService;
import edu.group5.petshelterbot.service.VolunteerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Основной Update Listener для бота.
 * {@link #process(List)} - Основной метод для распознания сообщений пользоваетля и бота.
 * {@link ShelterTopic} - Вариант приюта при ведении диалога.
 * {@link BotCommands} - Список с запросами пользователя, ответами бота и меню.
 * {@link #petReportNotification()} - Каждый день в 12:00 Отправляет сообщение пользователю
 * * если у него есть пёс или собака - для каждого питомца создается сообщение с его именем т датой окончания исп. срока
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    @Autowired
    private final TelegramBot tgBot;
    private final DogService dogService;
    private final CatService catService;
    private final VolunteerService volunteerService;
    private final OwnerService ownerService;
    private ShelterTopic shelterTopic = ShelterTopic.NOT_PICKED;
    private LocalDateTime localDateTime;

    public TelegramBotUpdatesListener(TelegramBot tgBot, DogService dogService,
                                      CatService catService,
                                      VolunteerService volunteerService, OwnerService ownerService) {
        this.dogService = dogService;
        this.catService = catService;
        this.volunteerService = volunteerService;
        this.tgBot = tgBot;
        this.ownerService = ownerService;
    }

    @PostConstruct
    public void init() {
        tgBot.setUpdatesListener(this);
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void petReportNotification() {
        localDateTime = LocalDateTime.now();
        if (localDateTime.getHour() == 23) {
            for (int i = 0; i <= ownerService.getAllOwners().size() - 1; i++) {
                catReportReminder(ownerService.getAllOwners().get(i).getTgUserId());
                dogReportReminder(ownerService.getAllOwners().get(i).getTgUserId());
            }
        }
    }

    @Override
    public int process(List<Update> updates) {

        /* Методо сохранения телефонного номера в бд. */
        updates.stream().filter(update -> update.message().contact() != null).forEach(update -> {
            Message msg = update.message();

            Contact contact = msg.contact();
            Long chatId = msg.chat().id();
            ownerService.setOwnerTelephoneNumber(ownerService.getOwnerByTgUserId(chatId).getId(), contact.phoneNumber());
            sendMessage(chatId, "Ваш телефонный номер добавлен в базу данных");
        });


        updates.stream().filter(update -> update.message() != null).forEach(update -> {
            logger.info("Processing update: {}", update);
            Message msg = update.message();
            Long chatId = msg.chat().id();
            String addressForVolunteer = "[" + msg.from().firstName() + "](tg://user?id=" + msg.from().id() + ")";

            if (msg.photo() != null && msg.caption() != null) {
                switch (shelterTopic) {
                    case CATS -> {
                        long pickedVolunteer = volunteerService.getRandomVolunteer("cat_shelter");
                        sendMessageToVolunteer(pickedVolunteer, addressForVolunteer + " " +
                                "прислал отчёт о своём питомце. Если отчёт не отвечает требованиям - сообщите об этом владельцу.");
                        sendPhotoReport(chatId, pickedVolunteer, msg.messageId());
                        sendMessage(chatId, "Ваш отчёт был отправлен волонтёру.");
                    }
                    case DOGS -> {
                        long pickedVolunteer = volunteerService.getRandomVolunteer("dog_shelter");
                        sendMessageToVolunteer(pickedVolunteer, addressForVolunteer + " " +
                                "прислал отчёт о своём питомце. Если отчёт не отвечает требованиям - сообщите об этом владельцу.");
                        sendPhotoReport(chatId, pickedVolunteer, msg.messageId());
                        sendMessage(chatId, "Ваш отчёт был отправлен волонтёру.");

                    }
                }
            } else if (msg.photo() != null && msg.caption() == null) {
                logger.warn("IMAGE WITHOUT CAPTION DETECTED");
                sendMessage(chatId, "К фото нету текста. Пожалуйста, напишите отчёт в описании к фото.");
            }

            // ПРОВЕРКА СООБЩЕНИЯ БОТАМ НА ПРИСУТВИЕ ТЕКСТА
            if (msg.text() != null) {
                String text = msg.text();
                switch (text) {
                    case BotCommands.START: {
                        if (!ownerService.checkOwnerExistsByTgId(msg.from().id())) {
                            ownerService.saveOwner(new Owner(msg.from().id(), msg.from().firstName()));
                            sendMessage(chatId, "Приветствую! ваш аккаунт создан.");
                        } else if (ownerService.checkOwnerExistsByTgId(msg.from().id())) {
                            sendMessage(chatId, "Приветствую, " + msg.chat().firstName() + "!");
                        }
                    }
                    shelterTopic = ShelterTopic.NOT_PICKED;
                    sendOptions(chatId,
                            "Выберите приют для животных."
                            , BotCommands.startMarkup);

                    break;
                    case BotCommands.PICK_SHELTER_CATS: {
                        shelterTopic = ShelterTopic.CATS;
                        sendOptions(chatId, "Приют для кошек. Опции бота:", BotCommands.mainMenuMarkup);
                    }
                    break;
                    case BotCommands.PICK_SHELTER_DOGS: {
                        shelterTopic = ShelterTopic.DOGS;
                        sendOptions(chatId, "Приют для собак. Опции бота:", BotCommands.mainMenuMarkup);
                    }
                    break;
                    case BotCommands.MAINMENU_SHELTER_INFORMATION: {
                        switch (shelterTopic) {
                            case CATS -> {
                                sendOptions(chatId, "Информция о приюте для котов", BotCommands.infoMenuMarkup);
                            }
                            case DOGS -> {
                                sendOptions(chatId, "Информция о приюте для собак", BotCommands.infoMenuMarkup);
                            }
                        }
                    }
                    break;
                    case BotCommands.MAINMENU_HOW_TO_ADOPT_A_PET: {
                        switch (shelterTopic) {
                            case CATS -> {
                                sendOptions(chatId, "Информция о том как приютить кота", BotCommands.adoptPetCatMenuMarkup);
                            }
                            case DOGS -> {
                                sendOptions(chatId, "Информция о том как приютить собаку", BotCommands.adoptPetDogMenuMarkup);
                            }
                        }
                    }
                    break;
                    case BotCommands.MAINMENU_SEND_PET_REPORT: {
                        sendOptions(chatId, BotCommands.R_REPORT_INSTRUCTIONS, BotCommands.mainMenuMarkup);

                    }
                    break;
                    case BotCommands.CALL_VOLUNTEER: {
                        switch (shelterTopic) {
                            case CATS -> {
                                sendOptions(chatId, "Одному из волонтёров кошачьего приюта отправлено сообщение, ждите ответа.", BotCommands.rebootMarkup);
                                sendMessageToVolunteer(volunteerService.getRandomVolunteer("cat_shelter"), addressForVolunteer + " " +
                                        "просить волонтёра на помощь.");
                            }
                            case DOGS -> {
                                sendOptions(chatId, "Одному из волонтёров собачьего приюта отправлено сообщение, ждите ответа.", BotCommands.rebootMarkup);
                                sendMessageToVolunteer(volunteerService.getRandomVolunteer("dog_shelter"), addressForVolunteer + " " +
                                        "просить волонтёра на помощь.");
                            }
                        }
                    }
                    break;
                    case BotCommands.RETURN_MAINMENU: {
                        sendOptions(chatId, "В главное меню.", BotCommands.mainMenuMarkup);
                    }
                    break;
                    case BotCommands.INFO_SHELTER_ADDRESS: {
                        switch (shelterTopic) {
                            case CATS -> {
                                sendOptions(chatId, "Адрес приюта:\n\n" + BotCommands.R_CAT_SHELTER_ADDRESS, BotCommands.infoMenuMarkup);
                            }
                            case DOGS -> {
                                sendOptions(chatId, "Адрес приюта:\n\n" + BotCommands.R_DOG_SHELTER_ADDRESS, BotCommands.infoMenuMarkup);
                            }
                        }
                    }
                    break;
                    case BotCommands.INFO_SHELTER_TIMETABLE: {
                        switch (shelterTopic) {
                            case CATS -> {
                                sendOptions(chatId, "График работы приюта:\n\n" + BotCommands.R_CAT_SHELTER_TIMETABLE, BotCommands.infoMenuMarkup);
                            }
                            case DOGS -> {
                                sendOptions(chatId, "График работы приюта:\n\n" + BotCommands.R_DOG_SHELTER_TIMETABLE, BotCommands.infoMenuMarkup);
                            }
                        }
                    }
                    break;
                    case BotCommands.INFO_CAR_PASS: {
                        switch (shelterTopic) {
                            case CATS -> {
                                sendOptions(chatId, BotCommands.R_CAT_SHELTER_SECURITY_NUMBER, BotCommands.infoMenuMarkup);
                            }
                            case DOGS -> {
                                sendOptions(chatId, BotCommands.R_DOG_SHELTER_SECURITY_NUMBER, BotCommands.infoMenuMarkup);
                            }
                        }
                    }
                    break;
                    case BotCommands.INFO_SAFETY_PRECAUTIONS: {
                        sendOptions(chatId, BotCommands.R_SAFETY_RULES, BotCommands.infoMenuMarkup);
                    }

                    break;
                    case BotCommands.ADOPT_LEAVE_CONTACTS: {
                        sendOptions(chatId, BotCommands.R_TYPE_IN_YOUR_NUMBER, BotCommands.receiveTelephoneNumber);
                    }
                    break;
                    case BotCommands.ADOPT_DOG_PROTIPS: {
                        sendOptions(chatId, BotCommands.R_PROTIPS_LINK, BotCommands.protipsLinkKeyboard);
                    }
                    break;
                    case BotCommands.ADOPT_HOW_TO_MEET_PET: {
                        sendMessage(chatId, BotCommands.R_HOW_TO_MEET_PET);
                    }
                    break;
                    case BotCommands.ADOPT_REQUIRED_DOCUMENTS: {
                        sendMessage(chatId, BotCommands.R_REQUIRED_DOCUMENTS);
                    }
                    break;
                    case BotCommands.ADOPT_REASONS_WHY_MAY_BE_DENIED: {
                        sendMessage(chatId, BotCommands.R_REASONS_WHY_MAY_BE_DENIED);
                    }
                    break;

                    case BotCommands.ADOPT_RECOMENDATIONS_MENU: {
                        sendOptions(chatId, "Выберите раздел рекомендаций:", BotCommands.adoptPetRecommendationsMenuMarkup);
                    }
                    break;
                    case BotCommands.ADOPT_RECOMENDATIONS_TRANSPORT: {
                        sendOptions(chatId, BotCommands.R_RECOMENDATIONS_TRANSPORT, BotCommands.adoptPetRecommendationsMenuMarkup);
                    }
                    break;
                    case BotCommands.ADOPT_RECOMENDATIONS_HOUSE_YOUNG: {
                        sendOptions(chatId, BotCommands.R_RECOMENDATIONS_HOUSE_YOUNG, BotCommands.adoptPetRecommendationsMenuMarkup);
                    }
                    break;
                    case BotCommands.ADOPT_RECOMENDATIONS_HOUSE_ADULT: {
                        sendOptions(chatId, BotCommands.R_RECOMENDATIONS_HOUSE_ADULT, BotCommands.adoptPetRecommendationsMenuMarkup);
                    }
                    break;
                    case BotCommands.ADOPT_RECOMENDATIONS_HOUSE_DISABLED: {
                        sendOptions(chatId, BotCommands.R_RECOMENDATIONS_HOUSE_DISABLED, BotCommands.adoptPetRecommendationsMenuMarkup);
                    }
                    break;
                }

                {
                    /**
                     * Функция добавить пользователя как волонтёра
                     * Аккаунт пользователя телеграма будет добавлен в БД как волонтёр
                     * определённоого приюта {@link Volunteer}. Одному из волонтёру будет отправлено сообщение,
                     * что другому пользователю бота нужна помощь.
                     */

                    if ("/volunteer_dog".equals(text)) {
                        if (volunteerService.checkVolunteerExists("dogShelter", msg.from().id())) {
                            sendMessage(chatId, "Вы уже были добавлены как волонтёр в приют для собак");
                        } else {
                            volunteerService.saveVolunteer(new Volunteer(msg.from().firstName() + msg.from().lastName(), "dogShelter", msg.from().id()));
                            sendMessage(chatId, msg.from().firstName() + ", вы добавлены как волонтёр в приют для собак");
                        }
                    }
                    if ("/volunteer_cat".equals(text)) {
                        if (volunteerService.checkVolunteerExists("catShelter", msg.from().id())) {
                            sendMessage(chatId, "Вы уже были добавлены как волонтёр в приюте для кошек.");
                        } else {
                            volunteerService.saveVolunteer(new Volunteer(msg.from().firstName() + msg.from().lastName(), "catShelter", msg.from().id()));
                            sendMessage(chatId, msg.from().firstName() + ", вы добавлены как волонтёр в приют для кошек");
                        }
                    }
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    void dogReportReminder(long chatId) {
        for (int i = 0; i < ownerService.getDogsIDs(ownerService.getOwnerByTgUserId(chatId).getId()).size(); i++) {
            long dogID = ownerService.getDogsIDs(ownerService.getOwnerByTgUserId(chatId).getId()).get(i);
            Dog dog = dogService.getDogByID(dogID);

            sendMessage(chatId, "Не забудьте сегодня отправить отчёт вашей собаки " +
                    dog.getName() +
                    ".\nДата окончания испытательного срока: " + dogService.getTrialDate(dogID)
            );
        }
    }

    void catReportReminder(long chatId) {
        for (int i = 0; i < ownerService.getCatsIDs(ownerService.getOwnerByTgUserId(chatId).getId()).size(); i++) {
            long catID = ownerService.getCatsIDs(ownerService.getOwnerByTgUserId(chatId).getId()).get(i);
            Cat cat = catService.getCatByID(catID);

            sendMessage(chatId, "Не забудьте сегодня отправить отчёт вашего кота " +
                    cat.getName() +
                    ".\nДата окончания испытательного срока: " + catService.getTrialDate(catID)
            );
        }
    }


    // Метод для простого ответа.
    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse sendResponse = tgBot.execute(sendMessage);
        if (!sendResponse.isOk()) logger.error("ERROR SENDING MESSAGE: {}", sendResponse.description());
    }

    private void sendOptions(Long chatId, String message, ReplyKeyboardMarkup reply) {
        SendMessage sendMenu = new SendMessage(chatId, message).replyMarkup(reply);
        SendResponse sendResponse = tgBot.execute(sendMenu);
        if (!sendResponse.isOk()) logger.error("ERROR SENDING MESSAGE: {}", sendResponse.description());
    }

    private void sendOptions(Long chatId, String message, Keyboard reply) {

        SendMessage sendMenu = new SendMessage(chatId, message);
        sendMenu.replyMarkup(reply);
        SendResponse sendResponse = tgBot.execute(sendMenu);

        if (!sendResponse.isOk()) logger.error("ERROR SENDING MESSAGE: {}", sendResponse.description());
    }

    private void sendMessageToVolunteer(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.Markdown);
        SendResponse sendResponse = tgBot.execute(sendMessage);
        if (!sendResponse.isOk()) logger.error("ERROR SENDING MESSAGE: {}", sendResponse.description());
    }

    private void sendPhotoReport(Long chatIdFrom, Long chatIdTo, int messageId) {
        ForwardMessage forwardMessage = new ForwardMessage(chatIdFrom, chatIdFrom, messageId);
        SendResponse sendResponse = tgBot.execute(forwardMessage);
        logger.info("A REPORT WAS SEND TO A VOLUNTEER.");
        if (!sendResponse.isOk()) logger.error("ERROR SENDING MESSAGE: {}", sendResponse.description());
    }


}
