package com.example.prueba.service;

import java.time.LocalDate;
import java.util.Date;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba.modelo.Cuenta;
import com.example.prueba.repositorio.CuentaRepository;

@Service
public class CuentaService {

    private static final Logger logger = LoggerFactory.getLogger(CuentaService.class);

    @Autowired
    private CuentaRepository cuentaRepository;

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
        if (cuenta != null) {
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

}
