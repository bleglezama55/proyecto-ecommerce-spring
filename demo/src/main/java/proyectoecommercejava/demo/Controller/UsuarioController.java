package proyectoecommercejava.demo.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import proyectoecommercejava.demo.model.Usuario;
import proyectoecommercejava.demo.service.IUsuarioService;

//Cognotación del controller para que spring lo reconozca
@Controller
//Nombre del url localhost llamdo usuario
@RequestMapping("/usuario")
public class UsuarioController{

    //Variable de tipo loger para imprimir a consola esta clase
    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    //Inyección que indica el contenedor framework una instancia de la clase
    @Autowired
    //Objeto IUsuarioService para accerder a la info
    private IUsuarioService usuarioService;

    //nombre del url localhost usuario/registro
    @GetMapping("/registro")
    //Método de crear vista usuario
    public String create(){
        //Devuelve a la página de usuario
        return "usuario/registro";
    }

    //nombre del url localhost llamado save tipo post
    @PostMapping("/save")
    //Método de guardar usuario
    private String save(Usuario usuario){
        //Se imprime el objeto usuario por consola
        logger.info("Usuario registro: {}", usuario);
        //Le pasamos el tipo de usuario llamado User
        usuario.setTipo("USER");
        //le pasamos el método guardar desde la intefaz IUsuarioService
        usuarioService.save(usuario);
        //Devuelve al directorio principal
        return "redirect:/";
    }

}