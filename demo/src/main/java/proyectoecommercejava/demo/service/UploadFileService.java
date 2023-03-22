package proyectoecommercejava.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//Le indicamos a springframework que es una clase de servicio
@Service
public class UploadFileService {
    //Declaramos una variable llamada folder en la cual va a cumplir la función de obtener la ubicación
    //de la imagen y su nombre, ya que el nombre de la imagen se va almacenar en la BD
    private String folder = "demo/images//";

    //Método para guardar la imagen 
    //MultipartFile: Seria como tal nuestra imagen para ser guardada
    public String saveImage(MultipartFile file) throws IOException{
        //Si el archivo no esta vacio
        if(!file.isEmpty()){
            //Entonces que el archivo cadena me lo convierta a bytes para la imagen
            byte [] bytes = file.getBytes();
            //Entonces vamos a guardar el nombre original de la ubicación de la imagen
            Path path = Paths.get(folder+file.getOriginalFilename());
            //la escritura de archivo va obtener la ruta de la imagen y la conversion a bytes
            Files.write(path, bytes);
            //Va a retornar el nombre de la imagen original
            return file.getOriginalFilename();

        }

        //De lo contrario de que no se guardo la imagen pues nos va a mandar las imagenes por defecto
        return "default.jpg";
    }

    //Método para eliminar imagen y recibimos el nombre de la imagen
    public void deleteImage(String nombre){
        //Va guardar la ruta donde se encuentra la imagen 
        String ruta = "demo/images//";
        //Se crea un objeto archivo en donde obtendremos la ruta y el nombre de la imagen
        File file = new File(ruta+nombre);
        //Eliminamos la imagen
        file.delete();

    }
}
