# Acceso a Datos Tarea Online 1

## Dificultades encontradas durante la implementación JSON
El mayor problema que he tenido de este proyecto fue que el archivo trabajos.json NO mostraba los "tipo" de trabajo (revisión o mecánico). Para resolver esto de alguna forma hice lo siguiente: En el método void Escribir de la clase Trabajos.java (negocio/ficheros/json), hice esto ->          mapper.writerWithDefaultPrettyPrinter()
                    .forType(new TypeReference<List<Trabajo>>() {})
                    .writeValue(fichero, trabajos);

De esta forma, me aseguro de que apareciese el tipo de trabajo elegido en el Json.
