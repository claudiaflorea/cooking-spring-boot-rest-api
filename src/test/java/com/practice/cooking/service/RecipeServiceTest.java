package com.practice.cooking.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.repository.RecipeRepository;
import com.practice.cooking.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = RecipeServiceTest.RecipeServiceTestConfig.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    public void testGetAllRecipes() {
        Mockito.when(recipeRepository.findAll()).thenReturn(TestUtils.getRecipeList());

        List<Recipe> recipes = recipeService.getAll();

        checkInitialRecipesList(recipes);
    }

    private void checkInitialRecipesList(List<Recipe> recipes) {
        assertEquals(recipes.size(), 5);
        assertAll("List of recipes",
            () -> assertEquals("Apple Pie", recipes.get(0).getName()),
            () -> assertEquals("Risotto", recipes.get(1).getName()),
            () -> assertEquals("Onion soup", recipes.get(2).getName()),
            () -> assertEquals("Creme brulee", recipes.get(3).getName()),
            () -> assertEquals("Fois-gras", recipes.get(4).getName())
        );
    }

    @Test
    public void testSaveAndGetRecipeById() {
        Recipe newAddedRecipe = new Recipe(6L, "Risotto", Difficulty.MEDIUM, TestUtils.getRisottoIngredients(), 1, RecipeType.SIDE);
        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(newAddedRecipe));

        recipeRepository.save(newAddedRecipe);
        Recipe retrievedRecipe = recipeService.getById(6L);

        //here we verify if the record was saved in the DB, 
        //and also if the getById method works
        assertEquals(retrievedRecipe, newAddedRecipe);
    }

    @Test
    public void testDeleteRecipe() {
        Mockito.when(recipeRepository.findAll()).thenReturn(TestUtils.getRecipeList());

        List<Recipe> recipes = recipeService.getAll();
        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(recipes.get(0)));

        checkInitialRecipesList(recipes);
        recipeService.delete(recipes.get(0).getId());
        Mockito.verify(recipeRepository, Mockito.atLeastOnce()).delete(recipes.get(0));
    }

    @Configuration
    public static class RecipeServiceTestConfig {

        @Bean
        public RecipeRepository recipeRepository() {
            return Mockito.mock(RecipeRepository.class);
        }

        @Bean
        public MongoOperations mongoOperations() {
            return Mockito.mock(MongoOperations.class);
        }

    }
}
