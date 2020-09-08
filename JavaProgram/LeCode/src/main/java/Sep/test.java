package Sep;

public class test {

    public static void main(String[] args) {
        Person person = new Person();
        person.setName("aaaa");
        person.setAge(1);
        Address address = new Address();
        address.setCountry("CN");
        address.setSheng("jiangsu");
        person.setAdderss(address);
        try {
            Person another = (Person) person.clone();
//            System.out.println(person == another);

            System.out.println("before");
            System.out.println(person.getName().hashCode());
            System.out.println(another.getName().hashCode());
            System.out.println("after");
            System.out.println(person.getName().hashCode());
            System.out.println(another.getName().hashCode());
            System.out.println(person.getName() == another.getName());
//            System.out.println("person name : " + person.getName());
//            System.out.println("another name : " + another.getName());
//            System.out.println("person age : " + another.getAge());
        } catch (CloneNotSupportedException e) {
            System.out.println("the Person not support clone!");
        }

    }
}
