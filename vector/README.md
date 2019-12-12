# Vector

## Introduction

`Vector` is an implementation of a sequence container based in the [STL Vector](https://en.cppreference.com/w/cpp/container/vector).

## TODO

- [ ] Fix insert functions

## Compiling

Assuming you have installed [CMake](https://cmake.org), [make](https://www.gnu.org/software/make/), [GTest](https://github.com/google/googletest) and [Git](https://git-scm.com/), we can follow these steps:

1. Download the project and moves into the root directory
    ```
    git clone https://github.com/imns1ght/vector && cd vector/
    ``` 

2. Create build files
    ```
    cmake -B build/ 
    ``` 

3. Start the compiling process. 
    ```
    make install -C build/
    ```
    After that, the library (`libvector.a`) it's located in `lib/`.

4. Create a symlink (shortcut) for the executable.
    ```
    ln -sf build/run_tests .
    ```

Finally, we have an executable inside the `build` directory that tests the library functions.
Also, we will have a library `libvector.a` in which the functions are compiled.

## Usage

You can use as the [STL Vector](https://en.cppreference.com/w/cpp/container/vector). See the simple [example](https://en.cppreference.com/w/cpp/container/vector#Example) below.

Example:
```cpp
#include <iostream>
#include "../include/vector.h"

int main() {
        // Create a vector containing integers
        alt::vector<int> v = {7, 5, 16, 8};

        // Add two more integers to vector
        v.push_back(25);
        v.push_back(13);

        // Iterate and print values of vector
        for (int n : v) {
                std::cout << n << '\n';
        }
}
```

Output:
```
7
5
16
8
25
13
```

## Running tests

Just run as usual (assuming `$` is the terminal prompt):

```
./run_tests
```

## Contributing
You are welcome! Create the pull requests. 

For major changes, please, open an issue first to discuss what you would like to change.

## Support
* Twitter: [@imns1ght](https://twitter.com/imns1ght) | E-mail: [jeffersonbrunoIT [at] gmail [dot] com](mailto:jeffersonbrunoit@gmail.com)

## Authors and acknowledgment
### Authors
* [Jefferson Bruno (imns1ght)](https://imns1ght.github.io)
* [Ranieri Santos](https://github.com/RanieriSantos)

### Acknowledgment
* [Professor Selan R. dos Santos](https://www.dimap.ufrn.br/~selan/)
* [GeeksforGeeks](https://www.geeksforgeeks.org/)
* [C++ Reference 1](https://en.cppreference.com/w/)
* [C++ Reference 2](http://www.cplusplus.com/reference/)

## License
[MIT](https://choosealicense.com/licenses/mit/)
