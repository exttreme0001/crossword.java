import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Dictionary {
    private final Set<String> words;
    public Dictionary(File file) throws IOException {
        words = new LinkedHashSet<>();
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String word;
        while((word = bufferedReader.readLine()) != null) {
            words.add(word);
        }
        bufferedReader.close();
    }
    public void output() {
        System.out.println("Словарь:");
        for(String word : words) {
            System.out.println(word);
        }
    }
    public void add(String word) {
        words.add(word);
    }
    public void addAll(Collection<String> c) {
        words.addAll(c);
    }
    public List<String> search(WordSelector selector) {
        List<String> result = new ArrayList<>();
        for(String word : words) {
            if(selector.select(word)) {
                result.add(word);
            }
        }
        return result;
    }
    public void remove(String word) {
        words.remove(word);
    }
}
