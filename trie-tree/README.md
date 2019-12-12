# Trie Data Structure

## Introduction

// TODO

## Compiling

Assuming you have installed [Maven](https://maven.apache.org/), [Java SE 13](https://www.oracle.com/technetwork/java/javase/downloads/index.html) and [Git](https://git-scm.com/), we can follow these steps:

1. Download the project and moves into the root directory
    ```
    git clone https://github.com/imns1ght/trie-tree && cd trie-tree/
    ``` 

2. Compile and create .jar
    ```
    mvn package
    ```
    After that, the library `trie.jar` it's located in `target/`.

3. Create a symlink (shortcut) for the executable.
    ```
    ln -sf target/trie.jar .
    ```

## Usage

Assuming `$` is the terminal prompt, you can use: 

```bash
$ java -jar trie.jar words.txt am
ama
ame
amar
amei
ameixa
```

```bash
$ java -jar trie.jar words.txt am 4
ama
ame
amar
amei
```

## TODO

- [x] Review code
- [x] Write JUnit tests
- [ ] Write Javadoc
- [ ] Treat args error.

## Contributing
You are welcome! Create the pull requests. 

For major changes, please, open an issue first to discuss what you would like to change.

## Support
* Twitter: [@imns1ght](https://twitter.com/imns1ght) | E-mail: [jeffersonbrunoIT [at] gmail [dot] com](mailto:jeffersonbrunoit@gmail.com)

## Authors and acknowledgment
### Authors
* [Jefferson Bruno (imns1ght)](https://imns1ght.github.io)

### Acknowledgment
* [GeeksForGeeks](https://www.geeksforgeeks.org)

## License
[MIT](https://choosealicense.com/licenses/mit/)
