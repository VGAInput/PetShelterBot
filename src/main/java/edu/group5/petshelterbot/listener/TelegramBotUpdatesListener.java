package edu.group5.petshelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
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

/**
 * Основной Update Listener для бота.
 * {@link #process(List)} - Основной метод для распознания сообщений пользоваетля и бота.
 * {@link ShelterTopic} - Вариант приюта при ведении диалога.
 * {@link BotCommands} - Список с запросами пользователя, ответами бота и меню.
 * <p>
 * <p>
 * * если у него есть пёс или собака - для каждого питомца создается сообщение с его именем т датой окончания исп. срока
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    @Autowired
    private final TelegramBot tgBot;
    private VolunteerHandler volunteerHandler;
    private OwnerHandler ownerHandler;


    public TelegramBotUpdatesListener(TelegramBot tgBot, DogService dogService,
                                      OwnerHandler ownerHandler, CatService catService,
                                      VolunteerService volunteerService, OwnerService ownerService, VolunteerHandler volunteerHandler) {

        this.tgBot = tgBot;
        this.volunteerHandler = volunteerHandler;
        this.ownerHandler = ownerHandler;
    }

    @PostConstruct
    public void init() {
        tgBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

        ownerHandler.processPhoneContact(updates);
        ownerHandler.processSendPhotoReport(updates);
        ownerHandler.processTextRecognizing(updates);
        ownerHandler.processReplyVolunteer(updates);

        volunteerHandler.processReplyReport(updates);
        volunteerHandler.processTextRecognizing(updates);

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
