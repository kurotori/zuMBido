# Imports go at the top
from microbit import *
import radio
import machine

radio.config(channel=2, length=250)
radio.on()
uart.init(baudrate=115200)

serial_buffer = ""

# Estados: 0 - inicial, 1 - sesion iniciada, 2 - iniciando sesión
#           3 - en chat, 4 - en listado de usuarios
estado = 0

# SubEstados: 1 - Menu Principal, 2 - Chat, 3 - Lista de Usuarios
subEstado = 0

marcaTiempo=0
subMarcaTiempo=0
nombre = ''
placa = machine.unique_id()
mensaje = ''
intentos = 0
usuarios=[]
placas=[]

def animacionRecibir():
    display.show(Image('00000:'
                       '09090:'
                       '00900:'
                       '00000:'
                       '00000'))
    sleep(150)
    display.clear()
    
    
def animacionEnviar():
    display.show(Image('00000:'
                       '00000:'
                       '00900:'
                       '09090:'
                       '00000'))
    sleep(150)
    display.clear()

def enviarMensaje(orden,valor):
    radio.send(orden+':'+valor)
    animacionEnviar()

#Imprime una línea decorativa
def linea():
    uart.write("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\r\n")

def lineaDecorada():
    uart.write("mÖ -- -- -- -- -- -- -- -- -- -- -- -- -- -- Öm\r\n")


def menuPrincipal():
    ##global nombre,usuarios,estado
    ##opcion=0
    ##while opcion!=3:
    lineaDecorada()
    escribir("Menú Principal")
    linea()
    escribir('1 - Ir al Chat')
    escribir('2 - Mostrar Usuarios')
        #opcion=input('Opción:')
        
    linea()
    #    if opcion=='2':
    #        escribir('Usuarios Conectados: ' +str(len(usuarios)+1) )
     #       linea()
      #      escribir(' ')
       #     escribir('Yo: '+nombre)
        #    for usuario in usuarios:
         #       escribir('- '+usuario)
          #  linea()
        #if opcion=='1':
         #   estado=1
          #  opcion=3
            

    

def evaluarComando(comando):
    cosa=""


    """Permite enviar un texto a la consola serial
    """
def escribir(texto):
    uart.write(texto + '\r\n')
    
def evaluarMensaje(mensaje):
    global nombre, estado, subEstado, intentos
    orden=mensaje[0:(mensaje.find(':'))]
    valor=mensaje[ (mensaje.find(':'))+1:len(mensaje)]

    if (orden=='nuevoLogin'):
        if(valor==nombre):
            enviarMensaje('nombreRepetido','')
    elif (orden=='nombreRepetido'):
        escribir('ERROR: Ese nombre ya está en uso')
        escribir('Intenta nuevamente con otro nombre')
        linea()
        estado=0
    elif(orden=='holaSoy'):
        usuario=valor[0:(valor.find(':'))]
        idPlaca=valor[(valor.find(':'))+1:len(valor)]
        if usuarios.count(usuario)<1:
            usuarios.append(usuario)
            placas.append(idPlaca)
            enviarMensaje('holaSoy',nombre+':'+placa)
    elif (orden=='m'):
        if estado==3:
            usuario=valor[0:(valor.find(':'))]
            msj=valor[(valor.find(':'))+1:len(valor)]
            escribir(usuario+':'+msj)
            linea()


# Interfaz de usuario inicial
display.scroll("MicroChat v1")

if estado == 0:
    lineaDecorada()
    escribir("                 MicroChat v1")
    linea()
    escribir("Hola")
    escribir("¿Cuál es tu nombre?")
    linea()

# Code in a 'while True:' loop repeats forever
while True:
    # Recepción de mensajes - Siempre activa
    mensaje = radio.receive()
    if mensaje:
        #escribir(mensaje)
        animacionRecibir()
        evaluarMensaje(mensaje)
        
    if estado == 2:
        if running_time() < marcaTiempo:
            if running_time() >= subMarcaTiempo:
                escribir("Conectando ...")
                subMarcaTiempo = running_time()+1000
        else:
                escribir('¡¡Listo!!')
                estado = 1
                linea()
                escribir('Hola, '+nombre)
                escribir('Te uniste a la red MicroChat')
                escribir('')
                menuPrincipal()
    
    
    # Manejo de comandos desde el puerto serial/consola
    if uart.any():
        # Obtener el primer caracter de la conexión
        char_bytes = uart.read(1)
        
        if char_bytes:
            # Conversión a caracteres de texto
            char = str(char_bytes, 'UTF-8')
            
            # Análisis: Si el caracter se corresponde con 'Enter' (\r o \n)
            #   se evalúa el comando recibido
            if char == '\n' or char == '\r':
                if serial_buffer:
                    #escribir("\n[Serial Command Received]:", serial_buffer)
                    escribir('')
                    
                    if estado == 0:
                        nombre = serial_buffer
                        enviarMensaje('nuevoLogin',nombre)
                        estado = 2
                        marcaTiempo=running_time()+3000
                        subMarcaTiempo=running_time()+1000
                        linea()
                        escribir("Conectando a la red MicroChat como "+nombre+"...")
                        
                        
                    elif estado == 1:
                        if serial_buffer=='1':
                            estado=3
                            lineaDecorada()
                            escribir('CHAT')
                            linea()
                            escribir('Ingresa tu mensaje y presiona Enter para enviarlo')
                            linea()
                    
                    elif estado == 3:
                        enviarMensaje("m",nombre+':'+serial_buffer)
                    
                        
                    # Reset the string container for the next phrase
                    uart.write("\r\n")
                    #escribir('Estado = '+str(estado))
                    serial_buffer = ""
            else:
                # Echo the user's keystroke back to the terminal window
                uart.write(char)
                serial_buffer += char

    # Microsecond pause to keep processing stable
    display.set_pixel(estado,0,3)
    sleep(10)
    display.clear
    sleep(10)
    
#    if (estado == 0):
#        linea()    
#        uart.write("MicroChat v1")
#        linea()
#        uart.write('Hola')
        #nombre = input
#        uart.write('¿Cuál es tu nombre?: ')
        #login(nombre)
#    if (estado == 1):
#        menuPrincipal()
    
    
        


