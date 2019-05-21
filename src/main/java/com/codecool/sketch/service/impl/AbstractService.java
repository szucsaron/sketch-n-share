package com.codecool.sketch.service.impl;

import com.codecool.sketch.model.User;
import com.codecool.sketch.service.exception.ServiceException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AbstractService {
    private User user;
    protected boolean adminMode;

    public AbstractService(User user) {
        this.user = user;
    }

    protected int fetchUserId() {
        return user.getId();
    }

    public void validateAdminMode(String adminRequestCode) {
        adminMode = adminRequestCode != null && adminRequestCode.equals('1');
    }

    protected int fetchInt(String intStr, String msgVarName) throws ServiceException {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            throw new ServiceException(msgVarName + " must be an integer");
        }
    }

    protected LocalDate fetchDate(String dateStr, String msgVarName) throws ServiceException {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new ServiceException("Date must follow the correct format: yyyy-mm-dd");
        }
    }
}
