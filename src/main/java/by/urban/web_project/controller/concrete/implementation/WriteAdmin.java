package by.urban.web_project.controller.concrete.implementation;

import jakarta.servlet.ServletException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.activation.DataHandler;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.utils.ByteArrayDataSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WriteAdmin implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String email = request.getParameter("email");
		String message = request.getParameter("message");
		// именно Part, потому что содержимое НЕ строка
		Part inputFile = request.getPart("inputFile");

		try {
			sendEmail(request.getServletContext(), email, message, inputFile);
			// устанавливаем в сессии оповещение об успехе
			request.getSession().setAttribute("successMessage", "Сообщение успешно отправлено.");
		} catch (Exception e) {
			// устанавливаем в сессии оповещение об ошибке
			request.getSession().setAttribute("errorMessage", "Ошибка: " + e.getMessage());
		} finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void sendEmail(ServletContext context, String userEmail, String userMessage, Part inputFile)
			throws IOException, ServletException {
		// класс для работы с набором свойств, хранящихся в виде пар "ключ=значение"
		// в нашем случае вытягиваем адрес почты и пароль к почте из файла
		// email.properties
		Properties properties = new Properties();
		// создаем входной поток на основе данных, доступных в контексте веб-приложения
		try (InputStream input = context.getResourceAsStream("/WEB-INF/email.properties")) {
			if (input == null) {
				throw new IOException("Не удалось найти файл email.properties");
			}
			// станд.метод, считывает данные из указанного входного потока
			properties.load(input);
		}

		final String adminEmail = properties.getProperty("admin.email");
		final String password = properties.getProperty("admin.password");

		// настройка свойств почтового соединения
		// взяты из thunderbird, подключенного к яндекс почте с заданным отдельным
		// паролем для приложения
		Properties mailProps = new Properties();
		// сервер требует проверки подлинности пользователя (логина и пароля)
		mailProps.put("mail.smtp.auth", "true");
		// включение протокола STARTTLS, который используется для обеспечения
		// защищенного соединения при передаче данных по SMTP
		mailProps.put("mail.smtp.starttls.enable", "true");
		// сервер, к которому будет подключаться клиент для отправки электронной почты
		mailProps.put("mail.smtp.host", "smtp.yandex.ru");
		// порт для отправки электронной почты с поддержкой STARTTLS (стандартный порт
		// для SMTP с шифрованием)
		mailProps.put("mail.smtp.port", "587");

		// создание сессии для управления параметрами соединения и аутентификации при
		// отправке электронной почты
		// создается анонимный класс, который наследует Authenticator
		Session session = Session.getInstance(mailProps, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(adminEmail, password);
			}
		});

		try {
			// создание сообщения
			Message msg = new MimeMessage(session);
			// отправляем от имени администратора на имя администратора
			msg.setFrom(new InternetAddress(adminEmail));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(adminEmail));
			// заголовок письма
			msg.setSubject("Сообщение от пользователя: " + userEmail);

			// создание многосоставного сообщения (textPart + attachmentPart)
			Multipart multipart = new MimeMultipart();

			// добавление текстовой части
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText(userMessage);
			multipart.addBodyPart(textPart);

			// обработка файла во вложении
			if (inputFile != null && inputFile.getSize() > 0) {
				// прописываем, что прикреплять можно только файлы определенных расширений
				String mimeType = inputFile.getContentType();
				if (!"image/png".equals(mimeType) && !"application/pdf".equals(mimeType)) {
					throw new ServletException("Разрешены только файлы форматов PNG и PDF");
				}

				// считывание содержимого файла в массив байтов
				byte[] attachmentData;
				try (InputStream inputStream = inputFile.getInputStream();
						//ByteArrayOutputStream используется как буффер для временного хранения считанных данных
						//вместо этого можно записать в файл на диске FileOutputStream fileOutputStream = new FileOutputStream("path/to/output/file")
						//или в коллекцию List<Byte> byteList = new ArrayList<>();
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
					byte[] buffer = new byte[4096];
					int bytesRead;
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						byteArrayOutputStream.write(buffer, 0, bytesRead);
					}
					attachmentData = byteArrayOutputStream.toByteArray();
				}

				// это экзмепляр для хранения вложения
				MimeBodyPart attachmentPart = new MimeBodyPart();
				// создаем обработчик, который будет использовать наш массив байтов и наш тип вложений 
				attachmentPart.setDataHandler(new DataHandler(new ByteArrayDataSource(attachmentData, mimeType)));
				// имя файла = имя загруженного пользователем файла
				attachmentPart.setFileName(inputFile.getSubmittedFileName());
				// файл будет отображаться не как часть текста, а как приложение
				attachmentPart.setDisposition(MimeBodyPart.ATTACHMENT);
				// добавляем вложение в многосоставное сообщение
				multipart.addBodyPart(attachmentPart);
			}

			// вставляем многосоставное сообщение как содержимое сообщения 
			msg.setContent(multipart);

			// отправка сообщения на сервер SMTP через класс Transport из JavaMail API
			Transport.send(msg);
			System.out.println("Сообщение успешно отправлено.");
		} catch (MessagingException e) {
			System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Ошибка ввода-вывода: " + e.getMessage());
			e.printStackTrace();
		}
	}
}