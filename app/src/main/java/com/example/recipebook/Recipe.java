package com.example.recipebook;

public class Recipe {

    private int r_id;
    private String r_name;
    private String r_ingr;
    private String r_steps;

    public Recipe(){}

    public Recipe(int id, String name, String ingr, String steps)
    {
        this.r_id = id;
        this.r_name = name;
        this.r_ingr = ingr;
        this.r_steps = steps;
    }

    public Recipe(String name, String ingr, String steps)
    {
        this.r_name = name;
        this.r_ingr = ingr;
        this.r_steps = steps;
    }

//    return recipe name for list view
    @Override
    public String toString() { return this.getR_name(); }

    public void setR_id(int id)
    {
        this.r_id = id;
    }

    public int getR_id()
    {
        return this.r_id;
    }

    public void setR_name(String name)
    {
        this.r_name = name;
    }

    public String getR_name() { return this.r_name; }

    public void setR_ingr(String ingr)
    {
        this.r_ingr = ingr;
    }

    public String getR_ingr() { return this.r_ingr; }

    public void setR_steps(String steps)
    {
        this.r_steps = steps;
    }

    public String getR_steps()
    {
        return this.r_steps;
    }

}
