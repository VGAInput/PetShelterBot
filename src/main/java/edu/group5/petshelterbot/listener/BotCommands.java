package edu.group5.petshelterbot.listener;

import com.pengrad.telegrambot.model.request.*;


/**
 * Отдельные команды для бота - используется в слушателе {@link TelegramBotUpdatesListener}.
 */
public class BotCommands {

    /**
     * КОМАНДЫ ДЛЯ БОТА С ПРИСТАВКАМИ:
     * =================================
     * MAINMENU_ - команды для основного меню.
     * INFO_ - команды ветки "Информация о приюте".
     * ADOPT_ - команды ветки "Как приютить питомца?".
     * REPORT_ - команды ветки "Отправить отчёт о питомце".
     */
    public static final String START = "/start";
    public static final String VOLUNTEER_ACCEPT_REPORT = "Отсчёт отвечает норме.";
    public static final String VOLUNTEER_DECLINE_AND_NOTIFY_REPORT = "Отсчёт НЕ отвечает норме.";

    public static final String PICK_SHELTER_CATS = "Приют для кошек";
    public static final String PICK_SHELTER_DOGS = "Приют для собак";

    public static final String MAINMENU_SHELTER_INFORMATION = "Информация о приюте";
    public static final String RETURN_MAINMENU = "В главное меню.";
    public static final String MAINMENU_HOW_TO_ADOPT_A_PET = "Как приютить питомца?";
    public static final String MAINMENU_SEND_PET_REPORT = "Отправить отчёт о питомце";
    public static final String MAINMENU_CHANGE_SHELTER = "Вернуться к выбору приюта";
    public static final String CALL_VOLUNTEER = "Вызвать волонтёра";

    public static final String INFO_SHELTER_ADDRESS = "Адрес";
    public static final String INFO_SHELTER_TIMETABLE = "График работы";
    public static final String INFO_CAR_PASS = "Оформить пропуск на машину";
    public static final String INFO_SAFETY_PRECAUTIONS = "Общие рекомендации";
    public static final String INFO_LEAVE_CONTACTS = "Оставить телефонный номер для связи.";

    public static final String ADOPT_HOW_TO_MEET_PET = "Правила знакомства с животным";
    public static final String ADOPT_REQUIRED_DOCUMENTS = "Необходимые документы";
    public static final String ADOPT_RECOMENDATIONS_MENU = "Список рекомендаций:";
    public static final String ADOPT_RECOMENDATIONS_TRANSPORT = "Рекомендации по транспортировке";
    public static final String ADOPT_RECOMENDATIONS_HOUSE_YOUNG = "Обустройство дома для щенка/котенка";
    public static final String ADOPT_RECOMENDATIONS_HOUSE_ADULT = "Обустройство дома для взрослого животного";
    public static final String ADOPT_RECOMENDATIONS_HOUSE_DISABLED = "Обустройство дома для животного с ограниченными возможностями";
    public static final String ADOPT_DOG_PROTIPS = "Советы кинологов:";
    public static final String ADOPT_REASONS_WHY_MAY_BE_DENIED = "Возможные причины отказа";
    public static final String ADOPT_LEAVE_CONTACTS = "Оставить телефонный номер";


    /**
     * Строки ответов, которые предоставляет бот:
     * <p>
     * R_ - (response) приставка строки как ответ бота .
     */
    public static final String R_CAT_SHELTER_ADDRESS = "улица Сарайшык, дом 5, Астана";
    public static final String R_TYPE_IN_YOUR_NUMBER = "Введите свой телефонный номер (без текста).";
    public static final String R_DOG_SHELTER_ADDRESS = "улица Ханов Керея и Жанибека, дом 4, Астана";
    public static final String R_CAT_SHELTER_TIMETABLE = "пн-пт  10:00 - 22:00\nсб-вс 11:00 - 21:00";
    public static final String R_DOG_SHELTER_TIMETABLE = "пн-пт  9:00 - 21:00\nсб-вс 10:00 - 21:00";
    public static final String R_CAT_SHELTER_SECURITY_NUMBER = "Для оформления пропуска на машину необходимо связаться с охраной клиники по номеру:\n" +
            "+7(617)255-34-34";
    public static final String R_DOG_SHELTER_SECURITY_NUMBER = "Для оформления пропуска на машину необходимо связаться с охраной клиники по номеру:\n" +
            "+7(625)313-78-56";
    public static final String R_SAFETY_RULES =
            "   — Обувь должна быть на подошве, исключающей непроизвольное скольжение;\n" +
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

