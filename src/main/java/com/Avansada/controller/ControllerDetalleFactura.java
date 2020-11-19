package com.Avansada.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Avansada.Modelo.Cliente;
import com.Avansada.Modelo.DetalleBodega;
import com.Avansada.Modelo.DetalleFactura;
import com.Avansada.Modelo.Factura;
import com.Avansada.Modelo.Producto;
import com.Avansada.repository.RepoCliente;
import com.Avansada.repository.RepoDetallaFactura;
import com.Avansada.repository.RepoFactura;
import com.Avansada.repository.RepoProducto;

@Controller
public class ControllerDetalleFactura {
	
	RepoDetallaFactura repo;
	RepoProducto repoProducto;
	RepoCliente repoCliente;
	RepoFactura repoFactura;
	public static Factura factura;
	

	
	
	public ControllerDetalleFactura(RepoDetallaFactura repo,RepoProducto repoProducto,RepoCliente repoCliente,RepoFactura repoFactura) {
		super();
		this.repo = repo;
		this.repoProducto=repoProducto;
		this.repoCliente=repoCliente;
		this.repoFactura=repoFactura;
	
	}
	
	
	
	@GetMapping("/AgregarMiCarrito/{id}")
	public String registrarDetalle(@PathVariable("id") Integer id,Model model) {
		
		if(ControllerCliente.getCedula()==0) {
			return "redirect:/index";
		}
		
		ArrayList<Factura> lista=repoFactura.lista(ControllerCliente.getCedula());
		if(lista.size()==0) {
			factura=new Factura();
			Cliente cliente=repoCliente.BuscarCLiente(ControllerCliente.getCedula());
			factura.setCliente(cliente);
			repoFactura.save(factura);
		}
		
		ArrayList<Factura> listaBusqueda=repoFactura.lista(ControllerCliente.getCedula());
		
		for (int i = 0; i < listaBusqueda.size(); i++) {
			
			if(listaBusqueda.get(i).getIdFactura()!=0 && listaBusqueda.get(i).getFecha()==null) {
				Factura factura=listaBusqueda.get(i);
				Producto producto=repoProducto.buscarProductoId(id);
			    int precioActual=factura.getPrecioTotal()+producto.getPrecioVentaUnidad();
			    factura.setPrecioTotal(precioActual);
			    repoFactura.save(factura);
			    int idFactura=factura.getIdFactura();
			    ArrayList<DetalleFactura> det=repo.buscarFactura(idFactura);
			    if(det.size()!=0) {
			    	DetalleFactura vDetalle=repo.buscar(factura.getDetalleFacturas().get(i).getIdFactura());
				    if(vDetalle!=null) {
				    	int cantid=vDetalle.getCantidad()+1;
				    	vDetalle.setCantidad(cantid);
				    	repo.save(vDetalle);
				    }else {
				    	DetalleFactura newDetalle=new DetalleFactura();
						newDetalle.setProducto(producto);
						newDetalle.setFactura(listaBusqueda.get(i));
						newDetalle.setCantidad(1);
						repo.save(newDetalle);
				    }
			    }else {
			    	DetalleFactura newDetalle=new DetalleFactura();
					newDetalle.setProducto(producto);
					newDetalle.setFactura(listaBusqueda.get(i));
					newDetalle.setCantidad(1);
					repo.save(newDetalle);
			    }
			    
				
			}else if(i+1 == listaBusqueda.size()){
				Producto producto=repoProducto.buscarProductoId(id);
				factura=new Factura();
				Cliente cliente=repoCliente.BuscarCLiente(ControllerCliente.getCedula());
				factura.setCliente(cliente);
				factura.setPrecioTotal(producto.getPrecioVentaUnidad());
				repoFactura.save(factura);
				
				
				DetalleFactura newDetalle=new DetalleFactura();
				newDetalle.setProducto(producto);
				newDetalle.setFactura(listaBusqueda.get(i));
				newDetalle.setCantidad(1);
				repo.save(newDetalle);
			}
			
			
		}
		return "redirect:/IndexCliente";
	}
	
	public ArrayList<DetalleFactura> listar(int cedulaCliente) {
		
		ArrayList<DetalleFactura> detalleFactura=repo.BuscarDetalleFactura(cedulaCliente);
		if(detalleFactura!=null) {
			return detalleFactura;
		}else {
			return null;
		}
		
	}
	
     public ArrayList<DetalleFactura> buscarCedulaFactura(int cedula,int idFactura) {
		
    	 ArrayList<DetalleFactura> detalleFactura=repo.buscarCedulaFactura(cedula, idFactura);
		if(detalleFactura!=null) {
			return detalleFactura;
		}else {
			return null;
		}
		
	}
	
	
	
    @GetMapping("/EliminarMiCarrito/{id}")
	public String eliminar(@PathVariable("id") Integer idDetalle) {
    	DetalleFactura detalle=repo.buscar(idDetalle);
    	Factura factura=detalle.getFactura();
    	int cantidaActual=detalle.getCantidad();
    	int precioProducto=detalle.getProducto().getPrecioVentaUnidad();
        int precioActual=factura.getPrecioTotal();
        int precioEliminacion=precioProducto*cantidaActual;
        int precioTotal=precioActual-precioEliminacion;
        
        System.err.println(precioActual);
        System.out.println(precioEliminacion);
        System.out.println(precioTotal);
        
        factura.setPrecioTotal(precioTotal);
    	repoFactura.save(factura);
    	repo.deleteById(idDetalle);
    	return "redirect:/Micarrito";
	}
    
	@GetMapping("/Restar/{id}")
	public String modificarCantidadMas(@PathVariable("id") Integer id) {
		DetalleFactura detalle=repo.buscar(id);
		if(detalle!=null) {
			Factura factura=detalle.getFactura();
			Producto producto=detalle.getProducto();
			int cantidad=detalle.getCantidad()-1;
			if(cantidad!=0) {
				int precioActual=factura.getPrecioTotal();
				int precioTotal=0;
				for (int i = 0; i < cantidad; i++) {
					precioTotal=precioActual-producto.getPrecioVentaUnidad();
				}
				System.out.println(precioTotal);
				factura.setPrecioTotal(precioTotal);
				repoFactura.save(factura);
				detalle.setFactura(factura);
				detalle.setCantidad(cantidad);
				repo.save(detalle);
				return "redirect:/Micarrito";
			}else {
				return "redirect:/IndexCliente" ;
			}
		}else {
			return "redirect:/Index" ;
		}
		
	}
	
	@GetMapping("/sumar/{id}")
	public String modificarCantidadMenos(@PathVariable("id") Integer id) {
		DetalleFactura detalle=repo.buscar(id);
		if(detalle!=null) {
			Factura factura=detalle.getFactura();
			Producto producto=detalle.getProducto();
			int cantidad=detalle.getCantidad()+1;
			if(cantidad!=0) {
				int precioActual=factura.getPrecioTotal();
				int precioTotal=0;
				for (int i = 1; i < cantidad; i++) {
					precioTotal=precioActual+producto.getPrecioVentaUnidad();
				}
				factura.setPrecioTotal(precioTotal);
				repoFactura.save(factura);
				detalle.setFactura(factura);
				detalle.setCantidad(cantidad);
				repo.save(detalle);
				return "redirect:/Micarrito";
			}else {
				return "redirect:/IndexCliente" ;
			}
		}else {
			return "redirect:/Index" ;
		}
		
	}
	

	

}
