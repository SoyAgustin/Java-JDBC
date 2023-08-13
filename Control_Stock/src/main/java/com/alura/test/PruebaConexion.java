package com.alura.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PruebaConexion {
	public static void main(String[] args) throws SQLException {
		//Usamos el método getConnection que recibe url, usuario y contraseña.
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
				"root",
				"Be594622d2"
				);
		System.out.println("Cerrando la conexión");
		//Siempre hay que cerrar la conexion.
		con.close();
	}
}
