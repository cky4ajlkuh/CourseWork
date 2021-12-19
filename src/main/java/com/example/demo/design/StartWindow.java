package com.example.demo.design;

import com.example.demo.entity.Owner;
import com.example.demo.service.ListService;
import com.example.demo.service.OwnerService;
import com.example.demo.service.WordService;
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
    @Autowired
    private WordService wordService;
    @Autowired
    private ListService listService;
    @Autowired
    private BasicWindow basicWindow;

    private final static JTextField createLogin = new JTextField(10);
    private final static JTextField insertLogin = new JTextField(10);
    private final static JLabel login = new JLabel("Логин: ");
    private final static JLabel newLogin = new JLabel("Придумайте логин: ");
    private final static JButton entry = new JButton("Войти");
    private final static JButton registration = new JButton("Регистрация");
    private final static JButton create = new JButton("Создать");
    private final static JPanel insertPanel = new JPanel();
    private final static JPanel createPanel = new JPanel();

    public StartWindow() {
        super("Start Window");
        setVisible(true);
        add(getInsertPanel());
        setResizable(false);
        setSize(new Dimension(300, 200));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JFrame window = this;
        entry.addMouseListener(new MouseAdapter() {
            Owner owner;

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!insertLogin.getText().isEmpty()) {
                        owner = ownerService.findByName(insertLogin.getText());
                        if (owner != null) {
                            setVisible(false);
                            basicWindow.basicWindow(owner);
                        } else {
                            JOptionPane.showMessageDialog(null, "Проверьте Логин !");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Некорректное имя!");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Проверьте Логин!");
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
                try {
                    if (!createLogin.getText().isEmpty()) {
                        owner = ownerService.findByName(createLogin.getText());
                        if (owner == null) {
                            owner = ownerService.create(createLogin.getText());
                            window.setVisible(false);
                            basicWindow.basicWindow(owner);
                        } else {
                            JOptionPane.showMessageDialog(null, "Логин занят!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Некорректное имя!");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Проверьте Логин! Возможно, он занят.");
                }
            }
        });
    }

    private JPanel getInsertPanel() {
        GridLayout layout = new GridLayout(3, 1, 10, 25);
        insertPanel.setLayout(layout);
        insertPanel.add(login);
        insertPanel.add(insertLogin);
        insertPanel.add(entry);
        insertPanel.add(registration);
        return insertPanel;
    }

    private void offInsertPanel() {
        insertPanel.setEnabled(false);
        insertPanel.setVisible(false);
    }

    private JPanel getCreatePanel() {
        GridLayout layout = new GridLayout(2, 1, 10, 25);
        insertPanel.setLayout(layout);
        createPanel.add(newLogin);
        createPanel.add(createLogin);
        createPanel.add(create);
        return createPanel;
    }
}
