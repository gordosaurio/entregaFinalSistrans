package com.example.prueba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.repositorio.UsuarioRepository;
import com.example.prueba.service.UsuarioService;


@SpringBootApplication
@RestController
public class PruebaApplication implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public static void main(String[] args) {
        SpringApplication.run(PruebaApplication.class, args);
    }

    

    @Override
    public void run(String... args) throws Exception {

        System.out.println("----------------------");
        System.out.println("holaaaaaa");
        System.out.println("----------------------");

/*
        usuarioService.crearUsuario("CC",1010034589,"Pepe Rios","carrera 11#12","ppe@gmail.com",3522596,1212,"CAJERO","colombiano");
        
        List<Usuario> usuarios = usuarioRepository.darUsuarios();
		for (Usuario u:usuarios){
            System.out.println(u.getNombre());
        }
		System.out.println("hola");
 */
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

