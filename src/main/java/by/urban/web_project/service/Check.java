package by.urban.web_project.service;

import by.urban.web_project.mockdb.AuthorRegistrationKeysDatabase;
import jakarta.servlet.http.HttpServletRequest;

public class Check {
	public boolean checkPassword(HttpServletRequest request, String passwordForRegistration, String confirmPassword) {
		return (passwordForRegistration.equals(confirmPassword));
	}

	public boolean checkAuthorKey(HttpServletRequest request, String inputAuthorKey) {
		//пока не подключена БД
		return AuthorRegistrationKeysDatabase.isValidKey(inputAuthorKey);
	}
}
