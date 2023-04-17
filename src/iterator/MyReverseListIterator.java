package iterator;

import java.util.List;

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
