package com.alura.jdbc.controller;

import java.util.List;

import com.alura.jdbc.DAO.ProductoDAO;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {
	
	private ProductoDAO productoDAO=new ProductoDAO();
	
	
	public ProductoController() {
		this.productoDAO = productoDAO;
	}

	
	public int modificar(String nombre, String descripcion, Integer cantidad,Integer id) {
		return productoDAO.modificar(nombre,descripcion,cantidad,id);
	}

	public int eliminar(Integer id)  {
		return productoDAO.eliminar(id);
	}

	public List<Producto> listar(){
		return productoDAO.listar();
	}

	public void guardar(Producto producto)  {
		productoDAO.guardar(producto);
	}

}
