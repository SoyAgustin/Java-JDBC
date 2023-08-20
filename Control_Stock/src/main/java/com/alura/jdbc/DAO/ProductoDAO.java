package com.alura.jdbc.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alura.jdbc.modelo.Producto;

/*DAO: Data Access Object*/
public class ProductoDAO {
	final private Connection con;
	
	public ProductoDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Producto producto) throws SQLException {
		try (con){
			con.setAutoCommit(false);

			final PreparedStatement statement = con.prepareStatement("INSERT INTO producto (nombre, descripcion, cantidad)"+
					"VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);

			try(statement){

				ejecutaRegistro(statement, producto);
				con.commit();

				System.out.println("COMMIT");
			}catch(Exception e){
				/*En caso de que haya un error, se cancela la transaccion*/
				con.rollback();
				System.out.println("ROLLBACK");
			}
		}
	}
	
	private void ejecutaRegistro(PreparedStatement statement,Producto producto)
			throws SQLException {
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		statement.setInt(3,producto.getCantidad());
		
		/*usando las prepared statement el metodo execute queda vacio*/
		statement.execute();
		
		/*Colocando el resultSet dentro del try with resources ya no es necesario 
		 * cerrar la conexion manualmente, ya que se implementa la intefaz 
		 * AutoCloseable*/
		final ResultSet resultSet = statement.getGeneratedKeys();
		try(resultSet){
		while(resultSet.next()) {
			/*Como solo se retorna el id, el metodo getInt busca en la unica 'columna' devuelta*/
			producto.setId(resultSet.getInt(1) );
			System.out.println(String.format(
					"Se guardó el objeto con ID: %s",
					producto)
					);
		}
		}
	}
}
