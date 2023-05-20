package com.tuana9a.gemdino;

import com.tuana9a.gemdino.app.App;

public class Main {
    public static void main(final String[] args) {
        new Thread(App.getInstance()).start();
    }
}
