package by.urban.web_project.utils;

import by.urban.web_project.model.User;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;

public class UpdateUtils {
    public static boolean updateProfileField(User user, String newValue, ProfileFieldToChange field, IChangeProfileService updateTool) {
        try {
            switch (field) {
                case NAME:
                    updateTool.updateName(user.getId(), newValue);
                    user.setName(newValue);
                    break;
                case PASSWORD:
                    updateTool.updatePassword(user.getId(), newValue);
                    break;
                case BIO:
                    updateTool.updateBio(user.getId(), newValue);
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