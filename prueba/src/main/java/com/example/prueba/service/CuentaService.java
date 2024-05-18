package com.example.prueba.service;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba.modelo.Cuenta;
import com.example.prueba.repositorio.CuentaRepository;

@Service
public class CuentaService {
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
}
