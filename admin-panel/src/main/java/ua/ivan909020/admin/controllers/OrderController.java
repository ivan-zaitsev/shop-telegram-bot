package ua.ivan909020.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import ua.ivan909020.admin.models.entities.Order;
import ua.ivan909020.admin.services.OrderService;
import ua.ivan909020.admin.utils.ControllerUtils;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "main/orders/all";
    }

    @GetMapping("/edit/{order}")
    public String showEditOrder(Model model, @PathVariable Order order) {
        model.addAttribute("order", order);
        return "main/orders/edit";
    }

    @PostMapping("/update")
    public String updateOrder(@Valid Order order, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            model.addAttribute("order", order);
            return "main/orders/edit";
        }

        orderService.update(order);
        return "redirect:/orders/edit/" + order.getId();
    }

    @PostMapping("/delete")
    public String deleteOrder(@RequestParam Integer id) {
        orderService.deleteById(id);
        return "redirect:/orders";
    }

}
