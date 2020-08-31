package ListTest;

public class Test {
    public static void main(String[] args) {
        MihSortLinkList mihLinkList = new MihSortLinkList();
        try {

            mihLinkList.create(4,2313, 21,31, 4213,1 ,4);
            System.out.println("size : " + mihLinkList.size());
            System.out.println("Fist : " + mihLinkList.getFirst());
            System.out.println("Last : " + mihLinkList.getLast());
            System.out.println(mihLinkList.toString());


            mihLinkList.add(2);
            System.out.println("size : " + mihLinkList.size());
            System.out.println("Fist : " + mihLinkList.getFirst());
            System.out.println("Last : " + mihLinkList.getLast());
            System.out.println(mihLinkList.toString());

            mihLinkList.add(3);
            System.out.println("size : " + mihLinkList.size());
            System.out.println("Fist : " + mihLinkList.getFirst());
            System.out.println("Last : " + mihLinkList.getLast());
            System.out.println(mihLinkList.toString());

            mihLinkList.remove(2);
            System.out.println("size : " + mihLinkList.size());
            System.out.println("Fist : " + mihLinkList.getFirst());
            System.out.println("Last : " + mihLinkList.getLast());
            System.out.println(mihLinkList.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
