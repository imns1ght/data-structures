package imns1ght;

import static org.junit.Assert.assertTrue;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import imns1ght.trie.Trie;

public class AppTest {
    @Test
    public void insertTest() {
        // Preparing the tree.
        Trie trie = new Trie();
        LinkedList<String> wordList = new LinkedList<String>();

        assertTrue(trie.getSize() == 0);

        wordList.add("a");
        wordList.add("ama");
        wordList.add("amar");
        wordList.add("ame");
        wordList.add("amei");
        wordList.add("ameixa");
        wordList.add("oi");
        wordList.add("oito");
        wordList.add("eu");
        wordList.add("ele");
        wordList.add("ela");
        wordList.add("eles");
        wordList.add("elas");

        for (String word : wordList) {
            trie.insert(word);
        }

        // After insert
        assertTrue(trie.getSize() == 13);
        for (String word : wordList) {
            assertTrue(trie.search(word));
        }
    }

    @Test
    public void searchTest() {
        // Preparing the tree.
        Trie trie = new Trie();
        trie.insert("a");
        trie.insert("ama");
        trie.insert("amar");
        trie.insert("ame");
        trie.insert("amei");
        trie.insert("ameixa");
        trie.insert("oi");
        trie.insert("oito");
        trie.insert("eu");
        trie.insert("ele");
        trie.insert("ela");
        trie.insert("eles");
        trie.insert("elas");
        String toSearchFalse = "lalala";
        String toSearchTrue = "oito";

        assertTrue(trie.search(toSearchTrue));
        assertFalse(trie.search(toSearchFalse));
    }

    @Test
    public void deleteTest() {
        // Preparing the tree.
        Trie trie = new Trie();
        trie.insert("a");
        trie.insert("ama");
        trie.insert("amar");
        trie.insert("ame");
        trie.insert("amei");
        trie.insert("ameixa");
        trie.insert("oi");
        trie.insert("oito");
        trie.insert("eu");
        trie.insert("ele");
        trie.insert("ela");
        trie.insert("eles");
        trie.insert("elas");
        String toRemove = "ame";

        // Before delete.
        assertTrue(trie.getSize() == 13);
        assertTrue(trie.search(toRemove));

        trie.remove(toRemove);

        // After delete.
        assertTrue(trie.getSize() == 12);
        assertFalse(trie.search(toRemove));
    }

    @Test
    public void autoCompleteTest() {
        Trie trie = new Trie();

        trie.insert("a");
        trie.insert("ama");
        trie.insert("amar");
        trie.insert("ame");
        trie.insert("amei");
        trie.insert("ameixa");
        trie.insert("oi");
        trie.insert("oito");
        trie.insert("eu");
        trie.insert("ele");
        trie.insert("ela");
        trie.insert("eles");
        trie.insert("elas");
        String prefix = "a";

        // First
        List<String> result = trie.autoComplete(prefix, 6);
        assertTrue(result.size() == 6);

        for (String word : result) {
            assertTrue(word.startsWith(prefix));
        }

        result = trie.autoComplete(prefix, 3);
        assertTrue(result.size() == 3);

        for (String word : result) {
            assertTrue(word.startsWith(prefix));
        }

        // Second
        prefix = "am";
        result = trie.autoComplete(prefix, 5);
        assertTrue(result.size() == 5);

        for (String word : result) {
            assertTrue(word.startsWith(prefix));
        }

        result = trie.autoComplete(prefix, 3);
        assertTrue(result.size() == 3);

        for (String word : result) {
            assertTrue(word.startsWith(prefix));
        }
    }
}
