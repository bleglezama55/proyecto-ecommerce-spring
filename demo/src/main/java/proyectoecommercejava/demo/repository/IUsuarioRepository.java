package proyectoecommercejava.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyectoecommercejava.demo.model.Usuario;

//Inyección de repositorio en spring
@Repository
//Creando una interfaz para obtener los datos del usuario
public interface IUsuarioRepository extends JpaRepository<Usuario,Integer>{

    //Nota: Se puede trabajar tambien con el atributo id para jalar toda la info del usuario 
    //pero esta vez se trabajara con el atributo email para obtener info del usuario a traves del email
    //Mètodo de tipo opcional para encontrar la info del usuario desde el email
    Optional<Usuario> findByEmail(String email);
    
}
