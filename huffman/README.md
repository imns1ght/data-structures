# Huffman Coding Algorithm

## Introduction

// TODO

Compress/decompress text files using Huffman's coding.

## Compiling

Assuming you have installed [Maven](https://maven.apache.org/), [Java SE 13](https://www.oracle.com/technetwork/java/javase/downloads/index.html) and [Git](https://git-scm.com/), we can follow these steps:

1. Download the project and moves into the root directory
    ```
    git clone https://github.com/imns1ght/huffman && cd huffman/
    ``` 

2. Compile and create .jar
    ```
    mvn package
    ```
    After that, the library `huffman.jar` it's located in `target/`.

3. Create a symlink (shortcut) for the executable.
    ```
    ln -sf target/huffman.jar .
    ```

## Usage

Assuming `$` is the terminal prompt, you can use: 

```bash
java -jar huffman.jar compress input.txt compressed.edz table.edt
```
```bash
java -jar huffman.jar extract compressed.edz table.edt output.txt
```

### Running tests

Just run as usual (assuming `$` is the terminal prompt):

```
./fastrun.sh
```

## TODO

- [ ] 
- [ ] 
- [ ] 

## Contributing
You are welcome! Create the pull requests. 

For major changes, please, open an issue first to discuss what you would like to change.

## Support
* Twitter: [@imns1ght](https://twitter.com/imns1ght) | E-mail: [jeffersonbrunoIT [at] gmail [dot] com](mailto:jeffersonbrunoit@gmail.com)
* E-mail: [Ranieri Santos](RanieriSantos@pm.me)

## Authors and acknowledgment
### Authors
* [Jefferson Bruno (imns1ght)](https://imns1ght.github.io)
* [Ranieri Santos](https://github.com/RanieriSantos)

## License
[MIT](https://choosealicense.com/licenses/mit/)
