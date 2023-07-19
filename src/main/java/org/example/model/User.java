package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private Long chatId;
    private String firstname;
    private String lastname;
    private String username;
    private String phoneNumber;
    private UserState userState;
}
