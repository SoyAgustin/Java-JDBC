package com.alura.jdbc.controller;

import java.sql.Connection;
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
		//Establecemos una conexion con la base de datos mysql
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

	
	/*Para guardar primero realizamos una conexion con la base de datos
	 * posteriormente creamos un statement (consulta)*/
    public void guardar(Map<String,String> producto) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		Statement statement= con.createStatement();
		
		/*En este caso necesitamos agregar comillas simples ( ' ) para la consulta
		 * y es por eso que se concatena y combina entre las comillas dobles ( " ).
		 * Para confurdirnos lo menos posible, entendemos que las comillas dobles son para 
		 * Java y las comillas simples para SQL. */
		
		/*Como el parámetro de este método es un Map llamado producto, la consulta 
		 * va a tomar el valor de la clave que se le pida con el método .get, así que 
		 * producto.get("clave") devuelve el valor correspondiente.*/
		statement.execute("INSERT INTO producto (nombre, descripcion, cantidad)"
				+"VALUES"+"("
				+"'"+producto.get("NOMBRE")+"'"+"," //explicitamente coloco las comillas simples en una cadena aparte
				+"'"+producto.get("DESCRIPCION")+"'"+","//El motivo es que se regresan Strings y eso cerraría las comillas dobles usuales
				+"'"+producto.get("CANTIDAD")+"'"
				+")"
				,Statement.RETURN_GENERATED_KEYS);
		/*Usamos el método sobrecargado que devuelve las keys generadas automaticamente
		 * en este caso son las ID*/
		
		ResultSet resultSet = statement.getGeneratedKeys();
		
		while(resultSet.next()) {
			/*Como solo se retorna el id, el metodo getInt busca en la unica 'columna' devuelta*/
			System.out.println(String.format(
					"Se guardó el objeto con ID: %d",
					resultSet.getInt(1) )
					);
		}
		
	}

}
