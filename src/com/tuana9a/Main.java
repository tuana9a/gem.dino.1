package com.tuana9a;

import com.tuana9a.app.App;

public class Main {
    public static void main(final String[] args) {
        new Thread(App.getInstance()).start();
    }
}
