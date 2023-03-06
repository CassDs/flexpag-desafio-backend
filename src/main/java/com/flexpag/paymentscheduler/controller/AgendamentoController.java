package com.flexpag.paymentscheduler.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flexpag.paymentscheduler.model.Agendamento;
import com.flexpag.paymentscheduler.service.AgendamentoService;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<Long> criarAgendamento() {
        Long id = agendamentoService.criarAgendamento();
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> consultarAgendamento(@PathVariable Long id) {
        Optional<Agendamento> agendamento = agendamentoService.consultarAgendamento(id);
        if (agendamento.isPresent()) {
            return ResponseEntity.ok(agendamento.get().getStatus());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAgendamento(@PathVariable Long id) {
        if (agendamentoService.excluirAgendamento(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAgendamento(@PathVariable Long id, @RequestBody LocalDateTime dataHora) {
        if (agendamentoService.atualizarAgendamento(id, dataHora)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
