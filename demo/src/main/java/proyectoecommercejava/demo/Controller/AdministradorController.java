package proyectoecommercejava.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import proyectoecommercejava.demo.model.Producto;
import proyectoecommercejava.demo.service.ProductoService;




@Controller
//Estamos mapeando la cadena administrador para en el navegador encuentre la dirección 
//del localhost principal
@RequestMapping("/administrador")
public class AdministradorController {

    //Autowired permite resolver la inyección de dependencias de los siguiente modos. 
    //En el constructor de la clase.
    //Autowired en el constructor, la inyección se realiza en el momento en que el objeto es creado, 
    //lo que nos permite marcar con un final real nuestro recurso inyectado
    @Autowired 
    //objeto producto service
    private ProductoService productoService;

    //Redirección para el localhost 8085 hacia a la home
    @GetMapping("")
    //Le pasamos el objeto Model para reunir en un solo objeto el atributo del modelo 
    public String home(Model model){

        //Lista de productos accediendo al objeto producto servicio para mostrar los productos
        List<Producto> productos = productoService.findAll();
        //el objeto model obtendra el nombre del atributo y el objeto de la lista de productos
        model.addAttribute("productos", productos);

        //Redirecciona al la vista principal
        return "administrador/home";
    }

    //función para que nos muestre los productos en la home

    
}
