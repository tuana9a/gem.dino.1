package gemdino.utils;

public class Timer {
    public static final long FOREVER = -1L;
    public long lastTime;
    public long deltaTime;
    public long now;

    public Timer() {
        final long currentTimeMillis = System.currentTimeMillis();
        this.lastTime = currentTimeMillis;
        this.now = currentTimeMillis;
    }

    public Timer(final long deltaTime) {
        this();
        this.deltaTime = deltaTime;
    }

    public long now() {
        return this.now = System.currentTimeMillis();
    }

    public void reset() {
        this.lastTime = this.now();
    }

    public boolean isTime() {
        return this.deltaTime != -1L && this.now() - this.lastTime >= this.deltaTime;
    }

    public boolean isTime(final long offset) {
        return this.now() - this.lastTime >= this.deltaTime + offset;
    }

    public boolean isTime(final double ratio) {
        return this.now() - this.lastTime >= this.deltaTime * (1.0 - ratio);
    }
}
