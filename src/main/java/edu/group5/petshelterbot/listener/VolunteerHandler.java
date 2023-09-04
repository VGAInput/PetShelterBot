package edu.group5.petshelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.group5.petshelterbot.service.ReportService;
import edu.group5.petshelterbot.service.VolunteerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class VolunteerHandler {
    private LocalDateTime localDateTime;

    private final VolunteerService volunteerService;
    private final ReportService reportService;
    private final TelegramBot tgBot;
    private BotFunctions botFunctions;
    private Long chatId;

    public VolunteerHandler(VolunteerService volunteerService, ReportService reportService, TelegramBot tgBot) {
        this.volunteerService = volunteerService;
        this.reportService = reportService;
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

    public void processTextRecognizing(List<Update> updates) {
        updates.stream().filter(update -> update.message().text() != null).forEach(update -> {
            botFunctions.logger.info("Processing update: {}", update);
            Message msg = update.message();
            chatId = msg.chat().id();

            if (volunteerService.isCurrentUserVolunteer(chatId)) {
                botFunctions.logger.info("Current user is volunteer");
                if (msg.text() != null) {
                    String text = msg.text();
                    switch (text) {
                        case "/готов": {
                            volunteerService.isReady(true, volunteerService.getVolunteerByTgUserId(chatId).get(0).getId());
                            botFunctions.sendMessage(chatId, "Вы готовы к работе с владельцами.", tgBot);
                        }
                        break;
                        case "/неготов": {
                            volunteerService.isReady(false, volunteerService.getVolunteerByTgUserId(chatId).get(0).getId());
                            botFunctions.sendMessage(chatId, "Вы свободны от обязанностей волонтёра.", tgBot);
                        }
                        break;
                    }
                }
            }
        });
    }

    public void processReplyReport(List<Update> updates) {
        updates.stream().filter(update -> update.message().replyToMessage() != null).forEach(update -> {
            botFunctions.logger.info("Processing update: {}", update);
            Message msg = update.message();
            if (volunteerService.isCurrentUserVolunteer(chatId)) {
                if (msg.replyToMessage().photo() != null && msg.replyToMessage().caption() != null) {
                    if (msg.text().equals("/одобрить")) {
                        reportService.setApprove(1, reportService.getReportByText(msg.replyToMessage().caption()).getId());
                        botFunctions.sendMessage(msg.replyToMessage().forwardFrom().id(), "Ваш отчёт одобрен, спасибо!", tgBot);

                    } else if (msg.text().equals("/отклонить")) {
                        reportService.setApprove(0, reportService.getReportByText(msg.replyToMessage().caption()).getId());
                        botFunctions.sendMessage(msg.replyToMessage().forwardFrom().id(), "Ваш отчёт не отвечает требованиям. Пожалуйста проверьте условия в " +
                                "меню " + BotCommands.MAINMENU_SEND_PET_REPORT, tgBot);
                    }
                    botFunctions.logger.info("REPLY DETECTED, SENDER OF ORIGINAL IS "
                            + msg.replyToMessage().chat().firstName());

                } else if (msg.replyToMessage().text() != null) {
                    if (msg.replyToMessage().text().contains("просит волонтёра на помощь") || msg.replyToMessage().text().contains("Ответ пользователя")) {
                        String senderId = "";
                        for (int i = 0; i < msg.replyToMessage().text().length() - 1; i++) {
                            if (msg.replyToMessage().text().charAt(i) == ' ') break;
                            senderId = senderId + msg.replyToMessage().text().charAt(i);

                        }
                        botFunctions.logger.info("SOMEONE NEED VOLUNTEER HELP " + senderId);
                        botFunctions.sendMessage(Long.parseLong(senderId), msg.from().id() + " (Волонтёр " + msg.from().firstName() +
                                ") Ответ волонтёра - ответьте на это сообщение (reply), для того что бы ответить волонтёру.\n\n" +
                                "" + msg.text(), tgBot);
                    }
                }
            }
        });
    }
}
