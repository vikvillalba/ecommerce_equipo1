/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import entidades.Resena;
import java.util.List;

/**
 *
 * @author erika
 */
public interface IResenaDAO {
    public List<Resena> obtenerTodasLasResenas();
    public void eliminarResena(Integer idResena);
    public void moderarResena(Integer idResena, String mensaje);
    public List<Resena> listarTodas();
    public boolean crearResena(Resena resena);
}
