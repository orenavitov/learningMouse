package Sep;

public class test2 {
    public String getString(Address address) {
        if (address == null) {
            return "address getString";
        }
        return address.toString();
    }

    public String getString(Person person) {
        if (person == null) {
            return "person getString";
        }
        return person.toString();
    }
}
