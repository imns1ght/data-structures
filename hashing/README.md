# Hashing

## Introduction

Study of Hashing with C++ language.

## TODO
- [ ] Review this old code
- [ ] Write a better README.md :P

## Compiling

Assuming you have installed [CMake](https://cmake.org), [GTest](https://github.com/google/googletest) and [Git](https://git-scm.com/), we can follow these steps:

1. Download the project and moves into the root directory
    ```
    git clone https://github.com/imns1ght/hashing && cd hashing/
    ``` 

2. Create a new directory in which the compilation output will be stored (including the executable).
    ```
    mkdir build
    ``` 

3. Moves into the directory created in the step above.
    ```
    cd build
    ```

4. Create the Makefile based on the script found in `CMakeLists.txt`, one level up.
    ```
    cmake -G "Unix Makefiles" .. .
    ```

5. Start the compiling process
    ```
    cmake --build .
    ```

6. Copies the library to the `lib` directory.
    ```
    make install
    ```  

7. Create a symlink (shortcut) from the `run_tests`. Anyway, it's optional.
    ```
    cd .. && ln -sf build/run_tests .
    ```

Finally, we have an executable inside the `build` directory that tests the library functions.
Also, we will have a library `libhashing.a` in which the functions are compiled.

## Usage

Just run as usual (assuming `$` is the terminal prompt):

```
./run_tests
```

## Contributing
You are welcome! Create the pull requests. 

For major changes, please, open an issue first to discuss what you would like to change.

## Support
* Twitter: [@imns1ght](https://twitter.com/imns1ght) | E-mail: [jeffersonbrunoIT [at] gmail [dot] com](mailto:jeffersonbrunoit@gmail.com)
* E-mail [RanieriSantos [at] protonmail [dot] com](mailto:RanieriSantos@protonmail.com)

## Authors and acknowledgment
### Authors
* [Jefferson Bruno (imns1ght)](https://imns1ght.github.io)
* [Ranieri Santos](https://github.com/RanieriSantos)

### Acknowledgment
* [Professor Selan R. dos Santos](https://www.dimap.ufrn.br/~selan/)
* [GeeksforGeeks](https://www.geeksforgeeks.org/)
* [C++ Reference](https://en.cppreference.com/w/)

## License
[MIT](https://choosealicense.com/licenses/mit/)
