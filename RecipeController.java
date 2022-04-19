package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public Object postRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails user) {
        recipe.setEmail(user.getUsername());
        return recipeService.save(recipe);
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        recipeService.deleteById(id, user.getUsername());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putRecipe(@Valid @RequestBody Recipe recipe,
                            @PathVariable Long id,
                            @AuthenticationPrincipal UserDetails user) {
        recipe.setId(id);
        recipe.setEmail(user.getUsername());
        recipeService.update(recipe);
    }

    @GetMapping("/search")
    public List<Recipe> getRecipes(@Valid @RequestParam(defaultValue = "") String category,
                                   @Valid @RequestParam(defaultValue = "") String name) {
        return recipeService.search(name, category);
    }

}
