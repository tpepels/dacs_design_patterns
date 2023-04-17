package SOLID.interface_seggregation;

/**
 * In this example, we have a Database interface that defines methods for
 * connecting to a database, inserting data, updating data, and deleting data.
 * However, the MySQLDatabase and OracleDatabase classes violate the ISP because
 * they both implement methods that may not be relevant to their behavior (e.g.,
 * MySQLDatabase may not need to implement delete() if it doesn't support the
 * feature or behavior).
 */
interface BadDatabase {
    void connect();

    void insert();

    void update();

    void delete();
}

class BadMySQLDatabase implements BadDatabase {
    public void connect() {
        System.out.println("Connecting to MySQL database...");
    }

    public void insert() {
        System.out.println("Inserting data into MySQL database...");
    }

    public void update() {
        System.out.println("Updating data in MySQL database...");
    }

    public void delete() {
        System.out.println("Deleting data from MySQL database...");
    }
}

class BadOracleDatabase implements BadDatabase {
    public void connect() {
        System.out.println("Connecting to Oracle database...");
    }

    public void insert() {
        System.out.println("Inserting data into Oracle database...");
    }

    public void update() {
        System.out.println("Updating data in Oracle database...");
    }

    public void delete() {
        System.out.println("Deleting data from Oracle database...");
    }
}

/*
 * To solve this problem, we can use interfaces that are specific to each
 * database operation, such as Insertable, Updatable, and Deletable, and have
 * classes implement only the interfaces that are relevant to their behavior.
 */

interface Database {
    void connect();
}

interface Insertable {
    void insert();
}

interface Updatable {
    void update();
}

interface Deletable {
    void delete();
}

class MySQLDatabase implements Database, Insertable, Updatable, Deletable {
    public void connect() {
        System.out.println("Connecting to MySQL database...");
    }

    public void insert() {
        System.out.println("Inserting data into MySQL database...");
    }

    public void update() {
        System.out.println("Updating data in MySQL database...");
    }

    public void delete() {
        System.out.println("Deleting data from MySQL database...");
    }
}

class OracleDatabase implements Database, Insertable, Updatable, Deletable {
    public void connect() {
        System.out.println("Connecting to Oracle database...");
    }

    public void insert() {
        System.out.println("Inserting data into Oracle database...");
    }

    public void update() {
        System.out.println("Updating data in Oracle database...");
    }

    public void delete() {
        System.out.println("Deleting data from Oracle database...");
    }
}
