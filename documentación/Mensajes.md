# zuMBido
## Mensajes

## 1- Estructura General de los Mensajes usados por el Sistema

>**NOTA:** Esta estructura no es definitiva y esta sujeta a revisiones.

Los mensajes que usa **zuMBido** siguen un patrón muy sencillo para simplificar su manejo e interpretación.

Inicialmente se consideró el uso de JSON, pero resultaba, para el proósito, demasiado complejo para implementar, por lo que se decidió establecer un patrón más sencillo.

La estructura actual sigue los siguientes patrones:

1. **Comando** : **SubComando**

1. **Comando** : **SubComando** : _<dato 1>_

1. **Comando** : **SubComando** : _<dato 1>_ : _<dato 2>_ 

>**NOTA:** Opcionalmente se podrían concatenar más datos a los mostrados en el patrón 3, cuidando de no superar el límite de caracteres (248)

## 2- Mensajes utilizados en el sistema

La comunicación entre componentes se divide en tres tipos:

- De Aplicación a Placa
- De Placa a Aplicación
- De Placa a Placa

### 2.1- De Aplicación a placa 

| **Comando** | **SubComando** |**datos** |

#### c : Comandos Generales

#### c - Iniciar conexión. 

`c:c`

La aplicación solicita a la placa los datos básicos para iniciar las comunicaciones. La placa responde con comandos de [identificación de placa](#bid---solicitud-de-identificación-de-placa) y grupo radial.

#### bid - Solicitud de Identificación de Placa.

`c:bid`

La aplicación solicita la identificación de la placa. La placa responde con un comando de identificación de placa que contiene el dato solicitado

#### gr - Solicitud o Cambio de Grupo Radial.

`c:gr`

`c:gr:x` - Siendo `x` un entero entre 0 y 255

*Sin Datos:* La aplicación solicita el grupo radial establecido en la placa. La placa responde con un comando de grupo radial.

*Con Datos:* La aplicación solicita el cambio del grupo radial de la placa al número contenido en los datos. La placa cambia el grupo radial y responde con un comando de grupo radial.

#### ka - Solicitud para Mantener la Conexión Activa (KEEP_ALIVE)

`c:ka`

La aplicación notifica a la placa que esta presente para mantener la conexión activa. Se emite cada 5 segundos desde la aplicación. La placa no responde a la aplicación, pero actualiza la marca de tiempo correspondiente (variable `tiempoKa`), y emite un [mensaje radial de conexión activa].

### 2.2- De Placa a Aplicación

### 2.3- De Placa a Placa

>**Nota:** Todos estos mensajes son **radiales** e incluyen la **id de la placa** donde se origina el mensaje.

#### m - Mensajes Públicos

`m:<mensaje>:<id_placa>`

Los mensajes públicos se envían al chat general (o su equivalente), y son recibidos y vistos por todos los usuarios conectados en ese grupo radial.

Tienen una longitud máxima de 229 caracteres.

>NOTA: La longitud máxima de los mensajes públicos puede ser 231, pero se mantiene en 229 para evitar sobrecargar el sistema radial.

#### p - Mensajes Privados (de usuario a usuario)

`p:<mensaje>:<id_placa_origen>:<id_placa_destino>`

Los mensajes privados se envían a una ventana de chat privado (o su equivalente). Al recibirse en cada placa, se compara, a nivel de placa, la id de la placa. Si no es igual a la id de la placa local, se ignora el mensaje (no pasa a la aplicación).

Tienen una longitud máxima de 212 caracteres.

>NOTA: La longitud máxima de los mensajes públicos puede ser 214, pero se mantiene en 212 para evitar sobrecargar el sistema radial.