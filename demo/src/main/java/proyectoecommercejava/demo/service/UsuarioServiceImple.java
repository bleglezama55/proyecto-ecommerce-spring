package proyectoecommercejava.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyectoecommercejava.demo.model.Usuario;
import proyectoecommercejava.demo.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImple implements IUsuarioService{

    //Creamos objeto IUsuarioRepository
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findById(Integer id) {
        // Devolvemos el id de la interfaz del método usuario find by id
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        // Devuelve al método guardar usuario de la interfaz
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        // // Devolvemos el email de la interfaz del método usuario find by email
        return usuarioRepository.findByEmail(email);
    }

    
}
