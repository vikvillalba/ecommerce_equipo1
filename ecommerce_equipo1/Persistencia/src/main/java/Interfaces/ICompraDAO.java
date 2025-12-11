/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import entidades.Pedido;

/**
 *
 * @author Alici
 */
public interface ICompraDAO {

    public Pedido generarPedido(Pedido pedido) throws Exception;

}
