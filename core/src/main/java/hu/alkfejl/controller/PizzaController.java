package hu.alkfejl.controller;

import hu.alkfejl.dao.PizzaDAO;
import hu.alkfejl.dao.PizzaDAOSqlite;
import hu.alkfejl.model.Pizza;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PizzaController {
    private PizzaDAO dao;

    private static volatile Map<String, PizzaController> instancesMap = new HashMap<>();
    private static final Object object = new Object();
    private PizzaController(String dbURL, String dbClass ) {
        dao = PizzaDAOSqlite.getInstance( dbURL, dbClass );
    }

    /**
     * Get an instance with fix db engine: org.sqlite.JDBC
     * {@link hu.alkfejl.controller.UserController#getInstance(String, String)} is called.
     *
     * @param dbURL URL of the db.
     * */
    public static PizzaController getInstance(String dbURL ) {
        return getInstance( dbURL, "org.sqlite.JDBC" );
    }

    /**
     * Get an instance for the database and the handling engine.
     * @param dbURL URL of the db. jdbc:whatever: must be the prefix
     * @param dbClass the handling class to load dynamically, e.g. org.sqlite.JDBC
     * */
    public static PizzaController getInstance(String dbURL, String dbClass ) {
        String combinedKey = dbURL + dbClass;

        if ( instancesMap.containsKey( combinedKey ) ) {
            return instancesMap.get( combinedKey );
        }

        synchronized ( object ) {
            if ( instancesMap.containsKey( combinedKey ) ) {
                return instancesMap.get( combinedKey );
            }
            var newInstance = new PizzaController(dbURL, dbClass);
            instancesMap.put( combinedKey, newInstance );

            return newInstance;
        }
    }

    /**
     * Get all the pizza in the database.
     *
     * @return A list of pizzas, containing every pizza from the db.
     * */
    public List<Pizza> getAllPizza() {
        return dao.getAllPizza();
    }

    /**
     * Save a pizza in db.
     *
     * @param pizza The Pizza object that should be saved in db
     *
     * @return true if the pizza is saved, false otherwise.
     * */
    public boolean savePizza( Pizza pizza ) {
        return dao.savePizza( pizza );
    }
}
