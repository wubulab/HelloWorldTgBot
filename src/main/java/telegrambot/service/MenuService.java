package telegrambot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuService {

    // Константи для callback data
    private static final String MENU1_BUTTON1 = "menu1_button1";
    private static final String MENU1_BUTTON2 = "menu1_button2";
    private static final String MENU1_NEXT    = "menu1_next";
    private static final String MENU2_BUTTON1 = "menu2_button1";
    private static final String MENU2_BUTTON2 = "menu2_button2";
    private static final String MENU2_BACK    = "menu2_back";

    public InlineKeyboardMarkup getMainMenu() {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        rows.add(createRow(
                createButton("Кнопка 1", MENU1_BUTTON1),
                createButton("Кнопка 2", MENU1_BUTTON2)
        ));

        rows.add(createRow(
                createButton("Далі", MENU1_NEXT)
        ));

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(rows);
        return keyboard;
    }

    public InlineKeyboardMarkup getMenu2() {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        rows.add(createRow(
                createButton("Кнопка 1", MENU2_BUTTON1),
                createButton("Кнопка 2", MENU2_BUTTON2)
        ));

        // Рядок з кнопкою "Назад"
        rows.add(createRow(
                createButton("Назад", MENU2_BACK)
        ));

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(rows);
        return keyboard;
    }

    // Допоміжний метод для створення кнопки
    private InlineKeyboardButton createButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }

    // Допоміжний метод для створення рядка кнопок
    private List<InlineKeyboardButton> createRow(InlineKeyboardButton... buttons) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        Collections.addAll(row, buttons);
        return row;
    }
}
