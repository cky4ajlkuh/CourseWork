package com.example.demo;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel {

    private static final int FIRST_FORM_COLUMN_NUMBER = 0;
    private static final int SECOND_FORM_COLUMN_NUMBER = 1;
    private static final int THIRD_FORM_COLUMN_NUMBER = 2;
    private static final int MEANING_COLUMN_NUMBER = 3;
    private final List<String> first_form;
    private final List<String> second_form;
    private final List<String> third_form;
    private final List<String> meaning;
    private int count;

    public TableModel(List<String> first_form, List<String> second_form, List<String> third_form, List<String> meaning) {
        this.first_form = first_form;
        this.second_form = second_form;
        this.third_form = third_form;
        this.meaning = meaning;
    }

    public void setCount(int size) {
        count = size;
    }

    @Override
    public int getRowCount() {
        return count;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == FIRST_FORM_COLUMN_NUMBER) {
            first_form.set(rowIndex, aValue.toString());
        }
        if (columnIndex == SECOND_FORM_COLUMN_NUMBER) {
            second_form.set(rowIndex, aValue.toString());
        }
        if (columnIndex == THIRD_FORM_COLUMN_NUMBER) {
            third_form.set(rowIndex, aValue.toString());
        }
        if (columnIndex == MEANING_COLUMN_NUMBER) {
            meaning.set(rowIndex, aValue.toString());
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case FIRST_FORM_COLUMN_NUMBER -> "Infinitive";
            case SECOND_FORM_COLUMN_NUMBER -> "Second Form";
            case THIRD_FORM_COLUMN_NUMBER -> "Third Form";
            case MEANING_COLUMN_NUMBER -> "Translation";
            default -> super.getColumnName(column);
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == FIRST_FORM_COLUMN_NUMBER) {
            return first_form.get(rowIndex);
        }
        if (columnIndex == SECOND_FORM_COLUMN_NUMBER) {
            return second_form.get(rowIndex);
        }
        if (columnIndex == THIRD_FORM_COLUMN_NUMBER) {
            return third_form.get(rowIndex);
        }
        if (columnIndex == MEANING_COLUMN_NUMBER) {
            return meaning.get(rowIndex);
        }
        throw new UnsupportedOperationException();
    }
}
