package com.example.prueba;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.modelo.Usuario;
import com.example.prueba.repositorio.CuentaRepository;
import com.example.prueba.repositorio.OficinaRepository;
import com.example.prueba.repositorio.PuntoAtencionRepository;
import com.example.prueba.repositorio.UsuarioRepository;
import com.example.prueba.service.CuentaService;
import com.example.prueba.service.OficinaService;
import com.example.prueba.service.PuntoAtencionService;
import com.example.prueba.service.UsuarioService;


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

        

        System.out.println("----------------------");
        System.out.println("holaaaaaa");
        System.out.println("----------------------");
        List<Usuario> usuarios = usuarioRepository.darUsuarios();
        ObjectId idUsuario = new ObjectId();
        String nombre = "no entro";
        Date fecha = new Date();
        Double saldo = 2500000d;
        for(Usuario u:usuarios){
            idUsuario = u.getId();
            nombre = u.getNombre();
        }
        cuentaService.crearCuenta(idUsuario,"AHORROS","ACTIVA",fecha,saldo,fecha);
        


/*
        ObjectId idOficina = new ObjectId();
        ObjectId idEliminar = new ObjectId();

        List<Oficina> oficinas= oficinaRepository.buscarOficinas();
        for (Oficina o:oficinas){
            System.out.println(o.getId());
            idOficina = o.getId();
        }
        puntoAtencionService.crearPuntoAtencion("cajero Tadeo", "DIGITAL", "centro Bogota", "08:40", "18:40", idOficina);
        List<PuntoAtencion> puntosAtencion = puntoAtencionRepository.darPuntosAtencion();
        for (PuntoAtencion punto: puntosAtencion){
            System.out.println(punto.getNombre());
            idEliminar = punto.getId();
        }
        puntoAtencionService.eliminarPuntoAtencion(idEliminar);
        System.out.println("---------------------------------------------------");
        System.out.println("lista eliminados");
        List<PuntoAtencion> puntosAtencionEliminado = puntoAtencionRepository.darPuntosAtencion();
        for (PuntoAtencion punto: puntosAtencionEliminado){
            System.out.println(punto.getNombre());
        }

        /*
        usuarioService.crearUsuario("CC",1010034589,"Pepe Rios","carrera 11#12","ppe@gmail.com",3522596,1212,"CAJERO","colombiano");
        
        ObjectId idUsuario = new ObjectId();
        List<Usuario> usuarios = usuarioRepository.darUsuarios();
		for (Usuario u:usuarios){
            System.out.println(u.getId());
            idUsuario = u.getId();
        }
        System.out.println("se va a insertar");
        //oficinaService.crearOficina("oficinas centrales norte", "carrera 12#43-54", 4, idUsuario);
        System.err.println("se inserto");
        List<Oficina> oficinas = oficinaRepository.buscarOficinas();
        for(Oficina o: oficinas){
            System.err.println(o.getNombre());
        }*/
/*
        
		System.out.println("hola");*/

/*
		List<Usuario> usuarios = usuarioRepository.porTipoUsuario("CLIENTE");

        for (Usuario b : usuarios) {
            System.out.println(b.getNombre());
        }

		//usuarioRepository.save(new Usuario("50", "CC",123444,"pruebita prueba","direccion de pruebita", "@email",320470,123321,"CLIENTE","colombiana"));
		
		List<Usuario> usuarios2 = usuarioRepository.porTipoUsuario("CLIENTE");

        for (Usuario b : usuarios2) {
            System.out.println(b.getNombre());
        }*/
	}
}

