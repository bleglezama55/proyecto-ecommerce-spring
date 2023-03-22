package proyectoecommercejava.demo.service;

import org.springframework.stereotype.Service;

import proyectoecommercejava.demo.model.DetalleOrden;

//Inyección del service para que lo reconosca el spring
@Service
public interface IDetalleOrdenService {

    DetalleOrden save (DetalleOrden detalleOrden);
    
}
