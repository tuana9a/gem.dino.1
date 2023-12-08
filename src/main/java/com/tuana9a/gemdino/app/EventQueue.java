package com.tuana9a.gemdino.app;

import com.tuana9a.gemdino.interfaces.EventHandler;

import java.util.LinkedList;
import java.util.Queue;

public class EventQueue {
    private static final EventQueue instance = new EventQueue();
    private final Queue<EventHandler> queue;

    private EventQueue() {
        this.queue = new LinkedList<>();
    }

    public static EventQueue getInstance() {
        return instance;
    }

    public synchronized EventHandler pop() {
        return this.queue.poll();
    }

    public synchronized void push(EventHandler event) {
        this.queue.offer(event);
    }
}
