package imns1ght;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * The {@code InputFile} class implements the necessary functions to read the text from files.
 * 
 * @author imns1ght
 */
public class ReadFile {
        private LinkedList<String> charsFromFile; // Chars readed from file.
        private Scanner input;

        public ReadFile(String input_path) {
                try {
                        File file = new File(input_path);
                        this.input = new Scanner(file);
                        this.charsFromFile = new LinkedList<String>();
                        
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                }
        }

        protected LinkedList<String> extract() {
                while (input.hasNext()) {
                        String word = input.next();
                        this.charsFromFile.add(word);
                }

                return this.charsFromFile;
        }
}
