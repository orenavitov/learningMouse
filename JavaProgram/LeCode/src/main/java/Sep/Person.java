package Sep;

public class Person implements Cloneable{

    private int age;
    private String name;
    private Address address;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAdderss() {
        return address;
    }

    public void setAdderss(Address address) {
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person copy = (Person) super.clone();
        copy.address = (Address) this.address.clone();
        return copy;
    }
}
