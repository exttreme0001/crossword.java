import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static java.lang.Math.*;

public class Window extends JFrame {
    private final JTextField[][] cellViews;

    public Window(Crossword crossword) {
        super("Моё окно");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 350);
        setResizable(false);

        setLayout(null);
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);

        Crossword.Cell[][] cells = crossword.getField();
        cellViews = new JTextField[cells.length][];
        for(int i = 0; i < cells.length; i++) {
            cellViews[i] = new JTextField[cells[i].length];
            for(int j = 0; j < cells[i].length; j++) {
                cellViews[i][j] = new JTextField();
                cellViews[i][j].setBounds(10 + 50 * j, 10 + 50 * i, 50, 50);
                cellViews[i][j].setBorder(new LineBorder(Color.BLACK, 1));
                cellViews[i][j].setFont(font);
                cellViews[i][j].setEditable(false);
                cellViews[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                cellViews[i][j].setForeground(Color.BLACK);
                if(cells[i][j] != null) {
                    cellViews[i][j].setBackground(Color.WHITE);
                    if(cells[i][j].isOpened()) {
                        cellViews[i][j].setText(Character.toString(Character.toUpperCase(cells[i][j].getLetter())));
                    }
                } else {
                    cellViews[i][j].setBackground(Color.BLACK);
                }
                add(cellViews[i][j]);
            }
        }

        JTextField word = new JTextField();
        word.setBounds(10, 270, 150, 30);
        word.setFont(new Font(word.getFont().getName(), Font.PLAIN, 20));
        add(word);

        JButton okButton = new JButton("OK");
        okButton.setBounds(170, 270, 90, 30);
        okButton.addActionListener(e -> {
            if(crossword.tryWord(word.getText())) {
                for(int i = 0; i < cells.length; i++) {
                    for(int j = 0; j < cells[i].length; j++) {
                        if(cells[i][j] != null && cells[i][j].isOpened()) {
                            cellViews[i][j].setText(Character.toString(Character.toUpperCase(cells[i][j].getLetter())));
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, "Есть такое слово!", "Успех :)", JOptionPane.INFORMATION_MESSAGE);
                if(crossword.solved()) {
                    okButton.setEnabled(false);
                    JOptionPane.showMessageDialog(this, "Кроссворд разгадан!", "Успех :)", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Нет такого слова", "Неудача :(", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(okButton);

        JButton okButton2 = new JButton("OK");
        okButton2.setFont(new Font(okButton2.getFont().getName(), Font.PLAIN, 6));
        okButton2.setBounds(270 + 150 - 20, 10 + 150 - 20, 40, 40);
        add(okButton2);

        double delta = 2 * PI / crossword.getLetters().size();
        double alpha = 0;
        for(Character c : crossword.getLetters()) {
            JTextField letter = new JTextField(Character.toString(Character.toUpperCase(c)));
            letter.setBounds((int) round(270 + 150 - 15 + (150 - 15) * cos(alpha)), (int) round(10 + 150 - 15 + (150 - 15) * sin(alpha)), 30, 30);
            letter.setEditable(false);
            letter.setBorder(new LineBorder(Color.BLACK, 1));
            letter.setBackground(new Color(153, 217, 234));
            letter.setHorizontalAlignment(SwingConstants.CENTER);
            letter.setForeground(Color.BLACK);
            letter.setFont(new Font(letter.getFont().getName(), Font.PLAIN, 20));
            add(letter);
            alpha += delta;
        }

        setVisible(true);
        invalidate();
    }
}
