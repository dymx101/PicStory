package org.menagerie.locks;

/**
 * Acts as a synchronous, mutable, thread-safe Object holder, for passing
 * information between two threads in a testable and consistent way.
 *
 * @author Scott Fines
 *         Date: Apr 26, 2011
 *         Time: 10:22:27 PM
 */
public class StateHolder<T> {
    private T item;

    public synchronized void set(T item){
        this.item = item;
    }

    public synchronized T get(){
        return item;
    }
}
