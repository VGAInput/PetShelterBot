package edu.group5.petshelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.group5.petshelterbot.entity.Owner;
import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.service.CatService;
import edu.group5.petshelterbot.service.DogService;
import edu.group5.petshelterbot.service.OwnerService;
import edu.group5.petshelterbot.service.VolunteerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private static final String CAT_SHELTER_ADDRESS = "улица Сарайшык, дом 5, Астана";
    private static final String DOG_SHELTER_ADDRESS = "улица Ханов Керея и Жанибека, дом 4, Астана";
    private static final String CAT_SHELTER_TIMETABLE = "пн-пт  10:00 - 22:00\nсб-вс 11:00 - 21:00";
    private static final String DOG_SHELTER_TIMETABLE = "пн-пт  9:00 - 21:00\nсб-вс 10:00 - 21:00";
    private static final String CAT_SHELTER_SECURITY_NUMBER = "Для оформления пропуска на машину необходимо связаться с охраной клиники по номеру:\n" +
            "+7(617)255-34-34";
    private static final String DOG_SHELTER_SECURITY_NUMBER = "Для оформления пропуска на машину необходимо связаться с охраной клиники по номеру:\n" +
            "+7(625)313-78-56";

    @Autowired
    private final TelegramBot tgBot;


    private final DogService dogService;
    private final CatService catService;
    private final VolunteerService volunteerService;
    private final OwnerService ownerService;
    private ShelterTopic shelterTopic = ShelterTopic.NOT_PICKED;

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

    /**
     * Метод для чтения и распознования команд боту.
     * Список команд, меню и кнопок: {@link BotCommands}
     *
     * @param updates
     * @return
     */
    @Override
    public int process(List<Update> updates) {
        updates.stream().filter(update -> update.message() != null).forEach(update -> {
            logger.info("Processing update: {}", update);
            Message msg = update.message();

            Long chatId = msg.chat().id();
            Long tgUserId = msg.from().id();
            String text = msg.text();
            String addressForVolunteer = "[" + msg.from().username() + "](tg://user?id=" + msg.from().id() + ")";

            switch (text) {
                case BotCommands.START: {
                    shelterTopic = ShelterTopic.NOT_PICKED;
                    sendOptions(chatId,
                            "Приветствую, " + msg.chat().firstName() + "! Выберите приют для животных."
                            , BotCommands.startMarkup);
                }
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
                case BotCommands.SHELTER_INFORMATION: {
                    switch (shelterTopic) {
                        case CATS -> {
                            sendOptions(chatId, "Информция о приюте для котов", BotCommands.shelterMenuMarkup);
                        }
                        case DOGS -> {
                            sendOptions(chatId, "Информция о приюте для собак", BotCommands.shelterMenuMarkup);
                        }
                    }
                }
                break;
                case BotCommands.HOW_TO_ADOPT_A_PET: {
                    switch (shelterTopic) {
                        case CATS -> {
                            sendOptions(chatId, "Информция о том как приютить кота", BotCommands.rebootMarkup);
                        }
                        case DOGS -> {
                            sendOptions(chatId, "Информция о том как приютить собаку", BotCommands.rebootMarkup);
                        }
                    }
                }
                break;
                case BotCommands.SEND_PET_REPORT: {
                    switch (shelterTopic) {
                        case CATS -> {
                            sendOptions(chatId, "Информция о том как отправить отчёт о коте", BotCommands.rebootMarkup);
                        }
                        case DOGS -> {
                            sendOptions(chatId, "Информция о том как отправить отчёт о собаке", BotCommands.rebootMarkup);
                        }
                    }
                }
                break;
                case BotCommands.CALL_VOLUNTEER: {
                    switch (shelterTopic) {
                        case CATS -> {
                            sendOptions(chatId, "Одному из волонтёров кошачьего приюта отправлено сообщение, ждите ответа.", BotCommands.rebootMarkup);
                        }
                        case DOGS -> {
                            sendOptions(chatId, "Одному из волонтёров собачьего приюта отправлено сообщение, ждите ответа.", BotCommands.rebootMarkup);
                        }
                    }
                }
                break;
                case BotCommands.SHELTER_ADDRESS: {
                    switch (shelterTopic) {
                        case CATS -> {
                            sendOptions(chatId, "Адрес приюта:\n\n" + CAT_SHELTER_ADDRESS, BotCommands.shelterMenuMarkup);
                        }
                        case DOGS -> {
                            sendOptions(chatId, "Адрес приюта:\n\n" + DOG_SHELTER_ADDRESS, BotCommands.shelterMenuMarkup);
                        }
                    }
                }
                break;
                case BotCommands.SHELTER_TIMETABLE: {
                    switch (shelterTopic) {
                        case CATS -> {
                            sendOptions(chatId, "График работы приюта:\n\n" + CAT_SHELTER_TIMETABLE, BotCommands.shelterMenuMarkup);
                        }
                        case DOGS -> {
                            sendOptions(chatId, "График работы приюта:\n\n" + DOG_SHELTER_TIMETABLE, BotCommands.shelterMenuMarkup);
                        }
                    }
                }
                break;
                case BotCommands.CAR_PASS: {
                    switch (shelterTopic) {
                        case CATS -> {
                            sendOptions(chatId, CAT_SHELTER_SECURITY_NUMBER, BotCommands.shelterMenuMarkup);
                        }
                        case DOGS -> {
                            sendOptions(chatId, DOG_SHELTER_SECURITY_NUMBER, BotCommands.shelterMenuMarkup);
                        }
                    }
                }
                break;
            }


            if ("/test".equals(text)) {
                sendMessageToVolunteer(volunteerService.getRandomVolunteer("dogshelter"), addressForVolunteer + " " +
                        "просить волонтёра на помощь.");
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

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
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

    private void sendMessageToVolunteer(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.Markdown);
        SendResponse sendResponse = tgBot.execute(sendMessage);
        if (!sendResponse.isOk()) logger.error("ERROR SENDING MESSAGE: {}", sendResponse.description());
    }

}
