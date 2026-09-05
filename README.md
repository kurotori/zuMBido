# zuMBido
## Un protocolo de comunicación de red entre Aplicaciones usando placas micro:bit como pasarela inalámbrica

**zuMBido** es un intento de protocolo de comunicaciones entre aplicaciones y sistemas utilizando el sistema de mensajería radial de las placas [**micro:bit**][l1] como pasarela de datos.

![i1]

## Objetivos
Este proyecto procura lograr un sistema de comunicación que permita diseñar sistemas que se comuniquen entre sí intercambiando mensajes mediante las placas micro:bit.

Esto es *a sabiendas* de que existen **muchas** formas más efectivas de lograr exactamente lo mismo. Pero se desarrolla de todas maneras, ya que permite:

- Una forma de conectividad que **obliga** a los usuarios a estar viéndose **cara a cara**, dado el escaso rango de la señal radial de las placas, fomentando así la interacción humana.
- Evitar los peligros inherentes en el uso de sistemas que dependan de redes tradicionales.
- Explorar las posibilidades de esta clase de tecnología.

En este sentido se apunta a lograr dos clases de sistemas:

1. **Aplicaciones de escritorio o consola que intercambien datos (mensajería, juegos, etc).** : El proyecto se encuentra en el desarrollo de este tipo de sistemas ahora mismo.
1. **Aplicaciones de escritorio o consola que intercambien datos con placas y sus sensores** : O sea, que permitan el control y el monitoreo de sistemas electrónicos que usen una micro:bit como parte de sus componentes. Esto se procura desarrollar en un futuro cercano.

Para lograrlo, *zuMBido* se basa en dos tipos de software bien diferenciados:

 - **Script en MicroPython** en la placa micro:bit, que gestiona el envío y la recepción de mensajes entre placas, y desde y hacia la aplicación.
 - **Aplicación (por ahora en Java)** que se ejecuta en el escritorio del usuario y se conecta a la placa micro:bit mediante el puerto serial de la misma.

 > **Nota Técnica**: Las placas micro:bit no solo exponen una conexión como medio de almacenamiento, sino que también exponen un puerto serial (_USB UART, 115200 Bd, 8 bits, sin paridad, 1 bit de parada_) a través de la conexión USB, mediante la cual es posible enviar y recibir datos de la placa.

## Funcionamiento de la red zuMBido

Al usar este sistema se establece lo que, básicamente, sería una red descentralizada de tipo malla: no hay un servidor que organice las conexiones, sino que cada placa se anuncia al resto al conectarse.

Esta conexión se mantiene mientras el sistema envíe información al grupo radial, que puede ser en la forma de mensajes intencionales, o meidante mensajes automáticos del tipo "keep alive", de lo contrario, los otros sistemas conectados purgarán su registro tras un período de inactividad, sin perjucio de volver a registrarlo en cuanto vuelva a comunicarse.

El protocolo de comunicaciones en sí esta en desarrollo activo, y se aceptan sugerencias. 

## Estado del proyecto

Alfa **muy** temprano. El código en este repositorio contiene una aplicación en Java que permite:

- La selección de la placa y el grupo radial
- La identificación del usuario en la red
- El intercambio de mensajes en un sistema de chat básico

También contiene el código en MicroPython para flashear en la micro:bit

## Cómo usar este código

### MicroPython

**NOTA IMPORTANTE:**

El código para la micro:bit **NO ES COMPATIBLE CON LA PLATAFORMA MAKECODE**.

La plataforma [makecode][l4] de Microsoft limita la cantidad de caracteres que puede enviar la placa por mensaje a alrededor de 32, a fin de potenciar otras capacidades de la misma.

Por ese motivo este proyecto comenzó usando _la otra plataforma de desarrollo oficial_ de desarrollo para micro:bit, que puede encontrarse en [https://python.microbit.org/v/3][l5], para luego pasar a desarrollarse íntegramente en un entorno virtual de Python mediante VS Code.

Copiando el contenido del archivo **[zuMBido-main.py][l6]** en el editor on-line que se menciona se puede obtener fácilmente el .hex para flashear la micro:bit. También es posible instalar un entorno virtual de Python con las librerías descritas en el archivo _constraints.txt_ para compilar y flashear el .hex diréctamente desde la consola con la utilidad _uflash_, incluída en las librerías.

El script _flashear.sh_ permite, en un sistema GNU/Linux, flashear el código sistemáticamente a todas las placas micro:bit conectadas a la PC.

### Java

El proyecto de Java incluído se esta desarrollando mediante [Apache Maven][l7] , usando [Apache NetBeans][l8], pero debería ser compatible con cualquier IDE capaz de manejar proyectos de Maven (se ha comprobado en VS Code e IntelliJ iDEa).


[l1]:https://microbit.org/
[l2]:https://ceibal.edu.uy/
[l3]:https://es.wikipedia.org/wiki/Uruguay
[l4]:https://makecode.microbit.org/
[l5]:https://python.microbit.org/v/3
[l6]:MicroPython/zuMBido-main.py
[l7]:https://maven.apache.org/

[i1]:out/diagramas/DiagramaDeComponentes/Diagrama%20de%20Componentes.png
