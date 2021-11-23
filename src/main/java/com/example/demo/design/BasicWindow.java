package com.example.demo.design;

import com.example.demo.TableModel;
import com.example.demo.entity.List;
import com.example.demo.entity.Owner;
import com.example.demo.entity.Word;
import com.example.demo.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class BasicWindow extends JFrame {

    @Autowired
    private OwnerService ownerService;
    private static Owner owner;
    private static List list;
    private static int id;
    private final static JButton search = new JButton("Найти");
    private final static JButton change = new JButton("Сменить пользователя");
    private final static JButton deleteWord = new JButton("Удалить слово");
    private final static JTextField searchWord = new JTextField();
    private final static JLabel nameTable = new JLabel("Ваш список слов:");
    private final static JLabel greeting = new JLabel("Чтобы найти неправильный глагол, введите его!");
    private final static JLabel idOwner = new JLabel("id: " + id);
    private final static java.util.List<String> first_form = new ArrayList<>();
    private final java.util.List<String> second_form = new ArrayList<>();
    private final java.util.List<String> third_form = new ArrayList<>();
    private final java.util.List<String> meaning = new ArrayList<>();
    private final TableModel model = new TableModel(first_form, second_form, third_form, meaning);
    private final static JPanel basicPanel = new JPanel();


    public BasicWindow(Owner owner) {
        id = owner.getId();
        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(750, 400));

        basicPanel.add(idOwner);
        basicPanel.add(change);
        basicPanel.add(greeting);
        basicPanel.add(searchWord);
        basicPanel.add(search);
        basicPanel.add(nameTable);
        basicPanel.add(deleteWord);
        basicPanel.add(pane);

        {
            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);
            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(idOwner)
                            .addComponent(change))
                    .addComponent(greeting)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(searchWord)
                            .addComponent(search))
                    .addComponent(nameTable)
                    .addComponent(pane)
                    .addComponent(deleteWord)
            );
            layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(idOwner)
                            .addComponent(change))
                    .addComponent(greeting)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(searchWord)
                            .addComponent(search))
                    .addComponent(nameTable)
                    .addComponent(pane)
                    .addComponent(deleteWord)
            );
        }

        {
            setVisible(true);
            add(basicPanel);
            setResizable(true);
            setSize(new Dimension(800, 800));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        checkList();
    }

    private void setList(List list) {
        model.setCount(list.getWords().size());
        for (Word word : list.getWords()) {
            first_form.add(word.getFirst_form());
            second_form.add(word.getSecond_form());
            third_form.add(word.getThird_form());
            meaning.add(word.getMeaning());
        }
        model.fireTableDataChanged();
    }

    private void checkList() {
        System.out.println("lala");
        if (!owner.getLists().getWords().isEmpty()) {
            setList(owner.getLists());
            System.out.println("2122312321321");
        }
        if (owner.getLists().getWords().isEmpty()) {
            list = new List();
            System.out.println("lox");
        }
    }
}

class BW {
    public static void main(String[] args) {
        //new BasicWindow();
    }
}