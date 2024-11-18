package by.urban.web_project.utils;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

public class ImageUtils {
    public static String saveImage(Part inputFile, String directory)
            throws ServletException, IOException, MessagingException {
        // прописываем, что прикреплять можно только файлы определенных расширений
        Set<String> allowedMimeTypes = new HashSet<>();
        allowedMimeTypes.add("image/png");
        allowedMimeTypes.add("image/jpg");
        allowedMimeTypes.add("image/jpeg");

        String inputMimeType = inputFile.getContentType();
        if (!allowedMimeTypes.contains(inputMimeType)) {
            throw new ServletException("Разрешены только файлы форматов PNG, JPG и JPEG.");
        }
        // Получаем абсолютный путь к корню веб-приложения
        String appRoot = new File(directory).getAbsolutePath();
        String imagesDirectory = appRoot + File.separator + "images";  // Путь к папке images в ТОМКАТЕ

        // Создаем директорию, если ее нет
        File directoryFile = new File(imagesDirectory);
        if (!directoryFile.exists()) {
            directoryFile.mkdirs(); // Создает директорию, если она не существует
        }

        // Получаем имя файла
        String fileName = inputFile.getSubmittedFileName();

        // Формируем полный путь для сохранения файла
        String filePath = imagesDirectory + File.separator + fileName;

        // Сохраняем файл
        try (InputStream inputStream = inputFile.getInputStream()) {
            Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return fileName;
    }
}