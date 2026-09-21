#!/bin/bash

# Source - https://stackoverflow.com/a/59839
# Posted by Grundlefleck, modified by community. See post 'Timeline' for change history
# Retrieved 2026-09-20, License - CC BY-SA 4.0



# Verificar que se haya pasado un archivo
if [ -z "$1" ]; then
    echo "Uso: $0 <archivo.py o archivo.mpy> [nombre_destino.mpy]"
    exit 1
fi

dir="./salida"

if [ ! -d "$dir" ]; then
  mkdir "$dir"
fi

ARCHIVO_ORIGEN="$dir/main.mpy"
# Por defecto se guardará como 'main.py' (o 'main.mpy') en la micro:bit
ARCHIVO_DESTINO="${2:-main.py}"

mpy-cross "$1" -o "$dir/main.mpy"

# Recorrer todos los puertos serie USB reconocidos por Linux para las micro:bit
for port in /dev/ttyACM*; do
    # Comprobar que el puerto realmente exista
    if [ -e "$port" ]; then
        echo "Enviando $ARCHIVO_ORIGEN a $port (como $ARCHIVO_DESTINO)..."
        
        python3 -c "
import microfs, serial, time
try:
    s = serial.Serial('$port', 115200, timeout=1, parity='N')
    s.write(b'\x03\x03')
    time.sleep(0.2)
    s.reset_input_buffer()
    microfs.put('$ARCHIVO_ORIGEN', '$ARCHIVO_DESTINO', serial=s)
    s.close()
    print(' -> ¡Éxito en $port!')
except Exception as e:
    print(f' -> Error en $port: {e}')
"
    fi
done