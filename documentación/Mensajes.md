# zuMBido
## Mensajes

## 1- Estructura General de los Mensajes usados por el Sistema

>**NOTA:** Esta estructura no es definitiva y esta sujeta a revisiones.

Los mensajes que usa **zuMBido** siguen un patrón muy sencillo para simplificar su manejo e interpretación.

Inicialmente se consideró el uso de JSON, pero resultaba, para el proósito, demasiado complejo para implementar, por lo que se decidió establecer un patrón más sencillo.

La estructura actual sigue los siguientes patrones:

1. **Comando** : **SubComando**

1. **Comando** : **SubComando** : **dato 1**

1. **Comando** : **SubComando** : **dato 1** : **dato 2** 

>**NOTA:** Opcionalmente se podrían concatenar más datos a los datos del patrón 3

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