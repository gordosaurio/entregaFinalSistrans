package com.example.prueba;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.modelo.Oficina;
import com.example.prueba.modelo.Usuario;
import com.example.prueba.repositorio.OficinaRepository;
import com.example.prueba.repositorio.UsuarioRepository;
import com.example.prueba.service.OficinaService;


@SpringBootApplication
@RestController
public class PruebaApplication implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private OficinaService oficinaService;

    public static void main(String[] args) {
        SpringApplication.run(PruebaApplication.class, args);
    }

    

    @Override
    public void run(String... args) throws Exception {

        System.out.println("----------------------");
        System.out.println("holaaaaaa");
        System.out.println("----------------------");


        /*usuarioService.crearUsuario("CC",1010034589,"Pepe Rios","carrera 11#12","ppe@gmail.com",3522596,1212,"CAJERO","colombiano");
        */
        ObjectId idUsuario = new ObjectId();
        List<Usuario> usuarios = usuarioRepository.darUsuarios();
		for (Usuario u:usuarios){
            System.out.println(u.getId());
            idUsuario = u.getId();
        }
        System.out.println("se va a insertar");
        oficinaService.crearOficina("oficinas centrales norte", "carrera 12#43-54", 4, idUsuario);
        System.err.println("se inserto");
        List<Oficina> oficinas = oficinaRepository.buscarOficinas();
        for(Oficina o: oficinas){
            System.err.println(o.getNombre());
        }
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

