package design_patterns.iterator;

// Define the interface for the Iterator
interface MyIterator<T> {
    boolean hasNext();

    T next();
}
