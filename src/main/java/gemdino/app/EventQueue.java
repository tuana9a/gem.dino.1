package gemdino.app;

import gemdino.interfaces.EventHandler;

import java.util.LinkedList;
import java.util.Queue;

public class EventQueue {
    private final Queue<EventHandler> queue;

    public EventQueue() {
        this.queue = new LinkedList<>();
    }

    public synchronized EventHandler pop() {
        return this.queue.poll();
    }

    public synchronized void push(EventHandler event) {
        this.queue.offer(event);
    }
}
