package com.example.prueba.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.prueba.modelo.Cuenta;
import com.example.prueba.repositorio.CuentaRepository;
import com.example.prueba.repositorio.UsuarioRepository;

@Service
public class CuentaService {

    private static final Logger logger = LoggerFactory.getLogger(CuentaService.class);

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public void crearCuenta(ObjectId idUsuario,String tipoCuenta,String estadoCuenta,Date fechaUltimaTransaccion,Double saldo,Date fechaCreacion){
        Cuenta cuenta = new Cuenta();
        cuenta.setIdUsuario(idUsuario);
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setEstadoCuenta(estadoCuenta);
        cuenta.setFechaUltimaTransaccion(fechaUltimaTransaccion);
        cuenta.setSaldo(saldo);
        cuenta.setFechaCreacion(fechaCreacion);
        cuentaRepository.save(cuenta);
    }

    public Cuenta cerrarCuenta(ObjectId id) {
        Cuenta cuenta = cuentaRepository.buscarporId(id);
        if (cuenta != null) {
            cuenta.setEstadoCuenta("CERRADA");
            cuentaRepository.save(cuenta);
        }
        return cuenta;
    }

    public Cuenta desactivarCuenta(ObjectId id) {
        Cuenta cuenta = cuentaRepository.buscarporId(id);
        if (cuenta != null )  {
            cuenta.setEstadoCuenta("DESACTIVADA");
            cuentaRepository.save(cuenta);
        }
        return cuenta;
    }

    public Cuenta consignarCuenta(ObjectId id,Double monto) {
        Cuenta cuenta = cuentaRepository.buscarporId(id);
        Double saldo= 0d;
        Double montoFinal = 0d;
        if (cuenta != null) {
            saldo = cuenta.getSaldo();
            montoFinal = saldo + monto;
            cuenta.setSaldo(montoFinal);
            logger.info("Fecha: {}, Número de cuenta: {}, Monto: {}, Tipo de operación: consignacion",
                LocalDate.now(), cuenta.getId(), monto);
            cuentaRepository.save(cuenta);
        }
        return cuenta;
    }

    public Cuenta retirarCuenta(ObjectId id,Double monto) {
        Cuenta cuenta = cuentaRepository.buscarporId(id);
        Double saldo= 0d;
        Double montoFinal = 0d;
        if (cuenta != null) {
            saldo = cuenta.getSaldo();
            montoFinal = saldo - monto;
            cuenta.setSaldo(montoFinal);
            logger.info("Fecha: {}, Número de cuenta: {}, Monto: {}, Tipo de operación: consignacion",
                LocalDate.now(), cuenta.getId(), monto);
            cuentaRepository.save(cuenta);
        }
        return cuenta;
    }

    public Cuenta transaferirCuenta(ObjectId id,ObjectId cuentaDestino, Double monto) {
        Cuenta cuenta = cuentaRepository.buscarporId(id);
        Cuenta cuentaD = cuentaRepository.buscarporId(cuentaDestino);
        Double saldoO= 0d;
        Double saldoD= 0d;
        Double montoFinalO = 0d;
        Double montoFinalD = 0d;
        if (cuenta != null && cuentaD !=null) {
            saldoO = cuenta.getSaldo();
            saldoD = cuentaD.getSaldo();
            montoFinalO = saldoO - monto;
            montoFinalD = saldoD + monto;
            cuenta.setSaldo(montoFinalO);
            cuentaD.setSaldo(montoFinalD);
            cuentaRepository.save(cuenta);
            cuentaRepository.save(cuentaD);
        }
        return cuenta;
    }

    public List<Cuenta> filtrarCuentas(String tipoCuenta, Double saldoMin, Double saldoMax, LocalDate fechaCreacion, LocalDate fechaUltimoMovimiento, String cliente) {
        List<Cuenta> cuentas = cuentaRepository.buscarCuentas();

        if (tipoCuenta != null && !tipoCuenta.isEmpty()) {
            List<Cuenta> cuentaFiltrada = new ArrayList<>();
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getTipoCuenta().equals(tipoCuenta)){
                    cuentaFiltrada.add(cuenta);
                }
            }
            cuentas = cuentaFiltrada;
        }
        if (saldoMin != null) {
            List<Cuenta> cuentaFiltrada = new ArrayList<>();
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getSaldo() >= saldoMin){
                    cuentaFiltrada.add(cuenta);
                }
            }
            cuentas = cuentaFiltrada;
        }
        if (saldoMax != null) {
            List<Cuenta> cuentaFiltrada = new ArrayList<>();
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getSaldo() <= saldoMax){
                    cuentaFiltrada.add(cuenta);
                }
            }
            cuentas = cuentaFiltrada;
        }
        if (fechaCreacion != null) {
            List<Cuenta> cuentaFiltrada = new ArrayList<>();
            for (Cuenta cuenta : cuentas) {
                LocalDate fechaCreacionCuenta = convertToLocalDateViaInstant(cuenta.getFechaCreacion());
                if (fechaCreacionCuenta.equals(fechaCreacion)) {
                    cuentaFiltrada.add(cuenta);
                }
            }
            cuentas = cuentaFiltrada;
        }
        if (fechaUltimoMovimiento != null) {
            List<Cuenta> cuentaFiltrada = new ArrayList<>();
            for (Cuenta cuenta : cuentas) {
                LocalDate fechaUltimoMovimientoCuenta = convertToLocalDateViaInstant(cuenta.getFechaCreacion());
                if (fechaUltimoMovimientoCuenta.equals(fechaUltimoMovimiento)) {
                    cuentaFiltrada.add(cuenta);
                }
            }
            cuentas = cuentaFiltrada;
        }
        if (cliente != null && !cliente.isEmpty()) {
            List<Cuenta> cuentaFiltrada = new ArrayList<>();
            for (Cuenta cuenta : cuentas) {
                ObjectId idUsuario = cuenta.getId();
                ObjectId clienteId = new ObjectId(cliente);
                
                if (clienteId.equals(idUsuario)){
                    cuentaFiltrada.add(cuenta);
                }
            }
            cuentas = cuentaFiltrada;
        }

        return cuentas;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate();
}


}
