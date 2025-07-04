/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Tienda.Web.controller;

import Tienda.Web.domain.Producto;
import Tienda.Web.Service.CategoriaService;
import Tienda.Web.Service.ProductoService;
//import Tienda.Web.Service.impl.FirebaseStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/producto")
public class ProductoController {
       
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;
    //@Autowired
    //private FirebaseStorageServiceImpl firebaseStorageService;
         
    @GetMapping("/listado")
    private String listado(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
                 
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
                 
        model.addAttribute("totalProductos",productos.size());
        return "/producto/listado";
    }
          
    // ✅ MÉTODO CORREGIDO - Ahora envía las categorías
    @GetMapping("/nuevo")
    public String productoNuevo(Producto producto, Model model) {
        // Agregar las categorías al modelo para el dropdown
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        
        return "/producto/modifica";
    }
         
    @PostMapping("/guardar")
    public String productoGuardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile) {
                
        if (!imagenFile.isEmpty()) {
            productoService.save(producto);
            //producto.setRutaImagen(
                    //firebaseStorageService.cargaImagen(
                            //imagenFile, 
                            //"producto", 
                            //producto.getIdProducto()));
        }
        productoService.save(producto);
        return "redirect:/producto/listado";
    }
    
    @GetMapping("/eliminar/{idProducto}")
    public String productoEliminar(Producto producto) {
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }
    
    // ✅ MÉTODO MODIFICAR TAMBIÉN NECESITA CATEGORÍAS
    @GetMapping("/modificar/{idProducto}")
    public String productoModificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);
                 
        // Agregar las categorías para el dropdown
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
                 
        return "/producto/modifica";
    }
}