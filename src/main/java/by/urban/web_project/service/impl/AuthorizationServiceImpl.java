package by.urban.web_project.service.impl;

import by.urban.web_project.mockdb.AuthorsDatabase;
import by.urban.web_project.mockdb.UsersDatabase;
import by.urban.web_project.model.roles.Author;
import by.urban.web_project.model.roles.User;
import by.urban.web_project.service.IAuthorizationService;

//Logic Stub For Authorization
public class AuthorizationServiceImpl implements IAuthorizationService {

	public Object checkAuth(String email, String password) {

		Author authorizedAuthor = AuthorsDatabase.getAuthorByEmailAndPassword(email, password);
		if (authorizedAuthor != null) {
			return authorizedAuthor;
		}

		User authorizedUser = UsersDatabase.getUserByEmailAndPassword(email, password);
		if (authorizedUser != null) {
			return authorizedUser;
		}

		return null;
	}
}