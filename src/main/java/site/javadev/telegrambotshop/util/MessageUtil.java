package site.javadev.telegrambotshop.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {

    public static SendMessage sendMessageWithButtons(Long chatId, String messageText, List<String> buttonLabels) {
        // Создаем объект SendMessage для отправки сообщения
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(messageText);

        // Создаем объект InlineKeyboardMarkup для добавления кнопок
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        // Для каждого текста кнопки создаем отдельную кнопку
        for (String label : buttonLabels) {
            // Создаем список для строк кнопок
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(label);
            button.setCallbackData("action:" + label); // Устанавливаем callback data с префиксом action:
            rowInline.add(button);
            rowsInline.add(rowInline);
        }

        // Устанавливаем клавиатуру в сообщение
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        return message;
    }

    // Метод для добавления кнопок на несколько строк (если кнопок больше одной строки)
    public static SendMessage sendMessageWithMultipleRows(Long chatId, String messageText, List<List<String>> buttonLabels) {
        // Создаем объект SendMessage для отправки сообщения
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(messageText);

        // Создаем объект InlineKeyboardMarkup для добавления кнопок
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        // Для каждой строки кнопок создаем соответствующие кнопки
        for (List<String> rowButtons : buttonLabels) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            for (String label : rowButtons) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(label);
                button.setCallbackData("action:" + label); // Устанавливаем callback data с префиксом action:
                rowInline.add(button);
            }
            rowsInline.add(rowInline);
        }

        // Устанавливаем клавиатуру в сообщение
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        return message;
    }
}
