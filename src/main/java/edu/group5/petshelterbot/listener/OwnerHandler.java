package edu.group5.petshelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.entity.Dog;
import edu.group5.petshelterbot.entity.Owner;
import edu.group5.petshelterbot.service.CatService;
import edu.group5.petshelterbot.service.DogService;
import edu.group5.petshelterbot.service.OwnerService;
import edu.group5.petshelterbot.service.VolunteerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class OwnerHandler {
    private final TelegramBot tgBot;
    private final OwnerService ownerService;
    private final VolunteerService volunteerService;
    private final CatService catService;
    private final DogService dogService;
    private BotFunctions botFunctions;
    private ShelterTopic shelterTopic = ShelterTopic.NOT_PICKED;

    public OwnerHandler(TelegramBot tgBot, OwnerService ownerService, VolunteerService volunteerService, CatService catService, DogService dogService) {
        this.tgBot = tgBot;
        this.ownerService = ownerService;
        this.volunteerService = volunteerService;
        this.catService = catService;
        this.dogService = dogService;
        botFunctions = new BotFunctions();
    }

    private LocalDateTime localDateTime;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void petReportNotification() {
        localDateTime = LocalDateTime.now();
        if (localDateTime.getHour() == 12) {
            for (int i = 0; i <= ownerService.getAllOwners().size() - 1; i++) {
                catReportReminder(ownerService.getAllOwners().get(i).getTgUserId());
                dogReportReminder(ownerService.getAllOwners().get(i).getTgUserId());
            }
        }
    }

    private void dogReportReminder(long chatId) {
        for (int i = 0; i < ownerService.getDogsIDs(ownerService.getOwnerByTgUserId(chatId).getId()).size(); i++) {
            long dogID = ownerService.getDogsIDs(ownerService.getOwnerByTgUserId(chatId).getId()).get(i);
            Dog dog = dogService.getDogByID(dogID);

            botFunctions.sendMessage(chatId, "Не забудьте сегодня отправить отчёт вашей собаки " +
                    dog.getName() +
                    ".\nДата окончания испытательного срока: " + dogService.getTrialDate(dogID), tgBot);
        }
    }

    private void catReportReminder(long chatId) {
        for (int i = 0; i < ownerService.getCatsIDs(ownerService.getOwnerByTgUserId(chatId).getId()).size(); i++) {
            long catID = ownerService.getCatsIDs(ownerService.getOwnerByTgUserId(chatId).getId()).get(i);
            Cat cat = catService.getCatByID(catID);

            botFunctions.sendMessage(chatId, "Не забудьте сегодня отправить отчёт вашего кота " +
                    cat.getName() +
                    ".\nДата окончания испытательного срока: " + catService.getTrialDate(catID), tgBot);
        }
    }

    public void processSendPhotoReport(List<Update> updates) {
        updates.stream().filter(update -> update.message().photo() != null).forEach(update -> {
            botFunctions.logger.info("Processing update: {}", update);
            Message msg = update.message();
            Long chatId = msg.chat().id();
            String addressForVolunteer = "[" + msg.from().firstName() + "](tg://user?id=" + msg.from().id() + ")";
            if (msg.photo() != null && msg.caption() != null) {
                switch (shelterTopic) {
                    case CATS -> {
                        long pickedVolunteer = volunteerService.getRandomVolunteer("cat_shelter");
                        botFunctions.sendMessageToVolunteer(pickedVolunteer, addressForVolunteer + " " +
                                " прислал отчёт о своём питомце. Ответьте на этот отчёт владельцу кота - подтвердите, если " +
                                "он отвечает нормам отчёта, иначе опишите ошибку.", tgBot);
                        botFunctions.sendPhotoReport(pickedVolunteer, chatId, msg.messageId(), tgBot);

                        botFunctions.sendMessage(chatId, "Ваш отчёт был отправлен волонтёру.", tgBot);
                    }
                    case DOGS -> {
                        long pickedVolunteer = volunteerService.getRandomVolunteer("dog_shelter");
                        botFunctions.sendPhotoReport(chatId, pickedVolunteer, msg.messageId(), tgBot);
                        botFunctions.logger.info(update.message().messageId() + "");
                        botFunctions.sendMessageToVolunteer(pickedVolunteer, addressForVolunteer + " " +
                                " прислал отчёт о своём питомце. Ответьте на этот отчёт владельцу кота - подтвердите, если " +
                                "он отвечает нормам отчёта, иначе опишите ошибку.", tgBot);

                        botFunctions.sendMessage(chatId, "Ваш отчёт был отправлен волонтёру.", tgBot);
                    }
                }
            } else if (msg.photo() != null && msg.caption() == null) {
                botFunctions.logger.warn("IMAGE WITHOUT CAPTION DETECTED");
                botFunctions.sendMessage(chatId, "К фото не добавлен текст. Пожалуйста, напишите отчёт в описании к фото.", tgBot);
            }
        });
    }

    public void processTextRecognizing(List<Update> updates) {
        updates.stream().filter(update -> update.message().text() != null).forEach(update -> {
            botFunctions.logger.info("Processing update: {}", update);
            Message msg = update.message();
            Long chatId = msg.chat().id();
            String addressForVolunteer = "[" + msg.from().firstName() + "](tg://user?id=" + msg.from().id() + ")";

            if (msg.text() != null) {
                String text = msg.text();
                switch (text) {
                    case BotCommands.START: {
                        if (!ownerService.checkOwnerExistsByTgId(msg.from().id())) {
                            ownerService.saveOwner(new Owner(msg.from().id(), msg.from().firstName()));
                            botFunctions.sendMessage(chatId, "Приветствую! ваш аккаунт создан.", tgBot);
                        } else if (ownerService.checkOwnerExistsByTgId(msg.from().id())) {
                            botFunctions.sendMessage(chatId, "Приветствую, " + msg.chat().firstName() + "!", tgBot);
                        }
                    }
                    shelterTopic = ShelterTopic.NOT_PICKED;
                    botFunctions.sendOptions(chatId,
                            "Выберите приют для животных."
                            , BotCommands.startMarkup, tgBot);

                    break;
                    case BotCommands.PICK_SHELTER_CATS: {
                        shelterTopic = ShelterTopic.CATS;
                        botFunctions.sendOptions(chatId, "Приют для кошек. Опции бота:", BotCommands.mainMenuMarkup, tgBot);
                    }
                    break;
                    case BotCommands.PICK_SHELTER_DOGS: {
                        shelterTopic = ShelterTopic.DOGS;
                        botFunctions.sendOptions(chatId, "Приют для собак. Опции бота:", BotCommands.mainMenuMarkup, tgBot);
                    }
                    break;
                    case BotCommands.MAINMENU_SHELTER_INFORMATION: {
                        switch (shelterTopic) {
                            case CATS -> {
                                botFunctions.sendOptions(chatId, "Информция о приюте для котов", BotCommands.infoMenuMarkup, tgBot);
                            }
                            case DOGS -> {
                                botFunctions.sendOptions(chatId, "Информция о приюте для собак", BotCommands.infoMenuMarkup, tgBot);
                            }
                        }
                    }
                    break;
                    case BotCommands.MAINMENU_HOW_TO_ADOPT_A_PET: {
                        switch (shelterTopic) {
                            case CATS -> {
                                botFunctions.sendOptions(chatId, "Информция о том как приютить кота", BotCommands.adoptPetCatMenuMarkup, tgBot);
                            }
                            case DOGS -> {
                                botFunctions.sendOptions(chatId, "Информция о том как приютить собаку", BotCommands.adoptPetDogMenuMarkup, tgBot);
                            }
                        }
                    }
                    break;
                    case BotCommands.MAINMENU_SEND_PET_REPORT: {
                        botFunctions.sendOptions(chatId, BotCommands.R_REPORT_INSTRUCTIONS, BotCommands.mainMenuMarkup, tgBot);
                    }
                    break;
                    case BotCommands.CALL_VOLUNTEER: {
                        switch (shelterTopic) {
                            case CATS -> {
                                botFunctions.sendOptions(chatId, "Одному из волонтёров кошачьего приюта отправлено сообщение, ждите ответа.", BotCommands.rebootMarkup, tgBot);
                                botFunctions.sendMessageToVolunteer(volunteerService.getRandomVolunteer("cat_shelter"), addressForVolunteer + " " +
                                        "просить волонтёра на помощь.", tgBot);
                            }
                            case DOGS -> {
                                botFunctions.sendOptions(chatId, "Одному из волонтёров собачьего приюта отправлено сообщение, ждите ответа.", BotCommands.rebootMarkup, tgBot);
                                botFunctions.sendMessageToVolunteer(volunteerService.getRandomVolunteer("dog_shelter"), addressForVolunteer + " " +
                                        "просить волонтёра на помощь.", tgBot);
                            }
                        }
                    }
                    break;
                    case BotCommands.RETURN_MAINMENU: {
                        botFunctions.sendOptions(chatId, "В главное меню.", BotCommands.mainMenuMarkup, tgBot);
                    }
                    break;
                    case BotCommands.INFO_SHELTER_ADDRESS: {
                        switch (shelterTopic) {
                            case CATS -> {
                                botFunctions.sendOptions(chatId, "Адрес приюта:\n\n" + BotCommands.R_CAT_SHELTER_ADDRESS, BotCommands.infoMenuMarkup, tgBot);
                            }
                            case DOGS -> {
                                botFunctions.sendOptions(chatId, "Адрес приюта:\n\n" + BotCommands.R_DOG_SHELTER_ADDRESS, BotCommands.infoMenuMarkup, tgBot);
                            }
                        }
                    }
                    break;
                    case BotCommands.INFO_SHELTER_TIMETABLE: {
                        switch (shelterTopic) {
                            case CATS -> {
                                botFunctions.sendOptions(chatId, "График работы приюта:\n\n" + BotCommands.R_CAT_SHELTER_TIMETABLE, BotCommands.infoMenuMarkup, tgBot);
                            }
                            case DOGS -> {
                                botFunctions.sendOptions(chatId, "График работы приюта:\n\n" + BotCommands.R_DOG_SHELTER_TIMETABLE, BotCommands.infoMenuMarkup, tgBot);
                            }
                        }
                    }
                    break;
                    case BotCommands.INFO_CAR_PASS: {
                        switch (shelterTopic) {
                            case CATS -> {
                                botFunctions.sendOptions(chatId, BotCommands.R_CAT_SHELTER_SECURITY_NUMBER, BotCommands.infoMenuMarkup, tgBot);
                            }
                            case DOGS -> {
                                botFunctions.sendOptions(chatId, BotCommands.R_DOG_SHELTER_SECURITY_NUMBER, BotCommands.infoMenuMarkup, tgBot);
                            }
                        }
                    }
                    break;
                    case BotCommands.INFO_SAFETY_PRECAUTIONS: {
                        botFunctions.sendOptions(chatId, BotCommands.R_SAFETY_RULES, BotCommands.infoMenuMarkup, tgBot);
                    }

                    break;
                    case BotCommands.ADOPT_LEAVE_CONTACTS: {
                        botFunctions.sendOptions(chatId, BotCommands.R_TYPE_IN_YOUR_NUMBER, BotCommands.receiveTelephoneNumber, tgBot);
                    }
                    break;
                    case BotCommands.ADOPT_DOG_PROTIPS: {
                        botFunctions.sendOptions(chatId, BotCommands.R_PROTIPS_LINK, BotCommands.protipsLinkKeyboard, tgBot);
                    }
                    break;
                    case BotCommands.ADOPT_HOW_TO_MEET_PET: {
                        botFunctions.sendMessage(chatId, BotCommands.R_HOW_TO_MEET_PET, tgBot);
                    }
                    break;
                    case BotCommands.ADOPT_REQUIRED_DOCUMENTS: {
                        botFunctions.sendMessage(chatId, BotCommands.R_REQUIRED_DOCUMENTS, tgBot);
                    }
                    break;
                    case BotCommands.ADOPT_REASONS_WHY_MAY_BE_DENIED: {
                        botFunctions.sendMessage(chatId, BotCommands.R_REASONS_WHY_MAY_BE_DENIED, tgBot);
                    }
                    break;

                    case BotCommands.ADOPT_RECOMENDATIONS_MENU: {
                        botFunctions.sendOptions(chatId, "Выберите раздел рекомендаций:", BotCommands.adoptPetRecommendationsMenuMarkup, tgBot);
                    }
                    break;
                    case BotCommands.ADOPT_RECOMENDATIONS_TRANSPORT: {
                        botFunctions.sendOptions(chatId, BotCommands.R_RECOMENDATIONS_TRANSPORT, BotCommands.adoptPetRecommendationsMenuMarkup, tgBot);
                    }
                    break;
                    case BotCommands.ADOPT_RECOMENDATIONS_HOUSE_YOUNG: {
                        botFunctions.sendOptions(chatId, BotCommands.R_RECOMENDATIONS_HOUSE_YOUNG, BotCommands.adoptPetRecommendationsMenuMarkup, tgBot);
                    }
                    break;
                    case BotCommands.ADOPT_RECOMENDATIONS_HOUSE_ADULT: {
                        botFunctions.sendOptions(chatId, BotCommands.R_RECOMENDATIONS_HOUSE_ADULT, BotCommands.adoptPetRecommendationsMenuMarkup, tgBot);
                    }
                    break;
                    case BotCommands.ADOPT_RECOMENDATIONS_HOUSE_DISABLED: {
                        botFunctions.sendOptions(chatId, BotCommands.R_RECOMENDATIONS_HOUSE_DISABLED, BotCommands.adoptPetRecommendationsMenuMarkup, tgBot);
                    }
                    break;
                }

                {

                }
            }
        });
    }

    public void processPhoneContact(List<Update> updates) {
        updates.stream().filter(update -> update.message().contact() != null).forEach(update -> {
            Message msg = update.message();
            Contact contact = msg.contact();
            Long chatId = msg.chat().id();
            ownerService.setOwnerTelephoneNumber(ownerService.getOwnerByTgUserId(chatId).getId(), contact.phoneNumber());
            botFunctions.sendMessage(chatId, "Ваш телефонный номер добавлен в базу данных", tgBot);
        });
    }

}
