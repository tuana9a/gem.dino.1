package gemdino.screen;

import gemdino.ui.UiManager;
import gemdino.utils.Timer;
import lombok.Getter;

public class Screen {
    public static final long DEFAULT_REFRESH_TIME = 10L;
    protected Timer refreshTimer;
    @Getter
    protected UiManager uiManager;

    public Screen() {
        this.refreshTimer = new Timer(DEFAULT_REFRESH_TIME);
        this.uiManager = new UiManager();
    }

    public void initUi() {

    }

    public void changeCursor() {
    }

    public void changeFont() {
    }

    public void update() {

    }

    public void render() {

    }
}
