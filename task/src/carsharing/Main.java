package carsharing;


import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class Main {

    static final Scanner SCANNER = new Scanner(System.in);
    static DB db;

    static void menuLogin() {
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
    }
    static void menuManager() {
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }
    static void menuCompany(String company) {
        System.out.println(company + " company:");
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
    }
    static void menuCustomer() {
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
    }
    static void login() {
        menuLogin();
        int choice = SCANNER.nextInt();
        switch (choice){
            case 0:
                System.exit(0);
                break;
            case 1:
                //Log in as a manager
                manager();
                break;
            case 2:
                //Log as Customer
                customerList();
                break;
            case 3:
                //Create a customer
                System.out.println("Enter the customer name:");
                String customerName = SCANNER.nextLine();
                customerName = SCANNER.nextLine();
                Customer customer = new Customer(customerName);
                db.addCustomer(SQL_QUERIES.getInsertCustomer(), customer);
                System.out.println("The customer was added!");
                login();
                break;
        }

    }
    static void manager() {
        menuManager();
        Company company = new Company();
        int choice = SCANNER.nextInt();
        switch (choice){
            case 0:
                //Back
                login();
                break;
            case 1:
                //Company list
                company = companyList();
                if ("unknown".equals(company.getName())) {
                    manager();
                } else {
                    company(company);
                }
                break;
            case 2:
                //Create a company
                System.out.println("Enter the company name:");
                String comp = SCANNER.nextLine();
                comp = SCANNER.nextLine();
                company = new Company(comp);
                db.addCompany(SQL_QUERIES.getInsertCompany(), company);
                System.out.println("The company was created!");
                manager();
                break;
        }
    }
    static Company companyList() {
        System.out.println();
        System.out.println("Choose a company:");
        List<Company> companies = db.showCompanies(SQL_QUERIES.getShowCompanies());
        Company company = new Company();
        if (!companies.isEmpty()) {
            companies.forEach(x -> System.out.println(x.getId() + ". " + x.getName()));
            System.out.println("0. Back");
            int choice = SCANNER.nextInt();
            if (choice == 0){
                manager();
            } else {
                company = companies.stream().filter(x -> x.getId() == choice).findAny().get();
            }
        } else {
            System.out.println("The company list is empty!");
        }
        return company;
    }
    static void company(Company company) {
        menuCompany(company.getName());
        int choice = SCANNER.nextInt();
        switch (choice) {
            case 0:
                //back to manager
                manager();
                break;
            case 1:
                //car list
                carList(company);
                System.out.println();
                company(company);
                break;
            case 2:
                //Create a car
                System.out.println("Enter the car name:");
                String carName = SCANNER.nextLine();
                carName = SCANNER.nextLine();
                Car car = new Car(carName);
                db.addCar(SQL_QUERIES.getInsertCar(), car,  company);
                System.out.println("The car was added!");
                System.out.println();
                company(company);
                break;
        }
    }

    static void carList(Company company) {
        int index = 1;
        List<Car> cars = db.showCarsOfCompany(SQL_QUERIES.getShowCars(), company);
        if (!cars.isEmpty()) {
            for (Car car: cars) {
                System.out.println(index + ". " + car.getName());
                index++;
            }
        } else {
            System.out.println("The car list is empty!");
        }
    }

    static void pickACar (List<Car> cars, Customer customer) {
        Car car = new Car();
        System.out.println();
        System.out.println("Choose a car:");
        cars.forEach(x -> System.out.println(x.getId() + ". " + x.getName()));
        System.out.println("0. Back");
        int choice = SCANNER.nextInt();
        if (choice != 0) {
           car = cars.stream().filter(x -> x.getId() == choice).findAny().get();
           db.rentCar(SQL_QUERIES.getRentCar(), car, customer);
            System.out.println("You rented '" + car.getName() +"'");
        }
        customer(customer);
    }

    static void customerList() {
        List<Customer> customers = db.showCustomers(SQL_QUERIES.getShowCustomers());
        if (!customers.isEmpty()) {
            System.out.println();
            customers.forEach(x -> System.out.println(x.getId() + ". " + x.getName()));
            System.out.println("0. Back");
            int choice = SCANNER.nextInt();
            if (choice == 0){
                login();
            } else {
                Customer cust = customers.stream().filter(x -> x.getId() == choice).findAny().get();
                customer(cust);
            }
        } else {
            login();
        }
    }
    static void customer(Customer customer) {
        System.out.println();
        menuCustomer();
        Map<String, String> rented = db.showRented(SQL_QUERIES.getSelectCustomerCar(), customer);
        int choice = SCANNER.nextInt();
        switch (choice) {
            case 0:
                //Back to manager
                login();
                break;
            case 1:
                //Rent a car
                if (!rented.isEmpty()) {
                    System.out.println("You've already rented a car!");
                    customer(customer);
                } else {
                    Company company = companyList();
                    if ("unknown".equals(company.getName())) {
                        System.out.println("The company list is empty!");
                        customer(customer);
                    } else {
                        List<Car> cars = db.showCarsOfCompany(SQL_QUERIES.getNotRentedCarsByCompany(), company);
                        if (cars.isEmpty()) {
                            System.out.println("No available cars in the '" + company.getName() +"' company.");
                            customer(customer);
                        } else {
                            pickACar(cars, customer);

                        }
                    }
                }
                break;
            case 2:
                //Return a rented car
                if (rented.isEmpty()) {
                    System.out.println("You didn't rent a car!");
                } else {
                    db.returnCar(SQL_QUERIES.getReturnCar(), customer);
                    System.out.println("You've returned a rented car!");
                }
                customer(customer);
                break;
            case 3:
                //My rented Cars
                if (rented.isEmpty()) {
                    System.out.println("You didn't rent a car!");
                } else {
                    rented.forEach((key, value) -> System.out.println("Your rented car:" + "\n" + key + "\n" +
                            "Company:" + "\n" + value));

                }
                customer(customer);
                break;
        }
    }





     static void start(String filename) {
         db = new DB(filename);
         db.dropTable(SQL_QUERIES.getDropIndexCompany());
         db.dropTable(SQL_QUERIES.getDropIndexCar());
         db.create(SQL_QUERIES.getCreateCompany());
         db.create(SQL_QUERIES.getCreateCar());
         db.create(SQL_QUERIES.getCreateCustomer());
         login();
     }

    public static void main(String[] args) {
        // write your code here
        String fileName = "";
        for (int i = 0; i < args.length -1; i++) {
            if (args[i].equalsIgnoreCase("-databaseFileName")) {
                fileName = args[i + 1];
                if (fileName.isEmpty()) {
                    fileName = "carsharing";
                }
            }

        }
        start(fileName);
    }
}