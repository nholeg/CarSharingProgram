package carsharing;

public class Company {
    private int id;
    private String name;

    Company() {
        this.id = -1;
        this.name = "unknown";
    }
    Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    Company(String name) {
        this.id = -1;
        this.name = name;
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
}
