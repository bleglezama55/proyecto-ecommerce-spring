package proyectoecommercejava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyectoecommercejava.demo.model.Producto;

//El repository es un artefacto que es de tipo spring para que el framework lo entienda de esa manera
@Repository
//Se crea una interface de la clase producto en el cual se creara los métodos crud de tipo entero
//para el id de cada clase
public interface IProductoRepository extends JpaRepository<Producto, Integer>{
    
}
