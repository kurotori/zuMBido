from microbit import *
from micropython import const
import radio
import machine
import gc
VER="0.1.4"
IKA=const(5000)
IEM=const(50)
MMSG=const(10)
IDP="".join("{:02x}".format(b) for b in machine.unique_id())

tiempo=0

tka=running_time() +  IKA #Temporizador para detectar conexión activa
lOn = []
lOff = []
msgs = []
uMsg = 0
grpR=7
rOn = True
bfSr = bytearray()

radio.config(length=250, group=grpR)
radio.on()
uart.init(baudrate=115200)

def radioOn(g):
    global rOn,grpR
    grpR=int(g)
    radio.config(length=250, group=grpR)
    radio.on()
    rOn = True
    display.set_pixel(1,0,9)

def radioOff():
    global rOn
    radio.off()
    display.set_pixel(1,0,0)
    rOn = False

def enviarS(t):
    uart.write(t + '\r\n')
    gc.collect()
    
def enviarR(t):
    if rOn:
        try:
            radio.send(t)
            pLed(4)

        except RuntimeError:
            pLed(2)
    
def agrMsg(m, p=False):
    if rOn:
        if len(msgs) < MMSG:
            if p:
                msgs.insert(0,m)
            else:
                msgs.append(m)

def pLed(l):
    lOn.append(l)

def piscar(m):
    if(m):
        while lOn:
            l = lOn.pop(0)
            display.set_pixel(l,0,9)
            lOff.append(l)
    else:
        while lOff:
            l = lOff.pop(0)
            display.set_pixel(l,0,0)

def evaluarComando(comando):
    global tka, tiempo
    
    # Si se recibió un comando, entonces la placa esta presente, por lo que actualizamos su estado
    tka = running_time() 
    
    datos=comando.split(':')
    orden = datos[0]
    
    # C: Comandos de Sistema recibidos de la App
    if(orden=="c"):   
        
        # V: Versión. La app solicita la versión actual del script en la placa
        if(datos[1]=='v'):
            enviarS('c:v:'+VER)
        
        # C: Conexión. La App solicita conectarse a la placa
        if(datos[1]=='c'):
            display.set_pixel(0,0,9)
            enviarS("c:bid:"+IDP)
            enviarS("c:gr:"+str(grpR))
            tka = running_time()
            radioOn(grpR)
        
        # BID: ID de Placa: La App solicita la ID de la placa
        if(datos[1]=='bid'):
            enviarS("c:bid:"+IDP)
        
        # GR: Grupo Radial: La App quiere gestionar el grupo radial de la placa
        if(datos[1]=='gr'):
            
            #Si hay más elementos en el comando, la solicitud es de cambio de grupo radial
            if(len(datos)>2):
                grupo=datos[2]
                radioOn(grupo)
                enviarS("m:b:Grupo radial establecido a " + grupo)
            
            #Si solo se trata del comando 'gr', la App solicita el grupo radial actual
            else:
                enviarS("c:gr:"+str(grpR))
        
        # KA: KeepAlive: comando para mantener la conexión activa --> EN DESARROLLO, NO IMPLEMENTADO
        if(datos[1]=='ka'): 
            if not rOn:
                radioOn(grpR)
            agrMsg('ka:' + IDP, True) #Agregamos el KEEP ALIVE como mensaje prioritario
            
    
    # R: Comandos de Red
    #           NOTA: En general, y por ahora, todo comando 'r' es un mensaje saliente
    if(orden=="r"):
       cuerpo = ":".join(datos[1:])
       agrMsg(cuerpo + ':' + IDP)
       
    gc.collect()   
    
        
# ### BUCLE PRINCIPAL ###
while True:
    tiempo_actual = running_time()
    
    # --- PARA PRUEBAS ----
    if button_a.was_pressed():
        enviarR("m:algo:"+IDP)
    # --- --- --- --- --- --
    
    #KEEP ALIVE Local: La placa comprueba que la app este presente, de lo contrario, cierra la comunicación radial
    if(tiempo_actual - tka) >= IKA:
       radioOff()
    
    #Proceso de la cola de mensajes
    
    if msgs and (tiempo_actual - uMsg) >= IEM:
        m = msgs.pop(0)
        enviarR(m)
        uMsg = tiempo_actual
        
    if not msgs:
        gc.collect()
    
    
    #
    if lOn:
        piscar(True)
        tiempo=running_time()+200
    if(running_time() > tiempo):
        piscar(False)
        
    # 1. RADIO -> SERIAL: Mensajes recibidos de otros micro:bits

    if rOn:
        mensaje_radio = radio.receive()
        if mensaje_radio:
            if(mensaje_radio[0] == 'p'):
                display.set_pixel(1,1,9)
                datos=mensaje_radio.split(':')
                if(datos[2] == IDP):
                    enviarS('r:'+mensaje_radio)
                display.set_pixel(1,1,0)
            else:        
                enviarS('r:'+mensaje_radio)
            pLed(3)

    # -------------------------------------------------------------
    # 2. SERIAL -> RADIO: Comandos enviados desde la app en Java
    # -------------------------------------------------------------
    if uart.any():
        bloque = uart.read()
        if bloque:
            for b in bloque:
        # 10 es '\n' y 13 es '\r' en código ASCII/byte
                if b == 10 or b == 13:
                    if bfSr:
                        try:
                            
                            cadena_raw = bytes(bfSr)
                            comando = str(cadena_raw, 'utf-8').strip()
                            # Decodificamos la trama COMPLETA a UTF-8 de una sola vez
                            if comando:
                                evaluarComando(comando)
                        except UnicodeError:
                            # Previene cuelgues si llega un byte corrupto por el cable
                            pass
                        
                        # Limpiar el buffer de bytes
                        bfSr = bytearray()
                        gc.collect()
                else:
                    bfSr.append(b)
    sleep(10)