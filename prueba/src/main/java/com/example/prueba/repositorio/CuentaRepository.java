package com.example.prueba.repositorio;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.example.prueba.modelo.Cuenta;

@Repository
public interface CuentaRepository  extends MongoRepository<Cuenta, ObjectId>{
    @Query("{}")
    List<Cuenta> buscarCuentas();

    Cuenta save(Cuenta cuenta);

    @Query("{_id: ?0}")
    Cuenta buscarporId(ObjectId id);

    @Query("{estadoCuenta: ?0}")
    List<Cuenta> buscarPorEstado(String estadoCuenta);

    @Query(value = "{_id : ?0}", exists = true)
    @Update("{$set: {saldo : ?1}}")
    void actualizarSaldo(ObjectId idPuntoAtencion, Double saldo);

    @Query("{_id : ?0}")
    @Update("{'$set':{'idUsuario': ?1,'tipoCuenta': ?2, 'estadoCuenta': ?3, 'fechaUltimaTransaccion': ?4, 'saldo': ?5, 'fechaCreacion': ?6}}")
    void updateCuenta(ObjectId id, ObjectId idUsuario, String tipoCuenta, String estadoCuenta, Date transaccion, Double saldo, Date creacion );

    @Query(value="{_id : ?0}", delete = true)
    void deleteById(ObjectId id);
}
