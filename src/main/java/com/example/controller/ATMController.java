package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ATMController {

    private int balance = 100000; // Initial balance

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("balance", balance);
        return "index";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam int amount, Model model) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            model.addAttribute("message", "Withdraw successful!");
        } else {
            model.addAttribute("message", "Insufficient balance or invalid amount!");
        }
        model.addAttribute("balance", balance);
        return "index";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam int amount, Model model) {
        if (amount > 0) {
            balance += amount;
            model.addAttribute("message", "Deposit successful!");
        } else {
            model.addAttribute("message", "Enter a valid amount!");
        }
        model.addAttribute("balance", balance);
        return "index";
    }
}

