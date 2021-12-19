package com.example.demo.design;

import com.example.demo.TableModel;
import com.example.demo.entity.List;
import com.example.demo.entity.Owner;
import com.example.demo.entity.Word;
import com.example.demo.service.ListService;
import com.example.demo.service.OwnerService;
import com.example.demo.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Component
@Transactional
public class BasicWindow extends JFrame {

    @Autowired
    private WordService wordService;
    @Autowired
    private ListService listService;
    @Autowired
    private StartWindow startWindow;
    //private WordService wordService;
    //private ListService listService;
    private HashMap<String, Word> mapWords = new HashMap<>();
    private List list;
    private JLabel idOwner = new JLabel();
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
    private static JPanel basicPanel = new JPanel();

    public BasicWindow() {
        super("Easy Verbs");
    }

    private void init() {
        removeAll();
        revalidate();
        repaint();
        mapWords = new HashMap<>();
        first_form = new ArrayList<>();
        second_form = new ArrayList<>();
        third_form = new ArrayList<>();
        meaning = new ArrayList<>();
        model = new TableModel(first_form, second_form, third_form, meaning);
        basicPanel = new JPanel();
        list = null;
    }

    public void basicWindow(Owner owner) {
        init();
        list = owner.getLists();
        idOwner.setText("id: " + owner.getId());

        if (list == null) {
            list = listService.create(owner);
        }
        if (!list.getWords().isEmpty()) {
            for (Word word : list.getWords()) {
                mapWords.put(word.getFirst_form(), word);
            }
            setList(mapWords);
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

                        if (!mapWords.containsKey(word.getFirst_form())) {
                            word = wordService.update(word, list);
                            list.setWord(word);
                            mapWords.put(word.getFirst_form(), word);
                            setList(mapWords);
                            model.fireTableDataChanged();
                            searchWord.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Слово уже есть в Листе!");
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Проверьте корректность ввода!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Проверьте корректность ввода! \n Возможно, это не неправильный глагол!");
                }
            }
        });

        deleteWord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!list.getWords().isEmpty()) {
                    JFrame jFrame = new JFrame();
                    String getMessage = JOptionPane.showInputDialog(jFrame, "Введите номер строки: ");
                    if (!getMessage.isEmpty()) {
                        int rowNumber = Integer.parseInt(getMessage);
                        if (rowNumber != 0) {
                            rowNumber = rowNumber - 1;
                            if (rowNumber < list.getWords().size()) {
                                clearArray();
                                Word[] words = list.getWords().toArray(new Word[0]);
                                wordService.update(words[rowNumber], null);
                                mapWords.remove(words[rowNumber].getFirst_form());
                                list = listService.findByID(list.getId());
                                setList(mapWords);
                                model.fireTableDataChanged();
                            } else {
                                JOptionPane.showMessageDialog(null, "Проверьте корректность ввода!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Нельзя удалить нулевую строку!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Нельзя удалить пустую строку!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Нельзя удалить пустой список!");
                }
            }
        });

        change.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    setVisible(false);
                    startWindow.setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        saveList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!list.getWords().isEmpty()) {
                    JFrame jFrame = new JFrame();
                    String getMessage = JOptionPane.showInputDialog(jFrame, "Придумайте название файла: ");
                    if (!getMessage.isEmpty()) {
                        try {
                            saveTable(getMessage);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null, "Ваш файл сохранен!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Нельзя сохранить пустой лист!");
                    }
                }
            }
        });
        setVisible(true);
    }

    private void clearArray() {
        first_form.clear();
        second_form.clear();
        third_form.clear();
        meaning.clear();
    }

    private void setList(HashMap<String, Word> mapWords) {
        Collection<Word> collection = mapWords.values();
        model.setCount(collection.size());
        for (Word word : collection) {
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
