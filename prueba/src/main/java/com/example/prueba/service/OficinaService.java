package com.example.prueba.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba.modelo.Oficina;
import com.example.prueba.repositorio.OficinaRepository;

@Service
public class OficinaService {
    @Autowired
    private OficinaRepository oficinaRepository;

    public void crearOficina (String nombre, String direccion, int numeroPuntosDisponibles, ObjectId idGerente) {
        Oficina oficina = new Oficina();
        oficina.setNombre(nombre);
        oficina.setDireccion(direccion);
        oficina.setNumeroPuntosDisponibles(numeroPuntosDisponibles);
        oficina.setIdGerente(idGerente);
        oficinaRepository.save(oficina);
    }
}
