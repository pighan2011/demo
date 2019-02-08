package com.bootdo.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HtmlController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/news/{id}")
    public String news(@PathVariable Long id, Model model) {
        model.addAttribute("newsId", id);
        return "news";
    }
}
