/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package Tienda.Web.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import Tienda.Web.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDao extends JpaRepository <Producto, Long> {
    
    public List<Producto> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup);
    
    @Query(value="SELECT a FROM Producto a where a.precio BETWEEN :precioInf AND :precioSup ORDER BY a.descripcion ASC")
    public List<Producto> metodoJPQL(@Param("precioInf")double precioInf, @Param("precioSup")double precioSup);
}