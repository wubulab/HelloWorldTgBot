package telegrambot.bot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegrambot.config.BotConfig;
import telegrambot.service.MenuService;

import java.util.Map;

public class HelloWorldBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldBot.class);
    private final BotConfig botConfig;
    private final MenuService menuService = new MenuService();

    // Java 17 record для зберігання відповіді (так юзаю 17 джаву вже давно, вирішив використати її фічу)
    private record BotResponse(String text, InlineKeyboardMarkup keyboard) {}

    private final Map<String, BotResponse> callbackResponses;

    public HelloWorldBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        callbackResponses = Map.of(
                "menu1_button1", new BotResponse("Кнопка 1", null),
                "menu1_button2", new BotResponse("Кнопка 2", null),
                "menu1_next",    new BotResponse("Меню 2:", menuService.getMenu2()),
                "menu2_button1", new BotResponse("Кнопка 1", null),
                "menu2_button2", new BotResponse("Кнопка 2", null),
                "menu2_back",    new BotResponse("Головне меню:", menuService.getMainMenu())
        );
    }

    @Override
    public void onUpdateReceived(Update update) {
        logger.info("Received update: {}", update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleIncomingMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }

    private void handleIncomingMessage(Update update) {
        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        if ("/start".equals(text)) {
            sendMessage(chatId, "Вітаю! Ось головне меню:", menuService.getMainMenu());
        } else {
            sendMessage(chatId, "Невідома команда!");
        }
    }

    private void handleCallbackQuery(Update update) {
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        BotResponse response = callbackResponses.get(data);
        if (response != null) {
            sendMessage(chatId, response.text(), response.keyboard());
        } else {
            sendMessage(chatId, "Невідома команда!");
        }
    }

    private void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, null);
    }

    private void sendMessage(Long chatId, String text, InlineKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        if (keyboard != null) {
            message.setReplyMarkup(keyboard);
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Error sending message", e);
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.botUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.botToken();
    }
}
