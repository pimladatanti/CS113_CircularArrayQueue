import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class CircularArrayQueue<E> extends AbstractQueue<E> implements Queue<E> {
    private int front;
    private int rear;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;

    private E[] theData;

    /**
     * Construct a queue with default initial capacity
     */
    public CircularArrayQueue() {
        this(DEFAULT_CAPACITY);
    }


    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Construct queue with specified initial capacity
     * @param initCapacity
     */
    public CircularArrayQueue(int initCapacity) {
        capacity = initCapacity;
        theData = (E[]) new Object[capacity];
        front = 0;
        rear = capacity - 1;
        size = 0;
    }

    /**
     * Insert item at rear of queue
     * @post item added to rear
     * @param item (element to add)
     * @return true
     */
    @Override
    public boolean offer(E item) {
        if (size == capacity) {
            reallocate();
        }
        size++;
        rear = (rear + 1) % capacity;
        theData[rear] = item;
        return true;
    }

    /**
     * Returns item at front of queue without removing it
     * @return Item at front of queue or null if queue is empty
     */
    @Override
    public E peek() {
        if (size == 0)
            return null;
        else
            return theData[front];
    }

    @Override
    public E element() {
        if (size == 0)
            throw new NoSuchElementException();
        else return theData[front];
    }

    /**
     * Removes entry at front of queue and returns if queue not empty
     * @post front references 2nd item in queue
     * @return item removed from front, or null if unsuccessful
     */
    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        E result = theData[front];
        front = (front + 1) % capacity;
        size--;
        return result;
    }

    //Private methods
    /**
     * Double the capacity and reallocate data
     * @pre array filled to capacity
     * @post capacity doubled and first half of array filled with data
     */
    private void reallocate() {
        int newCapacity = 2 * capacity;
        E[] newData = (E[]) new Object[newCapacity];
        int j = front;
        for (int i = 0; i < size; i++) {
            newData[i] = theData[j];
            j = (j+1) % capacity;
        }
        front = 0;
        rear = size - 1;
        capacity = newCapacity;
        theData = newData;
    }

    private class Iter implements Iterator<E> {
        private int index;
        private int count = 0;

        /**
         * Initializes Iter object to reference first queue element
         */
        public Iter() {
            index = front;
        }

        /**
         * @return true if there are more elements in the queue to access
         */
        @Override
        public boolean hasNext() {
            return count < size;
        }

        /** Returns the next element in queue
         * @pre index references next element to access
         * @post index and count are incremented
         * @return element with subscript index
         */
        @Override
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            E returnValue  = theData[index];
            index = (index + 1) % capacity;
            count++;
            return returnValue;
        }

        /** Remove the item accessed by the Iter object - not implemented*/
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }







}
