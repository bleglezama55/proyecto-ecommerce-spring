package proyectoecommercejava.demo.Controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import proyectoecommercejava.demo.model.Producto;
import proyectoecommercejava.demo.model.Usuario;
import proyectoecommercejava.demo.service.IUsuarioService;
import proyectoecommercejava.demo.service.ProductoService;
import proyectoecommercejava.demo.service.UploadFileService;



@Controller
//Estamos mapeando la cadena productos para en el navegador encuentre la dirección 
//del localhost principal
@RequestMapping("/productos")
public class ProductoController {

    //Logger: Nos va hacer un chequeo de lo que esta ejecutandose dentro del programa
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    // anotación que permite inyectar unas dependencias con otras dentro de Spring
    //como una mega factoria de objetos. Cada clase se registra para instanciar objetos con 
    //alguna de las anotaciones @Controller ,@Service ,@repository o @RestController.
    @Autowired
    //Variable de objeto de la clase ProductoService para acceder a todos los métodos de esa clase
    private ProductoService productoService;

    // anotación que permite inyectar unas dependencias con otras dentro de Spring
    //como una mega factoria de objetos. Cada clase se registra para instanciar objetos con 
    //alguna de las anotaciones @Controller ,@Service ,@repository o @RestController.
    @Autowired
    //Objeto de IUsuarioService para acceder a todos los métodos de esa clase
    private IUsuarioService usuarioService;

    // anotación que permite inyectar unas dependencias con otras dentro de Spring
    //como una mega factoria de objetos. Cada clase se registra para instanciar objetos con 
    //alguna de las anotaciones @Controller ,@Service ,@repository o @RestController.
    @Autowired
    //Variable de objeto de la clase UploadFileService para acceder a todos los métodos de esa clase
    private UploadFileService upload;

    //Redirección para el localhost 8085 hacia al show
    @GetMapping("")
    //el objeto model obtienen info desde el backend hacia a la vista para obtener los productos
    public String show(Model model){
        //Obtenemos desde el objeto modelo el complemento producto service para obtener los productos
        //hacia a la vista show
        model.addAttribute("productos", productoService.findAll());
        //Redirecciona al la vista de productos
        return "productos/show";
    }

    //Redirección para el localhost 8085 hacia al create
    @GetMapping("/create")
    public String create(){
        //Redirecciona al la vista de create
        return "productos/create";
    } 

    //Metodo que va a mapear la info desde el boton guardar para que se guarde en la BD
    //Pasamos por parametro objecto producto
    @PostMapping("/save")
    //RequestParam: es un parametro en cual le pasamos el nombre del id de la imagen del input html
    //HttpSession: Sesion de logeo al usuario para tener autenticaciòn del acceso a la pagina
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        //Mensaje por consola
        LOGGER.info("Este es el producto {}",producto);
        //Va a crear un objeto usuario para darle valor al id de la sessión del usuario 
        //Usuario User = new Usuario(1,"","","","","","","");
        Usuario User = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        //Va colocar ese objeto usuario para que el producto sea creado 
        producto.setUsuario(User);
        //Imagen
        if (producto.getId()==null){//Cuando se crea un producto
            //Obtiene el métoto guardar imagen de la clase UploadFileService 
            String nombreImagen = upload.saveImage(file);
            //Les pasa el nombre de la imagen al objeto producto
            producto.setImagen(nombreImagen);
        }else{
            
        }
        //El producto del servicio va a guardar el producto
        productoService.save(producto);
        //Redireccionamose a la vista show
        return "redirect:/productos";
    }

    //Metodo que va a mapear la info desde el boton editar para que se edite en la BD
    //Pasamos por parametro el id del producto
    //PathVariable: va a mapear la variable  en la url y pasa a la variable a la contigua 
    //cognotación del id
    @GetMapping("/edit/{id}") 
    public String edit(@PathVariable Integer id, Model model){
        //PAsamos el objeto producto
        Producto producto = new Producto();
        //obtenemos la variable optionalProducto ya que nos devuelve la busqueada de un objeto
        //tipo producto
        Optional<Producto> optionalProducto = productoService.get(id);
        //obtenemos el producto que lo va a mandar a buscar
        producto = optionalProducto.get();
        //Manda mensaje por consola
        LOGGER.info("Producto buscado: {}",producto);
        //Nos va enviar a la vista todo el objeto que lo ha buscado
        model.addAttribute("producto", producto);
        //Redireccionamose a la vista edit
        return "productos/edit";
    }

    //Redirección para el localhost 8085 hacia la vista update
    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException{
        
        //Creamos el objeto producto
        Producto p = new Producto();
        //el objeto producto obtendra el id del producto
        p=productoService.get(producto.getId()).get();
        
        if(file.isEmpty()){// cuando editamos la imagen pero no la cambiamos
            //Esa imagen del producto le va a pasar lo que hemos obtenido de esa misma imagen
            //cuando se este editando
            producto.setImagen(p.getImagen());

        }else{ // cuando se edita la imagen también

            //Eliminar cuando la imagen no sea por defecto
        if(!p.getImagen().equals("default.jpg")){//Si la imagen que no esta, es la imagen por defecto
            //Enotnces que me elimine esa imagen
            upload.deleteImage(p.getImagen());

        }
            //Obtiene la imagen nueva a guardar
            String nombreImagen = upload.saveImage(file);
            //le pasa esa imagen al producto
             producto.setImagen(nombreImagen);
        }
        //Le pasamos el usuario de los productos
        producto.setUsuario(p.getUsuario());
        //Le pasamos el metodo update para que nos obtenga el objeto producto y la info 
        productoService.update(producto);
        //Que nos redireccione a la vista productos
        return "redirect:/productos";
    }

    //Metodo que va a mapear la info desde el boton eliminar para que se edite en la BD
    //Pasamos por parametro el id del producto
    //PathVariable: va a mapear la variable id en la url
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        //Se crea el objeto producto
        Producto p = new Producto();
        //Obtendremos el id del producto
        p=productoService.get(id).get();
        //Eliminar cuando la imagen no sea por defecto
        if(!p.getImagen().equals("default.jpg")){//Si la imagen que no esta, es la imagen por defecto
            //Enotnces que me elimine esa imagen
            upload.deleteImage(p.getImagen());

        }
        //Le pasamos el metodo delete para que nos obtenga el objeto producto y elimine
        productoService.delete(id);
        //Que nos redireccione a la vista productos
        return "redirect:/productos";
    }
}
