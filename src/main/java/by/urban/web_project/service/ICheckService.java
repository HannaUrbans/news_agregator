package by.urban.web_project.service;

import by.urban.web_project.bean.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ICheckService {
    boolean checkInvalidEmail(String email) throws ServiceException;
}
