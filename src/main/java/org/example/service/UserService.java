package org.example.service;

import org.example.model.User;
import org.example.model.UserState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserService {
    List<User> users = new ArrayList<>();


    public User getByChatId(Long chatId) {
        for (User user : users)
            if (Objects.equals(user.getChatId(), chatId)) {
                return user;
            }
        return null;
    }


    public void add(User user) {
        users.add(user);
    }

    public void updateState(Long chatId, UserState userState) {
        for (User user : users)
            if (Objects.equals(user.getChatId(), chatId)) {
                user.setUserState(userState);
                return;
            }
    }
}