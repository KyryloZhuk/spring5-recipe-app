package guru.springframework.service.impl;

import guru.springframework.domain.Recipe;
import guru.springframework.repository.RecipeRepository;
import guru.springframework.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }
}
