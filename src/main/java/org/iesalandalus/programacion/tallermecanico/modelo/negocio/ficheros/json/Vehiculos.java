package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.json;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Vehiculos implements IVehiculos {
    private static final String FICHERO_VEHICULOS = "datos/ficheros/json/vehiculos.json";
    private static Vehiculos instancia;
    private final ObjectMapper mapper;



    private Vehiculos() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public static Vehiculos getInstancia() {
        if (instancia == null) {
            instancia = new Vehiculos();
        }
        return instancia;
    }

    @Override
    public void comenzar() {}

    @Override
    public void terminar() {}




    private List<Vehiculo> leer() {
        File fichero = new File(FICHERO_VEHICULOS);
        if (!fichero.exists()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(fichero, new TypeReference<List<Vehiculo>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el fichero JSON de vehículos", e);
        }
    }

    private void escribir(List<Vehiculo> vehiculos) {
        File fichero = new File(FICHERO_VEHICULOS);
        try {
            if (!fichero.getParentFile().exists()) {
                fichero.getParentFile().mkdirs();
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, vehiculos);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el fichero JSON de vehículos", e);
        }
    }






    @Override
    public List<Vehiculo> get() {
        return leer();
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        if (vehiculo == null) {
            throw new NullPointerException("No se puede insertar un vehículo nulo.");
        }
        List<Vehiculo> vehiculos = leer();
        if (vehiculos.contains(vehiculo)) {
            throw new TallerMecanicoExcepcion("El vehículo ya existe.");
        }
        vehiculos.add(vehiculo);
        escribir(vehiculos);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new NullPointerException("No se puede buscar un vehículo nulo.");
        }
        List<Vehiculo> vehiculos = leer();
        int indice = vehiculos.indexOf(vehiculo);
        return (indice != -1) ? vehiculos.get(indice) : null;
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        if (vehiculo == null) {
            throw new NullPointerException("No se puede borrar un vehículo nulo.");
        }
        List<Vehiculo> vehiculos = leer();
        if (!vehiculos.contains(vehiculo)) {
            throw new TallerMecanicoExcepcion("El vehículo no existe.");
        }
        vehiculos.remove(vehiculo);
        escribir(vehiculos);
    }
}