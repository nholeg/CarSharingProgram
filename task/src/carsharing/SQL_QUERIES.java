package carsharing;

public class SQL_QUERIES {
    final static String createCompany = "CREATE TABLE IF NOT EXISTS COMPANY (ID INT not NULL PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) not NULL UNIQUE)";
    final static String createCar = "CREATE TABLE IF NOT EXISTS CAR (ID INT not NULL PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) not NULL UNIQUE, " +
            "COMPANY_ID INT NOT NULL, " +
            "CONSTRAINT fk_company FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";

    final static String createCustomer = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
            "(ID INT not NULL PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(255) not NULL UNIQUE," +
            "RENTED_CAR_ID INT DEFAULT NULL," +
            "CONSTRAINT fk_car FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";

    final static String dropIndexCompany = "ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1";
    final static String dropIndexCar = "ALTER TABLE CAR ALTER COLUMN ID RESTART WITH 1";

    final static String showCompanies = "SELECT * FROM COMPANY order by ID";
    final static String insertCar = "INSERT INTO CAR (name, company_id ) VALUES (?, ?)";
    final static String showCustomers = "SELECT * FROM CUSTOMER order by ID";
    final static String showCars = "SELECT * FROM CAR WHERE COMPANY_ID = ?";
    final static String insertCompany = "INSERT INTO COMPANY (name) VALUES (?)";
    final static String insertCustomer = "INSERT INTO CUSTOMER (name) VALUES (?)";
    final static String selectCustomerCar = "SELECT CAR.NAME AS car , Company.NAME AS company FROM CAR " +
            "JOIN COMPANY ON CAR.COMPANY_ID = COMPANY.ID " +
            "JOIN CUSTOMER ON CUSTOMER.RENTED_CAR_ID = CAR.ID " +
            "WHERE CUSTOMER.ID = ? " +
            "order by car.id";
    final static String returnCar = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ?";

    final static String notRentedCarsByCompany =
            "SELECT CAR.ID, CAR.NAME, CAR.COMPANY_ID FROM CAR WHERE CAR.COMPANY_ID  = ? " +
            " MINUS SELECT CAR.ID, CAR.NAME, CAR.COMPANY_ID FROM CAR JOIN CUSTOMER ON CAR.ID = CUSTOMER.rented_car_id";




    final static String rentCar = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";

    public static String getDropIndexCompany() {
        return dropIndexCompany;
    }

    public static String getDropIndexCar() {
        return dropIndexCar;
    }

    public static String getRentCar() {
        return rentCar;
    }

    public static String getNotRentedCarsByCompany() {
        return notRentedCarsByCompany;
    }


    public static String getReturnCar() {
        return returnCar;
    }

    public static String getSelectCustomerCar() {
        return selectCustomerCar;
    }

    public static String getShowCustomers() {
        return showCustomers;
    }

    public static String getInsertCustomer() {
        return insertCustomer;
    }

    public static String getShowCompanies() {
        return showCompanies;
    }

    public static String getCreateCustomer() {
        return createCustomer;
    }

    public static String getShowCars() {
        return showCars;
    }

    public static String getCreateCompany() {
        return createCompany;
    }

    public static String getCreateCar() {
        return createCar;
    }

    public static String getInsertCompany() {
        return insertCompany;
    }

    public static String getInsertCar() {
        return insertCar;
    }
}
