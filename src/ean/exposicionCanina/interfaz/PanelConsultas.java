/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Ean (Bogotá - Colombia)
 * Unidad de Estudio: Desarrollo de Software
 * <p>
 * Proyecto Exposición Canina
 * Fecha: Marzo de 2021
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package ean.exposicionCanina.interfaz;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * Este es el panel donde se encuentran los botones para hacer consulta sobre la exposición
 *
 */
public class PanelConsultas extends JPanel implements ActionListener {
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    private static final String GANADOR = "Ganador";

    private static final String MENOS_PUNTOS = "Menos puntos";

    private static final String MAYOR_EDAD = "Mayor edad";

    private static final String CONTAR_RAZA = "Contar Raza";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Es una referencia a la clase principal de la interfaz
     */
    private InterfazExposicionCanina principal;

    // -----------------------------------------------------------------
    // Atributos de la Interfaz
    // -----------------------------------------------------------------

    /**
     * Es el botón para mostrar el nombre del perro ganador
     */
    private JButton botonGanador;

    /**
     * Es el botón para mostrar el perro con menos puntos
     */
    private JButton botonMenosPuntos;

    /**
     * Es el botón para mostrar el nombre del perro con mayor edad
     */
    private JButton botonMayorEdad;

    /**
     * Es el botón para conocer cuantos perros pertenecen a una raza
     */
    private JButton botonContarRaza;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el panel e inicializa sus componentes.
     * @param ventanaPrincipal es una referencia a la clase principal de la interfaz - ventanaPrincipal != null
     */
    public PanelConsultas(InterfazExposicionCanina ventanaPrincipal) {
        principal = ventanaPrincipal;
        setLayout(new GridLayout(4, 1, 10, 10));
        setBorder(new CompoundBorder(new EmptyBorder(4, 3, 3, 3), new TitledBorder("Consultas Exposición")));

        // Ganador
        botonGanador = new JButton("Ganador");
        botonGanador.addActionListener(this);
        botonGanador.setActionCommand(GANADOR);
        add(botonGanador);

        // Menos puntos
        botonMenosPuntos = new JButton("Menor Puntaje");
        botonMenosPuntos.addActionListener(this);
        botonMenosPuntos.setActionCommand(MENOS_PUNTOS);
        add(botonMenosPuntos);

        // Mayor edad
        botonMayorEdad = new JButton("Más Viejo");
        botonMayorEdad.addActionListener(this);
        botonMayorEdad.setActionCommand(MAYOR_EDAD);
        add(botonMayorEdad);

        // Cuantos de una raza
        botonContarRaza = new JButton(CONTAR_RAZA);
        botonContarRaza.addActionListener(this);
        botonContarRaza.setActionCommand(CONTAR_RAZA);
        add(botonContarRaza);
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Ejecuta una acción según el botón que se haya presionado.
     * @param evento es el evento de click sobre un botón
     */
    public void actionPerformed(ActionEvent evento) {
        String comando = evento.getActionCommand();

        if (GANADOR.equals(comando)) {
            principal.buscarGanador();
        }
        else if (MENOS_PUNTOS.equals(comando)) {
            principal.buscarMenorPuntaje();
        }
        else if (MAYOR_EDAD.equals(comando)) {
            principal.buscarMayorEdad();
        }
        else if (CONTAR_RAZA.equalsIgnoreCase(comando)) {
            principal.contarPerrosRaza();
        }
    }
}
