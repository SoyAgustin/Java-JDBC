package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.alura.jdbc.DAO.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {
	
	private ProductoDAO productoDAO;
	
	public ProductoController() {
		/*Tratamos la SQL excepcion directamente en recuperaConexion()
		 * y quitamos los trhows de la excepcion que teníamos mas adelante*/
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}

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
	public List<Producto> listar(){
		return productoDAO.listar();
	}

	
	
	public void guardar(Producto producto)  {
		productoDAO.guardar(producto);
	}

}
