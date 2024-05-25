    package com.example.prueba.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.example.prueba.repositorio.UsuarioRepository;
import com.example.prueba.service.CuentaService;



@Controller
public class CuentaController {

    public List<String> tipoCuenta = new ArrayList<>(Arrays.asList("AHORROS", "CORRIENTE", "AFC"));
    public List<String> abrirEstado = new ArrayList<>(Arrays.asList("ACTIVA"));
    public List<String> meses = new ArrayList<>(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));


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

@GetMapping("/cuenta/extractoCuenta/{idCuenta}")
    public String extractosGeneral(Model model, @PathVariable("idCuenta") ObjectId idCuenta, @RequestParam(value = "mes", required = false) String mes) {
        if(mes==null){
            
        }
        else{
            model.addAttribute("mesElegido", mes);
        }
        model.addAttribute("cuentas", cuentaRepository.buscarCuentas());
        model.addAttribute("meses", meses);
        model.addAttribute("idCuenta", idCuenta);

        return "extractoCuenta";
    

}



@PostMapping("/cuenta/extractoCuenta/{idCuenta}")
public String extractosInfo(Model model, @PathVariable("idCuenta") ObjectId idCuenta, String mes) {
    model.addAttribute("meses", meses);
    if (idCuenta != null) {
        String id = idCuenta.toString();
        String filePath = "logs/cuentas.log";

        model.addAttribute("res", "HOLAAA");
        try {
            // Buscar saldo del mes anterior
            String saldoInicial = null;
            String saldoFinal = null;
            int indexMesActual = meses.indexOf(mes);
            model.addAttribute("mesElegido", mes);
            for (int i = indexMesActual - 1; i >= 0; i--) {
                String mesAnterior = meses.get(i);
                Collection<String> lineasSaldoInicial = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)
                        .filter(linea -> linea.contains(id) && linea.contains("Fecha: 2024-" + mesAnterior))
                        .collect(Collectors.toList());
                if (!lineasSaldoInicial.isEmpty()) {
                    // Obtener el primer saldo encontrado
                    String linea = lineasSaldoInicial.iterator().next();
                    saldoInicial = linea.split(", ")[linea.split(", ").length - 2].split(": ")[1];
                    break;
                }
            }

            // Si no se encontró saldo del mes anterior, utilizar el primer saldo del mes actual
            if (saldoInicial == null) {
                Collection<String> lineasSaldoInicialMesActual = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)
                        .filter(linea -> linea.contains(id) && linea.contains("Fecha: 2024-" + mes))
                        .collect(Collectors.toList());
                if (!lineasSaldoInicialMesActual.isEmpty()) {
                    String linea = lineasSaldoInicialMesActual.iterator().next();
                    saldoInicial = linea.split(", ")[linea.split(", ").length - 2].split(": ")[1];
                }
            }

            model.addAttribute("saldoInicial", saldoInicial);

            // Buscar movimientos del mes actual
            Collection<String> lineasCoincidentes = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)
                    .filter(linea -> linea.contains(id) && linea.contains("Fecha: 2024-" + mes))
                    .collect(Collectors.toList());

            if (!lineasCoincidentes.isEmpty()) {
                // Encontrar el saldo final
                List<String> lineasCoincidentesList = new ArrayList<>(lineasCoincidentes);
                String ultimaLinea = lineasCoincidentesList.get(lineasCoincidentesList.size() - 1);
                saldoFinal = ultimaLinea.split(", ")[ultimaLinea.split(", ").length - 2].split(": ")[1];
                
                model.addAttribute("lineasCoincidentes", lineasCoincidentes);
                model.addAttribute("saldoFinal", saldoFinal);
            } else {
                model.addAttribute("lineasCoincidentes", "No se encontraron movimientos para la cuenta");
            }
        } catch (IOException e) {
            return "extractoCuenta";
        }
    }
    return "extractoCuenta";
}

@GetMapping("/cuenta/consultar")
public String consultarCuentas(@RequestParam(required = false) String tipoCuenta,
                            @RequestParam(required = false) Double saldoMin,
                            @RequestParam(required = false) Double saldoMax,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCreacion,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaUltimoMovimiento,
                            @RequestParam(required = false) String cliente,Model model) {
    List<Cuenta> cuentas = cuentaService.filtrarCuentas(tipoCuenta, saldoMin, saldoMax, fechaCreacion, fechaUltimoMovimiento, cliente);
    model.addAttribute("cuentas", cuentas);
    return "cuenta";
}

    
}
