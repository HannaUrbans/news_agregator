package by.urban.web_project.bean;

import java.util.HashMap;
import java.util.Map;

public class UsersDatabase {
	// делаем имитацию БД с данными пользователей, где ключ - порядковый номер
	private static Map<Integer, User> userDatabase = new HashMap<>();
	private static int id = 1;

	static {
		if (userDatabase.isEmpty()) {
		addUser(new User("Фродо Бэггинс", "frodo@yandex.ru", "hashedPassword1"));
		addUser(new User("Гэндальф", "gend@mail.com", "hashedPassword2"));
		addUser(new User("Леголас", "legolas@yandex.ru", "hashedPassword3"));
		addUser(new User("Гимли", "dwarf@gmail.com", "hashedPassword4"));
		addUser(new User("Арагорн", "aragorn@yandex.ru", "hashedPassword5"));
		addUser(new User("Голлум", "pureevil@gmail.com", "hashedPassword6"));
		addUser(new User("Боромир", "boromirthebest@yahoo.com", "hashedPassword7"));
		addUser(new User("Анна", "admin@gmail.com", "hashedPassword8"));
		}
	}

	private static void addUser(User user) {
		userDatabase.put(id++, user);
	}

	public static User getUserById(int id) {
		return userDatabase.get(id);
	}

	public static String getUserNameByEmail(String email) {
		for (User user : userDatabase.values()) {
			if (user.getEmail().equals(email)) {
				return user.getName();
			}
		}
		return null;
	}
	
	public static User getUserByEmail(String email) {
		for (User user : userDatabase.values()) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}
	
	public static User getUserByEmailAndPassword(String email, String password) {
		for (User user : userDatabase.values()) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}
	
}
