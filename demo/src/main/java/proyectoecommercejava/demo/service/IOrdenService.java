package proyectoecommercejava.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import proyectoecommercejava.demo.model.Orden;
import proyectoecommercejava.demo.model.Usuario;

//Inyección del service para que lo reconosca el spring
@Service
public interface IOrdenService {

    //Método de la interface que nos permite obtener una lista ordenes
    List<Orden> findAll();

    //Método de la interface de tipo orden llamada save
    Orden save (Orden orden);

    //Método de la interface para generar numero de orden
    String generarNumeroOrden();

    //Cramos un método de lista de orden que para que genere la info de las ordenes del usuario
    List<Orden> findByUsuario (Usuario usuario);

    
}
