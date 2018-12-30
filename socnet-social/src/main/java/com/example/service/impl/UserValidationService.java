package com.example.service.impl;

import com.example.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Service for validation user data
 *
 * @author Roman
 * @version 1.0
 */
@Service
public class UserValidationService {

    @Value(value = "${eldest.date.year}")
    private Integer year;

    /**
     * User validation method.
     * Username must be unique and at least 6 symbols long
     * Password must be at least 6 symbols long
     * First and last names must be validated to contain only text symbols and no numbers,
     * punctuation or other special symbols. Do not validate length.
     * Date of birth must be sane. No earlier then ${eldest.date.year}. No later then now
     *
     * @param target not null, if null {@link NullPointerException} will be thrown
     * @return
     */
    public Map<String, String> validate(Object target) {
        Map<String, String> errors = new HashMap<>();
        User user = (User) target;

        if (user != null) {
            if (user.getUsername() != null) {
                if (user.getUsername().length() < 6) {
                    errors.put("username", "Username length less then 6");
                }
            } else {
                errors.put("username", "must be entered");
            }

            if (user.getPassword() != null) {
                if (user.getPassword().length() < 6) {
                    errors.put("password", "Password length less then 6");
                }

                Pattern pattern = Pattern.compile("[0-9]");

                if (!pattern.matcher(user.getPassword()).find()) {
                    errors.put("password", "Password must contains numbers");
                }

                pattern = Pattern.compile("[a-zA-Z]");

                if (!pattern.matcher(user.getPassword()).find()) {
                    errors.put("password", "Password must contains letters");
                }
            } else {
                errors.put("password", "must be entered");
            }
        } else {
            errors.put("body", "invalid body");
        }
        return errors;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
