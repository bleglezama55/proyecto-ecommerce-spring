package proyectoecommercejava.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyectoecommercejava.demo.model.DetalleOrden;
import proyectoecommercejava.demo.repository.IDetalleOrdenRepository;

//Inyección del service para que lo reconosca el spring
@Service
public class DetalleOrdenServiceImple implements IDetalleOrdenService {

    //Objeto IOrdenRepository para obtener informacion
    @Autowired
    //Objeto IDetalleOrdenRepository para obtener información
    private IDetalleOrdenRepository detalleOrdenRepository;

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        // Devuelve el método IDetalleOrdenRepository de la inteface llamada save
        return detalleOrdenRepository.save(detalleOrden);
    }
    
}
