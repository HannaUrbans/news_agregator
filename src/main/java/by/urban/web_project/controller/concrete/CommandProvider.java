package by.urban.web_project.controller.concrete;

import java.util.HashMap;
import java.util.Map;

import by.urban.web_project.controller.concrete.implementation.DoAuth;
import by.urban.web_project.controller.concrete.implementation.DoRegistration;
import by.urban.web_project.controller.concrete.implementation.GoToAuthentificationPage;
import by.urban.web_project.controller.concrete.implementation.GoToRegistrationPage;
import by.urban.web_project.controller.concrete.implementation.GoToIndexPage;
import by.urban.web_project.controller.concrete.implementation.GoToNewsPage;
import by.urban.web_project.controller.concrete.implementation.WriteAdmin;
import by.urban.web_project.controller.concrete.implementation.NoSuchCommand;
import by.urban.web_project.controller.concrete.implementation.ShowStub;

public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<>();

	public CommandProvider() {
		commands.put(CommandName.DO_AUTH, new DoAuth());
		commands.put(CommandName.DO_REGISTRATION, new DoRegistration());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
		commands.put(CommandName.GO_TO_AUTHENTIFICATION_PAGE, new GoToAuthentificationPage());
		commands.put(CommandName.WRITE_ADMIN, new WriteAdmin());
		commands.put(CommandName.GO_TO_INDEX_PAGE, new GoToIndexPage());
		commands.put(CommandName.GO_TO_NEWS_PAGE, new GoToNewsPage());
		commands.put(CommandName.SHOW_STUB_PAGE, new ShowStub());
		commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
	}

	public Command takeCommand(String userCommand) {
		CommandName commandName;
		Command command;
		System.out.println("Attempting to take command: " + userCommand);
		try {
			commandName = CommandName.valueOf(userCommand.toUpperCase());
			command = commands.get(commandName);
		} catch (IllegalArgumentException | NullPointerException exc) {
			command = commands.get(CommandName.NO_SUCH_COMMAND);
		}
		return command;

	}
}
