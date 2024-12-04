package by.urban.web_project.controller.utils;

public class UrlFormatterUtil {
    public static String formatRedirectUrl(String role){
        return "GO_TO_"+role.toUpperCase()+"_ACCOUNT_PAGE";
    }
}
