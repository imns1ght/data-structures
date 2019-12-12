#!/bin/bash
HELP="Uso: ./run.sh [opção]\n\t-rc\tRemove todos os .class.\n\t-h\tImprime ajuda."

if [ $# -eq 0 ]; then
        clear
        javac src/main/java/huffman/*.java -d ./target/classes/huffman/
        java -cp ./target/classes/huffman huffman.Main
else
        while [ "$1" != "" ]; do
                case $1 in
                -rc)
                        rm -rf ./target/
                        ;;

                -h | --help)
                        echo -e $HELP
                        exit
                        ;;
                *)
                        echo -e "Argumento inválido. \n"
                        echo -e $HELP
                        exit
                        ;;
                esac
                shift
        done
fi
