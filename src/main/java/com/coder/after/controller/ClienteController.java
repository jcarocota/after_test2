package com.coder.after.controller;

import com.coder.after.entity.Cliente;
import com.coder.after.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente) {
        try {
            Cliente clienteGuardado = clienteService.guardar(cliente);
            return ResponseEntity.created(URI.create("")).body(clienteGuardado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getStackTrace());
        }
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> buscarPorId(@PathVariable(name = "id") Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);

        if(cliente.isPresent()) {
            return ResponseEntity.of(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/todos", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Cliente>> todos() {
        return ResponseEntity.ok().body(clienteService.todos());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> borrarPorId(@PathVariable(name = "id") Long id) {
        clienteService.borrarPorId(id);
        return ResponseEntity.ok().body("Cliente con ID: " + id + ", borrado");
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> modificar(@RequestBody Cliente cliente) {
        try {
            Optional<Cliente> clienteGuardado = clienteService.buscarPorId(cliente.getId());
            if(clienteGuardado.isPresent()) {
                Cliente clienteModificar = clienteGuardado.get();
                clienteModificar.setApellido(cliente.getApellido());
                clienteModificar.setNombre(cliente.getNombre());
                clienteModificar.setDni(cliente.getDni());

                clienteService.guardar(clienteModificar);
                return ResponseEntity.ok().body(clienteModificar);
            } else {
                return ResponseEntity.ok().body("Cliente con ID: " + cliente.getId() + ", inexistente");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getStackTrace());
        }
    }

}
