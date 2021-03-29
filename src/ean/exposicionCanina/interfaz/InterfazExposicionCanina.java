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
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import ean.exposicionCanina.mundo.ExposicionPerros;
import ean.exposicionCanina.mundo.Perro;
import org.apache.commons.text.WordUtils;

import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;

/**
 * Es la clase principal de la interfaz
 */
public class InterfazExposicionCanina extends JFrame {
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * La ruta del archivo con la información de los perros
     */
    public static final String ARCHIVO_PERROS = "./data/perros.txt";

    /**
     * La ruta al archivo con la base de datos de los perros
     */
    public static final String NOMBRE_BASE_DATOS_PERROS = "./data/perros.db";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Es una referencia a la exposición de perros
     */
    private ExposicionPerros exposicion;

    // -----------------------------------------------------------------
    // Atributos de la Interfaz
    // -----------------------------------------------------------------

    /**
     * Es el panel donde se muestra la lista de perros
     */
    private PanelListaPerros panelLista;

    /**
     * Es el panel donde se muestran los datos de un perro
     */
    private PanelDatosPerro panelDatos;

    /**
     * Es el panel donde se introducen los datos para agregar un perro
     */
    private PanelAgregarPerro panelAgregar;

    /**
     * Es el panel donde están los botones para los puntos de extensión
     */
    private PanelExtension panelExtension;

    /**
     * Es el panel donde están los botones para la realización de ordenamientos y búsqueda
     */
    private PanelBusquedaOrdenamientos panelBusquedaOrdenamientos;

    /**
     * Es el panel donde están las opciones para la realización de consultas
     */
    private PanelConsultas panelConsultas;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye la interfaz e inicializa todos sus componentes.
     *
     * @param archivoPerros es el nombre del archivo de propiedades que contiene la información de los perros
     */
    public InterfazExposicionCanina(String archivoPerros) {
        Dao<Perro, String> baseDatosPerros = cargarPerros(archivoPerros);
        exposicion = new ExposicionPerros(baseDatosPerros);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Exposición Canina");
        setSize(new Dimension(800, 650));
        setResizable(false);

        setLayout(new GridBagLayout());

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridBagLayout());

        panelLista = new PanelListaPerros(this);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 175;
        panelSuperior.add(panelLista, gbc);

        panelDatos = new PanelDatosPerro();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 45;
        gbc.ipady = 40;
        panelSuperior.add(panelDatos, gbc);

