import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainConsole {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Dictionary dictionary = new Dictionary(new File("dictionary.txt"));
            System.out.println("Сколько букв открыть (от 0 до 21): ");
            int n = Integer.parseInt(scanner.nextLine());
            Crossword crossword = new Crossword(dictionary, n, 8);
            while (!crossword.solved()) {
                crossword.output();
                System.out.print("Введите слово: ");
                String word = scanner.nextLine();
                if (crossword.tryWord(word)) {
                    System.out.println("Есть такое слово");
                } else {
                    System.out.println("Такого слова нет");
                }
            }
            System.out.println("Поздравляем, кроссворд разгадан!");
        } catch (IOException e) {
            System.out.println("Файл dictionary.txt не может быть прочитан");
        } catch (NumberFormatException e) {
            System.out.println("Введено не целое число");
        } catch (IncorrectAmountOfOpenLettersInCrosswordException e) {
            System.out.println("Нельзя открыть " + e.getIncorrectAmountOfOpenLetters() + " букв");
        }
    }
}
