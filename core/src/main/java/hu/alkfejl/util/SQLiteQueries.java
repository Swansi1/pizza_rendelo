package hu.alkfejl.util;

public class SQLiteQueries {
    public static final String REGISTER_USER = "INSERT INTO User (username, password, token) VALUES(?,?,?);";
    public static final String GET_USER_BY_NAME = "SELECT * FROM User WHERE username = ?;";
    public static final String GET_USER_BY_TOKEN = "SELECT * FROM User WHERE token = ?;";
    public static final String UPDATE_USER = "UPDATE User SET password = ?, token = ? WHERE username = ?;";
    public static final String GET_ALL_PIZZA = "SELECT * FROM Pizza;";
    public static final String SAVE_PIZZA = "INSERT INTO Pizza(name, toppings, cost) VALUES(?,?,?);";
}
