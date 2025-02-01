package com.eat.it.eatit.backend.service.user;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.data.Recipe;
import com.eat.it.eatit.backend.dto.AccountDTO;
import com.eat.it.eatit.backend.dto.RecipeDTO;
import com.eat.it.eatit.backend.dto.recipe.RecipeDetailsDTO;
import com.eat.it.eatit.backend.dto.recipe.RecipeSimpleDTO;
import com.eat.it.eatit.backend.mapper.RecipeMapper;
import com.eat.it.eatit.backend.repository.AccountRepository;
import com.eat.it.eatit.backend.service.FridgeService;
import com.eat.it.eatit.backend.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.eat.it.eatit.backend.mapper.AccountMapper.toDTO;
import static com.eat.it.eatit.backend.utils.account.UpdateAccountFields.updateAccountFields;
import static com.eat.it.eatit.backend.utils.recipe.UpdateRecipeFields.updateRecipeFields;

@Service
public class UserAccountService {

    AccountRepository accountRepository;
    FridgeService fridgeService;
    RecipeService recipeService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountService(
            AccountRepository accountRepository,
            FridgeService fridgeService,
            PasswordEncoder passwordEncoder,
            RecipeService recipeService
    ) {
        this.accountRepository = accountRepository;
        this.fridgeService = fridgeService;
        this.passwordEncoder = passwordEncoder;
        this.recipeService = recipeService;
    }

    public List<RecipeSimpleDTO> getAccountRecipes(Authentication authentication) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return Collections.emptyList();
        }
        return RecipeMapper.toSimpleDTOList(account.getAccountRecipes());
    }

    public RecipeDetailsDTO getAccountRecipeDetails(Authentication authentication, Long recipeId) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        Recipe recipe = account.getAccountRecipes().stream().filter(it -> it.getId().equals(recipeId)).findFirst().orElse(null);
        if (recipe == null) {
            return new RecipeDetailsDTO();
        }
        return RecipeMapper.toDetailsDTO(recipe);
    }

    public List<RecipeDTO> getLikedRecipes(Authentication authentication) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return Collections.emptyList();
        }
        return toDTO(account).getLikedRecipes();
    }


    @Transactional
    public AccountDTO updateAccountById(Authentication authentication, AccountDTO accountDTO) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        return updateAccountFields(accountDTO, account, accountRepository);
    }

    @Transactional
    public List<RecipeDTO> addRecipesToAccount(Authentication authentication, List<RecipeDTO> recipes) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return Collections.emptyList();
        }
        List<Recipe> accountRecipes = account.getAccountRecipes();
        accountRecipes.addAll(RecipeMapper.toEntityList(recipes));
        account.setAccountRecipes(accountRecipes);
        recipeService.addNewRecipes(accountRecipes);
        accountRepository.save(account);
        return RecipeMapper.toDTOList(account.getAccountRecipes());
    }

    @Transactional
    public List<RecipeDTO> addLikedRecipes(Authentication authentication, List<RecipeDTO> recipes) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return Collections.emptyList();
        }
        List<Recipe> likedRecipes = account.getLikedRecipes();
        likedRecipes.addAll(RecipeMapper.toEntityList(recipes));
        account.setLikedRecipes(likedRecipes);
        return RecipeMapper.toDTOList(account.getLikedRecipes());
    }

    @Transactional
    public RecipeDTO updateAccountRecipeById(Authentication authentication, Long recipeId, RecipeDTO recipeDTO) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        Recipe recipe = account.getAccountRecipes().stream()
                .filter(r -> r.getId().equals(recipeId))
                .findFirst()
                .orElse(null);
        return updateRecipeFields(recipeDTO, recipe);

    }
    @Transactional
    public boolean deleteAccountRecipe(Authentication authentication, Long recipeId) {
        Account account = getAccountEntityByName(authentication.getName());
        RecipeDTO accountRecipe = recipeService.getRecipeById(recipeId);
        if (account == null || accountRecipe == null) {
            return false;
        }
        account.getAccountRecipes()
                .stream()
                .filter(recipe -> recipe.getId().equals(recipeId))
                .findFirst()
                .ifPresent(recipe -> account.getAccountRecipes().remove(recipe));
        return true;
    }

    @Transactional
    public boolean deleteLikedRecipe(Authentication authentication, Long recipeId) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return false;
        }
        account.getLikedRecipes()
                .stream()
                .filter(recipe -> recipe.getId().equals(recipeId))
                .findFirst()
                .ifPresent(recipe -> account.getLikedRecipes().remove(recipe));
        return true;
    }

    @Transactional
    public RecipeDTO addNewAccountRecipe(Authentication authentication, RecipeDTO recipeDTO) {
        Account account = getAccountEntityByName(authentication.getName());
        if (account == null) {
            return null;
        }
        Recipe newRecipe = RecipeMapper.toEntity(recipeDTO);
        Recipe savedRecipe = recipeService.saveRecipe(newRecipe);
        account.getAccountRecipes().add(savedRecipe);
        return RecipeMapper.toDTO(savedRecipe);
    }

    private Account getAccountEntityByName(String username) {
        return accountRepository.findByUsername(username);
    }

}
