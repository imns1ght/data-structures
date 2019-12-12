package huffman;

import java.io.IOException;

/**
 * The {@code Decompress} class implements functions to decompress text files
 * compressed using the Huffman's coding algorithm.
 * 
 * @author Ranieri Santos
 * @author imns1ght
 */
public class Decompress {
        private IOFile iofile;
        private String input_path;
        private String table_path;
        private String output_path;

        /**
         * Default contructor.
         * 
         * @param input_path  Compressed file path.
         * @param table_path  Table of compressed file.
         * @param output_path Output path o decompressed file.
         * @throws IOException
         */
        public Decompress(String input_path, String table_path, String output_path) throws IOException {
                this.input_path = input_path;
                this.table_path = table_path;
                this.output_path = output_path;
                iofile = new IOFile(this.input_path, this.table_path, this.output_path);
        }

        /**
         * Starts decompress process.
         */
        protected void startDecompress() {
                try {
                        iofile.extract(this.input_path, this.output_path);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
