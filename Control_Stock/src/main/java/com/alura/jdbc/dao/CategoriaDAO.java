package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;

public class CategoriaDAO {
	/*
	private Connection con;

	public CategoriaDAO(Connection recuperaConexion) {
		this.con = con;
	}
*/	

	public List<Categoria> listar() {
		final Connection con =	new ConnectionFactory().recuperaConexion();
		List <Categoria> resultado  = new ArrayList<>();
		
		try { 
			var querySelect = "SELECT id, nombre FROM categoria";
			System.out.println(querySelect);
			final PreparedStatement statement = con.prepareStatement(
					"SELECT id, nombre FROM categoria") ;
			try(statement){
				final ResultSet resultSet = statement.executeQuery();
				try(resultSet){
					while(resultSet.next()) {
						var categoria = new Categoria(resultSet.getInt("id"),resultSet.getString("nombre") );
						
						resultado.add(categoria);
					}
					}
				return resultado;
				}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
