package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.alura.jdbc.factory.ConnectionFactory;
/*Clase que implementa el metodo eliminar tal cual está en ProductoController
 * con el  fin de ver el comportamieto cuando no existe el item seleccionado*/
public class PruebaDelete {
	public static void main(String[] args) throws SQLException {
		Connection con  = new ConnectionFactory().recuperaConexion();
		
		Statement statement = con.createStatement();		
		statement.execute("DELETE FROM producto WHERE ID = 99");
	
		System.out.println(statement.getUpdateCount());//Retorna 0
	}
}
