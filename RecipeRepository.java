package recipes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<List<Recipe>> findRecipesByCategoryIgnoreCaseOrderByDateDesc(String category);
    Optional<List<Recipe>> findRecipesByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
