package datastructures;

public class MyLinkedList<T> {
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node head;
    private int size = 0;

    public void add(T data) {
        Node newNode = new Node(data);
        if (head == null)
            head = newNode;
        else {
            Node temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newNode;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node temp = head;
        for (int i = 0; i < index; i++)
            temp = temp.next;
        return temp.data;
    }

    public void set(int index, T data) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node temp = head;
        for (int i = 0; i < index; i++)
            temp = temp.next;
        temp.data = data;
    }

    public void remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        if (index == 0) {
            head = head.next;
        } else {
            Node prev = head;
            for (int i = 0; i < index - 1; i++)
                prev = prev.next;
            prev.next = prev.next.next;
        }
        size--;
    }

    public int size() {
        return size;
    }

    public void forEach(MyConsumer<T> consumer) {
        Node temp = head;
        while (temp != null) {
            consumer.accept(temp.data);
            temp = temp.next;
        }
    }

    public interface MyConsumer<T> {
        void accept(T item);
    }
}
