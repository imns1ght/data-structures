package imns1ght;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import imns1ght.trie.Trie;

public class AutoComplete {
        protected String[] args;
        private String inputPath;
        private String prefix;
        private LinkedList<String> wordsFromFile;

        public AutoComplete(String[] args) {
                if (args == null || args.length <= 1) {
                        printHelp();
                } else {
                        this.args = args;
                        this.inputPath = this.args[0];
                        this.prefix = this.args[1];
                        this.wordsFromFile = new LinkedList<String>();
                }
        }

        protected void readFromFile() {
                ReadFile input = new ReadFile(inputPath);
                this.wordsFromFile = input.extract();
        }

        protected void execute() throws IOException {
                if (this.args != null && this.inputPath != null && this.prefix != null) {
                        readFromFile();
                        Trie trie = new Trie(this.wordsFromFile);
                        List<String> result = new ArrayList<String>();

                        if (this.args.length < 3) {
                                result = trie.autoComplete(prefix, 1000);
                        } else if (this.args.length == 3) {
                                result = trie.autoComplete(prefix, Integer.parseInt(this.args[2]));
                        }

                        for (String str : result) {
                                System.out.println(str);
                        }
                }
        }

        protected void printHelp() {
                System.out.println("Invalid argument.\n");
                System.out.println("Use: java -jar trie.jar <words.txt> <prefix> <limit>\n");
                System.out.println("Ex: java -jar trie.jar words.txt am");
                System.out.println("Ex: java -jar trie.jar words.txt am 4\n");
                System.out.println("  words.txt - Words to insert.");
                System.out.println("  prefix - Prefix to search.");
                System.out.println("  amount - Limit of words to print.\n");
        }
}