        panelBusquedaOrdenamientos = new PanelBusquedaOrdenamientos(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        gbc.ipady = 100;
        panelSuperior.add(panelBusquedaOrdenamientos, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(panelSuperior, gbc);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridBagLayout());

        panelAgregar = new PanelAgregarPerro(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 35;
        gbc.ipadx = 20;
        panelCentral.add(panelAgregar, gbc);

        panelConsultas = new PanelConsultas(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 85;
        panelCentral.add(panelConsultas, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(panelCentral, gbc);

        panelExtension = new PanelExtension(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(panelExtension, gbc);
        actualizarLista();
        centrar();
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Centra la ventana en la pantalla
     */
    private void centrar() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int xEsquina = (screen.width - getWidth()) / 2;
        int yEsquina = (screen.height - getHeight()) / 2;
        setLocation(xEsquina, yEsquina);
    }

    /**
     * Actualiza la lista de perros mostrada.
     */
    private void actualizarLista() {
        panelLista.refrescarLista(exposicion.darPerros());
    }

    /**
     * Elimina el perro seleccionado de la lista y actualiza la lista
     */
    public void eliminarPerroSeleccionado() {
        Perro perroSeleccionado = panelLista.darPerroSeleccionado();
        if (perroSeleccionado != null) {
            String nomPerroSeleccionado = perroSeleccionado.darNombre();
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que quiere eliminar el perro " + nomPerroSeleccionado + "?",
                    "Eliminar perro",
                    JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            try {
                exposicion.eliminarPerro(nomPerroSeleccionado);
                panelDatos.limpiarDatos();
                actualizarLista();
                JOptionPane.showMessageDialog(this, "Perro eliminado exitosamente!", "Eliminar Perro", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (SQLException exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el perro con nombre " + nomPerroSeleccionado, "Eliminar Perro", JOptionPane.INFORMATION_MESSAGE);
            }

        }

    }



    /**
     * Busca un perro usando el nombre y cuando lo encuentra lo selecciona en la lista y muestra sus datos.
     */
    public void buscar() {
        String nombreBuscado = JOptionPane.showInputDialog(this, "Nombre del perro");
        if (nombreBuscado != null) {
            Perro perro = exposicion.buscarPerro(nombreBuscado);

            if (perro != null) {
                actualizarLista();
                panelLista.seleccionar(perro);
                verDatos(perro);
            }
            else {
                JOptionPane.showMessageDialog(this, "No se encontró el perro");
            }
        }
    }

    /* ----------------------------
    MÉTODOS AGREGADOS
     */
    public void ordenarPorRaza() {
        exposicion.ordenarPorRaza();
        panelDatos.limpiarDatos();
        actualizarLista();
    }

    public void ordenarPorPuntos() {
        exposicion.ordenarPorPuntos();
        panelDatos.limpiarDatos();
        actualizarLista();
    }

    public void ordenarPorEdad() {
        exposicion.ordenarPorEdad();
        panelDatos.limpiarDatos();
        actualizarLista();
    }

    /*
    MÉTODOS AGREGADOS
    -------------------------------- */

    /**
     * Muestra los datos de un perro en el panel correspondiente.
     *
     * @param perro es el perro del que se quieren ver los datos - perro != null
     */
    public void verDatos(Perro perro) {
        panelDatos.mostrarDatos(perro);
    }

    /**
     * Agrega un nuevo perro.
     *
     * @param nombreP es el nombre del perro - nombreP != null
     * @param razaP   es la raza del perro - razaP != null
     * @param imagenP La ruta a la imagen del perro - imagenP != null
     * @param alturaP La altura del perro - alturaP > 0
     * @param edadP   La edad en meses del perro - edadP > 0
     */
    public void agregarPerro(String nombreP, String razaP, String imagenP, int alturaP, int edadP) {
        boolean agrego = exposicion.agregarPerro(nombreP, razaP, imagenP, alturaP, edadP);
        if (!agrego) {
            JOptionPane.showMessageDialog(this, "No se pudo agregar el perro dado que el nombre " + nombreP + " ya lo tiene otro perro", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            actualizarLista();
            panelLista.seleccionarUltimo();
        }

    }

    /**
     * Abre la base de datos con la información de los perros
     */
    private Dao<Perro, String> cargarPerros(String archivo) {
        try {
            // Nos conectamos con la base de datos de perros
            JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource("jdbc:sqlite:" + NOMBRE_BASE_DATOS_PERROS);

            // Ahora creamos la base de datos de perro, si no existe
            TableUtils.createTableIfNotExists(connectionSource, Perro.class);

            // Y por último, obtenemos acceso a la base de datos
            return DaoManager.createDao(connectionSource, Perro.class);
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Busca el perro ganador y muestra sus datos en el panel de datos.
     */
    public void buscarGanador() {
        Perro p = exposicion.buscarPerroMayorPuntaje();

        actualizarLista();
        if (p != null) {
            panelLista.seleccionar(p);
            verDatos(p);
        }
        else {
            JOptionPane.showMessageDialog(this, "No hay perros registrados en la exposición");
        }
    }

    /**
     * Busca el perro con menor puntaje en la exposición y muestra sus datos en el panel de datos
     */
    public void buscarMenorPuntaje() {
        Perro p = exposicion.buscarPerroMenorPuntaje();

        actualizarLista();
        if (p != null) {
            panelLista.seleccionar(p);
            verDatos(p);
        }
        else {
            JOptionPane.showMessageDialog(this, "No hay perros registrados en la exposición");
        }
    }

    /**
     * Busca el perro de mayor edad y muestra sus datos en el panel de datos
     */
    public void buscarMayorEdad() {
        Perro p = exposicion.buscarPerroMayorEdad();

        actualizarLista();
        if (p != null) {
            panelLista.seleccionar(p);
            verDatos(p);
        }
        else {
            JOptionPane.showMessageDialog(this, "No hay perros registrados en la exposición");
        }
    }

    /**
     * Permite saber cuantos perros de una determinada raza hay en la base de datos
     */
    public void contarPerrosRaza() {
        String raza = JOptionPane.showInputDialog(null, "¿Cuál raza de perro vamos a contar?",
                "Contar Razas", JOptionPane.QUESTION_MESSAGE);
        if (raza != null && raza.strip().length() != 0) {
            int cantidad = exposicion.contarPerrosRaza(WordUtils.capitalizeFully(raza));
            JOptionPane.showMessageDialog(null, "Tenemos " + cantidad + " perro(s)  de la raza " + raza);
        }
    }

    /**
     * Permite modificar los puntos asociados al perro seleccionado
     */
    public void cambiarPuntosPerroSeleccionado() {
        Perro perroSeleccionado = panelLista.darPerroSeleccionado();
        if (perroSeleccionado != null) {
            String resp = JOptionPane.showInputDialog(this, "¿Cual es el nuevo puntaje del perro " + perroSeleccionado.darNombre() + "?", String.valueOf(perroSeleccionado.darPuntos()));
            if (resp == null || resp.strip().length() == 0) {
                return;
            }
            try{
                int nuevosPuntos = Integer.parseInt(resp);
                if (nuevosPuntos <= 0) {
                    JOptionPane.showMessageDialog(this, "El número de puntos no es correcto!", "Cambiar Puntos", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                exposicion.cambiarPuntosPerro(perroSeleccionado.darNombre(), nuevosPuntos);
                panelDatos.limpiarDatos();
                actualizarLista();
                JOptionPane.showMessageDialog(this, "Puntos del perro " + perroSeleccionado.darNombre() + " cambiados exitosamente!");
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cambiar los puntos del perro", "Cambiar Puntos", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "El número de puntos no es correcto!", "Cambiar Puntos", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Permite conocer el primer perro que tiene más años que el que especifica el usuario
     */
    public void buscarPorEdad() {
        String resp = JOptionPane.showInputDialog(null, "¿Cuál es la edad mínima a buscar?",
                "Buscar por Edad", JOptionPane.QUESTION_MESSAGE);
        if (resp == null || resp.strip().length() == 0) {
            return;
        }
        int edad = 0;
        try {
            edad = Integer.parseInt(resp);
            if (edad <= 0) {
                JOptionPane.showMessageDialog(this, "La edad debe ser un valor numérico positivo", "Buscar por Edad", JOptionPane.ERROR_MESSAGE);
                return ;
            }
            Perro perro = exposicion.buscarPerroPorEdad(edad);
            if (perro != null) {
                actualizarLista();
                panelLista.seleccionar(perro);
                verDatos(perro);
            }
            else {
                JOptionPane.showMessageDialog(this, "No hay perros con una edad superior a " + edad + " meses!");
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un valor numérico positivo", "Buscar por Edad", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    // -----------------------------------------------------------------
    // Puntos de Extensión
    // -----------------------------------------------------------------

    /**
     * Ejecuta el punto de extensión 1
     */
    public void reqFuncOpcion1() {
        System.exit(0);
    }

    // -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * Ejecuta la aplicación
     *
     * @param args son los parámetros de ejecución de la aplicación. No deben usarse
     */
    public static void main(String[] args) {
        InterfazExposicionCanina iec = new InterfazExposicionCanina(ARCHIVO_PERROS);
        iec.setVisible(true);
    }


}