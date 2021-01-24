package com.fundzforus.server.form;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class LoginForm {
    private String message;
    private String email;
    private String password;
}
