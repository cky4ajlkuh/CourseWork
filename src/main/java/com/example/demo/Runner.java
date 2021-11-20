package com.example.demo;

import com.example.demo.design.StartWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

/*
@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private StartWindow window;

    @Override
    public void run(String... args) throws Exception {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                window.setVisible(true);
            }
        });
    }

}*/
@Component
public class Runner {

    @Autowired
    private StartWindow window;

    @PostConstruct
    private void initialize(){
        runner(window);
    }

    public void runner(JFrame frame){
        java.awt.EventQueue.invokeLater(() -> frame.setVisible(true));
    }

}
