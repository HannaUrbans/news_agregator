package by.urban.web_project.logic;

import jakarta.servlet.http.HttpServletRequest;

public class Check {
	public boolean checkPassword(HttpServletRequest request, String passwordForRegistration, String confirmPassword) {
		return (passwordForRegistration.equals(confirmPassword));
	}

}
