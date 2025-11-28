/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.itson.dao;

import Conexion.ConexionJPA;
import DAOs.UsuarioDAO;
import entidades.Usuario;
import enums.TipoUsuario;

/**
 * Clase Main para inicializar la base de datos del e-commerce
 *
 * @author erika
 */
public class DAO {

    public static void main(String[] args) {
        System.out.println("   INICIALIZANDO BASE DE DATOS - ECOMMERCE   ");

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

            // Crear un usuario administrador
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setCorreo("admin@ecommerce.com");
            admin.setContrasena("admin123");
            admin.setTipoUsuario(TipoUsuario.ADMINISTRADOR);

            if (usuarioDAO.crear(admin)) {
                System.out.println("  Usuario ADMINISTRADOR creado");
                System.out.println("    - Correo: admin@ecommerce.com");
                System.out.println("    - Password: admin123");
            }

            // Crear un usuario cliente
            Usuario cliente = new Usuario();
            cliente.setNombre("Cliente Ejemplo");
            cliente.setCorreo("cliente@ejemplo.com");
            cliente.setContrasena("cliente123");
            cliente.setTipoUsuario(TipoUsuario.CLIENTE);

            if (usuarioDAO.crear(cliente)) {
                System.out.println("Usuario CLIENTE creado");
                System.out.println("Correo: cliente@ejemplo.com");
                System.out.println("Password: cliente123");
            }

        } catch (Exception e) {
            System.err.println("\nERROR al inicializar la base de datos:");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
