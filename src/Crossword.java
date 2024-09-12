import java.util.*;

public class Crossword {
	private final Map<String, WordInfo> words;
	private final Cell[][] field = new Cell[5][5];
	private final Set<Character> letters = new TreeSet<>();
	private final RegexpBuilder[] builders = {
		() -> "[а-я]{5}",
		() -> field[0][0].letter + "[а-я]{4}",
		() -> field[2][0].letter + "[а-я]{4}",
		() -> field[0][2].letter + "[а-я]" + field[2][2].letter + "[а-я]{2}",
		() -> field[4][0].letter + "[а-я]" + field[4][2].letter + "[а-я]{2}",
		() -> field[0][4].letter + "[а-я]" + field[2][4].letter + "[а-я]" + field[4][4].letter
	};

	public Crossword(Dictionary dict, int n, int m) throws IncorrectAmountOfOpenLettersInCrosswordException {
		words = new LinkedHashMap<>(6);
		if(0 <= n && n <= 21) {
			Map<String, WordInfo> mapWords = new LinkedHashMap<>(6);
			String word;
			List<String> words;
			WordInfo wordInfo;
			generator: while(true) {
				dict.addAll(mapWords.keySet());
				mapWords.clear();
				for(int i = 0; i < field.length; i++) {
					for(int j = 0; j < field[i].length; j++) {
						field[i][j] = new Cell();
					}
				}
				field[1][1] = null;
				field[3][1] = null;
				field[1][3] = null;
				field[3][3] = null;

				for(int i = 0; i < builders.length; i++) {
					words = dict.search(new WordRegexpSelector(builders[i].build()));
					if(words.isEmpty()) continue generator;
					word = randomChoose(words);
					dict.remove(word);
					wordInfo = new WordInfo(i / 2, WordType.values()[i % 2]);
					mapWords.put(word, wordInfo);
					putOnField(word, wordInfo);
				}

				for(String str : mapWords.keySet()) {
					addLetters(str);
				}
				if(letters.size() > m) {
					letters.clear();
					continue;
				}
				break;
			}
			this.words.putAll(mapWords);
			Random random = new Random();
			for(int k = 0; k < n;) {
				int i = random.nextInt(field.length);
				int j = random.nextInt(field[i].length);
				if(field[i][j] != null && !field[i][j].isOpened) {
					field[i][j].isOpened = true;
					k++;
				}
			}
		} else {
			throw new IncorrectAmountOfOpenLettersInCrosswordException(n);
		}
	}

	public void outputTmp() {
		for(Map.Entry<String, WordInfo> entry : words.entrySet()) {
			String key = entry.getKey();
			WordInfo value = entry.getValue();
			System.out.println(key + " - " + value);
		}
	}

	public void output() {
		System.out.print("\u250C");
		for(int i = 0; i < 5; i++) {
			if(i != 0) {
				System.out.print("\u252C");
			}
			System.out.print("\u2500\u2500\u2500");
		}
		System.out.println("\u2510");
		for(int i = 0; i < field.length; i++) {
			if(i != 0) {
				System.out.print("\u251C");
				for(int k = 0; k < 5; k++) {
					if(k != 0) {
						System.out.print("\u253C");
					}
					System.out.print("\u2500\u2500\u2500");
				}
				System.out.println("\u2524");
			}
			System.out.print("\u2502");
			for(int j = 0; j < field[i].length; j++) {
				if(field[i][j] != null) {
					System.out.print(" " + (field[i][j].isOpened ? field[i][j].letter : " ") + " ");
				} else {
					System.out.print("\u2588\u2588\u2588");
				}
				System.out.print("\u2502");
			}
			System.out.println();
		}
		System.out.print("\u2514");
		for(int i = 0; i < 5; i++) {
			if(i != 0) {
				System.out.print("\u2534");
			}
			System.out.print("\u2500\u2500\u2500");
		}
		System.out.println("\u2518");
		System.out.println("Буквы: " + letters);
	}

	public boolean tryWord(String word) {
		WordInfo wordInfo = words.get(word);
		if(wordInfo != null) {
			for(int i = 0; i < word.length(); i++) {
				switch (wordInfo.type) {
					case HORIZONTAL -> field[2 * wordInfo.index][i].isOpened = true;
					case VERTICAL -> field[i][2 * wordInfo.index].isOpened = true;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean solved() {
		for(int i = 0; i < field.length; i++) {
			for(int j = 0; j < field[i].length; j++) {
				if(field[i][j] != null && !field[i][j].isOpened) {
					return false;
				}
			}
		}
		return true;
	}

	public Cell[][] getField() {
		return field;
	}

	public Set<Character> getLetters() {
		return letters;
	}

	private String randomChoose(List<String> words) {
		Random random = new Random();
		return words.get(random.nextInt(words.size()));
	}

	private void addLetters(String word) {
		for(int i = 0; i < word.length(); i++) {
			letters.add(word.charAt(i));
		}
	}

	private void putOnField(String word, WordInfo wordInfo) {
		for(int i = 0; i < word.length(); i++) {
			Cell cell = new Cell(word.charAt(i));
			switch (wordInfo.type) {
				case HORIZONTAL -> field[2 * wordInfo.index][i] = cell;
				case VERTICAL -> field[i][2 * wordInfo.index] = cell;
			}
		}
	}

	public static class Cell {
		private char letter;
		private boolean isOpened;

		public Cell() {}

		public Cell(char letter) {
			this.letter = letter;
		}

		public char getLetter() {
			return letter;
		}

		public boolean isOpened() {
			return isOpened;
		}
	}

	private enum WordType {HORIZONTAL, VERTICAL}

	private static class WordInfo {
		int index;
		WordType type;

		public WordInfo(int index, WordType type) {
			this.index = index;
			this.type = type;
		}
	}

	private interface RegexpBuilder {
		String build();
	}
}
