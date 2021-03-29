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
 * Este es el panel donde se encuentran los botones para realizar los ordenamientos por distintos criterios y las búsquedas.
 */
public class PanelBusquedaOrdenamientos extends JPanel implements ActionListener {
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    private static final String BUSCAR = "Buscar";

    private static final String CAMBIAR_PUNTOS = "CambiarPuntos";

    private static final String BUSCAR_EDAD = "BuscarEdad";

    private static final String ELIMINAR = "Eliminar";

    private static final String ORDENAR_RAZA = "OrdenarRaza";

    private static final String ORDENAR_PUNTOS = "OrdenarPuntos";

    private static final String ORDENAR_EDAD = "OrdenarEdad";

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
     * Es el botón para eliminar un perro de la lista de perros
     */
    private JButton botonEliminar;

    /**
     * Es el botón para cambiar los puntos de un perro
     */
    private JButton botonCambiarPuntos;

    /**
     * Es el botón para buscar el primer perro según edad
     */
    private JButton buscarPorEdad;

    /**
     * Es el botón para realizar una búsqueda
     */
    private JButton botonBuscar;
    // -----------------------------------------------------------------
    // Ordenamientos agregados
    // -----------------------------------------------------------------

    /**
     * Es el botón para ordenar la lista de perros por raza
     */
    private JButton botonOrdernarRaza;

    /**
     * Es el botón para ordenar la lista de perros por puntos
     */
    private JButton botonOrdernarPuntos;

    /**
     * Es el botón para ordenar la lista de perros por edad
     */
    private JButton botonOrdernarEdad;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el panel e inicializa todos sus componentes.
     * @param ventanaPrincipal es una referencia a la clase principal de la interfaz - ventanaPrincipal != null
     */
    public PanelBusquedaOrdenamientos(InterfazExposicionCanina ventanaPrincipal) {
        principal = ventanaPrincipal;

        setPreferredSize(new Dimension(200, 0));
        setBorder(new CompoundBorder(new EmptyBorder(4, 3, 3, 3), new TitledBorder("Buscar, Modificar y Ordernar")));
        setLayout(new GridBagLayout());

        botonEliminar = new JButton("Eliminar Perro");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        botonEliminar.setActionCommand(ELIMINAR);
        botonEliminar.addActionListener(this);
        add(botonEliminar, gbc);

        botonCambiarPuntos = new JButton("Cambiar Puntos Perro");
        botonCambiarPuntos.setActionCommand(CAMBIAR_PUNTOS);
        botonCambiarPuntos.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        add(botonCambiarPuntos, gbc);

        buscarPorEdad = new JButton("Buscar por Edad");
        buscarPorEdad.setActionCommand(BUSCAR_EDAD);
        buscarPorEdad.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        add(buscarPorEdad, gbc);

        botonBuscar = new JButton("Buscar Perro");
        botonBuscar.setActionCommand(BUSCAR);
        botonBuscar.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        add(botonBuscar, gbc);

        /* --------------------------
        Botones agregados
         */
        botonOrdernarRaza = new JButton("Ordernar por Raza");
        botonOrdernarRaza.setActionCommand(ORDENAR_RAZA);
        botonOrdernarRaza.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        add(botonOrdernarRaza, gbc);

        botonOrdernarPuntos = new JButton("Ordernar por Puntos");
        botonOrdernarPuntos.setActionCommand(ORDENAR_PUNTOS);
        botonOrdernarPuntos.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        add(botonOrdernarPuntos, gbc);

        botonOrdernarEdad = new JButton("Ordernar por Edad");
        botonOrdernarEdad.setActionCommand(ORDENAR_EDAD);
        botonOrdernarEdad.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        add(botonOrdernarEdad, gbc);
        /*
        Botones agregados
        -------------------------- */
    }

    /**
     * Ejecuta una acción según el botón que se haya presionado.
     * @param evento es el evento de click sobre un botón
     */
    public void actionPerformed(ActionEvent evento) {
        String comando = evento.getActionCommand();

        if (ELIMINAR.equals(comando)) {
            principal.eliminarPerroSeleccionado();
        }
        else if (CAMBIAR_PUNTOS.equals(comando)) {
            principal.cambiarPuntosPerroSeleccionado();
        }
        else if (BUSCAR_EDAD.equals(comando)) {
            principal.buscarPorEdad();
        }
        else if (BUSCAR.equals(comando)) {
            principal.buscar();
        }
        /* --------------------------
        Condiciones agregadas
         */
        else if (ORDENAR_RAZA.equals(comando)) {
            principal.ordenarPorRaza();
        }
        else if (ORDENAR_PUNTOS.equals(comando)) {
            principal.ordenarPorPuntos();
        }
        else if (ORDENAR_EDAD.equals(comando)) {
            principal.ordenarPorEdad();
        }
        /*
        Condiciones agregadas
        -------------------------- */
    }
}
