from microbit import *
import radio
import machine

#ID de la placa
id_placa = "".join("{:02x}".format(b) for b in machine.unique_id())

tiempo=0
tiempoKa=0 #Temporizador para detectar conexión activa
tempoAnterior=0

conexion=False

radio.config(length=250)
radio.on()


# Configuración de comunicación serial con la PC
uart.init(baudrate=115200)

buffer_serial = ""

# Configuración del canal de radio y tamaño de paquete
def activarRadio(grupo):
    radio.config(length=250, group=int(grupo))
    radio.on()
    display.set_pixel(1,0,9)

def desactivarRadio():
    radio.off()
    display.set_pixel(1,0,0)

def escribir(texto):
    uart.write(texto + '\r\n')
    
def enviarRadio(mensaje):
    radio.send(mensaje)
    parpadear(4,0,9,100)

def parpadear(xLed, yLed, intensidad, tiempo):
    fin = running_time() + tiempo
    while(running_time()<fin):
        display.set_pixel(xLed,yLed,intensidad);
    display.set_pixel(xLed,yLed,0);
    

def evaluarComando(comando):
    datos=comando.split(':')
    orden = datos[0]
    
    if(orden=="c"):    
        if(datos[1]=='c'):
            display.set_pixel(0,0,9);
            escribir("c:bid:"+id_placa)
            escribir("c:gr")
            conexion=True
        
        if(datos[1]=='ka'): #Keep Alive - Mantiene la conexión
            conexion=True
    if(orden=="gr"):
        grupo=datos[1]
        activarRadio(grupo)
        escribir("m:b:Grupo establecido a " + grupo)
        
while True:
    tiempo = running_time()
    # -------------------------------------------------------------
    # 1. RADIO -> SERIAL: Mensajes recibidos de otros micro:bits
    # -------------------------------------------------------------
    mensaje_radio = radio.receive()
    if mensaje_radio:
        # Reenvía el mensaje directamente a la PC terminado en un salto de línea
        uart.write(mensaje_radio + '\n')

    # -------------------------------------------------------------
    # 2. SERIAL -> RADIO: Comandos enviados desde la app en Java
    # -------------------------------------------------------------
    if uart.any():
        char_bytes = uart.read(1)
        if char_bytes:
            char = str(char_bytes, 'utf-8')
            
            # Detectar fin de comando (\n o \r)
            if char == '\n' or char == '\r':
                comando = buffer_serial.strip()
                if comando:
                    # Emitir el comando a la red RF
                    escribir("recibido:" + comando)
                    evaluarComando(comando)
                    # radio.send(comando)
                    enviarRadio(comando)
                    buffer_serial = ""
            else:
                buffer_serial += char
    
    sleep(10)