package ListTest;

public class MihSortLinkList<E extends Comparable<E>> {
    private Node head = new Node(null);

    private Node tail = head;

    private int lenght = 0;

    public MihSortLinkList() {

    }


    private static class Node<E extends Comparable<E>> {
        private E element;
        private Node next;

        public Node(E element) {
            this.element = element;
        }


        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }

    public int size() {
        return lenght;
    }

    public MihSortLinkList create(E... elements) {
        for(E element : elements) {
            this.add(element);
        }
        return this;
    }

    // 在尾部添加；
    // 排序， 从小到大
    public MihSortLinkList add(E element) {
        Node node = new Node(element);
        if (head.next == null) {
            head.next = node;
            tail = node;
        } else {
            Node pre = head;
            Node current = head.next;
            while (current != null && current.element.compareTo(element) <= 0) {
                pre = current;
                current = current.next;
            }
            pre.next = node;
            node.next = current;
            if (current == null) {
                tail = node;
            }
        }
        lenght ++;
        return this;
    }

    // 在第一个位置添加
    public MihSortLinkList addFirst(E element) {
        Node node = new Node(element);
        if(head.next == null) {
            tail = node;
        }
        head.next = node;
        lenght ++;
        return this;
    }

    // 移除第一个节点
    public MihSortLinkList removeFirst() throws Exception {
        if (lenght == 0) {
            throw new Exception("the list is empty");
        } else {
            Node firstNode = head.next;
            head.next = firstNode.next;
            if (firstNode.next == null) {
                tail = head;
            }
            firstNode = null;
            lenght --;
        }
        return this;
    }

    public MihSortLinkList remove(E element) throws Exception {
        if (lenght == 0) {
            throw new Exception("the list is empty");
        } else {
            Node current = head;
            Node next = head.next;
            while (next != null) {
                if (next.getElement().equals(element)) {
                    current.next = next.next;
                    if (next.next == null) {
                        tail = head;
                    }
                    next = null;
                    lenght --;
                    break;
                }
                current = next;
                next = current.next;
            }
        }
        return this;
    }

    public E getLast() {
        if (lenght == 0) {
            return null;
        } else {
            return (E)tail.element;
        }
    }

    public E getFirst() {
        if(lenght == 0) {
            return null;
        } else {
            return (E)head.next.element;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        if (lenght != 0) {
            Node current = head.next;
            while (current != null) {
                stringBuilder.append(current.toString()).append(",");
                current = current.next;
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
