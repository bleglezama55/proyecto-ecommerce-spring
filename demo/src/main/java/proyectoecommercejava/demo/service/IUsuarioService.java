package proyectoecommercejava.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import proyectoecommercejava.demo.model.Usuario;

//Inyecciòn de servicio spring
@Service
public interface IUsuarioService {
    //Definimos el método que va a permitir obtener un usuario de la BD desde el parametro Id
    Optional<Usuario> findById(Integer id);
    //Definimos el método que va a permitir guardar al usuario
    Usuario save (Usuario usuario);
    //Mètodo de tipo opcional para encontrar la info del usuario desde el email
    Optional<Usuario> findByEmail(String email);
    
}
