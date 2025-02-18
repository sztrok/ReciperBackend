package com.eat.it.eatit.backend.service.user.account;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.data.recipe.RecipeComponent;
import com.eat.it.eatit.backend.data.recipe.Recipe;
import com.eat.it.eatit.backend.data.recipe.RecipeStep;
import com.eat.it.eatit.backend.dto.recipe.RecipeDTO;
import com.eat.it.eatit.backend.mapper.refactored.recipe.RecipeRefactoredMapper;
import com.eat.it.eatit.backend.repository.AccountRepository;
import com.eat.it.eatit.backend.repository.recipe.RecipeRepository;
import com.eat.it.eatit.backend.service.recipe.RecipeComponentService;
import com.eat.it.eatit.backend.service.recipe.RecipeIngredientService;
import com.eat.it.eatit.backend.service.recipe.RecipeService;
import com.eat.it.eatit.backend.service.recipe.RecipeStepService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.eat.it.eatit.backend.mapper.refactored.recipe.RecipeRefactoredMapper.toDTO;
import static com.eat.it.eatit.backend.mapper.refactored.recipe.RecipeRefactoredMapper.toDTOList;

@Service
public class UserAccountRecipeService extends RecipeService {
    private final AccountRepository accountRepository;

    @Autowired
    public UserAccountRecipeService(
            RecipeRepository repository,
            RecipeComponentService componentService,
            RecipeIngredientService ingredientService,
            RecipeStepService stepService,
            AccountRepository accountRepository
    ) {
        super(repository, componentService, ingredientService, stepService);
        this.accountRepository = accountRepository;
    }

    public List<RecipeDTO> getAccountRecipes(Authentication authentication) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return Collections.emptyList();
        }
        return RecipeRefactoredMapper.toDTOList(account.getAccountRecipes());
    }

    public RecipeDTO getAccountRecipeById(Authentication authentication, Long recipeId) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        Optional<Recipe> recipe = repository.findById(recipeId);
        return recipe.map(RecipeRefactoredMapper::toDTO).orElse(null);
    }

    public List<RecipeDTO> getLikedRecipes(Authentication authentication) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return Collections.emptyList();
        }
        return RecipeRefactoredMapper.toDTOList(account.getLikedRecipes());
    }

    @Transactional
    public RecipeDTO addNewAccountRecipe(Authentication authentication, RecipeDTO dto) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        List<RecipeComponent> components = dto.getRecipeComponents().stream().map(componentService::save).toList();
        List<RecipeStep> steps = dto.getDetailedSteps().stream().map(stepService::save).toList();
        Recipe recipe = getRecipeRefactored(dto, steps, components);
        recipe.setOwnerAccount(account);
        account.getAccountRecipes().add(recipe);
        return toDTO(recipe);
    }

    @Transactional
    public List<RecipeDTO> addLikedRecipes(Authentication authentication, List<Long> ids) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        List<Recipe> recipes = repository.findAllById(ids);
        for(Recipe recipe : recipes) {
            if(!account.getLikedRecipes().contains(recipe)) {
                account.getLikedRecipes().add(recipe);
                recipe.getLikedAccounts().add(account);
            }
        }
        return toDTOList(account.getLikedRecipes());
    }

    @Transactional
    public RecipeDTO updateAccountRecipe(Authentication authentication, Long recipeId) {
        //TODO: zrobic update
        return new RecipeDTO();
    }

    private Account getAccountEntityByName(String username) {
        return accountRepository.findByUsername(username);
    }
}
