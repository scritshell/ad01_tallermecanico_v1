package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.json;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import java.util.List;

public class Vehiculos implements IVehiculos {
    private static Vehiculos instancia;
    private Vehiculos() {}

    public static Vehiculos getInstancia() {
        if (instancia == null) {
            instancia = new Vehiculos();
        }
        return instancia;
    }


    @Override public void comenzar() {}

    @Override public void terminar() {}

    @Override public List<Vehiculo> get() {
        return null;
    }

    @Override public void insertar(Vehiculo vehiculo) {}



    @Override public Vehiculo buscar(Vehiculo vehiculo) {
        return null;
    }

    @Override public void borrar(Vehiculo vehiculo) {}

}
