package com.example.prueba.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.prueba.modelo.Cuenta;
import com.example.prueba.repositorio.CuentaRepository;
import com.example.prueba.repositorio.UsuarioRepository;
import com.example.prueba.service.CuentaService;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


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

    private static final Logger logger = LoggerFactory.getLogger(CuentaController.class);

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

    
    @GetMapping("/cuenta/consignar/{idCuenta}")
    public String consignarCuenta(@PathVariable("idCuenta") ObjectId idCuenta,Model model, RedirectAttributes redirectAttributes) {;
        Cuenta cuenta = cuentaRepository.buscarporId(idCuenta);
        if(cuenta.getEstadoCuenta().equals("ACTIVA")){

            return "cuentaConsignar";
        }
        else if(!cuenta.getEstadoCuenta().equals("ACTIVA")){
            redirectAttributes.addFlashAttribute("mensaje", "No se puede consignar en una cuenta no activa.");

        }
        return "redirect:/cuenta" ;
    }


    @PostMapping("/cuenta/consignar/{idCuenta}/save")
    public String consignarCuentaGuardar(@PathVariable("idCuenta") ObjectId idCuenta,Model model, RedirectAttributes redirectAttributes, @RequestParam("monto") String monto) {;
        
        Double montoFloat = Double.parseDouble(monto);
        cuentaService.consignarCuenta(idCuenta, montoFloat);
        Cuenta cuenta = cuentaRepository.buscarporId(idCuenta);  
        Double saldo = cuenta.getSaldo();
        logger.info("Fecha: {}, Numero de cuenta: {}, Monto: {}, Tipo de operacion: consignacion, Saldo: {}",
            LocalDate.now(), idCuenta, monto, saldo);
        return "redirect:/cuenta";

    }
    

    @GetMapping("/cuenta/retirar/{idCuenta}")
    public String retirarCuenta(@PathVariable("idCuenta") ObjectId idCuenta, Model model, RedirectAttributes redirectAttributes){;
        Cuenta cuenta = cuentaRepository.buscarporId(idCuenta);
        if(cuenta.getEstadoCuenta().equals("ACTIVA")){

            return "cuentaRetirar";
            }
        else if(!cuenta.getEstadoCuenta().equals("ACTIVA")){
            redirectAttributes.addFlashAttribute("mensaje", "No se puede consignar en una cuenta no activa.");
        }
        else{
            redirectAttributes.addFlashAttribute("mensaje", "Ocurrio un error.");
        }
        return "redirect:/cuenta" ;
    }

    @PostMapping("/cuenta/retirar/{idCuenta}/save")
    public String retirarCuentaGuardar(@PathVariable("idCuenta") ObjectId idCuenta, @RequestParam("monto") String monto, RedirectAttributes redirectAttributes){
        Cuenta cuenta = cuentaRepository.buscarporId(idCuenta);
        Double saldo = cuenta.getSaldo();
        Double montoFloat = Double.parseDouble(monto);
        Double montoFinal = cuenta.getSaldo()-montoFloat;
        
        if(montoFinal>=0){

            cuentaService.retirarCuenta(idCuenta, montoFloat);
            redirectAttributes.addFlashAttribute("mensaje", "Retiro exitoso.");
            logger.info("Fecha: {}, Numero de cuenta: {}, Monto: {}, Tipo de operacion: retiro, Saldo: {}",
            LocalDate.now(), idCuenta, monto, saldo);
             
        }
        else{
            redirectAttributes.addFlashAttribute("mensaje", "No tiene suficiente saldo.");
        }

        return "redirect:/cuenta";
        }

        @GetMapping("/cuenta/transferir/{idCuenta}")
    public String cuentaTransferir(@PathVariable("idCuenta") ObjectId idCuenta, Model model, RedirectAttributes redirectAttributes) {
        Cuenta cuenta = cuentaRepository.buscarporId(idCuenta);
        Collection<Cuenta> cuentasTransferir = cuentaRepository.buscarPorEstado("ACTIVA");
        if (cuenta != null && cuenta.getEstadoCuenta().equals("ACTIVA")) {
            model.addAttribute("cuentasTransferir", cuentasTransferir);
            return "cuentaTransferir";
        } 
        
        else {
            redirectAttributes.addFlashAttribute("mensaje", "La cuenta debe estar activa para realizar una transferencia.");
            return "redirect:/cuenta";
    }
    }

@PostMapping("/cuenta/transferir/{idCuenta}/save")
    public String transferirCuentaGuardar(@PathVariable("idCuenta") ObjectId idCuenta, @RequestParam("cuentaDestino") ObjectId idCuentaDestino,@RequestParam("monto") String monto,RedirectAttributes redirectAttributes) {
        Cuenta cuentaOrigen = cuentaRepository.buscarporId(idCuenta);
        Cuenta cuentaDestino = cuentaRepository.buscarporId(idCuentaDestino);
        double montoDouble = Double.parseDouble(monto);
        
        if(cuentaOrigen.getSaldo() >= montoDouble){
                double saldoNuevoOrigen = cuentaOrigen.getSaldo() - montoDouble;
                double saldoNuevoDestino = cuentaDestino.getSaldo() + montoDouble;
                logger.info("Fecha: {}, Numero de cuenta origen: {},Numero de cuenta destino: {}, Monto: {}, Tipo de operacion: transferencia, Saldo cuenta origen: {}, Saldo cuenta destino: {}",
                LocalDate.now(), cuentaOrigen.getId(), cuentaDestino.getId(), montoDouble, saldoNuevoOrigen, saldoNuevoDestino);
                cuentaService.transaferirCuenta(idCuenta, idCuentaDestino, montoDouble);

            }
        else{
                redirectAttributes.addFlashAttribute("mensaje", "Los fondos no son suficientes.");
                
            }

        return "redirect:/cuenta";

}

}
