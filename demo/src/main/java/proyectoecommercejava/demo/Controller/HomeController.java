package proyectoecommercejava.demo.Controller;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import proyectoecommercejava.demo.model.DetalleOrden;
import proyectoecommercejava.demo.model.Orden;
import proyectoecommercejava.demo.model.Producto;
import proyectoecommercejava.demo.model.Usuario;
import proyectoecommercejava.demo.service.IDetalleOrdenService;
import proyectoecommercejava.demo.service.IOrdenService;
import proyectoecommercejava.demo.service.IUsuarioService;
import proyectoecommercejava.demo.service.ProductoService;



//Inyección de la clase controlador
@Controller
//Estamos mapeando la ruta raíz del navegador principal del usuario
@RequestMapping("/")
public class HomeController {

    //Variable de tipo Logger para imprimir pantalla
    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    //Inyección que indica el contenedor framework una instancia de la clase
    @Autowired
    //Objeto ProductoService para extraer info del producto
    private ProductoService productoService;

    //Inyección que indica el contenedor framework una instancia de la clase
    @Autowired
    //Objeto IUsuarioService para extraer info del usuario
    private IUsuarioService usuarioService;

    //Inyección que indica el contenedor framework una instancia de la clase
    @Autowired
    //Objeto IOrdenService para extraer info de la orden
    private IOrdenService ordenService;

    //Inyección que indica el contenedor framework una instancia de la clase
    @Autowired
    //Objeto IDetalleOrdenService para extraer info del detalle de orden
    private IDetalleOrdenService detalleOrdenService;

    //Para alamacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    //Datos de la orden
    Orden orden = new Orden();

    //Redirección para el localhost 8085 hacia a la home
    @GetMapping("")
    //Parametro model para que nos lleve la info de los productos hacia la vista
    //HttpSession: Sesion de logeo al usuario para tener autenticaciòn del acceso a la pagina
    public String Home(Model model, HttpSession session){
        //Imprimimos por consola el atributo de la sessión que fue llamada idusuario en la cadena
        log.info("Sesión del usuario: {}", session.getAttribute("idusuario"));
        //el objeto model obtendra el nombre del atributo y el objeto de los productos
        model.addAttribute("productos", productoService.findAll());

        //Obtenemos el id de usuario de la sessión de la home
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        
        return "usuario/home";
    }

     //Redirección para el localhost 8085 hacia a la vista productohome y le pasa un id
    @GetMapping("productohome/{id}")
    //Le enviamos el parametro del id que a punta a esa id a la ruta establecida del GetMapping
    //PathVariable: Indica el parametro de la ruta especifica
    //Parametro model para que nos lleve la info de los productos hacia la vista
    public String productoHome(@PathVariable Integer id, Model model){
        //Enviamos el mensaje por pantalla
        log.info("Id producto enviado con parametro {}",id);
        //Objeto producto
        Producto producto = new Producto();
        //Obtenemos el id 
        Optional<Producto> productoOptional = productoService.get(id);
        //extraemos el id desde el objeto producto
        producto = productoOptional.get();
        //el objeto model obtendra el nombre del atributo y el objeto de los productos
        model.addAttribute("producto",producto);
        //Nos devuelve a la vista productohome
        return "usuario/productohome";

    }

    //Redirección para el localhost 8085 hacia a la vista carrito con el nombre (cart)
    @PostMapping("/cart")
    //Método vista añadir carrito
    //Se agregaran por parametros la cantidad y el id del producto
    //RequestParam: Es el nombre del parametro que se va especificar en spring
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        //Objeto detalle orden
        DetalleOrden detalleOrden = new DetalleOrden();
        //Objeto producto
        Producto producto = new Producto();
        //Variable sumatotal inicializado
        double sumaTotal = 0;

        //Nos obtendra el id del producto
        Optional<Producto> optionalProducto = productoService.get(id);
        //Imprimimos por consola la información buscada del producto
        log.info("Producto añadido: {}", optionalProducto.get());
        //Imprimimos por consola la cantidad buscada del producto
        log.info("Cantidad: {}", cantidad);
        // el objeto producto nos obtendra la info del producto de lo que tiene en la base de datos
        producto = optionalProducto.get();

        //En el objeto detalle orden le pasa la info sus los campos obtenidos
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        //Precio por la cantidad de productos
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        //Validar que el producto no se añada 2 veces
        Integer idProducto = producto.getId();
        //Obtenemos la transmisión ddel carrito detalles y le pasamos por predicado del id del producto
        //que sea igual al idproducto para que no se añada 2 veces
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
        //Si el producto ingresado no es igual al producto ingresado
        if(!ingresado){
        //Entonces añadimos a la lista de detalles del carrito
        detalles.add(detalleOrden);

        }

        //Lo que hace basicamente es sumarnos todos los totales de los productos de la lista detalles
        //stream: transmite el mapeo de tipo doble generada a la lista detalles obteniendo 
        //la suma total
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
        //El objeto orden va extraer en el campo total, toda la sumatoria total del carrito de compras
        orden.setTotal(sumaTotal);
        //Generamos un atributo del modelo el nombre del atributo y el objeto de la lista detalles
        //ya que los datalles se encuentran los productos que va añadiendo el usuario  
        model.addAttribute("cart", detalles);
        //Le pasamos el objeto de la orden por el cual solo se mostrara el campo total
        model.addAttribute("orden", orden);

