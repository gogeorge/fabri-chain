package com.rungroop.web.controller;

import com.rungroop.web.dto.ClothesDto;
import com.rungroop.web.models.Cloth;
import com.rungroop.web.models.UserEntity;
import com.rungroop.web.security.SecurityUtil;
import com.rungroop.web.service.ClothService;
import com.rungroop.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ClothController {
    private ClothService clothService;
    private UserService userService;

    @Autowired
    public ClothController(ClothService clothService, UserService userService) {
        this.userService = userService;
        this.clothService = clothService;
    }

    @GetMapping("/clothes")
    public String listClothes(Model model) {
        UserEntity user = new UserEntity();
        List<ClothesDto> clothes = clothService.findAllClothes();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("clothes", clothes);
        return "clothes-list";
    }

    @GetMapping("/clothes/{clothId}")
    public String clothDetail(@PathVariable("clothId") long clothId, Model model) {
        UserEntity user = new UserEntity();
        ClothesDto clothesDto = clothService.findClothById(clothId);
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("cloth", clothesDto);
        return "clothes-detail";
    }

    @GetMapping("/clothes/new")
    public String createClothForm(Model model) {
        Cloth cloth = new Cloth();
        model.addAttribute("cloth", cloth);
        return "clothes-create";
    }

    @GetMapping("/clothes/{clothId}/delete")
    public String deleteCloth(@PathVariable("clothId")Long clothId) {
        clothService.delete(clothId);
        return "redirect:/clothes";
    }

    @GetMapping("/clothes/search")
    public String searchCloth(@RequestParam(value = "query") String query, Model model) {
        List<ClothesDto> clothes = clothService.searchClothes(query);
        model.addAttribute("clothes", clothes);
        return "clothes-list";
    }

    @PostMapping("/clothes/new")
    public String saveCloth(@Valid @ModelAttribute("cloth") ClothesDto clothesDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("cloth", clothesDto);
            return "clothes-create";
        }
        clothService.saveCloth(clothesDto);
        return "redirect:/clothes";
    }

    @GetMapping("/clothes/{clothId}/edit")
    public String editClothForm(@PathVariable("clubId") Long clubId, Model model) {
        ClothesDto club = clothService.findClothById(clubId);
        model.addAttribute("club", club);
        return "clubs-edit";
    }
    @PostMapping("/clothes/{clothId}/edit")
    public String updateClothes(@PathVariable("clothId") Long clothId,
                             @Valid @ModelAttribute("club") ClothesDto cloth,
                             BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("cloth", cloth);
            return "clothes-edit";
        }
        cloth.setId(clothId);
        clothService.updateCloth(cloth);
        return "redirect:/clothes";
    }
}
