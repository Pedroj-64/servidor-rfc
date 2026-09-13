#!/usr/bin/env bash

set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAR="$PROJECT_DIR/target/servidor-rfc-1.0.jar"
STOPPING=0

cd "$PROJECT_DIR"

if [ ! -f "$JAR" ]; then
    echo "No existe el JAR. Compilando el proyecto..."
    mvn clean package -DskipTests
fi

cleanup() {
    if [ -n "${BACKEND_PID:-}" ] && kill -0 "$BACKEND_PID" 2>/dev/null; then
        kill "$BACKEND_PID" 2>/dev/null || true
        wait "$BACKEND_PID" 2>/dev/null || true
    fi

    if [ -n "${RFC_PID:-}" ] && kill -0 "$RFC_PID" 2>/dev/null; then
        kill "$RFC_PID" 2>/dev/null || true
        wait "$RFC_PID" 2>/dev/null || true
    fi
}

stop() {
    STOPPING=1
    exit 130
}

trap cleanup EXIT
trap stop INT TERM

echo "Iniciando servidor RFC en el puerto 5000..."
java -cp target/classes com.uniquindio.lab.rfc.ServidorRFC &
RFC_PID=$!

echo "Iniciando aplicación web en http://localhost:8080 ..."
java -jar "$JAR" &
BACKEND_PID=$!

if ! wait "$BACKEND_PID"; then
    if [ "$STOPPING" -eq 0 ]; then
        echo "La aplicación web no pudo iniciar. Revisa si el puerto 8080 ya está ocupado." >&2
    fi
    exit 1
fi