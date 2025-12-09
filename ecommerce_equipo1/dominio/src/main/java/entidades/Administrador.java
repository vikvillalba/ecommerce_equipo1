package entidades;

import enums.TipoUsuario;
import java.io.Serializable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 *
 * @author Alici
 */
@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Usuario implements Serializable {

    public Administrador() {
    }

}
