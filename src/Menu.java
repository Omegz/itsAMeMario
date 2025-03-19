import java.util.List;

public enum Menu {
    VESUVIO("Vesuvio", 57, 1, List.of("tomatsauce", "ost", "skinke", "oregano")),
    AMERIKANER("Amerikaner", 53, 2, List.of("tomatsauce", "ost", "oksefars", "oregano")),
    CACCIATORE("Cacciatore", 57, 3, List.of("tomatsauce", "ost", "pepperoni", "oregano")),
    CARBONA("Carbona", 63, 4, List.of("tomatsauce", "ost", "kødsauce", "spaghetti", "cocktailpølser", "oregano"));

    private final String name;
    private final int price;
    private final int nr;
    private final List<String> ingredients;

    // ✅ Use List<String> instead of Array<String>
    Menu(String name, int price, int nr, List<String> ingredients) {
        this.name = name;
        this.price = price;
        this.nr = nr;
        this.ingredients = ingredients;
    }

    // Getters
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getNr() { return nr; }
    public List<String> getIngredients() { return ingredients; }
}
