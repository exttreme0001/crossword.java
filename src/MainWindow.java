import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MainWindow {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Dictionary dictionary = new Dictionary(new File("dictionary.txt"));
            int n = Integer.parseInt(JOptionPane.showInputDialog("Сколько букв открыть (от 0 до 21):", "12"));
            int m = Integer.parseInt(JOptionPane.showInputDialog("Сколько уникальных букв использовать:", "8"));
            Crossword crossword = new Crossword(dictionary, n, m);
            new Window(crossword);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Файл dictionary.txt не может быть прочитан", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Введено не целое число", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (IncorrectAmountOfOpenLettersInCrosswordException e) {
            System.out.println("Нельзя открыть " + e.getIncorrectAmountOfOpenLetters() + " букв");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, "Ошибка инициализации графики", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
