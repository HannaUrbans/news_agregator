package by.urban.web_project.controller.utils;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.ProfileDataField;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ICheckService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

public class UpdateUtil {
    private static final ICheckService checkService;
    private static final IChangeProfileService changeProfileService;

    static {
        try {
            checkService = ServiceFactory.getInstance().getCheckService();
            changeProfileService = ServiceFactory.getInstance().getChangeProfileService();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для проверки, было ли поле профиля обновлено пользователем,
     * для обновления (в случае необходимости) поля в базе данных через сервисный слой
     *
     * @param request          - объект HttpServletRequest, представляющий текущий запрос от клиента
     * @param auth             - объект Auth, представляющий зарегистрированного пользователя, для которого будет обновляться поле профиля
     * @param inputValue       - строка, представляющая новое значение поля профиля, введенное пользователем в форме
     * @param profileDataField - поле профиля, которое будет обновляться
     * @return true, если поле профиля было успешно обновлено в базе данных, и false в противном случае
     */
    public static boolean isProfileFieldUpdated(HttpServletRequest request, Auth auth, String inputValue, ProfileDataField profileDataField) {

        if (isInputNotEmpty(request, inputValue, profileDataField)) {
            return UpdateUtil.updateProfileField(auth, inputValue, profileDataField, changeProfileService);
        }
        return false;
    }

    /**
     * Метод для передачи обновленного значения поля в параметры сессии и для отображения сообщений о результате проведения операции по обновлению
     *
     * @param request          - объект HttpServletRequest, представляющий текущий запрос от клиента
     * @param isProfileUpdated - результат выполнения метода isProfileFieldUpdated
     * @param auth             - - объект Auth, представляющий зарегистрированного пользователя, для которого будет обновляться поле профиля
     * @param profileDataField - поле профиля, которое будет обновляться
     */
    public static void updateSessionAndDisplayMessage(HttpServletRequest request, boolean isProfileUpdated, Auth auth, ProfileDataField profileDataField) {
        if (isProfileUpdated) {
            try {
                //результат выполнения метода для выгрузки обновленного значения из БД
                String updatedFieldValueFromDb = changeProfileService.getFieldData(auth.getId(), profileDataField);
                //метод передает в параметры сессии обновленное значение
                updateNotificationMessage(request, profileDataField, updatedFieldValueFromDb);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            addChangeSuccessMessage(request, profileDataField);
        } else {
            addChangeErrorMessage(request, profileDataField, "Не было внесено изменений в поле ");
        }
    }

    /**
     * Метод для проверки, было ли поле профиля обновлено пользователем,
     * корректно ли пользователь ввёл старое значение поля (которое хранится в БД),
     * для обновления (в случае необходимости) поля в базе данных через сервисный слой.
     * Метод используется для полей, которые не хранятся в объекте Auth по соображениям безопасности (почта, пароль)
     *
     * @param request          - объект HttpServletRequest, представляющий текущий запрос от клиента
     * @param auth             - объект Auth, представляющий зарегистрированного пользователя, для которого будет обновляться поле профиля
     * @param oldInputValue    - строка, введенная пользователем в форме (если значение этого поля не соответствует значению из БД, то пользователь не имеет право обновлять значение поля)
     * @param newInputValue    - строка, представляющая новое значение поля профиля, введенное пользователем в форме
     * @param profileDataField - поле профиля, которое будет обновляться
     * @return true, если поле профиля было успешно обновлено в базе данных, и false в противном случае
     */
    public static boolean isProfileFieldCheckedAndUpdated(HttpServletRequest request, Auth auth, String oldInputValue, String newInputValue, ProfileDataField profileDataField) {
        try {
            String valueStoredInDatabase = changeProfileService.getFieldData(auth.getId(), profileDataField);
            if (areFieldsEqual(request, valueStoredInDatabase, oldInputValue, profileDataField)) {
                return isProfileFieldUpdated(request, auth, newInputValue, profileDataField);
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Метод, который задает новое значение определенному полю в БД через слои сервис-дао
     *
     * @param auth       - зарегистрированный пользователь, который будет искаться в БД по id
     * @param inputValue - новое значение поля, которое вводится в форме
     * @param field      - енам с видами полей
     * @param updateTool - метод слоя сервис, который служит прокладкой между контроллером и БД
     * @return true, в случае успешного обновления поля
     */
    private static boolean updateProfileField(Auth auth, String inputValue, ProfileDataField field, IChangeProfileService updateTool) {
        try {
            switch (field) {
                case NAME:
                    updateTool.updateName(auth.getId(), inputValue);
                    auth.setName(inputValue);
                    break;
                case EMAIL:
                    updateTool.updateEmail(auth.getId(), inputValue);
                    break;
                case PASSWORD:
                    updateTool.updatePassword(auth.getId(), inputValue);
                    break;
                case BIO:
                    updateTool.updateBio(auth.getId(), inputValue);
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

    /**
     * Метод для проверки, было ли введено что-то в поле, а также равно ли это введённое значение соответствующим данным из личного кабинета
     *
     * @param request          - объект HttpServletRequest, представляющий текущий запрос от клиента
     * @param storedValue      - строковое значение, хранит данные поля, полученные из БД
     * @param inputValue       - строковое значение, которое пользователь ввёл в форме
     * @param profileDataField - поле профиля, которое будет обновляться
     * @return true в случае равенства полей, false, если одно из полей/оба поля пустые, также если поля не равны
     */
    private static boolean areFieldsEqual(HttpServletRequest request, String storedValue, String inputValue, ProfileDataField profileDataField) {
        if (!isInputNotEmpty(request, inputValue, profileDataField)) {
            return false;
        }

        try {
            if (!checkService.checkFieldsEquality(storedValue, inputValue)) {
                addChangeErrorMessage(request, profileDataField, "Введённое вами значение не совпадает с данными из личного кабинета. Перепроверьте поле ");
                return false;
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    /**
     * Метод для проверки валидности введённых данных в поле
     *
     * @param request          - объект HttpServletRequest, представляющий текущий запрос от клиента
     * @param inputValue       - строковое значение, которое пользователь ввёл в форме
     * @param profileDataField - поле профиля, которое будет обновляться
     * @return true, если в поле присутствуют данные
     */
    private static boolean isInputNotEmpty(HttpServletRequest request, String inputValue, ProfileDataField profileDataField) {
        return inputValue != null && !inputValue.trim().isEmpty();
    }

    private static void addChangeSuccessMessage(HttpServletRequest request, ProfileDataField profileDataField) {
        request.getSession().setAttribute("change" + engFieldName(profileDataField) + "Success", "Поле " + rusFieldName(profileDataField) + " успешно обновлено!");
    }

    private static void addChangeErrorMessage(HttpServletRequest request, ProfileDataField profileDataField, String message) {
        request.getSession().setAttribute("change" + engFieldName(profileDataField) + "Error", message+rusFieldName(profileDataField));
    }

    private static void updateNotificationMessage(HttpServletRequest request, ProfileDataField profileDataField, String value) {
        request.getSession().setAttribute("updated" + engFieldName(profileDataField), value);
    }

    private static String engFieldName(ProfileDataField profileDataField) {
        char[] array = profileDataField.name().toCharArray();
        StringBuilder sb = new StringBuilder();
        sb.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            sb.append(Character.toLowerCase(array[i]));
        }
        return sb.toString();
    }

    private static String rusFieldName(ProfileDataField profileDataField) {
        return profileDataField.getDescription();
    }

}