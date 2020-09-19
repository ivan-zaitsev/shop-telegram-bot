package ua.ivan909020.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ivan909020.admin.domain.Order;
import ua.ivan909020.admin.services.OrderService;
import ua.ivan909020.admin.utils.ControllerUtils;
import ua.ivan909020.admin.utils.FormatDateTimeMethodModel;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final FormatDateTimeMethodModel dateTimeFormatter;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.dateTimeFormatter = new FormatDateTimeMethodModel();
    }

    @GetMapping
    public String showAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("formatDateTime", dateTimeFormatter);
        return "orders/all";
    }

    @GetMapping("/edit/{order}")
    public String showEditOrder(Model model, @PathVariable Order order) {
        model.addAttribute("order", order);
        model.addAttribute("formatDateTime", dateTimeFormatter);
        return "orders/edit";
    }

    @PostMapping("/update")
    public String updateOrder(@Valid Order order, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            model.addAttribute("order", order);
            model.addAttribute("formatDateTime", dateTimeFormatter);
            return "orders/edit";
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
