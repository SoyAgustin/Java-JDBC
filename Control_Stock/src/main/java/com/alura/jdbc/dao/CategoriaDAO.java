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
			final PreparedStatement statement = con.prepareStatement(querySelect) ;
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

	public List<Categoria> listarConProductos() {
		final Connection con =	new ConnectionFactory().recuperaConexion();
		List <Categoria> resultado  = new ArrayList<>();
		
		try { 
			/*Se usa el nombre C para la tabla categoria y P para la de producto*/
			var querySelect = "SELECT C.id, C.nombre, P.id, P.nombre, P.cantidad"
					+ " FROM categoria C "
					+" INNER JOIN producto P ON C.id = P.categoria_id";
			System.out.println(querySelect);
			final PreparedStatement statement = con.prepareStatement(
					querySelect) ;
			try(statement){
				final ResultSet resultSet = statement.executeQuery();
				try(resultSet){
					while(resultSet.next()) {
						
						Integer categoriaId = resultSet.getInt("id");
						String categoriaNombre = resultSet.getString("nombre");
						
						var categoria =resultado
								.stream()
								.filter(cat -> cat.getId().equals(categoriaId))
								.findAny().orElseGet(()->{
									var cat =new Categoria(categoriaId,categoriaNombre );
									
									resultado.add(cat);
									return cat;
								});
								
								
					}
					}
				return resultado;
				}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
