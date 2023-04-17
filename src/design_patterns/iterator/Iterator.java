package design_patterns.iterator;

import java.util.*;

// Define the interface for the Iterator
interface MyIterator<T> {
    boolean hasNext();

    T next();
}

// Define the Aggregate interface
interface MyAggregate<T> {
    MyIterator<T> createIterator();
}

// Define the ConcreteAggregate class
class MyList<T> implements MyAggregate<T> {
    private List<T> items = new ArrayList<>();

    public void addItem(T item) {
        items.add(item);
    }

    public MyIterator<T> createIterator() {
        return new MyListIterator<>(items);
    }
}

// Define the ConcreteIterator class for the MyList
class MyListIterator<T> implements MyIterator<T> {
    private List<T> items;
    private int position = 0;

    public MyListIterator(List<T> items) {
        this.items = items;
    }

    public boolean hasNext() {
        return position < items.size();
    }

    public T next() {
        T item = items.get(position);
        position++;
        return item;
    }
}

// Define another ConcreteIterator class for the MyList that iterates in reverse
class MyReverseListIterator<T> implements MyIterator<T> {
    private List<T> items;
    private int position;

    public MyReverseListIterator(List<T> items) {
        this.items = items;
        this.position = items.size() - 1;
    }

    public boolean hasNext() {
        return position >= 0;
    }

    public T next() {
        T item = items.get(position);
        position--;
        return item;
    }
}
