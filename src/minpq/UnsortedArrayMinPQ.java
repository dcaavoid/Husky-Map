package minpq;

import jakarta.annotation.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the item-priority pairs in no specific order.
     */
    private final List<PriorityNode<T>> items;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        items = new ArrayList<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        this.items.add(new PriorityNode<>(item, priority));
    }

    @Override
    public boolean contains(T item) {
        for(PriorityNode temp : this.items){
            if(temp.item().equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return this.items.get(getIndex()).item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        T temp = peekMin();
        this.items.remove(getIndex());
        return temp;
    }

    private int getIndex(){
        int index = 0;
        double min_priority = this.items.get(0).priority();

        for(int i = 1; i < this.items.size(); i++){
            if(this.items.get(i).priority() < min_priority){
                min_priority = this.items.get(i).priority();
                index = i;
            }
        }
        return index;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        for(PriorityNode temp : this.items){
            if(temp.item() == item){
                temp.setPriority(priority);
            }
        }
    }

    @Override
    public int size() {
        return this.items.size();
    }
}
