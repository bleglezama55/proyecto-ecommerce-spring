package proyectoecommercejava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyectoecommercejava.demo.model.DetalleOrden;

//Inyección del repository para que lo reconosca el spring
@Repository
//Creando una interfaz para obtener los datos del detalle de orden en la BD
public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer>{
    
}
