package com.example.clase11backend.controllers;

import com.example.clase11backend.entity.Employee;
import com.example.clase11backend.repos.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping("/buscarPorId")
    public ResponseEntity<HashMap<String, Object>> lista(@RequestParam(value = "id", required = false) Integer id) {

        HashMap<String, Object> map = new HashMap<>();
        if (id == null) {
            map.put("estado", "error");
            map.put("msg", "Debe enviar un ID");
            return ResponseEntity.badRequest().body(map);
        } else {
            Optional<Employee> optional = employeeRepository.findById(id);
            if (optional.isPresent()) {
                map.put("estado", "ok");
                map.put("empleado", optional.get());
            } else {
                map.put("estado", "error");
                map.put("msg", "No existe un usuario con el id " + id);
            }
            return ResponseEntity.ok(map);
        }

    }

    @GetMapping("/listar")
    public ResponseEntity<HashMap<String, Object>> listarEmpleados(
            @RequestHeader(value = "api-key", required = false) String apiKey) {

        HashMap<String, Object> map = new HashMap<>();
        if (apiKey != null) {
            if (apiKey.equals("EaQibIyUgcoCAyelLnDwUAxR1OX6AH")) {
                map.put("estado", "ok");
                map.put("lista", employeeRepository.findAll());
            } else {
                map.put("estado", "error");
                map.put("msg", "El Api key no coincide");
            }
            return ResponseEntity.ok(map);
        } else {
            map.put("estado", "error");
            map.put("msg", "Debe enviar un api-key");
            return ResponseEntity.badRequest().body(map);
        }
    }
}
