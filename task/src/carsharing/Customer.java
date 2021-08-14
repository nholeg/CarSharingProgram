package carsharing;

public class Customer {
    private int id;
    private String name;
    private int rentedCarId;

    Customer() {
        this.id = -1;
        this.name = "unknown";
        this.rentedCarId = -1;
    }
    Customer (int id, String name, int rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }
    Customer(String name) {
        this.id = -1;
        this.name = name;
        this.rentedCarId = -1;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(int rentedCarId) {
        this.rentedCarId = rentedCarId;
    }
}
