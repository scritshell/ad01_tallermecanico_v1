package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trabajos implements ITrabajos {
    private static final String FICHERO_TRABAJOS = "datos/ficheros/json/trabajos.json";
    private static Trabajos instancia;
    private final ObjectMapper mapper;

    private Trabajos() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public static Trabajos getInstancia() {
        if (instancia == null) {
            instancia = new Trabajos();
        }
        return instancia;
    }
    @Override
    public void comenzar() {}
    @Override
    public void terminar() {}




    private List<Trabajo> leer() {
        File fichero = new File(FICHERO_TRABAJOS);
        if (!fichero.exists()) {
            return new ArrayList<>();
        }
        try {
            // Jackson usará las anotaciones @JsonTypeInfo de la clase Trabajo para saber
            // si crea un Mecanico o una Revision de forma automática
            return mapper.readValue(fichero, new TypeReference<List<Trabajo>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el fichero JSON de trabajos", e);
        }
    }

    private void escribir(List<Trabajo> trabajos) {
        File fichero = new File(FICHERO_TRABAJOS);
        try {
            if (!fichero.getParentFile().exists()) {
                fichero.getParentFile().mkdirs();
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, trabajos);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el fichero JSON de trabajos", e);
        }
    }



    @Override
    public List<Trabajo> get() {
        return leer();
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        if (trabajo == null) {
            throw new NullPointerException("No se puede insertar un trabajo nulo.");
        }
        List<Trabajo> trabajos = leer();
        if (trabajos.contains(trabajo)) {
            throw new TallerMecanicoExcepcion("El trabajo ya existe.");
        }
        trabajos.add(trabajo);
        escribir(trabajos);
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        if (trabajo == null) {
            throw new NullPointerException("No se puede buscar un trabajo nulo.");
        }
        List<Trabajo> trabajos = leer();
        int indice = trabajos.indexOf(trabajo);
        return (indice != -1) ? trabajos.get(indice) : null;
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        if (trabajo == null) {
            throw new NullPointerException("No se puede borrar un trabajo nulo.");
        }
        List<Trabajo> trabajos = leer();
        if (!trabajos.contains(trabajo)) {
            throw new TallerMecanicoExcepcion("El trabajo no existe.");
        }
        trabajos.remove(trabajo);
        escribir(trabajos);
    }



    @Override
    public List<Trabajo> get(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("No se puede filtrar por un cliente nulo.");
        }
        List<Trabajo> trabajos = leer();
        List<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo t : trabajos) {
            if (t.getCliente().equals(cliente)) {
                trabajosCliente.add(t);
            }
        }
        return trabajosCliente;
    }
    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new NullPointerException("No se puede filtrar por un vehículo nulo.");
        }
        List<Trabajo> trabajos = leer();
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo t : trabajos) {
            if (t.getVehiculo().equals(vehiculo)) {
                trabajosVehiculo.add(t);
            }
        }
        return trabajosVehiculo;
    }



    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        if (trabajo == null) {
            throw new NullPointerException("No se puede añadir horas a un trabajo nulo.");
        }
        List<Trabajo> trabajos = leer();
        int indice = trabajos.indexOf(trabajo);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("El trabajo no existe.");
        }

        Trabajo trabajoEnLista = trabajos.get(indice);
        trabajoEnLista.anadirHoras(horas);

        escribir(trabajos);
        return trabajoEnLista;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        if (trabajo == null) {
            throw new NullPointerException("No se puede añadir precio de material a un trabajo nulo.");
        }
        List<Trabajo> trabajos = leer();
        int indice = trabajos.indexOf(trabajo);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("El trabajo no existe.");
        }
        Trabajo trabajoEnLista = trabajos.get(indice);

        if (trabajoEnLista instanceof Mecanico) {
            ((Mecanico) trabajoEnLista).anadirPrecioMaterial(precioMaterial);
            escribir(trabajos);
            return trabajoEnLista;
        } else {
            throw new TallerMecanicoExcepcion("No se puede añadir precio material a una revisión.");
        }
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        if (trabajo == null) {
            throw new NullPointerException("No se puede cerrar un trabajo nulo.");
        }
        List<Trabajo> trabajos = leer();
        int indice = trabajos.indexOf(trabajo);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("El trabajo no existe.");
        }
        Trabajo trabajoEnLista = trabajos.get(indice);
        trabajoEnLista.cerrar(fechaFin);

        escribir(trabajos);
        return trabajoEnLista;
    }




    @Override
    public Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes) {
        if (mes == null) {
            throw new NullPointerException("El mes no puede ser nulo.");
        }
        List<Trabajo> trabajos = leer();
        Map<TipoTrabajo, Integer> estadisticas = new HashMap<>();
        estadisticas.put(TipoTrabajo.MECANICO, 0);
        estadisticas.put(TipoTrabajo.REVISION, 0);

        for (Trabajo t : trabajos) {
            if (t.getFechaInicio().getMonth().equals(mes.getMonth()) &&
                    t.getFechaInicio().getYear() == mes.getYear()) {

                if (t instanceof Mecanico) {
                    estadisticas.put(TipoTrabajo.MECANICO, estadisticas.get(TipoTrabajo.MECANICO) + 1);
                } else if (t instanceof Revision) {
                    estadisticas.put(TipoTrabajo.REVISION, estadisticas.get(TipoTrabajo.REVISION) + 1);
                }
            }
        }
        return estadisticas;
    }
}