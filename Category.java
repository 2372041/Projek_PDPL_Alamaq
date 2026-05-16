public class Category {
    private int categoryId;
    private String name;
    private String type; // "INCOME" atau "EXPENSE"

    public Category(int categoryId, String name, String type) {
        this.categoryId = categoryId;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }
    
    public String getType() {
        return this.type;
    }
}