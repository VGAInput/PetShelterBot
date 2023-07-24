package edu.group5.petshelterbot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotFunctions {
    public final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public void sendMessage(Long chatId, String message,TelegramBot tgBot) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse sendResponse = tgBot.execute(sendMessage);
        if (!sendResponse.isOk()) logger.error("ERROR SENDING MESSAGE: {}", sendResponse.description());
    }
    public void sendPhotoReport(Long chatIdTo, Long chatIdFrom, int messageId,TelegramBot tgBot) {
        ForwardMessage forwardMessage = new ForwardMessage(chatIdTo, chatIdFrom, messageId);
        SendResponse sendResponse = tgBot.execute(forwardMessage);
        logger.info("A REPORT WAS SEND TO A VOLUNTEER.");
        if (!sendResponse.isOk()) logger.error("ERROR SENDING MESSAGE: {}", sendResponse.description());
    }
    public void sendOptions(Long chatId, String message, ReplyKeyboardMarkup reply,TelegramBot tgBot) {
        SendMessage sendMenu = new SendMessage(chatId, message).replyMarkup(reply);
        SendResponse sendResponse = tgBot.execute(sendMenu);
        if (!sendResponse.isOk()) logger.error("ERROR SENDING MESSAGE: {}", sendResponse.description());
    }

    public void sendOptions(Long chatId, String message, Keyboard reply,TelegramBot tgBot) {

        SendMessage sendMenu = new SendMessage(chatId, message);
        sendMenu.replyMarkup(reply);
        SendResponse sendResponse = tgBot.execute(sendMenu);

        if (!sendResponse.isOk()) logger.error("ERROR SENDING MESSAGE: {}", sendResponse.description());
    }

    public void sendMessageToVolunteer(Long chatId, String message,TelegramBot tgBot) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.Markdown);
        SendResponse sendResponse = tgBot.execute(sendMessage);
        if (!sendResponse.isOk()) logger.error("ERROR SENDING MESSAGE: {}", sendResponse.description());
    }

}
