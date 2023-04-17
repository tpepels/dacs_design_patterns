package iterator;

import java.util.List;

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
