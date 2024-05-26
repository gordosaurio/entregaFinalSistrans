package com.example.prueba.repositorio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Round;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.example.prueba.modelo.Cuenta;
import com.example.prueba.modelo.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, ObjectId>{
    
    @Query("{_id: ?0}")
    Usuario buscarporId(ObjectId id);

    @Query(value="{tipoUsuario:'?0'}", fields="{'nombre':1}")
    List<Usuario> porTipoUsuario(String tipoUsuario);

    @Query("{}")
    List<Usuario> darUsuarios();

    Usuario save(Usuario usuario);

    @Query("{_id : ?0}")
    @Update("{'$set': {'numeroDocumento': ?1}}")
    Integer updateNumeroDocumento(ObjectId userId, int numeroDocumento);

    @Query(value="{_id : ?0}", delete = true)
    void deleteById(ObjectId id);

}
