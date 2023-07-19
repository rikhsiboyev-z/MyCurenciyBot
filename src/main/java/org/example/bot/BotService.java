package org.example.bot;

import org.example.model.UserState;
import org.example.service.CurrencyService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class BotService {

    CurrencyService currencyService = new CurrencyService();

    public SendMessage register(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Please share your number ");
        sendMessage.setReplyMarkup(shareContact());
        return sendMessage;
    }

    public SendMessage enterAmount(String chatId) {

        SendMessage sendMessage = new SendMessage(chatId, "enter Amount");
        sendMessage.setReplyMarkup(replyKeyboardRemove());
        return sendMessage;

    }

    public SendMessage convertCurrency(String chatId, UserState state, Double amount) {

        String code = "";
        boolean toUZS = true;
        switch (state) {
            case EUR_UZS -> {
                code = "EUR";
                toUZS = true;
            }
            case UZS_EUR -> {

                code = "EUR";
                toUZS = false;
            }
            case UZS_USD -> {
                code = "USD";
                toUZS = false;
            }
            case USD_UZS -> {
                code = "USD";
                toUZS = true;
            }
        }
        String convert = currencyService.convert(code, toUZS, amount);
        SendMessage sendMessage = new SendMessage(chatId, convert);
        sendMessage.setReplyMarkup(menu());
        return sendMessage;

    }


    public SendMessage menu(String chatId) {

        SendMessage sendMessage = new SendMessage(chatId, "menu");
        sendMessage.setReplyMarkup(menu());
        return sendMessage;
    }

    private ReplyKeyboardMarkup shareContact() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        KeyboardRow keyboardButtons = new KeyboardRow();

        KeyboardButton keyboardButton = new KeyboardButton("share contact");
        keyboardButton.setRequestContact(true);

        keyboardButtons.add(keyboardButton);

        replyKeyboardMarkup.setKeyboard(List.of(keyboardButtons));
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup menu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow buttons = new KeyboardRow();
        buttons.add("UZS\uD83C\uDDFA\uD83C\uDDFF -> USD\uD83C\uDDFA\uD83C\uDDF8");
        buttons.add("UZS\uD83C\uDDFA\uD83C\uDDFF -> EUR\uD83C\uDDEA\uD83C\uDDFA");
        rows.add(buttons);


        buttons = new KeyboardRow();
        buttons.add("USD\uD83C\uDDFA\uD83C\uDDF8 -> UZS\uD83C\uDDFA\uD83C\uDDFF");
        buttons.add("EUR\uD83C\uDDEA\uD83C\uDDFA -> UZS\uD83C\uDDFA\uD83C\uDDFF");

        rows.add(buttons);


        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardRemove replyKeyboardRemove() {
        return new ReplyKeyboardRemove(true);
    }

}
