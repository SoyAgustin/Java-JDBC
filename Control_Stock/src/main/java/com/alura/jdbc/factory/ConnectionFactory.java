package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*Factory method, tiene como objeto crear una conexión en una sola clase.*/
public class ConnectionFactory {
	/*Vamos a usar el pool de conexiones de c3p0
	 * en lugar de crear directamente la conexion con el DriverManager.getconnection()*/
	private DataSource dataSource;
	public ConnectionFactory() {
		var pooledDatasource = new ComboPooledDataSource();
		pooledDatasource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
		pooledDatasource.setUser("root");
		pooledDatasource.setPassword("Be594622d2");
		this.dataSource = pooledDatasource;
	}
	
	public Connection recuperaConexion() throws SQLException {
		return  this.dataSource.getConnection();
	}
}
