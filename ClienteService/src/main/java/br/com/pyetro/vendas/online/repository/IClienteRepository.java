package br.com.pyetro.vendas.online.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.pyetro.vendas.online.domain.Cliente;

public interface IClienteRepository extends MongoRepository<Cliente, String> {
	
	Optional<Cliente> findByCpf(Long cpf);

}
