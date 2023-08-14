package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*Factory method, tiene como objeto crear una conexión en una sola clase.*/
public class ConnectionFactory {
	public Connection recuperaConexion() throws SQLException {
		return  DriverManager.getConnection(
                "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
                "root",
                "Be594622d2");
	}
}
