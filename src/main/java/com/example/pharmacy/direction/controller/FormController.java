package com.example.pharmacy.direction.controller;

import com.example.pharmacy.direction.dto.InputDto;
import com.example.pharmacy.pharmacy.service.PharmacyRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FormController {

    private final PharmacyRecommendationService pharmacyRecommendationService;
    @GetMapping
    public String main() {
        return "main";
    }

    /*
        입력한 정보를 InputDto에 담아서 받아오게 구현
     */
    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute InputDto inputDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output"); // output.hbs
        modelAndView.addObject("outputFormList",
                pharmacyRecommendationService.recommendPharmacyList(inputDto.getAddress()));

        return modelAndView;
    }
}
