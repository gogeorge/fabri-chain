package com.rungroop.web.controller;

import com.rungroop.web.dto.EventDto;
import com.rungroop.web.models.UserEntity;
import com.rungroop.web.security.SecurityUtil;
import com.rungroop.web.service.ClothService;
import com.rungroop.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ContactController {
    private UserService userService;

    @Autowired
    public ContactController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        System.out.println(":-> SESSION, " + user.getUsername());
        model.addAttribute("user", username);
        model.addAttribute("userEmail", user.getUsername());
        // if (username != null) {
        //     user = userService.findByUsername(username);
        //     System.out.println(":-> FIND BY, " + user);
        //     model.addAttribute("user", user);
        // }
        return "contact";
    }
}
