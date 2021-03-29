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

import com.j256.ormlite.dao.Dao;
import ean.exposicionCanina.mundo.Perro;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * Es el panel donde se muestra la lista de perros y están los botones para interactuar con la lista
 */
public class PanelListaPerros extends JPanel implements ListSelectionListener {
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
     * Es la lista que se muestra
     */
    private JList<Perro> listaPerros;

    /**
     * Componente de desplazamiento para contener la lista gráfica
     */
    private JScrollPane scroll;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el panel e inicializa todos sus componentes
     * @param ventanaPrincipal es una referencia a la clase principal de la interfaz - ventanaPrincipal != null
     */
    public PanelListaPerros(InterfazExposicionCanina ventanaPrincipal) {
        principal = ventanaPrincipal;

        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(new EmptyBorder(4, 3, 3, 3), new TitledBorder("Perros en la exposición")));

        listaPerros = new JList<>();
        listaPerros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPerros.addListSelectionListener(this);

        scroll = new JScrollPane(listaPerros);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), new LineBorder(Color.BLACK, 1)));

        add(scroll, BorderLayout.CENTER);
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Actualiza la lista de perros que se está mostrando
     */
    public void refrescarLista(Dao<Perro, String> perros) {
        ArrayList<Perro> lst = new ArrayList<>();
        for (Perro p : perros) {
            lst.add(p);
        }
        Perro[] arregloPerros = new Perro[lst.size()];
        lst.toArray(arregloPerros);
        listaPerros.setListData(arregloPerros);
        listaPerros.setSelectedIndex(0);
    }

    /**
     * Selecciona un elemento de la lista
     */
    public void seleccionar(Perro seleccionado) {
        listaPerros.setSelectedValue(seleccionado, true);
        // listaPerros.ensureIndexIsVisible(seleccionado);
    }

    /**
     * Permite seleccionar el último perro de la lista. El que acaba de ser agregado a la base de datos
     */
    public void seleccionarUltimo() {
        listaPerros.setSelectedIndex(listaPerros.getModel().getSize() - 1);
    }

    /**
     * Permite obtener el nombre del perro que está seleccionado de la lista. Si la lista está vacía o no hay un
     * perro seleccionado, retorna null
     */
    public Perro darPerroSeleccionado() {
        if (listaPerros.getSelectedValue() != null) {
            Perro p = listaPerros.getSelectedValue();
            return p;
        }
        return null;
    }

    /**
     * Cambia la información del perro que se está mostrando de acuerdo al nuevo perro seleccionado
     * @param e es el evento de cambio el ítem seleccionado en la lista
     */
    public void valueChanged(ListSelectionEvent e) {
        if (listaPerros.getSelectedValue() != null) {
            Perro p = listaPerros.getSelectedValue();
            principal.verDatos(p);
        }
    }
}