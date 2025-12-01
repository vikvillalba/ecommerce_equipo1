/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.itson.dao;

import Conexion.ConexionJPA;
import DAOs.UsuarioDAO;
import DAOs.ClienteDAO;
import entidades.Administrador;
import entidades.Usuario;
import entidades.Cliente;
import entidades.Direccion;
import entidades.Pedido;
//import enums.TipoUsuario;

/**
 * Clase Main para inicializar la base de datos del e-commerce
 *
 * @author erika
 */
public class DAO {

    public static void main(String[] args) {
        System.out.println("INICIALIZANDO BASE DE DATOS - ECOMMERCE   ");

        try {
            ConexionJPA conexion = ConexionJPA.getInstance();

            if (conexion.estaConectado()) {
                System.out.println("Conexion establecida exitosamente!");
                System.out.println("Base de datos: Ecommerce en localhost\n");
            } else {
                System.err.println("Error: No se pudo establecer la conexion");
                return;
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            ClienteDAO clienteDAO = new ClienteDAO();

            System.out.println("1. Creando usuario ADMINISTRADOR...");
            Usuario admin = new Administrador();
            admin.setNombre("Administrador");
            admin.setCorreo("admin@admin.com");
            admin.setContrasena("admin123");
//            admin.setTipoUsuario(TipoUsuario.ADMINISTRADOR);

            if (usuarioDAO.crear(admin)) {
                System.out.println("Usuario ADMINISTRADOR creado exitosamente");
                System.out.println("Nombre: Administrador");
                System.out.println("Correo: admin@admin.com");
                System.out.println("Contraseña: admin123");
                System.out.println("Tipo: ADMINISTRADOR\n");
            } else {
                System.err.println("Error al crear usuario ADMINISTRADOR\n");
            }

            System.out.println("2. Creando CLIENTE completo...");

            // Crear el Usuario del Cliente
            Cliente cliente = new Cliente();
            cliente.setNombre("Jesus en moto");
            cliente.setCorreo("jesus@moto.com");
            cliente.setContrasena("1234");
            //            usuarioCliente.setTipoUsuario(TipoUsuario.CLIENTE);

            // Crear la Dirección del Cliente
            Direccion direccion = new Direccion();
            direccion.setCalle("Narnia");
            direccion.setNumero("123");
            direccion.setColonia("Centro");
            direccion.setCodigoPostal("85000");

            // Crear el Cliente y asociar Usuario + Dirección
//            cliente.setUsuario(usuarioCliente);
            cliente.setTelefono("6441234567");
            cliente.setEstado(true);
            cliente.setDireccion(direccion);

            if (clienteDAO.registrar(cliente)) {
                System.out.println("CLIENTE creado exitosamente");
                System.out.println("Nombre: Jesus en moto");
                System.out.println("Correo: jesus@moto.com");
                System.out.println("Contraseña: 1234");
                System.out.println("Teléfono: 6441234567");
                System.out.println("Dirección: Narnia #123, Colonia Centro, CP 85000");
                System.out.println("Tipo: CLIENTE\n");
            } else {
                System.err.println("Error al crear CLIENTE\n");
            }

            
        } catch (Exception e) {
            System.err.println("\nERROR al inicializar la base de datos:");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
