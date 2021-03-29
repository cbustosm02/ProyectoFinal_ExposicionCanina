/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Ean (Bogotá - Colombia)
 * Unidad de Estudio: Desarrollo de Software
 * <p>
 * Proyecto Exposición Canina
 * Fecha: Marzo de 2021
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package ean.exposicionCanina.mundo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.apache.commons.text.WordUtils;

/**
 * Es la clase que representa a un perro. <br>
 */
@DatabaseTable(tableName = "perros")
public class Perro {
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * El nombre del perro
     */
    @DatabaseField(id = true)
    private String nombre;

    /**
     * La raza del perro
     */
    @DatabaseField(canBeNull = false)
    private String raza;

    /**
     * La ruta hasta la imagen del perro
     */
    @DatabaseField
    private String imagen;

    /**
     * Los puntos del perro en la exposición
     */
    @DatabaseField(canBeNull = false)
    private int puntos;

    /**
     * La edad en meses del perro
     */
    @DatabaseField(canBeNull = false)
    private int edad;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye un nuevo perro con los parámetros por defecto.
     */
    public Perro() {
        this("", "", "", 0, 0);
    }

    /**
     * Construye un nuevo perro con los parámetros indicados.
     */
    public Perro(String nombreP, String razaP, String imagenP, int puntosP, int edadP) {
        nombre = WordUtils.capitalizeFully(nombreP);
        raza = WordUtils.capitalizeFully(razaP);
        imagen = imagenP;
        puntos = puntosP;
        edad = edadP;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Retorna el nombre del perro.
     */
    public String darNombre() {
        return nombre;
    }

    /**
     * Retorna la raza del perro.
     */
    public String darRaza() {
        return raza;
    }

    /**
     * Retorna la ruta a la imagen del perro.
     */
    public String darImagen() {
        return imagen;
    }

    /**
     * Retorna los puntos del perro en la exposición.
     */
    public int darPuntos() {
        return puntos;
    }

    /**
     * Retorna la edad en meses del perro.
     */
    public int darEdad() {
        return edad;
    }

    /**
     * Permite cambiar el puntaje del perro
     */
    public void cambiarPuntos(int nuevosPuntos) {
        if (nuevosPuntos > 0) {
            puntos = nuevosPuntos;
        }
    }

    /**
     * Permite saber si un perro es igual a otro que se pasa como parámetro
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Perro perro = (Perro) o;

        return nombre.equalsIgnoreCase(perro.nombre);
    }

    /**
     * Obtiene el código de hash de este objeto
     */
    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

     /**
     * Retorna una cadena con el nombre del perro
     */
    public String toString() {
        return nombre + " (" + raza + ")";
    }

}