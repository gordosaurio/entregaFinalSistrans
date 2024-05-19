package com.example.prueba.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.prueba.modelo.Cuenta;
import com.example.prueba.repositorio.CuentaRepository;
import com.example.prueba.repositorio.OficinaRepository;
import com.example.prueba.repositorio.UsuarioRepository;
import com.example.prueba.service.CuentaService;
import com.example.prueba.service.OficinaService;


@Controller
public class CuentaController {

     public List<String> tipoCuenta = new ArrayList<>(Arrays.asList("AHORROS", "CORRIENTE", "AFC"));
     public List<String> abrirEstado = new ArrayList<>(Arrays.asList("ACTIVA"));

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/cuenta")
    public String listarCuenta(Model model) {
        model.addAttribute("cuentas", cuentaRepository.buscarCuentas());
        model.addAttribute("usuarios", usuarioRepository.darUsuarios());
        return "cuenta";
    }


    @GetMapping("/cuenta/new")
    public String mostrarFormularioCrearUsuario(Model model) {
        model.addAttribute("usuarios", usuarioRepository.porTipoUsuario("CLIENTE"));
        model.addAttribute("gerente", usuarioRepository.porTipoUsuario("GERENTE"));
        model.addAttribute("usuarios", usuarioRepository.darUsuarios());
        model.addAttribute("tipoCuenta", tipoCuenta);
        model.addAttribute("estadoCuenta", abrirEstado);
        return "cuentaNueva";
    }
   

    @PostMapping("/cuenta/new/save")
    public String procesarUsuarioCliente(
            @RequestParam("idUsuario") ObjectId idUsuario,
            @RequestParam("tipoCuenta") String tipoCuenta,
            @RequestParam("estadoCuenta") String estadoCuenta,
            @RequestParam("fechaUltimaTransaccion") @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaUltimaTransaccion,
            @RequestParam("saldo") String saldo,
            @RequestParam("fechaCreacion") @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaCreacion,
            Model model,
            RedirectAttributes redirectAttributes) {
    
        try {
            double saldoDouble = Double.parseDouble(saldo);


            cuentaService.crearCuenta(idUsuario,tipoCuenta,estadoCuenta,fechaUltimaTransaccion,saldoDouble,fechaCreacion);

            return "redirect:/cuenta";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("errorCreacionOficina", "error");
            return "redirect:/index";
        }
    }

    @GetMapping("/cuenta/cerrar/{idCuenta}")
    public String estadoACerrado(@PathVariable("idCuenta") ObjectId idCuenta,Model model, RedirectAttributes redirectAttributes) {
        Cuenta cuenta = cuentaRepository.buscarporId(idCuenta);
        if(cuenta.getSaldo().equals(0) && cuenta.getEstadoCuenta().equals("ACTIVA")){
            cuentaService.cerrarCuenta(idCuenta);
            return "redirect:/cuenta";
        }

        else if(cuenta.getSaldo()>0){
            redirectAttributes.addFlashAttribute("mensaje", "No se puede cerrar la cuenta el saldo debe ser cero.");

        }

        else if(!cuenta.getEstadoCuenta().equals("ACTIVA")){
            redirectAttributes.addFlashAttribute("mensaje", "No se puede cerrar la cuenta el estado no es activo.");

        }

        else{
            redirectAttributes.addFlashAttribute("mensaje", "No se puede cerrar la cuenta.");

        }
        return "redirect:/cuenta" ;
    }

    @GetMapping("/cuenta/desactivar/{idCuenta}")
    public String estadoDesactivado(@PathVariable("idCuenta") ObjectId idCuenta,Model model, RedirectAttributes redirectAttributes) {


        Cuenta cuenta = cuentaRepository.buscarporId(idCuenta);
        if(cuenta.getEstadoCuenta().equals("ACTIVA")){
            cuentaService.desactivarCuenta(idCuenta);
            return "redirect:/cuenta";
        }


        else if(!cuenta.getEstadoCuenta().equals("ACTIVA")){
            redirectAttributes.addFlashAttribute("mensaje", "No se puede desactivar la cuenta el estado no es activo.");

        }
        return "redirect:/cuenta" ;
    }

    @GetMapping("/cuenta/consignar/{idCuenta}/{monto}")
    public String consignarCuenta(@PathVariable("idCuenta") ObjectId idCuenta,Model model, RedirectAttributes redirectAttributes, @PathVariable("monto") Integer monto) {;
        Cuenta cuenta = cuentaRepository.buscarporId(idCuenta);
        if(cuenta.getEstadoCuenta().equals("ACTIVA")){
            Double montoFinal= monto.doubleValue();
            cuentaService.consignarCuenta(idCuenta, montoFinal);
            return "redirect:/cuenta";
        }


        else if(!cuenta.getEstadoCuenta().equals("ACTIVA")){
            redirectAttributes.addFlashAttribute("mensaje", "No se puede consignar en una cuenta no activa.");

        }
        else{
            redirectAttributes.addFlashAttribute("mensaje", "Revise el monto.");
        }
        return "redirect:/cuenta" ;
    }

}
