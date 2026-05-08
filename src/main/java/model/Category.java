package model;

public class Category {

    // =========================================
    // VARIABLES
    // =========================================

    private int id;

    private String categoryName;

    // =========================================
    // DEFAULT CONSTRUCTOR
    // =========================================

    public Category() {
    }

    // =========================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================

    public Category(int id,
                    String categoryName) {

        this.id = id;

        this.categoryName = categoryName;
    }

    // =========================================
    // GETTERS AND SETTERS
    // =========================================

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getCategoryName() {

        return categoryName;
    }

    public void setCategoryName(String categoryName) {

        this.categoryName = categoryName;
    }

    // =========================================
    // TO STRING METHOD
    // =========================================

    @Override
    public String toString() {

        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}