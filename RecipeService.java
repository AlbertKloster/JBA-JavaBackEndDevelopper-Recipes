package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    private final RecipeRepository repository;

    @Autowired
    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public Object save(Recipe recipe) {
        return Map.of("id", repository.save(recipe).getId());
    }

    public Object update(Recipe recipe) {
        Recipe oldRecipe = findById(recipe.getId());
        checkAuthor(oldRecipe, recipe.getEmail());
        return save(recipe);
    }

    public Recipe findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteById(Long id, String email) {
        Recipe recipe = findById(id);
        checkAuthor(recipe, email);
        repository.delete(recipe);
    }

    public List<Recipe> search(String name, String category) {
        if (name.isEmpty() && category.isEmpty() || !name.isEmpty() && !category.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return name.isEmpty() ?
                repository.findRecipesByCategoryIgnoreCaseOrderByDateDesc(category).orElse(List.of()) :
                repository.findRecipesByNameContainingIgnoreCaseOrderByDateDesc(name).orElse(List.of());
    }

    private void checkAuthor(Recipe recipe, String email) {
        if (recipe.getEmail() == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        if (!recipe.getEmail().equals(email))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

}