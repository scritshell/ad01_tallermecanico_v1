package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.json;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import java.util.List;

public class Clientes implements IClientes {
    private static Clientes instancia;
    private Clientes() {}

    public static Clientes getInstancia() {
        if (instancia == null) {
            instancia = new Clientes();
        }
        return instancia;

    }

    @Override public void comenzar() {}

    @Override public void terminar() {}

    @Override public List<Cliente> get() {
        return null;
    }

    @Override public void insertar(Cliente cliente) {}

    @Override public Cliente modificar(Cliente cliente, String nombre, String telefono) {
        return null;
    }

    @Override public Cliente buscar(Cliente cliente) {
        return null;
    }

    @Override public void borrar(Cliente cliente) {}
}