        //Nos redirecciona a la pagina carrito
        return "usuario/carrito";
    }

    //Redirección para el localhost 8085 hacia a la vista carrito con el nombre (delete /cart) y parametro id 
    @GetMapping("/delete/cart/{id}")
    //Quitar un producto del carrito
    //PathVariable:Variable de la ruta
    //Parametro model para que nos lleve la info de los productos hacia la vista
    public String deleteProductoCart(@PathVariable Integer id, Model model){
        //Lista nueva de productos
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        //Va recorrer la lista de detalles llamada detalleOrden
        for(DetalleOrden detalleOrden: detalles){
            //Si el id que esta recorriendo la lista de detalles es diferente al id del argumento del método 
            if(detalleOrden.getProducto().getId()!=id){
                //Que la orden nueva me añada el detalle de orden
                //Osea si encuentra el id que esta en detalles, entonces no la va añadir a la orden nueva
                ordenesNueva.add(detalleOrden);
                
            }

        }

        //Lo que tienen detalles le pasar lo que tiene la orden nueva
        //Porner la nueva lista con los productos restantes
        detalles = ordenesNueva;

        //Variable sumatoria
        double sumaTotal = 0;

        //Lo que hace basicamente es sumarnos todos los totales de los productos de la lista detalles
        //stream: transmite el mapeo de tipo doble generada a la lista detalles obteniendo 
        //la suma total
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
        //El objeto orden va extraer en el campo total, toda la sumatoria total del carrito de compras
        orden.setTotal(sumaTotal);
        //Generamos un atributo del modelo el nombre del atributo y el objeto de la lista detalles
        //ya que los datalles se encuentran los productos que va añadiendo el usuario  
        model.addAttribute("cart", detalles);
        //Le pasamos el objeto de la orden por el cual solo se mostrara el campo total
        model.addAttribute("orden", orden);
        //Redirección a la pagina carrito
        return "usuario/carrito";
    }

    //Nombre de la url getCart dellocalhost 
    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session){
        //Generamos un atributo del modelo el nombre del atributo y el objeto de la lista detalles
        //ya que los datalles se encuentran los productos que va añadiendo el usuario 
        model.addAttribute("cart", detalles);
        //Le pasamos el objeto de la orden por el cual solo se mostrara el campo total
        model.addAttribute("orden", orden);

        //Obtenemos el id de la sesión
        model.addAttribute("sesion", session.getAttribute("idusuario"));

        //Redirección a la pagina carrito
        return "/usuario/carrito";
    }

     //Nombre de la url orden dellocalhost
    @GetMapping("/order")
    //método para ver el resumen de la orden de la compra
    //HttpSession: Sesion de logeo al usuario para tener autenticaciòn del acceso a la pagina 
    public String order(Model model, HttpSession session){
        //Le pasamos el id de la sessión desde usuarioService al objeto usuario
        Usuario usuario = usuarioService.findById( Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        //Generamos un atributo del modelo el nombre del atributo y el objeto de la lista detalles
        //ya que los datalles se encuentran los productos que va añadiendo el usuario 
        model.addAttribute("cart", detalles);
        //Le pasamos el objeto de la orden por el cual solo se mostrara el campo total
        model.addAttribute("orden", orden);
        //Le pasamos el objeto del usuario por el cual solo se mostrara el id usuario
        model.addAttribute("usuario", usuario);
        //Redirección a la pagina resumen orden
        return "usuario/resumenorden";
    }

    //Nombre de la url orden dellocalhost
    @GetMapping("/saveOrder")
    //método para guardar la orden de la compra
    //HttpSession: Sesion de logeo al usuario para tener autenticaciòn del acceso a la pagina 
    public String saveOrder(HttpSession session){
        //Objeto de tipo fecha
        Date fechaCreacion = new Date();
        //le pasa la fecha de cracion recibida
        orden.setFechaCreacion(fechaCreacion);
        //le pasa el numero de orden generada por orden service
        orden.setNumero(ordenService.generarNumeroOrden());

        //Nota: Usuario que va hacer referencia a esa orden
        //Le pasamos el id de la sessión desde usuarioService al objeto usuario
        Usuario usuario = usuarioService.findById( Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        //Le pasa el usuario que se obtuvo 
        orden.setUsuario(usuario);
        //Le pasa la orden al metodo de la interface de guardar orden
        ordenService.save(orden);

        //Nota: Guardar Detalles
        //Crea un bucle de detalle de orden
        for(DetalleOrden dt:detalles){
            //le pasa la orden de los detalles
            dt.setOrden(orden);
            //Le pasa el datalle de la orden al metodo de la interface de guardar detalles
            detalleOrdenService.save(dt);
        }
        
        //Limpiar lista y Orden
        orden = new Orden();
        detalles.clear();

        //Redirecciona a la raiz de la pagina principal
        return "redirect:/";
    }

    //Nombre de la url orden dellocalhost
    @PostMapping("/search")
    //Método para buscar producto y pasa como argumento un parametro solicitado de tipo cadena
    //y el modelo
    public String searchProduct(@RequestParam String nombre, Model model){
        //Crea un mensaje por consola y le pasa el parametro nombre 
        log.info("Nombre del producto: {}", nombre);
        //Se crea una lista de productos donde encuentre toda la info y sera transmitido el filtro  
        //del nombre del producto que recolecte esa lista de producto
        List<Producto> productos = productoService.findAll().stream().filter(p->p.getNombre().contains(nombre)).collect(Collectors.toList());
        //Le pasa el atributo del modelo con la variable de la cadena llamada productos y el nombre
        //del atributo llamada productos
        model.addAttribute("productos", productos);

        //Redireccione al home del usuario
        return "usuario/home";
    }
    
}
