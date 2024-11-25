package by.urban.web_project.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    // Хеширование пароля (для регистрации)
    public static String hashPasswordForRegistration(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Проверка пароля (для авторизации)
    public static boolean checkPasswordForAuthorization(String candidatePassword, String storedHash) {
        return BCrypt.checkpw(candidatePassword, storedHash);
    }
}
