package com.example.prueba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prueba.modelo.Usuario;
import com.example.prueba.repositorio.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public void crearUsuario(String tipo_documento, Integer numero_documento, String nombre, String direccion_fisica, String direccion_digital, int telefono, int codigo_postal, String tipo_usuario, String nacionalidad) {
        Usuario usuario = new Usuario();
        usuario.setTipoDocumento(tipo_documento);
        usuario.setNumeroDocumento(numero_documento);
        usuario.setNombre(nombre);
        usuario.setDireccionFisica(direccion_fisica);
        usuario.setDireccionDigital(direccion_digital);
        usuario.setTelefono(telefono);
        usuario.setCodigoPostal(codigo_postal);
        usuario.setTipoUsuario(tipo_usuario);
        usuario.setNacionalidad(nacionalidad);
        usuarioRepository.save(usuario);
    }
}
