package edu.group5.petshelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.group5.petshelterbot.service.VolunteerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class VolunteerHandler {
    private LocalDateTime localDateTime;

    private final VolunteerService volunteerService;
    private final TelegramBot tgBot;
    private BotFunctions botFunctions;
    private Long chatId;

    public VolunteerHandler(VolunteerService volunteerService, TelegramBot tgBot) {
        this.volunteerService = volunteerService;
        this.tgBot = tgBot;

        botFunctions = new BotFunctions();
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void petReportNotification() {
        localDateTime = LocalDateTime.now();
        if (localDateTime.getHour() == 21) {
            if (volunteerService.isCurrentUserVolunteer(chatId)) {
                botFunctions.sendMessage(chatId, "Не забудьте проверить отчёты владельцев!", tgBot);
            }
        }
    }

    public void processReplyReport(List<Update> updates) {
        updates.stream().filter(update -> update.message().replyToMessage() != null).forEach(update -> {

            botFunctions.logger.info("Processing update: {}", update);
            Message msg = update.message();

            if (msg.replyToMessage().photo() != null && msg.replyToMessage().caption() != null) {
                botFunctions.logger.info("REPLY DETECTED, SENDER OF ORIGINAL IS "
                        + msg.replyToMessage().chat().firstName());
                botFunctions.sendMessage(msg.replyToMessage().chat().id(), "" + msg.text(), tgBot);
            }
        });
    }
}
