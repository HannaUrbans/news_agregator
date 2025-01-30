package by.urban.web_project.service.impl;

import by.urban.web_project.service.ICheckService;
import by.urban.web_project.service.ServiceException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckServiceImpl implements ICheckService {

    /**
     * Валидация email на бэке
     */
    @Override
    public boolean checkInvalidEmail(String email) throws ServiceException {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }
}