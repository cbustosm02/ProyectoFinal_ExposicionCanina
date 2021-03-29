/*
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  Universidad Ean (Bogotá - Colombia)
  Unidad de Estudio: Desarrollo de Software
  <p>
  Proyecto Exposición Canina
  Fecha: Marzo de 2021
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package ean.exposicionCanina.mundo;

import com.j256.ormlite.dao.Dao;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;


/**
 * Es la clase que se encarga de manejar, organizar, cargar y salvar los perros. <br>
 */
public class ExposicionPerros {
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Este objeto representa la base de datos con los perros
     */
    private Dao<Perro, String> perros;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye un nuevo manejador de perros vacío.
     */
    public ExposicionPerros(Dao<Perro, String> tablaPerros) {
        perros = tablaPerros;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Retorna una lista de perros. La lista que se retorna no es la misma que la almacenada en esta clase, pero si tiene el mismo orden.
     */
    public Dao<Perro, String> darPerros() {
        return this.perros;
    }

    /**
     * Busca un perro según su nombre y retorna el objeto Perro que tiene ese nombre. Retorna null si no encuentra
     * el perro con el nombre dado.
     */
    public Perro buscarPerro(String nombre) {
        // TODO: Busca un perro según su nombre y retorna el objeto Perro que tiene ese nombre. Retorna null si no encuentra el perro
        Perro encontrado = null;

                try {
                    ArrayList<Perro> p = (ArrayList<Perro>) perros.queryForEq("nombre", nombre);
                    if (p.size() > 0) {
                        encontrado = p.get(0);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    return null;
                }

        return encontrado;
    }

    /**
     * Agrega un nuevo perro a la exposición. <br>
     * <b>post: </b> El perro fue agregado a la exposición si no existe otro perro con el mismo nombre.
     */
    public boolean agregarPerro(String nombreP, String razaP, String imagenP, int puntosP, int edadP) {
        Perro perroBuscado = buscarPerro(nombreP);
        boolean agregado = false;
        if (perroBuscado == null) {
            /* -----------
               Método modificado - Ahora la imagen se guarda en base64 en la base de datos
               para evitar almacenar rutas parciales que no existen en otros dispositivos
               donde se ejecute el proyecto
            */
            byte[] fileContent;
            try {
                fileContent = FileUtils.readFileToByteArray(new File(imagenP));
                imagenP= Base64.getEncoder().encodeToString(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
               Método modificado
            ----------- */
            Perro nuevoPerro = new Perro(nombreP, razaP, imagenP, puntosP, edadP);
            try {
                perros.create(nuevoPerro);
                agregado = true;
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return agregado;
    }

    /**
     * Busca el perro que tenga el mayor puntaje en la exposición.
     */
    public Perro buscarPerroMayorPuntaje() {
        Perro pMayorPuntaje = null;

        // TODO: Busca el perro que tenga el mayor puntaje en la exposición.
        for (Perro p: perros) {
            if (pMayorPuntaje == null) {
                pMayorPuntaje = p;
            } else if (p.darPuntos() > pMayorPuntaje.darPuntos()) {
                pMayorPuntaje = p;
            }
        }

        return pMayorPuntaje;
    }

    /**
     * Busca el perro que tenga el menor puntaje en la exposición.
     */
    public Perro buscarPerroMenorPuntaje() {
        Perro pMenorPuntaje = null;

        // TODO: Busca el perro que tenga el mayor puntaje en la exposición.
        for (Perro p: perros) {
            if (pMenorPuntaje == null) {
                pMenorPuntaje = p;
            } else if (p.darPuntos() < pMenorPuntaje.darPuntos()) {
                pMenorPuntaje = p;
            }
        }
        return pMenorPuntaje;
    }

    /**
     * Busca el perro que tenga la mayor edad.
     */
    public Perro buscarPerroMayorEdad() {
        Perro pMayorEdad = null;

        // TODO: Busca el perro que tenga la mayor edad.
        for (Perro p: perros) {
            if (pMayorEdad == null) {
                pMayorEdad = p;
            } else if (p.darEdad() > pMayorEdad.darEdad()) {
                pMayorEdad = p;
            }
        }
        return pMayorEdad;
    }


    /**
     * Obtiene la cantidad de perros que pertenencen a la raza dada que se recibe como parámetro
     */
    public int contarPerrosRaza(String razaPerro) {
        int cont = 0;

        // TODO: Obtiene la cantidad de perros que pertenencen a la raza dada que se recibe como parámetro
        for (Perro p: perros) {
           if (p.darRaza().equalsIgnoreCase(razaPerro)) {
                cont++;
            }
        }
        return cont;
    }

    /**
     * Permite eliminar de la base de datos el perro que tiene el nombre dado
     */
    public void eliminarPerro(String nombreDelPerro) throws SQLException {
        // TODO: elimina de la base de datos el perro que tiene el nombre dado
        Perro p = buscarPerro(nombreDelPerro);
        perros.delete(p);
    }

    /**
     * Permite cambiarle los puntos asociados al perro con el nombre dado
     */
    public void cambiarPuntosPerro(String nombrePerro, int nuevosPuntos) throws SQLException {
        // TODO: cambia los puntos asociados al perro con el nombre dado
        Perro p = buscarPerro(nombrePerro);
        if (p != null) {
            p.cambiarPuntos(nuevosPuntos);
            perros.update(p);
        }
    }

    /**
     * Su misión es encontrar el primer perro en la base de datos que tenga una edad superior o igual a
     * la edad que se pasa como parámetro
     */
    public Perro buscarPerroPorEdad(int edad) {
        Perro perro = null;
        // TODO: encuentra el primer perro que tenga una edad superior o igual a la edad que se pasa como parámetro
        for (Perro p: perros ) {
            if (p.darEdad() >= edad) {
                perro = p;
                break;
            }
        }
        return perro;
    }

    /* ----------------------------
      MÉTODOS AGREGADOS
    */

    /**
     * Ordema la lista de perros por su raza de manera ascendente
     */
    public void ordenarPorRaza() {
        //TODO: Ordenar lista de perros por raza
        int n = 0;
        ArrayList<Perro> p = null;
        try {
           p = (ArrayList<Perro>) this.perros.queryForAll();
           n = p.size();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Perro temp;
        for (int i = 0; i < n; i++) {
            for (int j =  i +1; j < n; j++) {
                if (p.get(j).darRaza().compareToIgnoreCase(p.get(i).darRaza())<0) {
                    temp = p.get(i);
                    p.set(i, p.get(j));
                    p.set(j, temp);
                }
            }
        }
            System.out.println(p);
        for (Perro up: p) {
            try {
                perros.delete(up);
                perros.create(up);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Ordema la lista de perros por su puntos de manera ascendente
     */
    public void ordenarPorPuntos() {
        //TODO: Ordenar lista de perros por puntos
        int n = 0;
        ArrayList<Perro> p = null;
        try {
            p = (ArrayList<Perro>) this.perros.queryForAll();
            n = p.size();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Perro temp;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (p.get(j).darPuntos() < p.get(i).darPuntos()) {
                    temp = p.get(i);
                    p.set(i, p.get(j));
                    p.set(j, temp);
                }
            }
        }
        System.out.println(p);
        for (Perro up : p) {
            try {
                perros.delete(up);
                perros.create(up);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Ordema la lista de perros por su Edad de manera ascendente
     */
    public void ordenarPorEdad() {
        //TODO: Ordenar lista de perros por edad
        int n = 0;
        ArrayList<Perro> p = null;
        try {
            p = (ArrayList<Perro>) this.perros.queryForAll();
            n = p.size();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Perro temp;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (p.get(j).darEdad() < p.get(i).darEdad()) {
                    temp = p.get(i);
                    p.set(i, p.get(j));
                    p.set(j, temp);
                }
            }
        }
        System.out.println(p);
        for (Perro up : p) {
            try {
                perros.delete(up);
                perros.create(up);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /*
    MÉTODOS AGREGADOS
    -------------------------------- */
}