package site.javadev.telegrambotshop.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import site.javadev.telegrambotshop.service.ShopBot;

@Component
public class BotInitializer {

    private final ShopBot shopBot;

    @Autowired
    public BotInitializer(ShopBot shopBot) {
        this.shopBot = shopBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        System.out.println("Initializing Bot");
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(shopBot);
            System.out.println("Bot initialized");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize bot", e);
        }
    }
}