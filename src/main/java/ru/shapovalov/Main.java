package ru.shapovalov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private TerminalView terminalView;

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        terminalView.start();
    }
}