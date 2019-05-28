package com.codecool.sketch.service.impl;

import com.codecool.sketch.model.Role;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.exception.ServiceException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ImplAbstractService {
    private User user;
    protected boolean adminMode = false;

    public ImplAbstractService(User user) {
        this.user = user;
    }

    protected int fetchUserId() throws ServiceException{
        if (user == null) {
            throw new ServiceException("Invalid user login!");
        }
        return user.getId();
    }

    public void validateAdminMode(String requestAdmin) {
        adminMode = requestAdmin != null && requestAdmin.equals('1') && user.getRole().equals(Role.ADMIN);
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
