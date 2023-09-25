package hu.alkfejl.dao;

import hu.alkfejl.model.Pizza;

import java.util.List;

public interface PizzaDAO {
    List<Pizza> getAllPizza();
    boolean savePizza( final Pizza pizza );
}
