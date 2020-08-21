package com.vedran.recipe.controllers;

import com.vedran.recipe.dto.IngredientDto;
import com.vedran.recipe.dto.RecipeDto;
import com.vedran.recipe.dto.UnitOfMeasureDto;
import com.vedran.recipe.services.IngredientService;
import com.vedran.recipe.services.RecipeService;
import com.vedran.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeDtoById(id));

        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable Long recipeId,
                                       @PathVariable Long id,
                                       Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable Long recipeId,
                                         @PathVariable Long id,
                                         Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientDto ingredientDto) {
        IngredientDto savedIngredient = ingredientService.saveIngredientDto(ingredientDto);

        return "redirect:/recipe/" + savedIngredient.getRecipeId() + "/ingredient/" + savedIngredient.getId() + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable Long recipeId, Model model) {
        //make sure we have a good id value
        RecipeDto recipeDto = recipeService.findRecipeDtoById(recipeId);
        //todo raise exception if null

        //need to return back parent id for hidden form property
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientDto);

        //init uom
        ingredientDto.setUnitOfMeasure(new UnitOfMeasureDto());

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteById(@PathVariable Long recipeId, @PathVariable Long id) {
        ingredientService.deleteById(recipeId, id);

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
