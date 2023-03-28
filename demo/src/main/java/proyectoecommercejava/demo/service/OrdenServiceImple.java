package proyectoecommercejava.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyectoecommercejava.demo.model.Orden;
import proyectoecommercejava.demo.model.Usuario;
import proyectoecommercejava.demo.repository.IOrdenRepository;

//Inyección del service para que lo reconosca el spring
@Service
public class OrdenServiceImple implements IOrdenService{

    @Autowired
    //Objeto IOrdenRepository para obtener informacion 
    private IOrdenRepository ordenRepository;

    //Inyección del objeto para que reconozca spring 
    @Override
    public Orden save(Orden orden) {
        //Devuelve el método de la interface IOrdenRepository llamada save
        return ordenRepository.save(orden);
    }

    @Override
    public List<Orden> findAll() {
        // Devuelve el método de la interface IOrdenRepository llamada findAll();
        return ordenRepository.findAll();
    }

    //0000010 de DB a número
    //Método para generar numero de la orden
    public String generarNumeroOrden(){
        // Varible entero inicializado
        int numero = 0;
        //Variable String inicializado
        String numeroConcatenado="";

        //Se crea lista de ordenes del mismo método findAll() para que nos retorne todas las ordenes
        List<Orden> ordenes = findAll();

         //Se crea una lista de enteros
        List<Integer> numeros = new ArrayList<Integer>();

        //La listar ordenes va a transmitir un bucle añadiendo la lista de enteros obteniendo una 
        //conversión de una cadena a numero entero 
        ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));

        //Si la lista esta vacia 
        if(ordenes.isEmpty()){
            //Entonces es igual a uno
            numero=1;
        }else{
            //De lo contrario ese número me obtenga el número maximo de la lista
            numero = numeros.stream().max(Integer::compareTo).get();
            //Y que ese numero me valla incrementando de valor
            numero++;
        }

        //Si el número es mayor a 10
        if(numero<10){ //000000001
            //Entonces que me que me concatene //000000010
            numeroConcatenado="000000000"+String.valueOf(numero);
        }else if(numero<100){
            //Si es 100 que me que me concatene //000000100
            numeroConcatenado="00000000"+String.valueOf(numero);
        }else if(numero<1000){
            //Si es 1000 que me que me concatene //000001000
            numeroConcatenado="0000000"+String.valueOf(numero);
        }else if(numero<10000){
            //Si es 10000 que me que me concatene //000010000
            numeroConcatenado="000000"+String.valueOf(numero);
        }

        return numeroConcatenado;
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
        // devuelve el método de a interfaz de la lista de ordenes del usuario
        return ordenRepository.findByUsuario(usuario);
    }

    @Override
    public Optional<Orden> findById(Integer id) {
        // obtenemos el id de la orden a traves metodo de la interfaz findById en la clase
        //ordenRepository
        return ordenRepository.findById(id);
    }

    
    
}
