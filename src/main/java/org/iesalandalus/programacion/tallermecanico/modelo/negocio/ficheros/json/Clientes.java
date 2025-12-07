package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Clientes implements IClientes {
    private static final String FICHERO_CLIENTES = "datos/ficheros/json/clientes.json";
    private static Clientes instancia;
    private final ObjectMapper mapper;

    private Clientes() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public static Clientes getInstancia() {
        if (instancia == null) {
            instancia = new Clientes();
        }
        return instancia;
    }

    @Override
    public void comenzar() {}
    @Override
    public void terminar() {}





    private List<Cliente> leer() {
        File fichero = new File(FICHERO_CLIENTES);
        if (!fichero.exists()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(fichero, new TypeReference<List<Cliente>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el fichero JSON de clientes", e);
        }
    }

    private void escribir(List<Cliente> clientes) {
        File fichero = new File(FICHERO_CLIENTES);
        try {
            if (!fichero.getParentFile().exists()) {
                fichero.getParentFile().mkdirs();
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichero, clientes);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el fichero JSON de clientes", e);
        }
    }




    @Override
    public List<Cliente> get() {
        return leer();
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        if (cliente == null) {
            throw new NullPointerException("No se puede insertar un cliente nulo.");
        }
        List<Cliente> clientes = leer();
        if (clientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion("El cliente ya existe.");
        }
        clientes.add(cliente);
        escribir(clientes);
    }

    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        if (cliente == null) {
            throw new NullPointerException("No se puede modificar un cliente nulo.");
        }
        List<Cliente> clientes = leer();
        int indice = clientes.indexOf(cliente);
        if (indice == -1) {
            throw new TallerMecanicoExcepcion("El cliente no existe.");
        }
        Cliente clienteAActualizar = clientes.get(indice);
        boolean modificado = false;

        if (nombre != null && !nombre.isBlank()) {
            clienteAActualizar.setNombre(nombre);
            modificado = true;
        }
        if (telefono != null && !telefono.isBlank()) {
            clienteAActualizar.setTelefono(telefono);
            modificado = true;
        }
        if (modificado) {
            escribir(clientes);
        }
        return clienteAActualizar;

    }

    @Override
    public Cliente buscar(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("No se puede buscar un cliente nulo.");
        }
        List<Cliente> clientes = leer();
        int indice = clientes.indexOf(cliente);
        if (indice != -1) {
            return clientes.get(indice);
        }
        return null;
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        if (cliente == null) {
            throw new NullPointerException("No se puede borrar un cliente nulo.");
        }
        List<Cliente> clientes = leer();
        if (!clientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion("El cliente no existe.");
        }
        clientes.remove(cliente);
        escribir(clientes);
    }
}