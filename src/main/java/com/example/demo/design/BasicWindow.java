package com.example.demo.design;

import com.example.demo.entity.List;
import com.example.demo.entity.Owner;
import com.example.demo.entity.Word;
import com.example.demo.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class BasicWindow extends JFrame {

    @Autowired
    private OwnerService ownerService;

    private final static JButton search = new JButton("Найти");
    private final static JButton deleteList = new JButton("Удалить список");
    private final static JButton addList = new JButton("Добавить список");
    private final static JButton change = new JButton("Сменить пользователя");
    private final static JButton deleteWord = new JButton("Удалить слово");
    private final static JTextField searchWord = new JTextField();
    private final static JLabel greeting = new JLabel("Чтобы найти неправильный глагол, введите его!");
    private final static JLabel idOwner = new JLabel("id: ");
    private final static DefaultTableModel tableModelList = new DefaultTableModel();
    private final static Object[] columnsHeader = new String[]{"infinitive", "Past Simple", "Past Participle", "Перевод"};
    private final static JPanel basicPanel = new JPanel();

    public BasicWindow(Owner owner) {

        JComboBox comboList = new JComboBox(new String[]{" "});

        tableModelList.setColumnIdentifiers(columnsHeader);
        JTable tableList = new JTable(tableModelList);
        JScrollPane pane = new JScrollPane(tableList);
        pane.setPreferredSize(new Dimension(750, 400));
        List list = owner.getLists();

        basicPanel.add(idOwner);
        basicPanel.add(change);
        basicPanel.add(greeting);
        basicPanel.add(searchWord);
        basicPanel.add(search);
        basicPanel.add(comboList);
        basicPanel.add(addList);
        basicPanel.add(deleteList);
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
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(comboList)
                            .addComponent(addList)
                            .addComponent(deleteList)
                            .addComponent(deleteWord))
                    .addComponent(pane)
            );
            layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(idOwner)
                            .addComponent(change))
                    .addComponent(greeting)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(searchWord)
                            .addComponent(search))
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(comboList)
                            .addComponent(addList)
                            .addComponent(deleteList)
                            .addComponent(deleteWord))
                    .addComponent(pane)
            );
        }

        {
            setVisible(true);
            add(basicPanel);
            setResizable(true);
            setSize(new Dimension(800, 800));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

    }

    private void setList(List list) {
        if (!list.getWords().isEmpty()) {
            for (Word word : list.getWords()) {
                String first_form = word.getFirst_form();
                String second_form = word.getSecond_form();
                String third_form = word.getThird_form();
                String meaning = word.getMeaning();
                //передать значения в таблицу
            }
        }
    }

}

class BW {
    public static void main(String[] args) {
    }
}