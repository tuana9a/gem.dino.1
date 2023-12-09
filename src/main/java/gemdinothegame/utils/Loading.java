package gemdinothegame.utils;

import java.util.Arrays;
import java.util.ArrayList;

public class Loading {
    public int currentProgress;
    public int fullProcess;
    public boolean loadSuccess;
    public long deltaTime;
    public ArrayList<String> errorMessages;

    public Loading(final int full, final int current, final long time) {
        this.errorMessages = new ArrayList<>();
        this.loadSuccess = true;
        this.fullProcess = full;
        this.currentProgress = current;
        this.deltaTime = time;
    }

    public void update() {
        ++this.currentProgress;
        if (this.currentProgress >= this.fullProcess) {
            this.currentProgress = this.fullProcess;
        }
        try {
            Thread.sleep(this.deltaTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onError(final String s) {
        this.loadSuccess = false;
        this.addErrorMessages(s);
    }

    public void addErrorMessages(final String... s) {
        this.errorMessages.addAll(Arrays.asList(s));
    }
}
