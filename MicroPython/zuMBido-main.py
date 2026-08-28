from microbit import *
import radio
import machine

#ID de la placa
id_placa = "".join("{:02x}".format(b) for b in machine.unique_id())

tiempo=0
tiempoKa=0 #Temporizador para detectar conexión activa
tempoAnterior=0
grupoRadial=7


conexion=False

radio.config(length=250, group=grupoRadial)
radio.on()


# Configuración de comunicación serial con la PC
uart.init(baudrate=115200)

buffer_serial = ""

# Configuración del canal de radio y tamaño de paquete
def activarRadio(grupo):
    grupoRadial=int(grupo)
    radio.config(length=250, group=grupoRadial)
    radio.on()
    display.set_pixel(1,0,9)

def desactivarRadio():
    radio.off()
    display.set_pixel(1,0,0)

def enviarSerial(texto):
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
    global conexion,tiempoKa, tiempo, id_placa
    datos=comando.split(':')
    orden = datos[0]
    
    if(orden=="c"):   
        if(datos[1]=='c'):
            display.set_pixel(0,0,9)
            enviarSerial("c:bid:"+id_placa)
            enviarSerial("c:gr:"+str(grupoRadial))
            conexion=True
            
        if(datos[1]=='bid'):
            enviarSerial("c:bid:"+id_placa)
        
        if(datos[1]=='gr'):
            if(len(datos)>2):
                grupo=datos[2]
                activarRadio(grupo)
                enviarSerial("m:b:Grupo radial establecido a " + grupo)
            else:
                enviarSerial("c:gr:"+str(grupoRadial))
        
        if(datos[1]=='ka'): #Keep Alive - Mantiene la conexión
            conexion=True
    
    if(orden=="r"):
       msj = ""
       for i in range(1,len(datos)):
           msj = msj+":"+datos[i] 
       enviarRadio(msj+':'+id_placa)
        
while True:
    
    tiempo = running_time()
    # if(tiempo>=tiempoKa):
    #     enviarSerial("c:ka")
    #     tiempoKa = tiempo + 1000
    # if(tiempo>=(tiempoKa-100)):
    #     conexion=False
    # if(conexion==True):
    #     
    # else:
    #     display.set_pixel(0,0,0)
    # -------------------------------------------------------------
    # 1. RADIO -> SERIAL: Mensajes recibidos de otros micro:bits
    # -------------------------------------------------------------
    mensaje_radio = radio.receive()
    if mensaje_radio:
        # Reenvía el mensaje directamente a la PC terminado en un salto de línea
        uart.write('r:'+mensaje_radio + '\n')

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
                    
                    # ** PARA PRUEBAS **
                    enviarSerial("recibido:" + comando)
                    
                    # Se evalúa el comando recibido
                    evaluarComando(comando)
                    # radio.send(comando)
                    #enviarRadio(comando)
                    buffer_serial = ""
            else:
                buffer_serial += char
    
    sleep(10)