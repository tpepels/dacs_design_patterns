package SOLID.dependency_inversion;

/*
 * In this example, UserService directly depends on MySQLDatabase, which is a
 * low-level module.
 */

class BadMySQLDatabase {
    public void connect() {
        // Connect to MySQL database
    }

    public void query(String query) {
        // Execute the query
    }
}

class BadUserService {
    private BadMySQLDatabase database;

    public BadUserService() {
        this.database = new BadMySQLDatabase();
    }

    public void getUserById(int id) {
        database.connect();
        database.query("SELECT * FROM users WHERE id = " + id);
    }
}

/*
 * In the solution, we introduce the Database interface and make MySQLDatabase
 * implement it. Now, UserService depends on the abstraction Database, adhering
 * to the Dependency Inversion Principle.
 */

interface Database {
    void connect();

    void query(String query);
}

class MySQLDatabase implements Database {
    public void connect() {
        // Connect to MySQL database
    }

    public void query(String query) {
        // Execute the query
    }
}

class UserService {
    private Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public void getUserById(int id) {
        database.connect();
        database.query("SELECT * FROM users WHERE id = " + id);
    }
}
