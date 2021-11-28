package com.example.demo.design;

import com.example.demo.TableModel;
import com.example.demo.entity.List;
import com.example.demo.entity.Owner;
import com.example.demo.entity.Word;
import com.example.demo.service.ListService;
import com.example.demo.service.WordService;

import javax.swing.*;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
    private final static JButton saveList = new JButton("Сохранить список");
    private final static JTextField searchWord = new JTextField();
    private final static JLabel nameTable = new JLabel("Ваш список слов:");
    private final static JLabel greetingFirst = new JLabel("Чтобы найти неправильный глагол, введите одну из его трех форм");
    private final static JLabel greetingSecond = new JLabel("или введите его перевод без пробела и не с заглавной буквы!");
    private java.util.List<String> first_form = new ArrayList<>();
    private java.util.List<String> second_form = new ArrayList<>();
    private java.util.List<String> third_form = new ArrayList<>();
    private java.util.List<String> meaning = new ArrayList<>();
    private TableModel model = new TableModel(first_form, second_form, third_form, meaning);
    private JLabel idOwner;
    private final static JPanel basicPanel = new JPanel();

    public BasicWindow(Owner owner, WordService wordService, ListService listService) {
        setVisible(true);
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
        basicPanel.add(greetingFirst);
        basicPanel.add(greetingSecond);
        basicPanel.add(searchWord);
        basicPanel.add(search);
        basicPanel.add(nameTable);
        basicPanel.add(deleteWord);
        basicPanel.add(saveList);
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
                    .addComponent(greetingFirst)
                    .addComponent(greetingSecond)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(searchWord)
                            .addComponent(search))
                    .addComponent(nameTable)
                    .addComponent(pane)
                    .addGroup(layout.createParallelGroup()
                            .addComponent(deleteWord)
                            .addComponent(saveList)
                    )
            );
            layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(idOwner)
                            .addComponent(change))
                    .addComponent(greetingFirst)
                    .addComponent(greetingSecond)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(searchWord)
                            .addComponent(search))
                    .addComponent(nameTable)
                    .addComponent(pane)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(deleteWord)
                            .addComponent(saveList)
                    )

            );
        }

        {
            add(basicPanel);
            setResizable(true);
            setSize(new Dimension(800, 800));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!searchWord.getText().isEmpty()) {
                    clearArray();
                    try {
                        Word word = wordService.findByWord(searchWord.getText());
                        word = wordService.update(word, list);
                        list.setWord(word);
                        setList(list);
                        model.fireTableDataChanged();
                        searchWord.setText("");
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Проверьте корректность ввода! \n Возможно, это не неправильный глагол!");
                    }
                }
            }
        });

        deleteWord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!list.getWords().isEmpty()) {
                    JFrame jFrame = new JFrame();
                    String getMessage = JOptionPane.showInputDialog(jFrame, "Введите номер строки: ");
                    int rowNumber = Integer.parseInt(getMessage);
                    rowNumber = rowNumber - 1;
                    if (rowNumber < list.getWords().size()) {
                        try {
                            clearArray();
                            Word[] words = list.getWords().toArray(new Word[list.getWords().size()]);
                            wordService.update(words[rowNumber], null);
                            list = listService.findByID(list.getId());
                            setList(list);
                            model.fireTableDataChanged();
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, "Проверьте корректность ввода!");
                        }
                    }
                }
            }
        });

        change.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    dispose();
                    setVisible(false);
                    new StartWindow();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        saveList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!list.getWords().isEmpty()) {
                    try {
                        JFrame jFrame = new JFrame();
                        String getMessage = JOptionPane.showInputDialog(jFrame, "Придумайте название файла: ");
                        saveTable(getMessage);
                        JOptionPane.showMessageDialog(null, "Ваш файл сохранен!");
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Нельзя сохранить пустой лист!");
                    }
                }
            }
        });
    }

    private void clearArray() {
        first_form.clear();
        second_form.clear();
        third_form.clear();
        meaning.clear();
    }

    private void setList(List list) {
        model.setCount(list.getWords().size());
        for (Word word : list.getWords()) {
            first_form.add(word.getFirst_form());
            second_form.add(word.getSecond_form());
            third_form.add(word.getThird_form());
            meaning.add(word.getMeaning());
            model.fireTableDataChanged();
        }
    }

    public void saveTable(String nameFile) throws Exception {
        BufferedWriter bfw = new BufferedWriter(new FileWriter(nameFile + ".txt"));
        for (int i = 0; i < model.getColumnCount(); i++) {
            bfw.write(model.getColumnName(i));
            bfw.write("\t");
        }
        for (int i = 0; i < model.getRowCount(); i++) {
            bfw.newLine();
            for (int j = 0; j < model.getColumnCount(); j++) {
                bfw.write((String) (model.getValueAt(i, j)));
                bfw.write("\t");
            }
        }
        bfw.close();
    }
}
