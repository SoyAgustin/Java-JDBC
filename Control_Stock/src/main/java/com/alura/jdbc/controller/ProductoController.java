package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;

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

	
	
	public void guardar(Map<String,String> producto) throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con){
			/*Manejo de las transacciones de la base de datos se hacen automaticamente
			 * */
			con.setAutoCommit(false);

			final PreparedStatement statement = con.prepareStatement("INSERT INTO producto (nombre, descripcion, cantidad)"+
					"VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
			try(statement){
				/*Ahora vamos a agregar una nueva regla de negocio
				 * solo se admiten en una peticion hasta 50 productos
				 * si son más la query se dividira en dos*/

				String nombre = producto.get("NOMBRE");
				String descripcion = producto.get("DESCRIPCION");
				Integer cantidad = Integer.valueOf(producto.get( "CANTIDAD"));
				Integer maximaCantidad = 50;
				/*La logica va a ser restar la maxima cantidad hasta que la cantidad sea
				 * cero, de esta forma se dividiran los registros en bloques de la maxima cantidad*/
				try {	
					do {
						int cantidadParaGuardar = Math.min(cantidad, maximaCantidad);
						ejecutaRegistro(statement, nombre, descripcion, cantidadParaGuardar);
						cantidad -= maximaCantidad;
					}while(cantidad>0) ;

					//Hasta el momento en el que se cumple todo el ciclo (sin errores) se usa el commit
					con.commit();
					System.out.println("COMMIT");
				}catch(Exception e){
					/*En caso de que haya un error, se cancela la transaccion*/
					con.rollback();
					System.out.println("ROLLBACK");
				}
			}
		}
	}

	private void ejecutaRegistro(PreparedStatement statement, String nombre, String descripcion, Integer cantidad)
			throws SQLException {
//Creando el error de prueba para cantidades menores a 50
//	if(cantidad <50) {
//		throw new RuntimeException("Ocurrio un error");
//	}
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3,cantidad);
		
		/*usando las prepared statement el metodo execute queda vacio*/
		statement.execute();
		
		/*Colocando el resultSet dentro del try with resources ya no es necesario 
		 * cerrar la conexion manualmente, ya que se implementa la intefaz 
		 * AutoCloseable*/
		final ResultSet resultSet = statement.getGeneratedKeys();
		try(resultSet){
		while(resultSet.next()) {
			/*Como solo se retorna el id, el metodo getInt busca en la unica 'columna' devuelta*/
			System.out.println(String.format(
					"Se guardó el objeto con ID: %d",
					resultSet.getInt(1) )
					);
		}
		}
	}

}
