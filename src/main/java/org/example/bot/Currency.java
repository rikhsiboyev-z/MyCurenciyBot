package org.example.bot;

import lombok.SneakyThrows;
import org.example.model.User;
import org.example.model.UserState;
import org.example.service.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.model.UserState.MENU;
import static org.example.model.UserState.REGISTER;

public class Currency extends TelegramLongPollingBot {


    BotService botService = new BotService();
    UserService userService = new UserService();

    @Override
    public String getBotToken() {
        return "6355090143:AAFQ-L0w4VTyrbE_XbnEtNy-p2rWhXUeQZw";
    }


    @Override
    public String getBotUsername() {
        return "http://t.me/zaxaNew_bot";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        Long chatId = message.getChatId();

        User currentUser = userService.getByChatId(chatId);
        UserState userState = UserState.START;

        if (currentUser != null) {
            userState = currentUser.getUserState();
            switch (userState) {
                case REGISTER -> {
                    switch (text) {
                        case "UZS\uD83C\uDDFA\uD83C\uDDFF -> USD\uD83C\uDDFA\uD83C\uDDF8" -> {

                            userService.updateState(chatId, UserState.UZS_USD);
                            userState = UserState.UZS_USD;
                        }
                        case "UZS\uD83C\uDDFA\uD83C\uDDFF -> EUR\uD83C\uDDEA\uD83C\uDDFA" -> {
                            userService.updateState(chatId, UserState.UZS_EUR);
                            userState = UserState.UZS_EUR;

                        }
                        case "USD\uD83C\uDDFA\uD83C\uDDF8 -> UZS\uD83C\uDDFA\uD83C\uDDFF" -> {
                            userService.updateState(chatId, UserState.USD_UZS);
                            userState = UserState.USD_UZS;

                        }
                        case "EUR\uD83C\uDDEA\uD83C\uDDFA -> UZS\uD83C\uDDFA\uD83C\uDDFF" -> {
                            userService.updateState(chatId, UserState.EUR_UZS);
                            userState = UserState.EUR_UZS;
                        }
                    }
                }
                case EUR_UZS, USD_UZS, UZS_EUR, UZS_USD -> {
                    try {
                        Double amount = Double.valueOf(text);
                        execute(botService.convertCurrency(chatId.toString(), userState, amount));
                        userService.updateState(chatId, REGISTER);



                    } catch (NumberFormatException e) {
                        execute(new SendMessage(chatId.toString(), "please enter valid number"));

                    }

                    return;
                }
            }

        } else if (message.hasContact()) {
            Contact contact = message.getContact();

            User user = User.builder()
                    .chatId(chatId)
                    .firstname(contact.getFirstName())
                    .lastname(contact.getLastName())
                    .phoneNumber(contact.getPhoneNumber())
                    .userState(REGISTER)
                    .build();
            userService.add(user);
            userState = REGISTER;
        }


        switch (userState) {

            case START -> {
                execute(botService.register(chatId.toString()));
            }
            case REGISTER -> {
                execute(botService.menu(chatId.toString()));
            }
            case EUR_UZS, USD_UZS, UZS_EUR, UZS_USD -> {
                execute(botService.enterAmount(chatId.toString()));
            }
        }


    }
}
