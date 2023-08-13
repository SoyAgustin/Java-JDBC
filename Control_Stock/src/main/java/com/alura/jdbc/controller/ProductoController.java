package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoController {

	public void modificar(String nombre, String descripcion, Integer id) {
		// TODO
	}

	public void eliminar(Integer id) {
		// TODO
	}

	//La SQLExcepcion la trata en ControDeStockFrame  en el método cargar tabla con un try catch.
	public List<?> listar() throws SQLException{
		//EStablecemos una conexion con la base de datos mysql
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/control_de_stock?useTImeZone=true&serverTimeZone=UTC",
				"root",
				"Be594622d2");
		
		//En java las querys de sql son del tipo Statement del paquete sql
		Statement statement = con.createStatement();
		
		//SELECT devuelve un true porque devuelve un listado, INSERT, UPDATE O DELETE un false
		Boolean result = statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM producto");
		
		//Vemos en la consola que arroja como resultado un true
		System.out.println(result);
		
		con.close();
		return new ArrayList<>();
	}

    public void guardar(Object producto) {
		// TODO
	}

}
