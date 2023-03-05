package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class OptimizedHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of item-priority pairs.
     */
    private final List<PriorityNode<T>> items;
    /**
     * {@link Map} of each item to its associated index in the {@code items} heap.
     */
    private final Map<T, Integer> itemToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        items = new ArrayList<>();
        itemToIndex = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        PriorityNode node = new PriorityNode<>(item, priority);
        this.items.add(node);
        this.itemToIndex.put(item, node.hashCode());
        swim(size() - 1);
    }

    @Override
    public boolean contains(T item) {
        return itemToIndex.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return this.items.get(1).item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        T temp = peekMin();
        swap(0, size() - 1);
        this.items.remove(size() - 1);
        sink(0);
        return temp;
    }

//    private int getIndex(){
//        int index = 0;
//        double min_priority = this.items.get(0).priority();
//
//        for(int i = 1; i < this.items.size(); i++){
//            if(this.items.get(i).priority() < min_priority){
//                min_priority = this.items.get(i).priority();
//                index = i;
//            }
//        }
//        return index;
//    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        this.items.get(this.itemToIndex.get(item)).setPriority(priority);
    }

    @Override
    public int size() {
        return this.items.size();
    }

    private void swim(int index){
        int parent = (index - 1) / 2;
        while(accessible(parent) && this.items.get(index).priority() < this.items.get(parent).priority()){
            swap(index, parent);
            index = parent;
            parent = (parent - 1) / 2; // ?
        }
    }

    private void sink(int index){
        int child = min((index + 1) * 2 - 1, (index + 1) * 2); // ?
        while(accessible(child) && this.items.get(index).priority() > this.items.get(child).priority()){
            swap(index, child);
            index = child;
            child = min((index + 1) * 2 - 1, (index + 1) * 2);
        }
    }

    private boolean accessible(int index){
        return 1 <= index && index <= size();
    }

    private void swap(int index1, int index2){
        PriorityNode temp = this.items.get(index1);
        this.items.set(index1, this.items.get(index2));
        this.items.set(index2, temp);
    }

    private int min(int index1, int index2){
        if(!accessible(index1) && !accessible(index2)) return 0;
        else if(accessible((index1))
                && (!accessible(index2)
                || this.items.get(index1).priority() < this.items.get(index2).priority())) return index1;
        else return index2;
    }
}
