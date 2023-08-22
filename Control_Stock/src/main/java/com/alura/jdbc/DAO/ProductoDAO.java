package com.alura.jdbc.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

/*DAO: Data Access Object*/
public class ProductoDAO {
	
	public void guardar(Producto producto)  {
		final Connection con  = new ConnectionFactory().recuperaConexion();
		try (con){
			final PreparedStatement statement = con.prepareStatement("INSERT INTO producto (nombre, descripcion, cantidad)"+
					"VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
			try(statement){
				ejecutaRegistro(statement, producto);
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
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

	public List<Producto> listar() {
		
		List<Producto> resultado  = new ArrayList<>();

		final Connection con  = new ConnectionFactory().recuperaConexion();

		try(con){
			final PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM producto");
			try(statement){
				statement.execute();

				final ResultSet resultSet = statement.getResultSet();

				try(resultSet){
					while(resultSet.next()) {

						Producto fila = new Producto(resultSet.getInt("ID"),
								resultSet.getString("NOMBRE"),
								resultSet.getString("DESCRIPCION"),
								resultSet.getInt("CANTIDAD"));

						resultado.add(fila);
					}
				}
			}
			return resultado;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	public int eliminar(Integer id) {
		final Connection con  = new ConnectionFactory().recuperaConexion();
		try(con){
		
			final PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE ID =?");
			try(statement){
				statement.setInt(1, id);
				statement.execute();
				
				int updateCount = statement.getUpdateCount();

				return  updateCount;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	public int modificar(String nombre, String descripcion, Integer cantidad,Integer id) {
		final Connection con = new ConnectionFactory().recuperaConexion();
		try(con){
		
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
			
			
			int updateCount = statement.getUpdateCount();

			return  updateCount;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}


