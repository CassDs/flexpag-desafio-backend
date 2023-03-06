package com.flexpag.paymentscheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.flexpag.paymentscheduler.model.Agendamento;
import com.flexpag.paymentscheduler.repository.AgendamentoRepository;
import com.flexpag.paymentscheduler.service.AgendamentoService;

@SpringBootTest
public class PaymentSchedulerApplicationTests {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Test
    public void testCriarAgendamento() {
        Agendamento agendamento = new Agendamento();
        agendamento.setValor(new BigDecimal("10.00"));
        agendamento.setDataHora(LocalDateTime.now().plusDays(1));

        Long id = agendamentoService.criarAgendamento();

        assertNotNull(id);
    }

    @Test
    public void testConsultarAgendamento() {
        Agendamento agendamento = new Agendamento();
        agendamento.setValor(new BigDecimal("10.00"));
        agendamento.setDataHora(LocalDateTime.now().plusDays(1));

        Long id = agendamentoService.criarAgendamento();

        Optional<Agendamento> optionalAgendamento = agendamentoService.consultarAgendamento(id);

        String status = optionalAgendamento.map(Agendamento::getStatus).orElse(null);

        assertThat(status).isEqualTo("pending");
    }

    @Test
    public void testExcluirAgendamento() {
        Agendamento agendamento = new Agendamento();
        agendamento.setValor(new BigDecimal("10.00"));
        agendamento.setDataHora(LocalDateTime.now().plusDays(1));

        Long id = agendamentoService.criarAgendamento();

        agendamentoService.excluirAgendamento(id);

        assertFalse(agendamentoRepository.findById(id).isPresent());
    }

    @Test
    public boolean atualizarAgendamento(Long id, LocalDateTime dataHora) {
        Optional<Agendamento> optionalAgendamento = agendamentoRepository.findById(id);
        if (optionalAgendamento.isPresent()) {
            Agendamento agendamento = optionalAgendamento.get();
            if (agendamento.getStatus().equals("created")) { // mudança aqui
                agendamento.setDataHora(dataHora);
                agendamento.setStatus("updated");
                agendamentoRepository.save(agendamento);
                return true;
            }
        }
        return false;
    }
}

