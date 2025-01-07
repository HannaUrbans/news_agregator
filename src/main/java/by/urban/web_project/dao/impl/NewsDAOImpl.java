package by.urban.web_project.dao.impl;

import by.urban.web_project.bean.News;
import by.urban.web_project.bean.NewsImportance;
import by.urban.web_project.bean.User;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.INewsDAO;
import by.urban.web_project.dbmanager.ConnectionPool;
import by.urban.web_project.dbmanager.ConnectionPoolException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsDAOImpl implements INewsDAO {

    ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public int addNews(News news) throws DAOException {
        //автора добавляем отдельно
        String query = "INSERT INTO news_management.news (importance, title, image, brief, content, publish_date, categories_id) VALUES (?, ?, ?, ?, ?, ?, (SELECT id FROM news_management.categories WHERE title = ? LIMIT 1))";
        int id;
        LocalDateTime publishDate;

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement1.setString(1, news.getImportance().name());
            preparedStatement1.setString(2, news.getTitle());
            preparedStatement1.setString(3, news.getImageUrl());
            preparedStatement1.setString(4, news.getBrief());
            preparedStatement1.setString(5, news.getContent());
            publishDate = LocalDateTime.now();
            preparedStatement1.setTimestamp(6, Timestamp.valueOf(publishDate));
            preparedStatement1.setString(7, news.getCategory());

            int affectedRows = preparedStatement1.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet resultSet1 = preparedStatement1.getGeneratedKeys()) {
                    if (resultSet1.next()) {
                        id = resultSet1.getInt(1);
                    } else {
                        throw new SQLException("Не удалось получить ID новостей");
                    }
                }
            } else {
                throw new SQLException("Не добавлены строки в таблицу news");
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return id;
    }

    public boolean addPrimaryAuthor(int newsId, int authId) throws DAOException {
        String query = "INSERT INTO news_management.news_authors (news_id, users_id) VALUES ((SELECT id FROM news_management.news WHERE id = ? LIMIT 1), (SELECT id FROM news_management.users WHERE id = ? ))";
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement2 = connection.prepareStatement(query)) {
            // хоть у нас возможно авторство нескольких, но при добавлении статьи за новостью числится тот, от чьего id зашли в кабинет и отправили новость
            preparedStatement2.setInt(1, newsId);
            preparedStatement2.setInt(2, authId);
            int affectedRowsForAuthor = preparedStatement2.executeUpdate();
            return affectedRowsForAuthor > 0;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    public boolean addCoauthor(int coauthorId, int newsId) throws DAOException {
        //здесь мы уже не update запрос делаем, а именно добавляем еще одного автора
        String query = "INSERT INTO news_management.news_authors (news_id, users_id) VALUES (?, ?)";
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newsId);
            preparedStatement.setInt(2, coauthorId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Не удалось добавить соавтора в новость " + newsId, e);
        }
    }

    public boolean deleteNews(int newsId) throws DAOException {
        // Сначала удаляем связанные записи в news_authors
        String deleteAuthorsQuery = "DELETE FROM news_management.news_authors WHERE news_id = ?";
        String deleteNewsQuery = "DELETE FROM news_management.news WHERE id = ?";

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement deleteAuthorsStatement = connection.prepareStatement(deleteAuthorsQuery);
             PreparedStatement deleteNewsStatement = connection.prepareStatement(deleteNewsQuery)
        ) {
            // Удаляем связанные записи
            deleteAuthorsStatement.setInt(1, newsId);
            deleteAuthorsStatement.executeUpdate();

            // Теперь удаляем саму новость
            deleteNewsStatement.setInt(1, newsId);
            int rowsAffected = deleteNewsStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Новость успешно удалена.");
                return true;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return false;
    }

    public List<News> getAllNews() throws DAOException {
        String query = "SELECT n.id, n.importance, n.title, n.image, n.brief, n.content, n.publish_date, n.categories_id, c.title AS category_title " +
                       "FROM news_management.news n " +
                       "JOIN news_management.categories c ON n.categories_id = c.id ORDER BY n.publish_date DESC";

        List<News> newsList = new ArrayList<>();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                NewsImportance importance = NewsImportance.valueOf(resultSet.getString("importance"));
                String title = resultSet.getString("title");
                String imageUrl = resultSet.getString("image");
                String brief = resultSet.getString("brief");
                String content = resultSet.getString("content");
                LocalDateTime publishDate = resultSet.getTimestamp("publish_date").toLocalDateTime();
                String categoryTitle = resultSet.getString("category_title");  // category_title из таблицы categories

                List<User> newsAuthors = getNewsAuthorsByNewsId(id);

                newsList.add(new News(id, importance, title, imageUrl, brief, content, publishDate, newsAuthors, categoryTitle));
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }

        return newsList;
    }

    public List<User> getNewsAuthorsByNewsId(int newsId) throws DAOException {
        String queryAuthors = "SELECT u.id, u.name FROM news_management.users u " +
                              "JOIN news_management.news_authors na ON u.id = na.users_id " +
                              "WHERE na.news_id = ?";

        List<User> authors = new ArrayList<>();

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryAuthors)) {
            preparedStatement.setInt(1, newsId);  // Устанавливаем id новости в запрос
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String name = resultSet.getString("name");

                    authors.add(new User(userId, name, UserRole.AUTHOR));
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return authors;
    }

    public News getNewsById(int id) throws DAOException {
        String query = "SELECT * FROM news_management.news WHERE id = ?";
        String categoryQuery = "SELECT title FROM news_management.categories WHERE id = ?";

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int categoryId = resultSet.getInt("categories_id");

                    // Теперь выполняем запрос, чтобы получить title категории
                    try (PreparedStatement categoryStatement = connection.prepareStatement(categoryQuery)) {
                        categoryStatement.setInt(1, categoryId);
                        try (ResultSet categoryResultSet = categoryStatement.executeQuery()) {
                            String categoryTitle = null;
                            if (categoryResultSet.next()) {
                                categoryTitle = categoryResultSet.getString("title");
                            }


                            return new News(id,
                                    NewsImportance.valueOf(resultSet.getString("importance")),
                                    resultSet.getString("title"),
                                    resultSet.getString("image"),
                                    resultSet.getString("brief"),
                                    resultSet.getString("content"),
                                    resultSet.getTimestamp("publish_date").toLocalDateTime(),
                                    getNewsAuthorsByNewsId(id),
                                    categoryTitle);  // Передаем categoryTitle
                        }
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return null;
    }

    public List<News> getAllNewsByAuthor(int authorId) throws DAOException {
        List<News> resList = new ArrayList<>();
        String query = "SELECT news_id FROM news_management.news_authors WHERE users_id = ? ORDER BY (SELECT publish_date FROM news_management.news WHERE id = news_id)  DESC";
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, authorId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    resList.add(getNewsById(resultSet.getInt("news_id")));
                }

            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return resList;
    }

    public boolean updateNewsArticle(int newsId, News news) throws DAOException {
        String query = "UPDATE news_management.news SET importance = ?, title = ?, image = ?, brief = ?, content = ?, publish_date = ?, news.update_date = ?, categories_id = (SELECT id FROM news_management.categories WHERE title = ?) WHERE id = ?";
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, news.getImportance().name());
            preparedStatement.setString(2, news.getTitle());
            preparedStatement.setString(3, news.getImageUrl());
            preparedStatement.setString(4, news.getBrief());
            preparedStatement.setString(5, news.getContent());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(news.getPublishDate()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, news.getCategory());
            preparedStatement.setInt(9, newsId);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Ошибка при обновлении новости с ID " + newsId + ": " + e.getMessage(), e);
        }
    }

    public List<News> findNewsByType(NewsImportance newsImportance) throws DAOException {
        List<News> resList = new ArrayList<>();
        String query = "SELECT * FROM news_management.news WHERE importance = ?";
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newsImportance.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    resList.add(getNewsById(resultSet.getInt("id")));
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return resList;
    }
}

