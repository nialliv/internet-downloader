package ru.artemev.internetdownloader;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.artemev.internetdownloader.service.BookSaverService;

@SpringBootApplication
@RequiredArgsConstructor
public class InternetDownloaderApplication implements CommandLineRunner {

    private final BookSaverService service;

    public static void main(String[] args) {
        SpringApplication.run(InternetDownloaderApplication.class, args);
    }

    @Override
    public void run(String... args) {
        service.downloadLordOfTheMysteries();
    }
}
