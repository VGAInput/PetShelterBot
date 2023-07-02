package edu.group5.petshelterbot.listener;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;


/**
 * Отдельные команды для бота - используется в слушателе {@link TelegramBotUpdatesListener}.
 */
public class BotCommands {

    /**
     * КОМАНДЫ ДЛЯ БОТА С ПРИСТАВКАМИ:
     * =================================
     *      MAINMENU_ - команды для основного меню.
     *      INFO_ - команды ветки "Информация о приюте".
     *      ADOPT_ - команды ветки "Как приютить питомца?".
     *      REPORT_ - команды ветки "Отправить отчёт о питомце".
     */
    public static final String START = "/start";

    public static final String PICK_SHELTER_CATS = "Приют для кошек";
    public static final String PICK_SHELTER_DOGS = "Приют для собак";

    public static final String MAINMENU_SHELTER_INFORMATION = "Информация о приюте";
    public static final String MAINMENU_HOW_TO_ADOPT_A_PET = "Как приютить питомца?";
    public static final String MAINMENU_SEND_PET_REPORT = "Отправить отчёт о питомце";
    public static final String MAINMENU_CHANGE_SHELTER = "Вернуться к выбору приюта";
    public static final String CALL_VOLUNTEER = "Вызвать волонтёра";

    public static final String INFO_SHELTER_ADDRESS = "Адрес";
    public static final String INFO_SHELTER_TIMETABLE = "График работы";
    public static final String INFO_CAR_PASS = "Оформить пропуск на машину";
    public static final String INFO_SAFETY_PRECAUTIONS = "Общие рекомендации";
    public static final String INFO_LEAVE_CONTACTS = "Оставить телефонный номер для связи.";

    /**
     * Строки ответов, которые предоставляет бот:
     *
     *      R_ - (response) приставка строки как ответ бота .
     *
     */
    public static final String R_CAT_SHELTER_ADDRESS = "улица Сарайшык, дом 5, Астана";
    public static final String R_DOG_SHELTER_ADDRESS = "улица Ханов Керея и Жанибека, дом 4, Астана";
    public static final String R_CAT_SHELTER_TIMETABLE = "пн-пт  10:00 - 22:00\nсб-вс 11:00 - 21:00";
    public static final String R_DOG_SHELTER_TIMETABLE = "пн-пт  9:00 - 21:00\nсб-вс 10:00 - 21:00";
    public static final String R_CAT_SHELTER_SECURITY_NUMBER = "Для оформления пропуска на машину необходимо связаться с охраной клиники по номеру:\n" +
            "+7(617)255-34-34";
    public static final String R_DOG_SHELTER_SECURITY_NUMBER = "Для оформления пропуска на машину необходимо связаться с охраной клиники по номеру:\n" +
            "+7(625)313-78-56";
    public static final String R_SAFETY_RULES =
            "— обувь должна быть на подошве, исключающей непроизвольное скольжение;\n" +
                    "\n" +
                    "— верхняя одежда должна соответствовать погоде, исключать промокание, а также должна быть облегающей и исключать возможность непроизвольных зацепов за ограждения, строения и иные конструкции.\n" +
                    "\n" +
                    "Запрещается носить в карманах одежды колющие, режущие и стеклянные предметы.\n" +
                    "\n" +
                    "Возможно использование дополнительных средств индивидуальной защиты. Средства индивидуальной защиты должны соответствовать размеру, применяться в исправном, чистом состоянии по назначению и храниться в специально отведенных и оборудованных местах с соблюдением санитарных правил.\n" +
                    "\n" +
                    "При общении с животными работники и посетители приюта обязаны соблюдать меры персональной и общественной безопасности.\n" +
                    "\n" +
                    "При входе в какое-либо помещение или вольер или выходе из него необходимо обязательно закрыть дверь.\n" +
                    "\n" +
                    "В случае проведения на территории приюта каких-либо работ (погрузо-разгрузочные, строительные работы, уборка вольеров, перевод собак с одной территории на другую и т.п.) не допускается препятствовать осуществлению этих работ и отвлекать работников приюта (в том числе разговорами и вопросами).\n" +
                    "\n" +
                    "Работники и посетители приюта обязаны соблюдать правила личной гигиены, в том числе мыть руки с дезинфицирующими средствами после общения с животными.\n" +
                    "\n" +
                    "Нахождение на территории в излишне возбужденном состоянии, а также в состоянии алкогольного, наркотического или медикаментозного опьянения строго запрещено.\n" +
                    "\n" +
                    "Поджигать мусор, пользоваться открытым огнем, а также нарушать иные требования пожарной безопасности на территории приюта категорически запрещено. ";


    /**
     * меню с ответами, которые предоставляет бот - используется в слушателе  {@link TelegramBotUpdatesListener}.
     */
    public static final ReplyKeyboardMarkup rebootMarkup = new ReplyKeyboardMarkup(new String[]{START});
    public static final ReplyKeyboardMarkup startMarkup = new ReplyKeyboardMarkup(new String[]{PICK_SHELTER_CATS, PICK_SHELTER_DOGS});
    public static final ReplyKeyboardMarkup mainMenuMarkup = new ReplyKeyboardMarkup(new String[]{
            MAINMENU_SHELTER_INFORMATION, MAINMENU_HOW_TO_ADOPT_A_PET, MAINMENU_SEND_PET_REPORT},
            new String[]{CALL_VOLUNTEER,START});
    public static final ReplyKeyboardMarkup infoMenuMarkup = new ReplyKeyboardMarkup(new String[]{
            INFO_SHELTER_ADDRESS, INFO_SHELTER_TIMETABLE, INFO_SAFETY_PRECAUTIONS,INFO_CAR_PASS},
            new String[]{INFO_LEAVE_CONTACTS,CALL_VOLUNTEER,START});
}
