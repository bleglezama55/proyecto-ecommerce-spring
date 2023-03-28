package proyectoecommercejava.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyectoecommercejava.demo.model.Orden;
import proyectoecommercejava.demo.model.Usuario;

//Inyección del repository para que lo reconosca el spring
@Repository
//Creando una interfaz para obtener los datos de la orden en la BD
public interface IOrdenRepository extends JpaRepository<Orden, Integer>{

    //Cramos un método de lista de orden que para que genere la info de las ordenes del usuario
    List<Orden> findByUsuario (Usuario usuario);
    
}
