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
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import ua.ivan909020.admin.models.entities.Product;
import ua.ivan909020.admin.services.CategoryService;
import ua.ivan909020.admin.services.ProductService;
import ua.ivan909020.admin.utils.ControllerUtils;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showAllProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "main/products/all";
    }

    @GetMapping("/add")
    public String showAddProduct(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "main/products/add";
    }

    @GetMapping("/edit/{product}")
    public String showEditProduct(Model model, @PathVariable Product product) {
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "main/products/edit";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid Product product,
            BindingResult bindingResult,
            Model model,
            @RequestParam MultipartFile photo
    ) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.findAll());
            return "main/products/add";
        }

        productService.save(product, photo);
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateProduct(
            @Valid Product product,
            BindingResult bindingResult,
            Model model,
            @RequestParam(required = false) MultipartFile photo
    ) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.findErrors(bindingResult));
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.findAll());
            return "main/products/edit";
        }

        productService.update(product, photo);
        return "redirect:/products/edit/" + product.getId();
    }

    @PostMapping("/delete")
    public String deleteCategory(@RequestParam Integer id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

}
