package by.urban.web_project.controller.utils;

import by.urban.web_project.model.UserRole;

public class UrlFormatterUtil {
    public static String formatRedirectUrl(UserRole role){
        return "GO_TO_"+role+"_ACCOUNT_PAGE";
    }
}
