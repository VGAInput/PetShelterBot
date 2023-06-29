package edu.group5.petshelterbot.listener;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;


/**
 * Отдельные команды для бота - используется в слушателе {@link TelegramBotUpdatesListener}.
 */
public class BotCommands {
    public static final String START = "/start";
    public static final String PICK_SHELTER_CATS = "Приют для кошек";
    public static final String PICK_SHELTER_DOGS = "Приют для собак";
    public static final String SHELTER_INFORMATION = "Информация о приюте";
    public static final String HOW_TO_ADOPT_A_PET = "Как приютить питомца?";
    public static final String SEND_PET_REPORT = "Отправить отчёт о питомце";
    public static final String CALL_VOLUNTEER = "Вызвать волонтёра";
    public static final String SHELTER_ADDRESS = "Адрес";
    public static final String SHELTER_TIMETABLE = "График работы";
    public static final String CAR_PASS = "Оформить пропуск на машину";
    public static final String SAFETY_PRECAUTIONS = "Общие рекомендации";

    /**
     * меню с ответами, которые предоставляет бот - используется в слушателе  {@link TelegramBotUpdatesListener}.
     */
    public static final ReplyKeyboardMarkup rebootMarkup = new ReplyKeyboardMarkup(new String[]{START});
    public static final ReplyKeyboardMarkup startMarkup = new ReplyKeyboardMarkup(new String[]{PICK_SHELTER_CATS, PICK_SHELTER_DOGS});
    public static final ReplyKeyboardMarkup mainMenuMarkup = new ReplyKeyboardMarkup(new String[]{
            SHELTER_INFORMATION, HOW_TO_ADOPT_A_PET, SEND_PET_REPORT},
            new String[]{CALL_VOLUNTEER});
    public static final ReplyKeyboardMarkup shelterMenuMarkup = new ReplyKeyboardMarkup(new String[]{
            SHELTER_ADDRESS, SHELTER_TIMETABLE, SAFETY_PRECAUTIONS},
            new String[]{CAR_PASS});
}
