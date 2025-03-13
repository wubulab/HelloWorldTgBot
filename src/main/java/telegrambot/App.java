package telegrambot;

import com.sun.tools.javac.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegrambot.bot.HelloWorldBot;
import telegrambot.config.BotConfig;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {


        BotConfig botConfig = new BotConfig("YOUR_BOT_TOKEN", "YOUR_BOT_USERNAME");


        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new HelloWorldBot(botConfig));
            logger.info("Bot started successfully.");
        } catch (Exception e) {
            logger.error("Error starting bot: {}", e.getMessage());
        }
    }
}