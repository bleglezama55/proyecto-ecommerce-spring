package proyectoecommercejava.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import proyectoecommercejava.demo.model.Orden;
import proyectoecommercejava.demo.model.Usuario;
import proyectoecommercejava.demo.service.IOrdenService;
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

    //Inyección que indica el contenedor framework una instancia de la clase
    @Autowired
    //
    private IOrdenService ordenService;

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

    @GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session){
        //Le pasamos el atributo del modelo llamada sesion y tambien le pasamos la sesion del id usuario
        model.addAttribute("sesion", session.getAttribute("idusuario"));

        //objeto usuario almacena el método del id de la session  
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();

        //Se crea lista de ordenes que va a almacenar la orden del servicio pasando la session del 
        //id usuario
        List<Orden> ordenes = ordenService.findByUsuario(usuario); 

        //atributo del modelo llamado ordenes y le pasa las ordenes
        model.addAttribute("ordenes", ordenes);
        return "usuario/compras";
    }

    //nombre del url localhost y va indicar el id de la orden y cuales detalles indica esa orden
    //PathVariable: Nos permite mapear el argumento que vienen en la url
    //HttpSession: Sesion de logeo al usuario para tener autenticaciòn del acceso a la pagina
    //Model: Nos permite traer un atributo del objeto espcificado
    @GetMapping("/detalle/{id}")
    //Método de la vista detalle compra
    public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model){
        //Imprimimos por consola el id de la orden
        logger.info("Id de la orden: {}", id);
        //Le pasamos el id de la orden a traves del metodo de la interfaz findById de ordenService
        Optional<Orden> orden = ordenService.findById(id);
        //le pasamos el atributo del modelo de la orden para que me obtenga esa orden los detalles
        model.addAttribute("detalles", orden.get().getDetalle());
        //Sesion del id del usuario
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        //Devuelve la vista del detallecompra
        return "usuario/detallecompra";
    }


}