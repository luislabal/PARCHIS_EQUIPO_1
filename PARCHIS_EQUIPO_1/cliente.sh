#!/bin/sh
# Compila y abre el juego (cliente).  Pide la dirección del servidor al arrancar.
cd "$(dirname "$0")" || exit 1
mkdir -p target/classes
javac -encoding UTF-8 -d target/classes $(find src/main/java -name '*.java') || exit 1
cp -r src/main/java/imagenes target/classes/ 2>/dev/null
exec java -cp target/classes VISTA.Interfaz
