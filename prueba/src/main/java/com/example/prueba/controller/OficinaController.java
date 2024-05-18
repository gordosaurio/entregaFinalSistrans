package com.example.prueba.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.prueba.modelo.Usuario;
import com.example.prueba.repositorio.OficinaRepository;
import com.example.prueba.repositorio.UsuarioRepository;
import com.example.prueba.service.OficinaService;
import com.example.prueba.service.UsuarioService;

@Controller
public class OficinaController {



    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private OficinaService oficinaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/oficina")
    public String listarOficina(Model model) {
        model.addAttribute("oficinas", oficinaRepository.buscarOficinas());
        model.addAttribute("usuarios", usuarioRepository.darUsuarios());
        return "oficina";
    }


    @GetMapping("/oficina/new")
    public String mostrarFormularioCrearUsuario(Model model) {
        model.addAttribute("gerentes", usuarioRepository.porTipoUsuario("GERENTE"));
        model.addAttribute("usuarios", usuarioRepository.darUsuarios());
        return "oficinaNueva";
    }
    

    @PostMapping("/oficina/new/save")
    public String procesarUsuarioCliente(
            @RequestParam("nombre") String nombre,
            @RequestParam("direccion") String direccion,
            @RequestParam("numeroPuntosDisponibles") String numeroPuntosDisponibles,
            @RequestParam("idGerente") ObjectId idGerente,
            Model model,
            RedirectAttributes redirectAttributes) {
    
        try {

            int numeroPuntosDisponiblesInteger = Integer.parseInt(numeroPuntosDisponibles);
            oficinaService.crearOficina(nombre, direccion, numeroPuntosDisponiblesInteger, idGerente);
    

            return "redirect:/oficina";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("errorCreacionOficina", "error");
            return "redirect:/oficina";
        }
    }
    


}
