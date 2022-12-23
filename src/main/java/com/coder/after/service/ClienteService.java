package com.coder.after.service;

import com.coder.after.entity.Cliente;
import com.coder.after.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> todos() {
        return clienteRepository.findAll();
    }

    public void borrarPorId(Long id) {
        clienteRepository.deleteById(id);
    }
}
