import java.io.File;
import java.io.IOException;
import java.util.List;

public class Cheat {
	public static void main(String[] args) {
		if(args.length > 0) {
			try {
				Dictionary dictionary = new Dictionary(new File("dictionary.txt"));
				List<String> words = dictionary.search(new WordRegexpSelector(args[0]));
				for(String word : words) {
					System.out.println(word);
				}
			} catch(IOException e) {
				System.out.println("Файл dictionary.txt не может быть прочитан");
			}
		} else {
			System.out.println("Необходим аргумент - регулярное выражение");
		}
	}
}
