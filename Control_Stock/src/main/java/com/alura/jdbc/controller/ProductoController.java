package com.alura.jdbc.controller;

import java.util.List;

import com.alura.jdbc.DAO.ProductoDAO;
import com.alura.jdbc.modelo.Categoria;
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
	
	/*Método sobrecargado para listar productos con base 
	 * en su categoría*/
	public List<Producto> listar(Categoria categoria){
		return productoDAO.listar(categoria.getId());
	}
	
	public void guardar(Producto producto, Integer categoriaId)  {
		producto.setCategoriaId(categoriaId);
		productoDAO.guardar(producto);
	}

}
