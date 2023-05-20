package com.tuana9a.gemdino.app;

import com.tuana9a.gemdino.interfaces.ActionEvent;

import java.util.LinkedList;
import java.util.Queue;

public class ActionQueue {
    private static final ActionQueue instance = new ActionQueue();
    private final Queue<ActionEvent> queue;

    private ActionQueue() {
        this.queue = new LinkedList<>();
    }

    public static ActionQueue getInstance() {
        return instance;
    }

    public synchronized ActionEvent pop() {
        return this.queue.poll();
    }

    public synchronized void push(ActionEvent event) {
        this.queue.offer(event);
    }
}
