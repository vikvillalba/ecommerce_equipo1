package entidades;

import enums.TipoUsuario;
import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
