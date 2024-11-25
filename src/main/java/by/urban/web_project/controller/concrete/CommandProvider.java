package by.urban.web_project.controller.concrete;

import by.urban.web_project.controller.concrete.impl.*;
import by.urban.web_project.dao.DAOException;
import by.urban.web_project.service.ServiceException;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() throws ServiceException, DAOException {
        commands.put(CommandName.DO_AUTH, new DoAuth());
        commands.put(CommandName.DO_REGISTRATION, new DoRegistration());
        commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
        commands.put(CommandName.GO_TO_AUTHENTIFICATION_PAGE, new GoToAuthentificationPage());
        commands.put(CommandName.WRITE_ADMIN, new WriteAdmin());
        commands.put(CommandName.GO_TO_INDEX_PAGE, new GoToIndexPage());
        commands.put(CommandName.GO_TO_NEWS_PAGE, new GoToNewsPage());
        commands.put(CommandName.GO_TO_USER_ACCOUNT_PAGE, new GoToUserAccountPage());
        commands.put(CommandName.GO_TO_AUTHOR_ACCOUNT_PAGE, new GoToAuthorAccountPage());
        commands.put(CommandName.GO_TO_CHANGE_FORM, new GoToChangeForm());
        commands.put(CommandName.GO_TO_ADD_NEWS_FORM_PAGE, new GoToAddNewsFormPage());
        commands.put(CommandName.ADD_NEWS, new AddNews());
        commands.put(CommandName.SHOW_ALL_NEWS, new ShowAllNews());
        commands.put(CommandName.CHANGE_NAME, new ChangeName());
        commands.put(CommandName.CHANGE_PASSWORD, new ChangePassword());
        commands.put(CommandName.CHANGE_BIO, new ChangeBio());
        commands.put(CommandName.CHANGE_NEWS_ARTICLE, new ChangeNewsArticle());
        commands.put(CommandName.SHOW_STUB_PAGE, new ShowStub());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        commands.put(CommandName.LOGOUT, new LogOut());
    }

    public Command takeCommand(String userCommand) {
        CommandName commandName;
        Command command;
        //System.out.println("Attempting to take command: " + userCommand);
        try {
            commandName = CommandName.valueOf(userCommand.toUpperCase());
            command = commands.get(commandName);
        } catch (IllegalArgumentException | NullPointerException exc) {
            command = commands.get(CommandName.NO_SUCH_COMMAND);
        }
        return command;

    }
}
