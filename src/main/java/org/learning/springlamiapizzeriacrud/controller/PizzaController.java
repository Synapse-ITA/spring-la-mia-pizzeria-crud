package org.learning.springlamiapizzeriacrud.controller;
import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model) {
        // METODO CHE LEGGE TUTTI GLI ELEMENTI DEL DATABASE
        List<Pizza> pizzaList = pizzaRepository.findAll();
        model.addAttribute("pizzaList", pizzaList);
        return "pizza/list";
    }
    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            Pizza pizza = result.get();
            model.addAttribute("pizza", pizza);
            return "pizza/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }
    @GetMapping("/create")
    public String create(Model model) {
        Pizza pizza = new Pizza();
        model.addAttribute("pizza", pizza);
        return "pizza/create";
    }
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pizza/create";
        }
        Pizza savedPizza = pizzaRepository.save(formPizza);
        return "redirect:/pizza/show/" + savedPizza.getId();
    }
}

