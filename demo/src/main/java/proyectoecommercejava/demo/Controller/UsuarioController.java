package proyectoecommercejava.demo.Controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
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
    public String save(Usuario usuario){
        //Se imprime el objeto usuario por consola
        logger.info("Usuario registro: {}", usuario);
        //Le pasamos el tipo de usuario llamado User
        usuario.setTipo("USER");
        //le pasamos el método guardar desde la intefaz IUsuarioService
        usuarioService.save(usuario);
        //Devuelve al directorio principal
        return "redirect:/";
    }

    //obtendra nombre del url localhost llamado login
    @GetMapping("/login")
    //Método de la vista login 
    public String login(){
        //Devuelve a la vista login
        return "usuario/login";
    }

    //nombre del url localhost llamado accerder tipo post
    @PostMapping("/acceder")
    //Mètodo para accerder al login 
    //HttpSession: Sesion de logeo al usuario para tener autenticaciòn del acceso a la pagina  
    public String accerder(Usuario usuario, HttpSession session){
        logger.info("Accesos: {}", usuario);
        //Me va obtener un usuario que tenga ese email
        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
        //Me va imprimir por consola la info del email del usuario desde la BD
        //logger.info("Usuario de la BD: {}", user.get());
        //Si el usuario tiene presente ese registro con ese email en la BD
        if(user.isPresent()){
            //Entonces que me obtenga la sesion desde el id usuario
            session.setAttribute("idusuario", user.get().getId());
            //Si el el usuario tienen el tipo de usuario (ADMIN)
            if(user.get().getTipo().equals("ADMIN")){
                //Entonces que me redireccione a la pagina del administrador
                return "redirect:/administrador";
            }else{
                //De otro modo que me redireccione a la pagina principal
                return "redirect:/";
            }
        }else{
            //Mensaje de error
            logger.info("Usuario no existe");
        }
        //Nos devuelve al directorio principal
        return "redirect:/";
    }

}