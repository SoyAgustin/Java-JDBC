package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.DAO.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {

	public void modificar(String nombre, String descripcion, Integer cantidad,Integer id) throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion();
		try(con){
			//Haciendo uso de prepared statement para evitar sql inyection
			final PreparedStatement statement = con.prepareStatement("UPDATE producto SET "
					+ " nombre =?,"
					+ " descripcion=?,"
					+ " cantidad=?"
					+ " WHERE id=?");
			try(statement){
				statement.setString(1,nombre);
				statement.setString(2,descripcion);
				statement.setInt(3, cantidad);
				statement.setInt(4, id);	
				statement.execute();
			}	
		}
	}

	public int eliminar(Integer id) throws SQLException {
		final Connection con  = new ConnectionFactory().recuperaConexion();
		try(con){
			//Haciendo uso de prepared statement para evitar sql inyection
			final PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE ID =?");
			try(statement){
				statement.setInt(1, id);
				statement.execute();

				//Para saber si se realizo bien la eliminación usamos el método getUpdateCount()
				//Este numero entero dice cuántas líneas fueron modificadas.
				int updateCount = statement.getUpdateCount();

				con.close();

				return  updateCount;
			}
		}
	}

	//La SQLExcepcion la trata en ControDeStockFrame  en el método cargar tabla con un try catch.
	//Declaeamos que el metodo devuelve un listado de tipo Map<String,String>
	public List<Map<String,String>> listar() throws SQLException{
		//Establecemos una conexion con la base de datos mysql
		final Connection con  = new ConnectionFactory().recuperaConexion();
		try(con){
			//Hacemos uso del prepared statement para evitar sql inyection
			final PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM producto");
			try(statement){
				statement.execute();

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
		}
	}

	
	
	public void guardar(Producto producto) throws SQLException {
		ProductoDAO productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
		
		productoDAO.guardar(producto);
	}

}