    public static final String R_HOW_TO_MEET_PET = "ПРАВИЛА ПО ЗНАКОМСТВУ С ПИТОМЦЕМ";
    public static final String R_REQUIRED_DOCUMENTS = "Необходим паспорт для заполнения документов при приёме питомца.";
    public static final String R_REASONS_WHY_MAY_BE_DENIED = "-Отсутствие регистрации или соббственного жилья.\n" +
            "-Отсутствие места для питомца в доме\n -Нахождение в чёрном списке приютов\n";
    public static final String R_RECOMENDATIONS_TRANSPORT = "Первое время рекомендуется использовать клетку для питомца. Собак рекомендуется" +
            "приучивать к клетке как к личному домику в котором она будет в безопасности и комфорте, а не наказанию.";
    public static final String R_RECOMENDATIONS_HOUSE_YOUNG = "После переезда питомцу понадобится максимальное внимание, поэтому стоит примерно неделю неотлучно находиться при нем." +
            " Потому что, попав в незнакомую обстановку, питомец может доставить кучу хлопот." +
            " Для питомца первые дни пребывания у вас дома будут самыми тяжелыми, и поведение ее будет далеко не радужным, но это проходит.";
    public static final String R_RECOMENDATIONS_HOUSE_ADULT = "Чем старше собака, тем больше вероятность того, что она уже обучена. В действительности, в приютах не так много собак, которые с самого рождения были бездомными." +
            " У многих собак были когда-то хозяева, и такие особи вполне могут помнить некоторые команды.\n" +
            "\nПоэтому, если вы приютите у себя взрослую собаку, вам не нужно будет уделять много времени на ее обучение. Ученые давно доказали, что у собак есть память," +
            " поэтому взятого на воспитание питомца не надо приучать к «туалетным ритуалам», он и так может их неплохо помнить. Уже через несколько дней вы вместе со своим новым другом сможете понять, как хорошо он помнит команды. Хотя главное, конечно, не их количество, а общение с жаждущим внимания существом и ваша забота.";
    public static final String R_RECOMENDATIONS_HOUSE_DISABLED = "РЕКОМЕНДАЦИИ ДЛЯ ПИТОМЦЕВ С ИНВАЛИДНОСТЬЮ";
    public static final String R_PROTIPS_LINK = "Ссылки на обучающие видео.";
    public static final String R_REPORT_INSTRUCTIONS = "Сделайте фото питомца, а затем напишите к фото следующее:\n" +
            "-Его рацион.\n" +
            "-Его общее самочувствие и привыкание к новому дому.\n" +
            "-Опишите его поведение.\n" +
            "Один из наших волонтёров примет фото.";

    /**
     * меню с ответами, которые предоставляет бот - используется в слушателе  {@link TelegramBotUpdatesListener}.
     */
    public static final ReplyKeyboardMarkup rebootMarkup = new ReplyKeyboardMarkup(new String[]{START});
    public static final ReplyKeyboardMarkup startMarkup = new ReplyKeyboardMarkup(new String[]{PICK_SHELTER_CATS, PICK_SHELTER_DOGS});
    public static final ReplyKeyboardMarkup mainMenuMarkup = new ReplyKeyboardMarkup(new String[]{
            MAINMENU_SHELTER_INFORMATION, MAINMENU_HOW_TO_ADOPT_A_PET, MAINMENU_SEND_PET_REPORT},
            new String[]{CALL_VOLUNTEER, START});
    public static final ReplyKeyboardMarkup infoMenuMarkup = new ReplyKeyboardMarkup(new String[]{
            INFO_SHELTER_ADDRESS, INFO_SHELTER_TIMETABLE, INFO_SAFETY_PRECAUTIONS, INFO_CAR_PASS},
            new String[]{INFO_LEAVE_CONTACTS, CALL_VOLUNTEER, START});

    public static final ReplyKeyboardMarkup adoptPetDogMenuMarkup = new ReplyKeyboardMarkup(new String[]{
            ADOPT_HOW_TO_MEET_PET, ADOPT_REQUIRED_DOCUMENTS, ADOPT_LEAVE_CONTACTS, ADOPT_RECOMENDATIONS_MENU},
            new String[]{ADOPT_DOG_PROTIPS, ADOPT_REASONS_WHY_MAY_BE_DENIED, RETURN_MAINMENU});

    public static final ReplyKeyboardMarkup adoptPetCatMenuMarkup = new ReplyKeyboardMarkup(new String[]{
            ADOPT_HOW_TO_MEET_PET, ADOPT_REQUIRED_DOCUMENTS, ADOPT_LEAVE_CONTACTS},
            new String[]{ADOPT_RECOMENDATIONS_MENU, ADOPT_REASONS_WHY_MAY_BE_DENIED, RETURN_MAINMENU});

    public static final ReplyKeyboardMarkup adoptPetRecommendationsMenuMarkup = new ReplyKeyboardMarkup(
            new String[]{ADOPT_RECOMENDATIONS_TRANSPORT},
            new String[]{ADOPT_RECOMENDATIONS_HOUSE_YOUNG},
            new String[]{ADOPT_RECOMENDATIONS_HOUSE_ADULT},
            new String[]{ADOPT_RECOMENDATIONS_HOUSE_DISABLED},
            new String[]{MAINMENU_HOW_TO_ADOPT_A_PET});

    public static final Keyboard receiveTelephoneNumber = new ReplyKeyboardMarkup(
            new KeyboardButton[]{
                    new KeyboardButton(ADOPT_LEAVE_CONTACTS).requestContact(true),
                    new KeyboardButton(RETURN_MAINMENU)
            });

    public static final InlineKeyboardMarkup protipsLinkKeyboard = new InlineKeyboardMarkup(
            new InlineKeyboardButton[]{new InlineKeyboardButton("Первичное общениям с собакой").url("https://www.youtube.com/watch?v=Bssrpyso_QA"),
                    new InlineKeyboardButton("Рекомендации кинологов").url("https://www.youtube.com/watch?v=Mv4E47QBCQo"),
            });


}
