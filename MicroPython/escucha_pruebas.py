from microbit import *
import radio
import gc

# Configuración inicial de la radio
grupoRadial=7
radio.config(length=250, group=grupoRadial)
radio.on()

# Configuración de comunicación serial con la PC
uart.init(baudrate=115200)

def enviarSerial(texto):
    """Envía un mensaje desde la placa a la App mediante la comuniación serial UART

    Args:
        texto (string): mensaje a ser enviado a la App
    """
    uart.write(texto + '\r\n')
    gc.collect()

while True:

            
            mensaje_radio = radio.receive()
            
            if mensaje_radio:
                # Reenvía el mensaje directamente a la PC terminado en un salto de línea
                enviarSerial(mensaje_radio)