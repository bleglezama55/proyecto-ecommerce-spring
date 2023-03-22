package proyectoecommercejava.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyectoecommercejava.demo.model.Usuario;

//Inyección de repositorio en spring
@Repository
//Creando una interfaz para obtener los datos del usuario
public interface IUsuarioRepository extends JpaRepository<Usuario,Integer>{
    
}
