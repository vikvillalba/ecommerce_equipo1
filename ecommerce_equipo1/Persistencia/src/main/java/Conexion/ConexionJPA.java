package Conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase Singleton para gestionar la conexión a la base de datos MySQL usando JPA. Conexión configurada para localhost, base de datos: Ecommerce
 *
 * @author Jack Murrieta
 */
public class ConexionJPA {

    private static ConexionJPA instancia;
    private EntityManagerFactory emf;
    private static final String PERSISTENCE_UNIT_NAME = "Ecommerce";

    /**
     * Constructor privado para implementar el patrón Singleton
     */
    private ConexionJPA() {
        try {
            this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            System.out.println("Conexion JPA establecida exitosamente a la BD: Ecommerce");
        } catch (Exception e) {
            System.err.println("Error al crear EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la instancia única de ConexionJPA (Singleton)
     *
     * @return Instancia de ConexionJPA
     */
    public static ConexionJPA getInstance() {
        if (instancia == null) {
            synchronized (ConexionJPA.class) {
                if (instancia == null) {
                    instancia = new ConexionJPA();
                }
            }
        }
        return instancia;
    }

    /**
     * Crea y devuelve un nuevo EntityManager
     *
     * @return EntityManager para realizar operaciones con la BD
     */
    public EntityManager getEntityManager() {
        if (emf == null || !emf.isOpen()) {
            throw new IllegalStateException("EntityManagerFactory no esta disponible");
        }
        return emf.createEntityManager();
    }

    /**
     * Obtiene el EntityManagerFactory
     *
     * @return EntityManagerFactory
     */
    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    /**
     * Cierra el EntityManagerFactory
     */
    public void cerrarConexion() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("Conexion JPA cerrada");
        }
    }

    /**
     * Verifica si la conexión está activa
     *
     * @return true si está abierta, false en caso contrario
     */
    public boolean estaConectado() {
        return emf != null && emf.isOpen();
    }
}
