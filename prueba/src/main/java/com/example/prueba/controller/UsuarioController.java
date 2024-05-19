package com.example.prueba.controller;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.prueba.repositorio.UsuarioRepository;
import com.example.prueba.service.UsuarioService;

@Controller
public class UsuarioController {

    public List<String> tipoUsuario = new ArrayList<>(Arrays.asList("CLIENTE", "GERENTE"));
    public List<String> tipoDocumento = new ArrayList<>(Arrays.asList("CC", "CE"));

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario")
    public String listarUsuario(Model model) {
        model.addAttribute("usuario", usuarioRepository.darUsuarios());
        return "usuario";
    }


    @GetMapping("/usuario/new")
    public String mostrarFormularioCrearUsuario(Model model) {
        model.addAttribute("tipoUsuario", tipoUsuario);
        model.addAttribute("tipoDocumento", tipoDocumento);
        return "UsuarioNuevo";
    }
    

    @PostMapping("/usuario/new/save")
    public String procesarUsuarioCliente(
            @RequestParam("tipoDocumento") String tipoDocumento,
            @RequestParam("numeroDocumento") String numeroDocumento,
            @RequestParam("nombre") String nombre,
            @RequestParam("direccionFisica") String direccionFisica,
            @RequestParam("direccionDigital") String direccionDigital,
            @RequestParam("telefono") String telefono,
            @RequestParam("codigoPostal") String codigoPostal,
            @RequestParam("tipoUsuario") String tipoUsuario,
            @RequestParam("nacionalidad") String nacionalidad,
            Model model,
            RedirectAttributes redirectAttributes) {
    
        try {

            int numeroDocumentoInteger = Integer.parseInt(numeroDocumento);
            int telefonoInteger = Integer.parseInt(telefono);
            int codigoPostalInteger = Integer.parseInt(codigoPostal);
            

            usuarioService.crearUsuario(tipoDocumento, numeroDocumentoInteger, nombre, direccionFisica, direccionDigital, telefonoInteger, codigoPostalInteger, tipoUsuario, nacionalidad);
    

            return "redirect:/usuario";

        } catch (NumberFormatException e) {

            model.addAttribute("errorCreacionUsuario", "Formato de número inválido en uno de los campos.");
            return "redirect:/usuario";
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("errorCreacionUsuario", "error");
            return "redirect:/usuario";
        }
    }
    


}
