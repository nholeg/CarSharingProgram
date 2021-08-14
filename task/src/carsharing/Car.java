package carsharing;

public class Car {
    private int id;
    private String name ;
    private int companyId;

    Car(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;

    }
    Car() {
        this.id = -1;
        this.name = "unknown";
        this.companyId = -1;
    }
    Car(String name) {
        this.id = -1;
        this.name = name;
        this.companyId = -1;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
