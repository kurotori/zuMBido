# Diccionario de Claves, Funciones, y Variables

## Archivo: zuMBido-main.py

### Variables y Constantes

#### Constantes
- **VER** : Versión del código
- **IKA** : Intervalo de milisgundos para chequear/enviar la señal Keep Alive
- **IEM** : Intervalo entre envío de mensajes
- **MMSG** : Cantidad máxima de mensajes admitidos en la cola de mensajes
- **IDP** : ID de la placa

---

#### Variables

- **tka** : Tiempo Keep Alive, marca de tiempo de la última señal Keep Alive, para detectar una conexión activa desde la aplicación.
- **lOn** : LEDs ON, lista de LEDs para encender (para parpadeo)
- **lOff** : LEDs OFF, lista de LEDs para apagar (para parpadeo)
- **msgs** : Mensajes, lista de mensajes para enviar por radio
- **uMsg** : Último Mensaje, marca de tiempo del último mensaje enviado
- **grpR** : Grupo Radial, número de grupo radial aactivo en la placa
- **rOn** : Radio Activa, almacena si el sistema radial esta activo o no
- **bfSr** : Buffer Serial, almacena, temporalmente, los datos obtenidos desde la conexión serial

---

### Funciones

- **radioOn(g)** : Activa la comunicación radial del sistema y/o cambia el grupo radial.
    - g (int): numero de grupo radial que se quiere usar con la placa.
- **radioOff()** : Desactiva la comunicación radial del sistema
- **enviarS(t)** : Envía un mensaje desde la placa a la App mediante la comuniación serial UART
    - t (String): Mensaje a ser enviado hacia la App
- **enviarR(t)** :  Envía un mensaje por el sistema radial de la placa
    - t (string): mensaje a ser enviado. Debe seguir los parámetros de forma de los mensajes
- **agrMsg(m,p)** : Agrega un mensaje, y su nivel de prioridad a la cola de mensajes radiales.
    - m (String): El texto del mensaje. Debe seguir el patrón de formato de los mensajes
    - p (Boolean): Si es `True` el mensaje se considera prioritario y se añade al principio de la cola de mensajes.
- **pLed(l)** : Agrega un LED a la lista de encendido (*lOn*).
    - l (int): Número del LED a encender, entre 0 y 4. 
    > NOTA: Solo se consideran los LEDs de la primera fila de la matríz de LEDs de la micro:bit

