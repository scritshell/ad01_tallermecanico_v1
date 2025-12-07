package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.json;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Trabajos implements ITrabajos {
    private static Trabajos instancia;
    private Trabajos() {}

    public static Trabajos getInstancia() {
        if (instancia == null) {
            instancia = new Trabajos();
        }
        return instancia;
    }

    @Override public void comenzar() {}

    @Override public void terminar() {}

    @Override public List<Trabajo> get() {
        return null;
    }

    @Override public List<Trabajo> get(Cliente cliente) {
        return null;
    }

    @Override public List<Trabajo> get(Vehiculo vehiculo) {
        return null;
    }

    @Override public Map<TipoTrabajo,Integer> getEstadisticasMensuales(LocalDate mes) {
        return null;
    }

    @Override public void insertar(Trabajo trabajo) {}

    @Override public Trabajo anadirHoras(Trabajo trabajo, int horas) {
        return null;
    }

    @Override public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) {
        return null;
    }

    @Override public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) {
        return null;
    }

    @Override public Trabajo buscar(Trabajo trabajo) {
        return null;
    }

    @Override public void borrar(Trabajo trabajo) {}

}
