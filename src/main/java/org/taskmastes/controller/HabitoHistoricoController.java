package org.taskmastes.controller;


import org.taskmastes.model.Habito;
import org.taskmastes.model.HabitoHistorico;
import org.taskmastes.service.HabitoHistoricoService;
import org.taskmastes.service.HabitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class HabitoHistoricoController {

    @Autowired
    private HabitoHistoricoService habitoHistoricoService;

    @Autowired
    private HabitoService habitoService;

    // Lista todo o histórico de hábitos
    @GetMapping("/habitos/historico")
    public String listHabitoHistorico(Model model) {
        model.addAttribute("historicos", habitoHistoricoService.findAll());
        model.addAttribute("habitos", habitoService.findAll());
        return "habitos/historico-list";
    }

    // Adiciona um novo registro ao histórico de um hábito
    @PostMapping("/habitos/historico/add")
    public String addHabitoHistorico(@RequestParam Long habitoId, @RequestParam String data) {
        Habito habito = habitoService.findById(habitoId);
        if (habito != null) {
            HabitoHistorico historico = new HabitoHistorico();
            historico.setHabito(habito);
            historico.setData(LocalDate.parse(data)); // Converte a data para LocalDate
            habitoHistoricoService.save(historico);
        }
        return "redirect:/habitos/historico";
    }

    // Remove um registro do histórico pelo ID
    @PostMapping("/habitos/historico/remove")
    public String removeHabitoHistorico(@RequestParam Long id) {
        habitoHistoricoService.deleteById(id);
        return "redirect:/habitos/historico";
    }
}