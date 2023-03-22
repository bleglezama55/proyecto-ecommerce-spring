package proyectoecommercejava.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyectoecommercejava.demo.model.Producto;
import proyectoecommercejava.demo.repository.IProductoRepository;

//Se implementa service para hacer llamado a los métodos
@Service
public class ProductoServiceImple implements ProductoService{

    //Autowired: Indica que ya estamos inyectando a esta clase un objeto
    @Autowired
    //Vamosa declarar un objeto de tipo Repository para que esa inteface nos permita obtener
    //los métodos crud que ya nos proporciana JPA Repostory
    private IProductoRepository productoRepository;

    @Override
    public Producto save(Producto producto) {
        //De vuelve el método guardar
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(Integer id) {
        //De vuelve el método optional para obtener el id
        return productoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        //Cuando obtenemos el método save en actualizar, es cuando al objeto le mandamos un id null
        //este lo va crear pero cuando el objeto le mandamos con un id que ya existe en la base
        //entonces lo va actualizar
        productoRepository.save(producto);
    }

    @Override
    public void delete(Integer id) {
        //De vuelve el método eliminar y obtene el id
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> findAll() {
       //Devuelve toda la lista de productos
       return productoRepository.findAll();
    }
    
    
}
