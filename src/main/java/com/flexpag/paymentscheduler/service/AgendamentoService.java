package com.flexpag.paymentscheduler.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flexpag.paymentscheduler.model.Agendamento;
import com.flexpag.paymentscheduler.repository.AgendamentoRepository;

@Service
public class AgendamentoService {

  @Autowired
  private AgendamentoRepository agendamentoRepository;

  public Long criarAgendamento() {
	    Agendamento agendamento = new Agendamento();
	    agendamento.setStatus("pending");
	    agendamento.setValor(BigDecimal.ZERO); // adiciona um valor padrão
	    agendamento.setDataHora(LocalDateTime.now()); // adiciona a data/hora atual
	    Agendamento novoAgendamento = agendamentoRepository.save(agendamento);
	    return novoAgendamento.getId();
	}

  public Optional<Agendamento> consultarAgendamento(Long id) {
    return agendamentoRepository.findById(id);
  }

  public boolean excluirAgendamento(Long id) {
    Optional<Agendamento> agendamento = agendamentoRepository.findById(id);
    if (agendamento.isPresent() && agendamento.get().getStatus().equals("pending")) {
      agendamentoRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public boolean atualizarAgendamento(Long id, LocalDateTime dataHora) {
	     Optional<Agendamento> optionalAgendamento = agendamentoRepository.findById(id);
	     if (optionalAgendamento.isPresent()) {
	         Agendamento agendamento = optionalAgendamento.get();
	         if (agendamento.getStatus().equals("pending")) {
	             agendamento.setDataHora(dataHora);
	             agendamento.setStatus("updated");
	             agendamentoRepository.save(agendamento);
	             return true;
	         }
	     }
	     return false;
	 }
}



