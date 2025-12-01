/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package Exceptions;

/**
 *
 * @author Alici
 */
public class ModeloException extends Exception {

    /**
     * Creates a new instance of <code>ModeloException</code> without detail
     * message.
     */
    public ModeloException() {
    }

    public ModeloException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an instance of <code>ModeloException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ModeloException(String msg) {
        super(msg);
    }
}
