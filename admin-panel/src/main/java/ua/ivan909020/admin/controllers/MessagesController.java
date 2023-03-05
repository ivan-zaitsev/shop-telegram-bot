package ua.ivan909020.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ua.ivan909020.admin.models.entities.Message;
import ua.ivan909020.admin.services.MessageService;
import ua.ivan909020.admin.utils.ControllerUtils;

@Controller
@RequestMapping("/messages")
public class MessagesController {

    private final MessageService messageService;

    @Autowired
    public MessagesController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String showAllMessages(Model model) {
        model.addAttribute("messages", messageService.findAll());
        return "main/messages/all";
    }

    @GetMapping("/edit/{message}")
    public String showEditMessage(Model model, @PathVariable Message message) {
        model.addAttribute("message", message);
        return "main/messages/edit";
    }

    @PostMapping("/update")
    public String updateMessage(@Valid Message message, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", message);
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            return "main/messages/edit";
        }

        messageService.update(message);
        return "redirect:/messages/edit/" + message.getId();
    }

}
