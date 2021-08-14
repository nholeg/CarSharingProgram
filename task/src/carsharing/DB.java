package carsharing;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DB {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static String DB_URL = "";

    public DB(String fileName) {
        this.DB_URL = "jdbc:h2:./src/carsharing/db/" + fileName;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void dropTable(String table) {

        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            try (PreparedStatement preparedStatement = conn.prepareStatement(table)) {
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void create(String action){

        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            try (PreparedStatement preparedStatement = conn.prepareStatement(action)) {
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void returnCar(String action, Customer customer){

        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            try (PreparedStatement preparedStatement = conn.prepareStatement(action)) {
                preparedStatement.setInt(1, customer.getId());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rentCar(String action, Car car, Customer customer){

        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            try (PreparedStatement preparedStatement = conn.prepareStatement(action)) {
                preparedStatement.setInt(1, car.getId());
                preparedStatement.setInt(2, customer.getId());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static String isRented(String select) {
        String data = "";
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(select)) {
                ResultSet rs = preparedStatement.executeQuery();
                data = rs.getString("RENTED_CAR_ID");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<Company> showCompanies(String sql) {
        List<Company> data = new LinkedList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.isBeforeFirst()) {
                    while (rs.next()) {
                        data.add(new Company(rs.getInt("ID"), rs.getString("NAME")));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<Car> showCarsOfCompany(String sql, Company company) {
        List<Car> data = new LinkedList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, company.getId());
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.isBeforeFirst()) {
                    while (rs.next()) {
                        data.add(new Car(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("COMPANY_ID")));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void addCompany(String sql, Company company) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, company.getName());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCar(String sql, Car car, Company company) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, car.getName());
                preparedStatement.setInt(2, company.getId());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCustomer(String sql, Customer customer) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, customer.getName());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Customer> showCustomers(String sql) {
        List<Customer> data = new LinkedList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.isBeforeFirst()) {
                    System.out.println("The customer list is empty!");
                } else {
                    while (rs.next()) {
                        data.add(new Customer(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("RENTED_CAR_ID")));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }



    public static Map<Integer, String> show(String select, int companyId) {
        Map<Integer, String> data = new TreeMap<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(select)) {
                preparedStatement.setInt(1, companyId);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.isBeforeFirst()) {
                    while (rs.next()) {
                        data.put(rs.getInt("id"), rs.getString("name"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Map<String, String> showRented(String select, Customer customer) {
        Map<String, String> data = new TreeMap<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(select)) {
                preparedStatement.setInt(1, customer.getId());
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.isBeforeFirst()) {
                    while (rs.next()) {
                        data.put(rs.getString("car"), rs.getString("company"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void add(String add, String par1) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(add)) {
                preparedStatement.setString(1, par1);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("The company was created!");

    }
    public static void add(String add, String par1, int par2) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(add)) {
                preparedStatement.setString(1, par1);
                preparedStatement.setInt(2, par2);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("The car was created!");

    }


}
