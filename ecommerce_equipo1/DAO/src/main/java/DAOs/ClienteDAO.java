/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import entidades.Cliente;
import entidades.Direccion;
import enums.TipoUsuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maryr
 */
public class ClienteDAO {

    private static List<Cliente> clientes = new ArrayList<>();
    private static int contadorId = 1;

    static {
        Direccion dir1 = new Direccion();
        dir1.setId(1);
        dir1.setCalle("Narnia");
        dir1.setNumero("123");
        dir1.setColonia("Centro");
        dir1.setCodigoPostal("85000");

        Cliente c1 = new Cliente();
        c1.setId(1);
        c1.setNombre("Jesus en moto");
        c1.setCorreo("moto@ejemplo.com");
        c1.setContrasena("123456");
        c1.setTelefono("6441234567");
        c1.setEstado(true);
        c1.setTipoUsuario(TipoUsuario.CLIENTE);
        c1.setDireccion(dir1);

        clientes.add(c1);
        contadorId = 2;
    }

    public Cliente autenticar(String correo, String contrasena) {
        for (Cliente c : clientes) {
            if (c.getCorreo().equals(correo)
                    && c.getContrasena().equals(contrasena)
                    && c.isEstado()) {
                return c;
            }
        }
        return null;
    }

    public boolean registrar(Cliente cliente) {
        try {
            cliente.setId(contadorId++);

            if (cliente.getDireccion() != null) {
                cliente.getDireccion().setId(cliente.getId());
            }

            clientes.add(cliente);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeCorreo(String correo) {
        for (Cliente c : clientes) {
            if (c.getCorreo().equalsIgnoreCase(correo)) {
                return true;
            }
        }
        return false;
    }

    public Cliente obtenerPorId(Integer id) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public Cliente obtenerPorCorreo(String correo) {
        for (Cliente c : clientes) {
            if (c.getCorreo().equalsIgnoreCase(correo)) {
                return c;
            }
        }
        return null;
    }

    public boolean actualizar(Cliente cliente) {
        Cliente existente = obtenerPorId(cliente.getId());
        if (existente != null) {
            existente.setNombre(cliente.getNombre());
            existente.setCorreo(cliente.getCorreo());
            existente.setContrasena(cliente.getContrasena());
            existente.setTelefono(cliente.getTelefono());
            existente.setEstado(cliente.isEstado());
            existente.setTipoUsuario(cliente.getTipoUsuario());

            if (cliente.getDireccion() != null) {
                if (existente.getDireccion() == null) {
                    existente.setDireccion(new Direccion());
                    existente.getDireccion().setId(existente.getId());
                }
                Direccion dirExistente = existente.getDireccion();
                Direccion dirNueva = cliente.getDireccion();

                dirExistente.setCalle(dirNueva.getCalle());
                dirExistente.setNumero(dirNueva.getNumero());
                dirExistente.setColonia(dirNueva.getColonia());
                dirExistente.setCodigoPostal(dirNueva.getCodigoPostal());
            }
            return true;
        }
        return false;
    }

    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }

    //METODOS DEL ADMIN
    public boolean eliminar(Integer id) {
        Cliente cliente = obtenerPorId(id);
        if (cliente != null) {
            clientes.remove(cliente);
            return true;
        }
        return false;
    }

    public boolean desactivar(Integer id) {
        Cliente cliente = obtenerPorId(id);
        if (cliente != null) {
            cliente.setEstado(false);
            return true;
        }
        return false;
    }

    public boolean activar(Integer id) {
        Cliente cliente = obtenerPorId(id);
        if (cliente != null) {
            cliente.setEstado(true);
            return true;
        }
        return false;
    }
}
