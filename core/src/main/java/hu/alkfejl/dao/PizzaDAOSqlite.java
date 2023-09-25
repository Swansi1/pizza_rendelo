package hu.alkfejl.dao;

import hu.alkfejl.model.Pizza;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hu.alkfejl.util.SQLiteQueries.*;

public class PizzaDAOSqlite implements PizzaDAO {
    private final String dbURL;
    private final String dbClass;

    private static volatile Map<String, PizzaDAOSqlite> instancesMap = new HashMap<>();
    private static final Object object = new Object();

    private PizzaDAOSqlite(String dbURL, String dbClass ) {
        this.dbURL = dbURL;
        this.dbClass = dbClass;

        try {
            Class.forName( dbClass );
        } catch (ClassNotFoundException e) {
            System.err.println( "The class for handling db could not be loaded" + dbClass );
        }
    }

    public static PizzaDAOSqlite getInstance(String dbURL, String dbClass ) {
        String combinedKey = dbURL + dbClass;

        if ( instancesMap.containsKey( combinedKey ) ) {
            return instancesMap.get( combinedKey );
        }

        synchronized ( object ) {
            if ( instancesMap.containsKey( combinedKey ) ) {
                return instancesMap.get( combinedKey );
            }
            var newInstance = new PizzaDAOSqlite(dbURL, dbClass);
            instancesMap.put( combinedKey, newInstance );

            return newInstance;
        }
    }

    @Override
    public List<Pizza> getAllPizza() {
        List<Pizza> pizzas = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection( dbURL );
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( GET_ALL_PIZZA )
        ) {

            while ( rs.next() ) {
                var pizza = new Pizza();
                pizza.setName( rs.getString( "name" ) );
                pizza.setToppings( rs.getString( "toppings" ) );
                pizza.setCost( rs.getInt( "cost" ) );

                pizzas.add( pizza );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pizzas;
    }

    @Override
    public boolean savePizza( Pizza pizza ) {
        boolean result;
        try( Connection connection = DriverManager.getConnection( dbURL );
            PreparedStatement ps = connection.prepareStatement( SAVE_PIZZA )
        ) {
            int index = 1;
            ps.setString( index++, pizza.getName());
            ps.setString( index++, pizza.getToppings() );
            ps.setInt( index++, pizza.getCost() );

            int affectedRows = ps.executeUpdate();
            result = 1 == affectedRows;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
