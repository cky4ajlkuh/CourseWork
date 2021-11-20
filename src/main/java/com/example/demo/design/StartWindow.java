package com.example.demo.design;

import com.example.demo.entity.Owner;
import com.example.demo.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class StartWindow extends JFrame {

    @Autowired
    private OwnerService ownerService;
    private final static JTextField createLogin = new JTextField(10);
    private final static JTextField createPassword = new JTextField(10);
    private final static JTextField insertLogin = new JTextField(10);
    private final static JTextField insertPassword = new JTextField(10);
    private final static JLabel login = new JLabel("Логин: ");
    private final static JLabel password = new JLabel("Пароль: ");
    private final static JLabel newPassword = new JLabel("Придумайте пароль: ");
    private final static JLabel newLogin = new JLabel("Придумайте логин: ");
    private final static JButton entry = new JButton("Войти");
    private final static JButton registration = new JButton("Регистрация");
    private final static JButton create = new JButton("Создать");
    private final static JPanel insertPanel = new JPanel();
    private final static JPanel createPanel = new JPanel();

    public StartWindow() {
        createPanel.add(createLogin);
        createPanel.add(createPassword);
        createPanel.add(newPassword);
        createPanel.add(newLogin);
        createPanel.add(create);
        add(getInsertPanel());
        setResizable(false);
        setSize(new Dimension(300, 200));

        entry.addMouseListener(new MouseAdapter() {
            Owner owner;

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    owner = ownerService.findByName(insertLogin.getText());
                    if (owner != null) {
                        dispose();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    //тут потом ошибку придумаю
                }
            }
        });

        registration.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                offInsertPanel();
                add(getCreatePanel());
            }
        });

        create.addMouseListener(new MouseAdapter() {
            Owner owner;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (createLogin.getText() != null) {
                    owner = ownerService.findByName(createLogin.getText());
                    if (owner == null) {
                        owner = ownerService.create(createLogin.getText());
                        dispose();
                    }
                }
            }
        });
    }

    private JPanel getInsertPanel() {

        insertPanel.setMaximumSize(new Dimension(250, 150));
        insertPanel.setMinimumSize(new Dimension(250, 150));
        insertPanel.setPreferredSize(new Dimension(250, 150));
        GridLayout layout = new GridLayout(3, 2);
        layout.setHgap(10);
        layout.setVgap(10);
        insertPanel.setLayout(layout);
        insertPanel.add(login);
        insertPanel.add(insertLogin);
        insertPanel.add(password);
        insertPanel.add(insertPassword);
        insertPanel.add(entry);
        insertPanel.add(registration);
        /*
        GroupLayout insertLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(insertLayout);
        insertLayout.setAutoCreateGaps(true);
        insertLayout.setAutoCreateContainerGaps(true);
        insertLayout.setHorizontalGroup(insertLayout.createParallelGroup()
                .addGroup(insertLayout.createSequentialGroup()
                        .addComponent(login)
                        .addComponent(insertLogin))
                .addGroup(insertLayout.createSequentialGroup()
                        .addComponent(password)
                        .addComponent(insertPassword))
                .addGroup(insertLayout.createSequentialGroup()
                        .addComponent(entry)
                        .addComponent(registration))
        );
        insertLayout.setVerticalGroup(insertLayout.createSequentialGroup()
                .addGroup(insertLayout.createParallelGroup()
                        .addComponent(login)
                        .addComponent(insertLogin))
                .addGroup(insertLayout.createParallelGroup()
                        .addComponent(password)
                        .addComponent(insertPassword))
                .addGroup(insertLayout.createParallelGroup()
                        .addComponent(entry)
                        .addComponent(registration))
        );

        insertPanel.setEnabled(true);
        insertPanel.setVisible(true);
        */
        return insertPanel;
    }

    private void offInsertPanel() {
        insertPanel.setEnabled(false);
        insertPanel.setVisible(false);
    }

    private JPanel getCreatePanel() {
        GroupLayout createLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(createLayout);
        createLayout.setAutoCreateGaps(true);
        createLayout.setAutoCreateContainerGaps(true);
        createLayout.setHorizontalGroup(createLayout.createParallelGroup()
                .addGroup(createLayout.createSequentialGroup()
                        .addComponent(newLogin)
                        .addComponent(createLogin))
                .addGroup(createLayout.createSequentialGroup()
                        .addComponent(newPassword)
                        .addComponent(createPassword))
                .addComponent(create)
        );
        createLayout.setVerticalGroup(createLayout.createSequentialGroup()
                .addGroup(createLayout.createParallelGroup()
                        .addComponent(newLogin)
                        .addComponent(createLogin))
                .addGroup(createLayout.createParallelGroup()
                        .addComponent(newPassword)
                        .addComponent(createPassword))
                .addComponent(create)
        );
        return createPanel;
    }

    private void offCreatePanel() {
        createPanel.setVisible(false);
        createPanel.setEnabled(false);
    }

}
