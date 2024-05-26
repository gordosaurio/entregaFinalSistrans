package com.example.prueba;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.repositorio.*;
import com.example.prueba.service.*;
import com.example.prueba.modelo.*;
import com.example.prueba.controller.*;



@SpringBootApplication
@RestController
public class PruebaApplication implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PuntoAtencionRepository puntoAtencionRepository;

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private OficinaService oficinaService;

    @Autowired
    private PuntoAtencionService puntoAtencionService;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private CuentaService cuentaService;

    public static void main(String[] args) {
        SpringApplication.run(PruebaApplication.class, args);
    }

    

    @Override
    public void run(String... args) throws Exception {

 
       /*  System.out.println("CRUD USUARIO");

        System.out.println("\nRead");
        
        usuarios.forEach(usuario -> System.out.println(usuario.getNombre()));
        
        System.out.println("\nCreate");
        Usuario nuevo = new Usuario("CC", 123444, "pruebita 1", "direccion de pruebita", "@email", 320470, 123321, "CLIENTE", "colombiana");
        usuarioRepository.save(nuevo);
        System.out.println("\nSe creó el usuario: ");
        System.out.println(nuevo);
        
        ObjectId idUsuario = new ObjectId();
        
        List<Usuario> usuarios2 = usuarioRepository.darUsuarios();
        
        for (Usuario b : usuarios2) {
            if (b.getNombre().equals("pruebita 1")) {
                System.out.println("\nSe encontró el nuevo usuario");
                System.out.printf("Nombre: %s\nNúmero de Documento: %d\n", b.getNombre(), b.getNumeroDocumento());
                idUsuario = b.getId();
                break; // Salir del bucle una vez que se encuentra el usuario
            }
        }
        
        System.out.println("\nUpdate");
        usuarioRepository.updateNumeroDocumento(idUsuario, 12313210);
        
        Usuario modificado = usuarioRepository.buscarporId(idUsuario);
        System.out.println("\nUsuario modificado: ");
        System.out.println(modificado);
        
        System.out.println("\nDelete");
        usuarioRepository.deleteById(idUsuario);
        
        List<Usuario> usuarios3 = usuarioRepository.darUsuarios();
        
        boolean usuarioEncontrado = false;
        for (Usuario b : usuarios3) {
            if (b.getNombre().equals("pruebita 1")) {
                usuarioEncontrado = true;
                System.out.printf("\nSe encontró el nuevo usuario: %s\nNúmero de Documento: %d\n", b.getNombre(), b.getNumeroDocumento());
                break;
            }
        }
        
        if (!usuarioEncontrado) {
            System.out.println("\nNo se encontró el nuevo usuario");
        }
        
 */


 /*        System.out.println("CRUD PUNTO DE ATENCION");

        System.out.println("\nRead");
        List<PuntoAtencion> puntos = puntoAtencionRepository.darPuntosAtencion();

        int count = 0;
        for (PuntoAtencion b : puntos) {
            if (count >= 10) {
                break; 
            }
            System.out.println(b.getNombre());
            count++;
        }

        System.out.println("\nCreate");
        ObjectId idOficina = new ObjectId("66492c81e0c54e311bf65333");
        
        PuntoAtencion nuevo = new PuntoAtencion("pruebita 1","PERSONALIZADA","locacion","8","9", idOficina);
        puntoAtencionRepository.save(nuevo);
        System.out.println("\nSe creó el punto de atención ");

        ObjectId idPunto = new ObjectId();

        List<PuntoAtencion> puntos2 = puntoAtencionRepository.darPuntosAtencion();

        for (PuntoAtencion b : puntos2) {
            if (b.getNombre().equals("pruebita 1")) {
                System.out.println("\nSe encontró el nuevo puntoAtencion");
                System.out.printf("Información: %s\n", b.toString());
                idPunto = b.getId();
                break; 
            }
        }

        System.out.println("\nUpdate");
        puntoAtencionRepository.editarPuntoAtencion(idPunto, "pruebita mod","mod","mod","8","9", idOficina);

        System.out.println("\nPunto de Atención modificado: ");
        System.out.println(puntoAtencionRepository.buscarporId(idPunto));

        System.out.println("\nDelete");
        puntoAtencionRepository.deleteById(idPunto);


        List<PuntoAtencion> puntos3 = puntoAtencionRepository.darPuntosAtencion();
        boolean usuarioEncontrado = false;
        for (PuntoAtencion b : puntos3) {
            if (b.getNombre().equals("pruebita 1")) {
                usuarioEncontrado = true;
                System.out.printf("\nSe encontró");
                break;
            }
        }

        if (!usuarioEncontrado) {
            System.out.println("\nNo se encontró el nuevo punto de Atención");
        }
 */


/* 
       System.out.println("CRUD OFICINA");

        System.out.println("\nRead");
        List<Oficina> oficinas = oficinaRepository.buscarOficinas();

        int count = 0;
        for (Oficina b : oficinas) {
            if (count >= 10) {
                break; 
            }
            System.out.println(b.getNombre());
            count++;
        }

        System.out.println("\nCreate");
        ObjectId idGerente = new ObjectId("66492bcce0c54e311bf65327");
        

        Oficina nuevo = new Oficina("pruebita 1", "direccion",1,  idGerente);
        oficinaRepository.save(nuevo);
        System.out.printf("\nSe creó la nueva oficina: %s",nuevo.toString() );

        ObjectId idOficina = new ObjectId();

        List<Oficina> oficinas2 = oficinaRepository.buscarOficinas();

        for (Oficina b : oficinas2) {
            if (b.getNombre().equals("pruebita 1")) {
                System.out.println("\nSe encontró el nuevo puntoAtencion");
                System.out.printf("Información: %s\n", b.toString());
                idOficina = b.getId();
                break; 
            }
        }

        System.out.println("\nUpdate");
        oficinaRepository.updateOficina(idOficina, "pruebita 1", "direccion",100,  idGerente);

        System.out.println("\noficina modificada: ");
        System.out.println(oficinaRepository.buscarporId(idOficina).toString());

        System.out.println("\nDelete");
        oficinaRepository.deleteById(idOficina);


        List<Oficina> oficinas3 = oficinaRepository.buscarOficinas();
        boolean usuarioEncontrado = false;
        for (Oficina b : oficinas3) {
            if (b.getNombre().equals("pruebita 1")) {
                usuarioEncontrado = true;
                System.out.printf("\nSe encontró");
                break;
            }
        }

        if (!usuarioEncontrado) {
            System.out.println("\nNo se encontró la nueva Oficina.");
        } */

        System.out.println("CRUD CUENTA");

        System.out.println("\nRead");
        List<Cuenta> cuentas = cuentaRepository.buscarCuentas();

        int count = 0;
        for (Cuenta b : cuentas) {
            if (count >= 10) {
                break; 
            }
            System.out.println(b.getId());
            System.out.println(b.getIdUsuario());
            count++;
        }

        System.out.println("\nCreate");
        ObjectId idGerente = new ObjectId("66492bcce0c54e311bf65327");
        Date fecha = java.util.Date.from(java.time.LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant());

        Cuenta nuevo = new Cuenta(idGerente,"AHORROS","ACTIVA", fecha,  (double) 12132, fecha );
        cuentaRepository.save(nuevo);
        System.out.printf("\nSe creó la nueva cuenta: %s",nuevo.toString() );

        ObjectId idCuentaMod = new ObjectId();

        List<Cuenta> cuentas2 = cuentaRepository.buscarCuentas();

        for (Cuenta b : cuentas2) {
            if (b.getId().equals(nuevo.getId())) {
                System.out.println("\nSe encontró el nuevo puntoAtencion");
                System.out.printf("Información: %s\n", b.toString());
                idCuentaMod = b.getId();
                break; 
            }
        }

        System.out.println("\nUpdate");
        cuentaRepository.updateCuenta(idCuentaMod, idGerente,"AHORROS","CERRADA", fecha,  (double) 0, fecha );

        System.out.println("\nCuenta modificada: ");
        System.out.println(cuentaRepository.buscarporId(idCuentaMod).toString());

        System.out.println("\nDelete");
        cuentaRepository.deleteById(idCuentaMod);


        List<Cuenta> cuentas3 = cuentaRepository.buscarCuentas();
        boolean usuarioEncontrado = false;
        for (Cuenta b : cuentas3) {
            if (b.getId().equals(nuevo.getId())) {
                usuarioEncontrado = true;
                System.out.printf("\nSe encontró");
                break;
            }
        }

        if (!usuarioEncontrado) {
            System.out.println("\nNo se encontró la nueva cuenta.");}


	}
}

