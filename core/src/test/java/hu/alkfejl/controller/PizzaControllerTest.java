package hu.alkfejl.controller;

import hu.alkfejl.model.Pizza;
import hu.alkfejl.model.User;

public class PizzaControllerTest {
    public static void main( String[] args ) {

        System.out.println( "Getting controller" );
        var pizzaController = PizzaController.getInstance( "jdbc:sqlite:TODO/pizza/core/src/main/resources/pizza.sqlite",
                "org.sqlite.JDBC" );

        System.out.println("No pizza");
        var pizzas = pizzaController.getAllPizza();
        assert pizzas.size() == 0;

        System.out.println("Save new pizza.");

        Pizza pizza = new Pizza();
        pizza.setName( "Hawaii" );
        pizza.setToppings( "Cheese, Pineapple" );
        pizza.setCost( 3 );

        pizzaController.savePizza( pizza );

        System.out.println("No pizza");
        pizzas = pizzaController.getAllPizza();
        assert pizzas.size() == 1;

    }
}
