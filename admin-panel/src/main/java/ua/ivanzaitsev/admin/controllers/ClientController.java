package ua.ivanzaitsev.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ua.ivanzaitsev.admin.models.entities.Client;
import ua.ivanzaitsev.admin.services.ClientService;
import ua.ivanzaitsev.admin.utils.ControllerUtils;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String showAllClients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "main/clients/all";
    }

    @GetMapping("/edit/{client}")
    public String showEditClient(Model model, @PathVariable Client client) {
        model.addAttribute("client", client);
        return "main/clients/edit";
    }

    @PostMapping("/update")
    public String updateClient(@Valid Client client, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            model.addAttribute("client", client);
            return "main/clients/edit";
        }

        clientService.update(client);
        return "redirect:/clients/edit/" + client.getId();
    }

}
