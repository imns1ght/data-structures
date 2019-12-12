package huffman;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.HashMap;

/**
 * The {@code InputFile} class implements the necessary functions to read the
 * text from files and use it in the operations of compress/decompress.
 * 
 * @author Ranieri Santos
 * @author imns1ght
 */
public class IOFile {
        private HashMap<String, Integer> symbolTable;
        private HashMap<Integer, Integer> charsFromFile; // Chars readed from file.
        private FileInputStream in; // Aux to reset buffer to the beginning.
        private BufferedReader bufferedChars; // Input file.

        /**
         * Compress constructor.
         * 
         * @param input_path path of bufferedChars file.
         * @throws UnsupportedEncodingException
         */
        public IOFile(String input_path) throws UnsupportedEncodingException {
                try {
                        this.in = new FileInputStream(new File(input_path));
                        this.bufferedChars = new BufferedReader(new InputStreamReader(this.in));
                        this.charsFromFile = new HashMap<Integer, Integer>();
                        fileToMap();
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                }
        }

        /**
         * Decompress constructor.
         * 
         * @param input_path path of bufferedChars file.
         * @param map        path of char map.
         */
        public IOFile(String input_path, String table_path, String output_path) throws IOException {
                try {
                        this.symbolTable = new HashMap<String, Integer>();
                        edtToTable(table_path);

                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                }
        }

        /**
         * Check if directory exists.
         * 
         * @param file path to the file, or to be created.
         */
        private void fileExists(String file) {
                int i;
                if (file.contains("/")) {
                        for (i = file.length(); i >= 0; i--) {
                                if (file.charAt(i - 1) == '/')
                                        break;
                        }
                        File checkFile = new File(file.substring(0, i));
                        checkFile.mkdirs();
                }
        }

        /**
         * Writes compressed file and map.
         * 
         * @param map_bin     Map of characters.
         * @param output_path Compressed file save path.
         * @param compressMap Map save path.
         * @throws IOException
         */
        protected void writeToFile(HashMap<Integer, String> map_bin, String output_path, String compressMap)
                        throws IOException {
                fileExists(output_path);
                fileExists(compressMap);
                BitSet bits = new BitSet();

                OutputStream compressed = new BufferedOutputStream(new FileOutputStream(output_path));
                // BufferedWriter debug_compressed = new BufferedWriter(new
                // FileWriter("assets/output/debug.txt"));

                writeSymbolTable(map_bin, compressMap);

                int num_bitsets = 0; // Number of bits
                int charac;
                String curr_key = "";
                // Write compressed file.
                while ((charac = this.bufferedChars.read()) != -1) {
                        curr_key = map_bin.get(charac);

                        if (curr_key != null) {
                                for (int i = 0; i < curr_key.length(); i++) {
                                        bits.set(num_bitsets++, curr_key.charAt(i) - '0' > 0 ? true : false);
                                }

                                // debug_compressed.append(curr_key);
                        }
                }

                curr_key = map_bin.get(-1); // Add EOF

                if (curr_key != null) {
                        for (int i = 0; i < curr_key.length(); i++) {
                                bits.set(num_bitsets++, curr_key.charAt(i) - '0' > 0 ? true : false);
                        }

                        // debug_compressed.append(curr_key);
                }

                byte[] bytes = bits.toByteArray();
                compressed.write(bytes);
                compressed.close();
                // debug_compressed.close();
        }

        /**
         * Writes hash map to file.
         * 
         * @param map_bin     Map with ASCII code of readed characters from file.
         * @param compressMap Path to sava map.
         * @throws IOException
         */
        protected void writeSymbolTable(HashMap<Integer, String> map_bin, String compressMap) {
                try {
                        BufferedWriter symbol_table = new BufferedWriter(new FileWriter(compressMap));
                        // Write symbol table.
                        for (HashMap.Entry<Integer, String> entry : map_bin.entrySet()) {
                                if (entry.getKey() == -1) {
                                        symbol_table.append("EOF" + entry.getValue() + '\n');
                                } else if (entry.getKey() == 10) {
                                        symbol_table.append("EOL" + entry.getValue() + '\n');
                                } else if (entry.getKey() == 13) {
                                        symbol_table.append("CR" + entry.getValue() + '\n');
                                } else {
                                        symbol_table.append(Character.toString((char) (int) entry.getKey())
                                                        + entry.getValue() + '\n');
                                }
                        }
                        symbol_table.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        /**
         * Recover the original file content... or tries.
         * 
         * @param input_path  Compressed file path.
         * @param output_path Decompressed file path.
         * @throws IOException
         */
        protected void extract(String input_path, String output_path) throws IOException {
                fileExists(output_path);
                BitSet bits = BitSet.valueOf(Files.readAllBytes(Paths.get(input_path)));
                BufferedWriter decompressed = new BufferedWriter(new FileWriter(output_path));

                int i = 0;
                String word = "";

                while (!bits.isEmpty()) {
                        word += bits.get(i) == true ? Integer.toString(1) : Integer.toString(0);
                        Integer curr_key = this.symbolTable.get(word);

                        if (curr_key != null && curr_key.intValue() == -1) {
                                break;
                        } else if (curr_key != null) {
                                decompressed.write((char) curr_key.intValue());
                                word = "";
                        }
                        i++;
                }

                decompressed.close();
        }

        /**
         * Count character occurrences from file to the hash map.
         * 
         * @return reference to the hash map.
         */
        protected HashMap<Integer, Integer> fileToMap() {
                int charac;
                try {
                        while ((charac = this.bufferedChars.read()) != -1) {
                                if (this.charsFromFile.containsKey(charac)) {
                                        this.charsFromFile.put(charac, this.charsFromFile.get(charac) + 1);
                                } else {
                                        this.charsFromFile.put(charac, 1);
                                }
                        }
                        this.charsFromFile.put(-1, 1);
                        this.in.getChannel().position(0);
                        this.bufferedChars = new BufferedReader(new InputStreamReader(this.in));
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return this.charsFromFile;
        }

        /**
         * 
         * @param symbolTablePath Path to the file map of compressed file.
         * @return A HashMap of symbols.
         * @throws FileNotFoundException If bufferedChars file not found.
         */
        protected void edtToTable(String table_path) throws FileNotFoundException {
                BufferedReader bufferedChars = new BufferedReader(new FileReader(table_path));
                String line;

                try {
                        while ((line = bufferedChars.readLine()) != null) {
                                if (line.contains("EOF")) {// End of file
                                        this.symbolTable.put(line.substring(3), -1);
                                } else if (line.contains("EOL")) { // End of line
                                        this.symbolTable.put(line.substring(3), 10);
                                } else if (line.contains("CR")) { // Carriage return
                                        this.symbolTable.put(line.substring(2), 13);
                                } else {
                                        this.symbolTable.put(line.substring(1), (int) line.charAt(0));
                                }
                        }
                        bufferedChars.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        /**
         * Print the hash map.
         */
        protected void printHashMap() {
                this.charsFromFile.entrySet().forEach(entry -> {
                        System.out.println("[ " + entry.getKey() + " | " + entry.getValue() + "\t]");
                });
        }
}
