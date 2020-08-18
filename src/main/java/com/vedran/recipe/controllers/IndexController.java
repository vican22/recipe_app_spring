package com.vedran.recipe.controllers;

import com.vedran.recipe.models.Category;
import com.vedran.recipe.models.UnitOfMeasure;
import com.vedran.recipe.repositories.CategoryRepository;
import com.vedran.recipe.repositories.UnitOfMeasureRepository;
import com.vedran.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

}
