package com.example.demo.design;

import com.example.demo.TableModel;
import com.example.demo.entity.List;
import com.example.demo.entity.Owner;
import com.example.demo.entity.Word;
import com.example.demo.service.ListService;
import com.example.demo.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

@Transactional
public class BasicWindow extends JFrame {

    private WordService wordService;
    private ListService listService;
    private List list;
    private static int id = 0;
    private final static JButton search = new JButton("Найти");
    private final static JButton change = new JButton("Сменить пользователя");
    private final static JButton deleteWord = new JButton("Удалить слово");
    private final static JTextField searchWord = new JTextField();
    private final static JLabel nameTable = new JLabel("Ваш список слов:");
    private final static JLabel greeting = new JLabel("Чтобы найти неправильный глагол, введите его!");
    private java.util.List<String> first_form = new ArrayList<>();
    private java.util.List<String> second_form = new ArrayList<>();
    private java.util.List<String> third_form = new ArrayList<>();
    private java.util.List<String> meaning = new ArrayList<>();
    private TableModel model = new TableModel(first_form, second_form, third_form, meaning);
    private JLabel idOwner;
    private final static JPanel basicPanel = new JPanel();

    public BasicWindow(Owner owner, WordService wordService, ListService listService) {
        this.wordService = wordService;
        this.listService = listService;
        id = owner.getId();
        list = owner.getLists();
        idOwner = new JLabel("id: " + id);

        if (list == null) {
            list = listService.create(owner);
        } else {
            setList(list);
        }

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

        //checkList();
        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!searchWord.getText().isEmpty()) {
                    try {
                        Word word = wordService.findByWord(searchWord.getText());
                        word = wordService.update(word, list);
                        list.setWord(word);
                        setList(list);
                        model.fireTableDataChanged();
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Проверьте введенное слово!");
                    }
                }
            }

        });
    }

    private void setList(List list) {
        model.setCount(list.getWords().size());
        first_form.clear();
        second_form.clear();
        third_form.clear();
        meaning.clear();
        for (Word word : list.getWords()) {
            first_form.add(word.getFirst_form());
            second_form.add(word.getSecond_form());
            third_form.add(word.getThird_form());
            meaning.add(word.getMeaning());
            model.fireTableDataChanged();
        }
        /*
        for (int i = 0; i < list.getWords().size(); i++) {
            model.setValueAt(first_form.get(i), i, 0);
            model.setValueAt(second_form.get(i), i, 1);
            model.setValueAt(third_form.get(i), i, 2);
            model.setValueAt(meaning.get(i), i, 3);
        }*/
    }

    private void checkList() {
        if (list.getWords().isEmpty()) {
            list = new List();
        } else {
            setList(list);
        }
    }
}

class BW {
    public static void main(String[] args) {
        //new BasicWindow();
    }
}