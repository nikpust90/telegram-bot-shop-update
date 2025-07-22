package site.javadev.telegrambotshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import site.javadev.telegrambotshop.model.Product;
import site.javadev.telegrambotshop.util.MessageUtil;

import java.util.List;

@Component
public class ShopBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String botToken;

    private final String START_COMMAND = "/start";
    private String shopName;
    private final ShopService shopService;

    public ShopBot(@Value("${bot.token}") String botToken, ShopService shopService) {
        super(botToken);
        this.botToken = botToken;
        this.shopService = shopService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleTextMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }
    @Override
    public String getBotUsername() {
        return name;
    }

    // Обработка текстового сообщения
    private void handleTextMessage(Update update) {
        Message updateMessage = update.getMessage();
        if (updateMessage.hasText()) {
            String messageText = updateMessage.getText();
            Long chatId = updateMessage.getChatId();

            if (START_COMMAND.equals(messageText)) {
                handleStartCommand(chatId);
            } else if (shopName != null) {
                handleProductSearch(chatId, messageText);
            }
        }
    }

    // Обработка команды /start
    private void handleStartCommand(Long chatId) {
        List<String> shops = shopService.getShopNames();
        SendMessage sendMessage = MessageUtil.sendMessageWithButtons(chatId, "Выберите магазин:", shops);
        executeSendMessage(sendMessage); // отправляем сообщение с кнопками
    }

    // Поиск товара по имени
    private void handleProductSearch(Long chatId, String productName) {
        Product product = shopService.getProductByName(productName);
        String message = (product != null)
                ? product.toString()
                : String.format("Товар с именем '%s' не найден.", productName);

        sendMessage(chatId, message);
    }

    // Обработка нажатия на кнопку
    private void handleCallbackQuery(Update update) {
        shopName = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        StringBuilder message = new StringBuilder();
        message.append("Вы выбрали магазин: ").append(shopName).append("\nВведите наименование товара:");
        sendMessage(chatId, message.toString());
    }

    private void sendMessage(Long id, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText(message);
        executeSendMessage(sendMessage);
    }

    private void executeSendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
