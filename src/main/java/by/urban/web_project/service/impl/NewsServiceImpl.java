package by.urban.web_project.service.impl;

import by.urban.web_project.model.News;
import by.urban.web_project.mockdb.NewsDatabase;
import by.urban.web_project.model.NewsImportance;
import by.urban.web_project.service.INewsService;
import by.urban.web_project.service.ServiceException;

import java.util.ArrayList;
import java.util.List;

//Logic Stub For Adding News To Main Page
public class NewsServiceImpl implements INewsService {
	public News getBreakingNews() throws ServiceException{
		// вытягиваем новости из класса-имитации базы данных
		List<News> news = NewsDatabase.getAllNews();
		for (News item : news) {
			if (item.getImportance() == NewsImportance.BREAKING) {
				return item; // вернуть первую найденную срочную новость
			}
		}
		return null;
	}

	public News getTopNews() throws ServiceException{
		List<News> news = NewsDatabase.getAllNews();
		for (News item : news) {
			if (item.getImportance() == NewsImportance.TOP) {
				return item; // вернуть первую найденную топовую новость
			}
		}
		return null;
	}

	public List<News> getRegularNews() throws ServiceException {
		List<News> news = NewsDatabase.getAllNews();
		List<News> regularNewsList = new ArrayList<>();
		for (News item : news) {
			if (item.getImportance() == NewsImportance.REGULAR) {
				regularNewsList.add(item);
			}
		}
		return regularNewsList; // в случае неудачи вернется пустой список, отдельно не надо прописывать return
								// null;
	}
}
