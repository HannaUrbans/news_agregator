package by.urban.web_project.controller.utils;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.User;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.utils.ProfileFieldToChange;

public class UpdateUtil {

    public static boolean updateProfileField(Auth auth, String newValue, ProfileFieldToChange field, IChangeProfileService updateTool) {
        try {
            switch (field) {
                case NAME:
                    updateTool.updateName(auth.getId(), newValue);
                    auth.setName(newValue);
                    break;
                case EMAIL:
                    updateTool.updateEmail(auth.getId(), newValue);
                    break;
                case PASSWORD:
                    updateTool.updatePassword(auth.getId(), newValue);
                    break;
                case BIO:
                    updateTool.updateBio(auth.getId(), newValue);
                    break;
                default:
                    return false;
            }
            return true;
        } catch (ServiceException e) {
            e.printStackTrace();
            return false;
        }
    }
}