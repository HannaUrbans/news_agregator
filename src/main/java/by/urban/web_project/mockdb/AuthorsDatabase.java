package by.urban.web_project.mockdb;

import by.urban.web_project.model.roles.Author;

import java.util.HashMap;
import java.util.Map;

public class AuthorsDatabase {
    // делаем имитацию БД с данными авторов, где ключ - порядковый номер
    private static Map<String, Author> authorDatabase = new HashMap<>();

    static {
        addAuthor(new Author("Эддард Старк", "ned.stark@westeros.com", "hashedPassword1",
                "Эддард Старк — лорд Винтерфелла, глава дома Старков. " +
                        "Честный и справедливый, он был одним из самых верных слуг короны. " +
                        "Он всегда ставил честь и долг выше всего, что стоило ему жизни. " +
                        "Эддард был убит в Королевской Гавани, но его наследие продолжили дети."));

        addAuthor(new Author("Тирион Ланнистер", "tyrion.lannister@westeros.com", "hashedPassword2",
                "Тирион Ланнистер, умный и ироничный карлик, известен своими политическими маневрами. " +
                        "Несмотря на свое положение, он стал важным игроком на политической арене. " +
                        "Он всегда использовал свой ум, чтобы выжить в жестоком мире. " +
                        "Тирион был правителем Семи Королевств, пока его не предали родные."));

        addAuthor(new Author("Дейенерис Таргариен", "daenerys.targaryen@westeros.com", "hashedPassword3",
                "Дейенерис Таргариен — последняя выжившая из дома Таргариенов. " +
                        "Она стала известна как Мать драконов, стремясь вернуть себе Железный трон. " +
                        "Ее путешествие было полным трудностей и потерь, но она не отступала от своей цели. " +
                        "Дейенерис сражалась не только с врагами, но и с сомнениями в себе."));

        addAuthor(new Author("Джон Сноу", "jon.snow@westeros.com", "hashedPassword4",
                "Джон Сноу — бастард Эддарда Старка, позже узнавший свою истинную природу. " +
                        "Он стал Верховным Стражем и затем королем Севера. " +
                        "Джон всегда был человеком чести и долга, что помогло ему выиграть уважение и любовь людей. " +
                        "Он сыграл ключевую роль в борьбе с угрозой из-за Стены."));

        addAuthor(new Author("Арья Старк", "arya.stark@westeros.com", "hashedPassword5",
                "Арья Старк — младшая дочь Эддарда Старка, ставшая мастером убийства. " +
                        "Она путешествовала по миру, обучаясь искусству боя и выживания. " +
                        "Арья искала справедливости для своей семьи и мстила тем, кто причинил им боль. " +
                        "Ее жестокость и решимость сделали ее одной из самых опасных женщин Вестероса."));

        addAuthor(new Author("Сандор Клиган", "hound@westeros.com", "hashedPassword6",
                "Сандор Клиган, известный как Пес, — бывший рыцарь с темной душой. " +
                        "Он был жесток и беспощаден, но также пережил множество травм, как физических, так и моральных. " +
                        "В конце концов, он стал более человечным, хотя его жестокая репутация не оставляла его. " +
                        "Сандор умер в бою с воинами короля ночи, но оставил след в истории Вестероса."));

        addAuthor(new Author("Бран Старк", "bran.stark@westeros.com", "hashedPassword7",
                "Бран Старк, младший сын Эддарда, стал трехглазым вороньем. " +
                        "Он обладал уникальными способностями к путешествиям во времени и в пространстве. " +
                        "Его знания позволили ему сыграть ключевую роль в спасении мира от Темных сил. " +
                        "Бран стал королем шести королевств, восстанавливая порядок в разрушенном мире."));

        addAuthor(new Author("Санса Старк", "sansa.stark@westeros.com", "hashedPassword8",
                "Санса Старк начала свою жизнь как наивная девушка, но пережила многие испытания. " +
                        "После трагических событий она стала умной и решительной женщиной. " +
                        "Санса использовала свою стратегию и манипуляции для того, чтобы выжить в мире интриг. " +
                        "Она стала королевой Севера, восстановив силы своего рода и народу."));
    }

    //эти методы временные, когда удалится заглушка и подключится БД, то они будут скорее всего в AuthorDAOImpl
    private static void addAuthor(Author author) {
        authorDatabase.put(author.getEmail(), author);
    }


    public static String getAuthorNameByEmail(String email) {
        for (Author author : authorDatabase.values()) {
            if (author.getEmail().equals(email)) {
                return author.getName();
            }
        }
        return null;
    }

    public static Author getAuthorByEmail(String email) {
        for (Author author : authorDatabase.values()) {
            if (author.getEmail().equals(email)) {
                return author;
            }
        }
        return null;
    }

    public static Author getAuthorByEmailAndPassword(String email, String password) {
        for (Author author : authorDatabase.values()) {
            if (author.getEmail().equals(email) && author.getPassword().equals(password)) {
                return author;
            }
        }
        return null;
    }

    public static boolean isEmailExistsInAuthorsDB(String email) {
        return authorDatabase.containsKey(email);
    }

}
