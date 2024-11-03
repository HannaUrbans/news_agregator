package by.urban.web_project.logic;

import by.urban.web_project.bean.UsersDatabase;
import by.urban.web_project.bean.User;

public class LogicStubForAuthorization {

	public boolean checkAuth(String email, String password) {

		User authorizedUser = UsersDatabase.getUserByEmailAndPassword(email, password);

		return authorizedUser != null;

	}
}