package com.mycompany.conexionbd;

import java.sql.*;
import java.util.Scanner;

public class ConexionBD {

    //Nos conectamos a la base de datos con el usuario DAM 
    static final String DB_URL = "jdbc:mysql://localhost:3306/jcvd";
    static final String USER = "DAM";
    static final String PASS = "1234";
    static final String QUERY = "SELECT * FROM videojuegos";

    public static void main(String[] args) {
        //Comprobamos que la conexión funcione
        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); //Creamos el Statement y ejecutamos la consulta
            Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(QUERY);) {
            //Cerramos la conexión por seguridad al acabar
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }

    public static boolean buscaNombre(String nombre) {
        Boolean busca = false;
        String tabla = "videojuegos";
        //Hacemos la búsqueda con el String que se le introduzca
        String QUERY = "SELECT Nombre FROM videojuegos WHERE Nombre='" + nombre + "'";
        //Abrimos la conexión
        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            //Creamos el Statement y hacemos la consulta
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                //Mientras encuentre resiltados, busca en la columna nombre si coincide. Si es igual te devuelve true, si no el valor seguirá false
                tabla = rs.getString("Nombre");
                if (tabla.equals(nombre)) {
                    busca = true;
                }
            }
            //Cierra la conexión
            stmt.close();
            //Trata errores de SQL
        } catch (SQLException e) {
            System.out.println("Error");
        }
        return busca;
    }

    public void lanzaConsulta() {
        //Hacemos la consulta
        String QUERY = "SELECT * FROM videojuegos WHERE Nombre='Fortnite'";
        //Conectamos a la base de datos
        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            //Crea el Statement y ejecuta la consulta que le hemos pasado una vez se haya comprobado que la conexión funciona
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            //Escribimos el id el nombre el género, la fecha de lanzamiento, la compañía y el precio del juego que indicamos(Fornite)
            while (rs.next()) {
                System.out.println("El id es: " + rs.getInt("id"));
                System.out.println("Su nombre es: " + rs.getString("Nombre"));
                System.out.println("El género es: " + rs.getString("Genero"));
                System.out.println("La fecha es: " + rs.getDate("FechaLanzamiento"));
                System.out.println("La compañía es: " + rs.getString("Compañia"));
                System.out.println("EL precio es: " + rs.getFloat("Precio"));
            }
            //Cerramos la conexion
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }

    public void nuevoRegistro() {
        //Insertamos en la tabla videojuegos un nuevo juego, en este caso el Clash Royele
        String QUERY = "INSERT INTO videojuegos (Nombre, Genero, FechaLanzamiento, Compañia, Precio) VALUES ('Clash Royale','Estrategia','2016-3-2','Supercell','10')";
        //Hacemos la conexión
        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement stmt = conn.createStatement();
            //Con esto actualizamos la base de datos y añadimos el juego
            stmt.executeUpdate(QUERY);
            System.out.println("Se ha añadido el juego");
            //Cerramos la conexión
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }

    public void nuevoRegistroDos(String nombre, String genero, String Fecha, String compañia, float precio) {
        //Introducimos los valores que se nos ha especificado anteriormente
        String QUERY = "INSERT INTO videojuegos (Nombre, Genero, FechaLanzamiento, Compañia, Precio) VALUES ('" + nombre + "','" + genero + "','" + Fecha + "','" + compañia + "','" + precio + "')";
        //Abrimos conexión, creamos el Statement y ejecutamos la consulta
        //Después ponemos un mensaje de que se añadió el juego que pusiste, cerramos la conexión y tratamos posibles errores SQL
        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(QUERY);
            System.out.println("Se ha añadido el juego: " + nombre);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }

    public boolean eliminarRegistro(String nombre) {
        boolean busca = false;
        //Hacemos la consulta para que borre el juego que tenga el mismo nombre que se introduce
        String QUERY = "Delete from videojuegos WHERE Nombre='" + nombre + "'";
        String tabla = "videojuegos";
        //Abrimos conexión, creamos el Statement y ejecutamos la consulta
        try ( Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                //Mientras encuentre resultados, busca en la columna nombre si coincide. Si es igual te devuelve true y lo elimina, si no el valor seguirá false
                tabla = rs.getString("Nombre");
                if (tabla.equals(nombre)) {
                    stmt.executeUpdate(QUERY);
                    System.out.println("EL videojuego " + nombre + " ha sido eliminado correctamente");
                    busca = true;
                }
            }
            //Cerramos la conexión para evitar agujeros de seguridad y tratamos excepciones
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error");
        }
        return busca;
    }
}
