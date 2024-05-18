package com.example.prueba.repositorio;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.prueba.modelo.Cuenta;

@Repository
public interface CuentaRepository  extends MongoRepository<Cuenta, ObjectId>{
    @Query("{}")
    List<Cuenta> buscarCuentas();

    Cuenta save(Cuenta cuenta);

    @Query("{_id: ?0}")
    Cuenta buscarporId(ObjectId id);
}
