package by.urban.web_project.mockdb;


import java.util.HashSet;
import java.util.Set;


public class AuthorRegistrationKeysDatabase {
    // делаем имитацию БД с ключами, которые выдаются авторам для дальнейшей регистрации на сайте не как простой пользователь
    private static final Set<String> authorRegistrationKeysDatabase = new HashSet<>();

    static {
        addAuthorKey("hashedKey1");
        addAuthorKey("hashedKey2");
        addAuthorKey("hashedKey3");
        addAuthorKey("hashedKey4");
        addAuthorKey("hashedKey5");
        addAuthorKey("hashedKey6");
        addAuthorKey("hashedKey7");
        addAuthorKey("hashedKey8");
    }

    //эти методы временные, когда удалится заглушка и подключится БД, то они будут скорее всего в AuthorDAOImpl
    private static void addAuthorKey(String authorKey) {
        authorRegistrationKeysDatabase.add(authorKey);
    }

    public static boolean isValidKey(String authorKey) {
        return authorRegistrationKeysDatabase.contains(authorKey);
    }
}

