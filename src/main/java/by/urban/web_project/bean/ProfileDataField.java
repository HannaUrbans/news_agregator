package by.urban.web_project.bean;

public enum ProfileDataField {
    NAME("имя"),
    EMAIL("e-mail"),
    PASSWORD("пароль"),
    BIO("биография");

    private final String description;

    ProfileDataField(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
