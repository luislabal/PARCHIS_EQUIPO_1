#!/bin/sh
# Compila y enciende el servidor de Parchís.  Uso: ./servidor.sh [puerto]   (por defecto 5000)
cd "$(dirname "$0")" || exit 1
mkdir -p target/classes
javac -encoding UTF-8 -d target/classes $(find src/main/java -name '*.java') || exit 1
cp -r src/main/java/imagenes target/classes/ 2>/dev/null
exec java -cp target/classes MODELO.ServidorParchis "$@"
