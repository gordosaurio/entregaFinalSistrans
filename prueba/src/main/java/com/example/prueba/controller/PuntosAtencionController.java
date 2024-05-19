package com.example.prueba.controller;
import java.util.ArrayList;
import java.util.Arrays;

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

import com.example.prueba.repositorio.OficinaRepository;
import com.example.prueba.repositorio.PuntoAtencionRepository;
import com.example.prueba.repositorio.UsuarioRepository;
import com.example.prueba.service.OficinaService;
import com.example.prueba.service.PuntoAtencionService;
import com.example.prueba.service.UsuarioService;

@Controller
public class PuntosAtencionController {
    public List<String> tipoPuntoAtencion = new ArrayList<>(Arrays.asList("DIGITAL", "PERSONALIZADA"));

    @Autowired
    private PuntoAtencionRepository puntoAtencionRepository;

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private PuntoAtencionService puntoAtencionService;

    @GetMapping("/puntosAtencion")
    public String listarUsuario(Model model) {
        model.addAttribute("puntosAtencion", puntoAtencionRepository.darPuntosAtencion());
        return "puntosAtencion";
    }


    @GetMapping("/puntosAtencion/new")
    public String mostrarFormularioCrearUsuario(Model model) {
        model.addAttribute("tiposPuntosAtencion", tipoPuntoAtencion);
        model.addAttribute("oficinas", oficinaRepository.buscarOficinas());
        return "puntosAtencionNuevo";
    }
    

    @PostMapping("/puntosAtencion/new/save")
    public String procesarUsuarioCliente(
            @RequestParam("nombre") String nombre,
            @RequestParam("tipoPuntoAtencion") String tipoPuntoAtencion,
            @RequestParam("locacion") String locacion,
            @RequestParam("horarioApertura") String horarioApertura,
            @RequestParam("horarioCierre") String horarioCierre,
            @RequestParam("idOficina") ObjectId idOficina,
            Model model,
            RedirectAttributes redirectAttributes) {
    
        try {


            

            puntoAtencionService.crearPuntoAtencion( nombre, tipoPuntoAtencion, locacion,  horarioApertura,  horarioCierre, idOficina);

            return "redirect:/puntosAtencion";

        } catch (NumberFormatException e) {

            model.addAttribute("errorCreacionUsuario", "Formato de número inválido en uno de los campos.");
            return "redirect:/puntosAtencion";
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("errorCreacionUsuario", "error");
            return "redirect:/puntosAtencion";
        }
    }
    
    @GetMapping("/puntosAtencion/borrar/{idPuntoAtencion}")
    public String eliminarPuntosAtencion(@PathVariable("idPuntoAtencion") ObjectId idPuntosAtencion,Model model) {
        puntoAtencionRepository.deleteById(idPuntosAtencion);
        return "redirect:/puntosAtencion";
    }

    @GetMapping("/puntoAtencion/editar/{idPuntoAtencion}")
    public String editarPuntoAtencion(@PathVariable("idPuntoAtencion") ObjectId idPuntosAtencion, Model model) {
        model.addAttribute("tiposPuntosAtencion", tipoPuntoAtencion);
        model.addAttribute("oficinas", oficinaRepository.buscarOficinas());
        return "puntosAtencionEditar";
    }

    @PostMapping("/puntoAtencion/editar/{idPuntoAtencion}/save")
    public String guardarEdicionPuntoAtencion(
            @PathVariable("idPuntoAtencion") ObjectId idPuntoAtencion,
            @RequestParam("nombre") String nombre,
            @RequestParam("tipoPuntoAtencion") String tipoPuntoAtencion,
            @RequestParam("locacion") String locacion,
            @RequestParam("horarioApertura") String horarioApertura,
            @RequestParam("horarioCierre") String horarioCierre,
            @RequestParam("idOficina") ObjectId idOficina,
            Model model,
            RedirectAttributes redirectAttributes) {


                try {
                   


                    puntoAtencionService.editarPuntoAtencion( idPuntoAtencion,nombre, tipoPuntoAtencion, locacion,  horarioApertura,  horarioCierre, idOficina);
        
                    return "redirect:/puntosAtencion";
        
                } catch (Exception e) {

                    return "ERROR";
                }
            }
    
}
