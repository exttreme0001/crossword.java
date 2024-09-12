import java.util.regex.Pattern;

public class WordRegexpSelector implements WordSelector {
    private final Pattern regexp;

    public WordRegexpSelector(String regexp) {
        this.regexp = Pattern.compile(regexp);
    }

    @Override
    public boolean select(String word) {
        return regexp.matcher(word).matches();
    }
}
