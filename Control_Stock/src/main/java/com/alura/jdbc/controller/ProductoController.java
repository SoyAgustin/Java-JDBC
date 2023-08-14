package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public void modificar(String nombre, String descripcion, Integer id) {
		// TODO
	}

	public void eliminar(Integer id) {
		// TODO
	}

	//La SQLExcepcion la trata en ControDeStockFrame  en el método cargar tabla con un try catch.
	//Declaeamos que el metodo devuelve un listado de tipo Map<String,String>
	public List<Map<String,String>> listar() throws SQLException{
		//EStablecemos una conexion con la base de datos mysql
		Connection con  = new ConnectionFactory().recuperaConexion();
		
		//En java las querys de sql son del tipo Statement del paquete sql
		Statement statement = con.createStatement();
		
		//Se ejecuta la consulta a la base de datos y se almacena internamente 
		//en el statement para despues consultar el resultado en resultSet
		statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM producto");
		
		//resultSet es de un tipo iterador, tiene el metodo next
		ResultSet resultSet = statement.getResultSet();
		
		List<Map<String,String>> resultado  = new ArrayList<>();
		
		/*Se itera sobre cada elemento del resultado
		 * Para entender mejor el resultado lo queremos en forma de tabla 
		 * las filas se crean con un Map de tipo HashMap, mientras que las
		 * columnas se forman con el método put.
		 * 
		 * El método getInt, getString, etc, reciben como parametro el nombre de la
		 * columna (campo) o bien pueden recibir enteros, comenzando por el numero 1.
		 * 
		 *  Como tenemos un Map<String,String> se hace un 
		 *  cast a los enteros de la columna ID y CANTIDAD*/
		while(resultSet.next()) {
			
			Map<String, String> fila = new HashMap<>();
			fila.put("ID", String.valueOf(resultSet.getInt("ID")));
			fila.put("NOMBRE",resultSet.getString("NOMBRE"));
			fila.put("DESCRIPCION",resultSet.getString("DESCRIPCION"));
			fila.put("CANTIDAD",String.valueOf(resultSet.getInt("CANTIDAD")));
			
			resultado.add(fila);
		}
		con.close();
		return resultado;
	}

    public void guardar(Object producto) {
		// TODO
	}

}
