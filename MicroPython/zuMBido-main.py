from microbit import *
import radio
import machine
import gc

tiempo=0
INTERVALO_KEEP_ALIVE = 5000
tiempoKa=running_time() +  INTERVALO_KEEP_ALIVE#Temporizador para detectar conexión activa
tempoAnterior=0

ledsOn = []
ledsOff = []

# Cola de salida de mensajes radiales
mensajes = []
ultimo_envio = 0
INTERVALO_ENVIO_MS = 50  # Pausa mínima entre transmisiones por radio
MAX_COLA = 10

conexion=False

# Configuración inicial de la radio
grupoRadial=7
radio.config(length=250, group=grupoRadial)
radio.on()
radioAct = True


# Configuración de comunicación serial con la PC
uart.init(baudrate=115200)

buffer_serial = bytearray() #""

ID_PLACA = "".join("{:02x}".format(b) for b in machine.unique_id())
 

# Configuración del canal de radio y tamaño de paquete
def activarRadio(grupo):
    global radioAct
    """Activa la comunicación radial del sistema y/o cambia el grupo radial.

    Args:
        grupo (int): numero de grupo radial que se quiere usar con la placa.
    """
    global grupoRadial
    grupoRadial=int(grupo)
    radio.config(length=250, group=grupoRadial)
    radio.on()
    radioAct = True
    display.set_pixel(1,0,9)

def desactivarRadio():
    global radioAct
    """Desactiva la comunicación radial y notifica a la red que la placa se desconectó
    """
    #agregarMensaje('lo:'+ID_PLACA) # Se envía un mensaje de logout ("lo") notificando a la red
    radio.off()
    display.set_pixel(1,0,0)
    radioAct = False

def enviarSerial(texto):
    """Envía un mensaje desde la placa a la App mediante la comuniación serial UART

    Args:
        texto (string): mensaje a ser enviado a la App
    """
    uart.write(texto + '\r\n')
    
def enviarRadio(mensaje):
    """Envía un mensaje por el sistema radial de la placa

    Args:
        mensaje (string): mensaje a ser enviado. Debe seguir los parámetros de forma de los mensajes
    """
    if radioAct:
        try:
            radio.send(mensaje)
            parpadearLed(4)

        except RuntimeError:
            parpadearLed(2)
    #parpadear(4,0,9,100)
    
def agregarMensaje(mensaje, prioritario=False):
    """Agrega un mensaje a la cola de mensajes, estableciendo su prioridad

    Args:
        mensaje (string): el mensaje a ser enviado
        prioritario (boolean, optional): Establece si se trata de un mensaje prioritario o no. Defaults to false.
    """
    if radioAct:
        if len(mensajes) < MAX_COLA:
            if prioritario:
                mensajes.insert(0,mensaje)
            else:
                mensajes.append(mensaje)
    


# def parpadear(xLed, yLed, intensidad, tiempo):
#     fin = running_time() + tiempo
#     while(running_time()<fin):
#         display.set_pixel(xLed,yLed,intensidad);
#     display.set_pixel(xLed,yLed,0);

def parpadearLed(led):
    ledsOn.append(led)

def parpadear(modo):
    if(modo=='on'):
        while ledsOn:
            led = ledsOn.pop(0)
            display.set_pixel(led,0,9)
            ledsOff.append(led)
    if(modo=='off'):
        while ledsOff:
            led = ledsOff.pop(0)
            display.set_pixel(led,0,0)
            
            

