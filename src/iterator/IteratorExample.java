package iterator;

import java.util.ArrayList;
import java.util.List;

// Example usage
public class IteratorExample {
    public static void main(String[] args) {
        List<String> myList = new ArrayList<>();
        myList.add("Apple");
        myList.add("Banana");
        myList.add("Cherry");

        MyIterator<String> iterator = new MyListIterator<>(myList);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("Iterating in reverse...");
        MyIterator<String> reverseIterator = new MyReverseListIterator<>(myList);
        while (reverseIterator.hasNext()) {
            System.out.println(reverseIterator.next());
        }
    }
}
