package proyectoecommercejava.demo.service;

import java.util.List;
import java.util.Optional;

import proyectoecommercejava.demo.model.Producto;

//Esta interface se van a definir los métodos crud para la clase Producto
public interface ProductoService {
    //Método de objeto producto, que va a guardar la info de los productos 
    public Producto save(Producto producto);
    //Método Opcional nos permite validar si es que el objeto que mandamos a llamar exista o no
    public Optional<Producto> get(Integer id);
    //Método de actualizar la info de los productos
    public void update(Producto producto);
    //Método de eliminar la info de los productos
    public void delete(Integer id);
    //Lista de producto que me encuentre todos los registros
    public List<Producto> findAll();
}
