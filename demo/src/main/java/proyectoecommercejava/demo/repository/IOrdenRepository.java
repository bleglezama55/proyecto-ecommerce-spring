package proyectoecommercejava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyectoecommercejava.demo.model.Orden;

//Inyección del repository para que lo reconosca el spring
@Repository
//Creando una interfaz para obtener los datos de la orden en la BD
public interface IOrdenRepository extends JpaRepository<Orden, Integer>{
    
}
