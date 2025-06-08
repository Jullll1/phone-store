package com.julia.phone_store.controller;

import com.julia.phone_store.model.Phone;
import com.julia.phone_store.service.PhoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/phones")
public class PhoneController {

    private static final Logger logger = LoggerFactory.getLogger(PhoneController.class);

    private final PhoneService phoneService;

    @Autowired
    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    // Вивід усіх товарів для HTML-форми
    @GetMapping
    public String listPhones(Model model) {
        logger.info("Rendering list of phones for HTML");
        List<Phone> phones = phoneService.getAllPhones();
        model.addAttribute("phones", phones);
        return "list";
    }

    // Перехід на форму додавання
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("phone", new Phone());
        return "form";
    }

    // Додавання або оновлення запису через форму
    @PostMapping("/save")
    public String savePhone(@ModelAttribute Phone phone) {
        phoneService.savePhone(phone);
        return "redirect:/phones";
    }

    // Додавання запису через API
    @PostMapping
    @ResponseBody
    public Phone createPhone(@RequestBody Phone phone) {
        logger.info("Creating phone via API: {}", phone);
        return phoneService.savePhone(phone);
    }

    // Перехід на форму редагування
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Phone> phone = phoneService.getPhoneById(id);
        if (phone.isPresent()) {
            model.addAttribute("phone", phone.get());
            return "form";
        } else {
            return "redirect:/phones";
        }
    }

    // Видалення запису
    @GetMapping("/delete/{id}")
    public String deletePhone(@PathVariable Long id) {
        phoneService.deletePhone(id);
        return "redirect:/phones";
    }

    // Вивід усіх товарів для API
    @GetMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Phone> getAllPhones() {
        logger.info("Fetching all phones via API");
        return phoneService.getAllPhones();
    }
}