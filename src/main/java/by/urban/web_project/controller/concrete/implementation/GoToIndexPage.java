package by.urban.web_project.controller.concrete.implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.bean.News;
import by.urban.web_project.bean.NewsDatabase;

public class GoToIndexPage implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// вытягиваем новости из класса-имитации базы данных
		List<News> news = NewsDatabase.getAllNews();

		News breakingNews = null;
		News topNews = null;
		List<News> regularNewsList = new ArrayList<>();

		for (News item : news) {
			switch (item.getImportance()) {
			case BREAKING:
				breakingNews = item;
				break;
			case TOP:
				topNews = item;
				break;
			case REGULAR:
				regularNewsList.add(item);
				break;
			}
		}

		if (breakingNews != null) {
			request.setAttribute("breakingNews", breakingNews); // выводим последнюю добавленную срочную новость
		}
		if (topNews != null) {
			request.setAttribute("topNews", topNews); // выводим последнюю добавленную топовую новость
		}

		// передаем сообщения о результате отправления формы,
		// сессия нужна, чтобы сообщение не потерялось после редиректа
		String successMessage = (String) request.getSession().getAttribute("successMessage");
		String errorMessage = (String) request.getSession().getAttribute("errorMessage");

		if (successMessage != null) {
			request.setAttribute("successMessage", successMessage);
			request.getSession().removeAttribute("successMessage"); // Удаляем сообщение после отображения, чтобы оно в
																	// реквесте не было передано на следующую страницу
		}
		if (errorMessage != null) {
			request.setAttribute("errorMessage", errorMessage);
			request.getSession().removeAttribute("errorMessage"); // Удаляем сообщение после отображения, чтобы оно в
																	// реквесте не было передано на следующую страницу
		}

		request.setAttribute("regularNews", regularNewsList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main_index.jsp");
		dispatcher.forward(request, response);
	}
}
