package com.practice.cooking.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.practice.cooking.converter.RecipeEntityToDtoConverter;
import com.practice.cooking.dto.RecipeDto;
import com.practice.cooking.exception.NotFoundException;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    private final ConversionService conversionService;

    @GetMapping()
    public List<RecipeDto> getAllRecipes() {
        return recipeService.getAll()
            .stream()
            .map(recipe -> conversionService.convert(recipe, RecipeDto.class))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RecipeDto getRecipeById(@PathVariable(value = "id") Long id) throws NotFoundException {
        return conversionService.convert(recipeService.getById(id), RecipeDto.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createRecipe(@RequestBody RecipeDto recipe) {
        recipeService.add(conversionService.convert(recipe, Recipe.class));
    }

    @PutMapping("/{id}")
    public void updateRecipe(@PathVariable(value = "id") Long id, @RequestBody RecipeDto recipeDetails) throws NotFoundException {
        recipeService.update(id, conversionService.convert(recipeDetails, Recipe.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRecipe(@PathVariable(value = "id") Long id) throws NotFoundException {
        recipeService.delete(id);
    }
    
}