def evaluarComando(comando):
    global conexion,tiempoKa, tiempo
    
    # Si se recibió un comando, entonces la placa esta presente, por lo que actualizamos su estado
    tiempoKa = running_time() 
    
    datos=comando.split(':')
    orden = datos[0]
    
    # C: Comandos de Sistema recibidos de la App
    if(orden=="c"):   
        
        # C: Conexión. La App solicita conectarse a la placa
        if(datos[1]=='c'):
            display.set_pixel(0,0,9)
            enviarSerial("c:bid:"+ID_PLACA)
            enviarSerial("c:gr:"+str(grupoRadial))
            conexion=True
        
        # BID: ID de Placa: La App solicita la ID de la placa
        if(datos[1]=='bid'):
            enviarSerial("c:bid:"+ID_PLACA)
        
        # GR: Grupo Radial: La App quiere gestionar el grupo radial de la placa
        if(datos[1]=='gr'):
            
            #Si hay más elementos en el comando, la solicitud es de cambio de grupo radial
            if(len(datos)>2):
                grupo=datos[2]
                activarRadio(grupo)
                enviarSerial("m:b:Grupo radial establecido a " + grupo)
            
            #Si solo se trata del comando 'gr', la App solicita el grupo radial actual
            else:
                enviarSerial("c:gr:"+str(grupoRadial))
        
        # KA: KeepAlive: comando para mantener la conexión activa --> EN DESARROLLO, NO IMPLEMENTADO
        if(datos[1]=='ka'): 
            #conexion=True
            if not radioAct:
                activarRadio(grupoRadial)
            agregarMensaje('ka:' + ID_PLACA, True) #Agregamos el KEEP ALIVE como mensaje prioritario
            
    
    # R: Comandos de Red
    #           NOTA: En general, y por ahora, todo comando 'r' es un mensaje saliente
    if(orden=="r"):
       cuerpo = ":".join(datos[1:])
       agregarMensaje(cuerpo + ':' + ID_PLACA)
       
    gc.collect()   
    
        
# ### BUCLE PRINCIPAL ###
while True:
    tiempo_actual = running_time()
    
    # --- PARA PRUEBAS ----
    if button_a.was_pressed():
        enviarRadio("m:algo:"+ID_PLACA)
    # --- --- --- --- --- --
    
    #KEEP ALIVE Local: La placa comprueba que la app este presente, de lo contrario, cierra la comunicación radial
    if(tiempo_actual - tiempoKa) >= INTERVALO_KEEP_ALIVE:
       desactivarRadio()
    
    #Proceso de la cola de mensajes
    
    if mensajes and (tiempo_actual - ultimo_envio) >= INTERVALO_ENVIO_MS:
        m = mensajes.pop(0)
        enviarRadio(m)
        ultimo_envio = tiempo_actual
        
    if not mensajes:
        gc.collect()
    
    
    #
    if(len(ledsOn)>0):
        parpadear("on")
        tiempo=running_time()+200
    if(running_time() > tiempo):
        parpadear("off")
    
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
    if radioAct:
        mensaje_radio = radio.receive()
        if mensaje_radio:
            # Reenvía el mensaje directamente a la PC terminado en un salto de línea
            uart.write('r:'+mensaje_radio + '\n')
            parpadearLed(3)

    # -------------------------------------------------------------
    # 2. SERIAL -> RADIO: Comandos enviados desde la app en Java
    # -------------------------------------------------------------
    if uart.any():
        bloque = uart.read()
        if bloque:
            for b in bloque:
        # 10 es '\n' y 13 es '\r' en código ASCII/byte
                if b == 10 or b == 13:
                    if buffer_serial:
                        try:
                            
                            cadena_raw = bytes(buffer_serial)
                            comando = str(cadena_raw, 'utf-8').strip()
                            # Decodificamos la trama COMPLETA a UTF-8 de una sola vez
                            # comando = buffer_serial.decode('utf-8').strip()
                            if comando:
                                evaluarComando(comando)
                        except UnicodeError:
                            # Previene cuelgues si llega un byte corrupto por el cable
                            pass
                        
                        # Limpiar el buffer de bytes
                        buffer_serial = bytearray()
                        gc.collect()
                else:
                    buffer_serial.append(b)
    
    
    # -->> Sistema de lectura serial anterior
        # if bloque:
        #     for char_byte in bloque:
        #         char = chr(char_byte)
        #         if char == '\n' or char == '\r':
        #             comando = buffer_serial.strip()
        #             if comando:
        #                 # Emitir el comando a la red RF
                        
        #                 # ** PARA PRUEBAS **
        #                 enviarSerial("recibido:" + comando)
                        
        #                 # Se evalúa el comando recibido
        #                 evaluarComando(comando)
        #                 # radio.send(comando)
        #                 #enviarRadio(comando)
        #                 buffer_serial = ""
        #         else:
        #             buffer_serial += char
    sleep(10)
    #gc.collect()