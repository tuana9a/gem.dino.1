// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.ui;

import java.util.Arrays;
import java.awt.Graphics;
import java.util.Comparator;
import java.util.ArrayList;

public class UiManager
{
    private final ArrayList<UiComponent> uiComponents;
    private static Comparator<UiComponent> stateCompare;
    
    public UiManager() {
        this.uiComponents = new ArrayList<UiComponent>();
    }
    
    public void updateAll() {
        this.uiComponents.sort(UiManager.stateCompare);
        for (int i = 0; i < this.uiComponents.size(); ++i) {
            final UiComponent o = this.uiComponents.get(i);
            o.update();
        }
        this.checkMouseOverAll();
    }
    
    public void renderAll(final Graphics g) {
        for (int i = 0; i < this.uiComponents.size(); ++i) {
            final UiComponent o = this.uiComponents.get(i);
            if (!o.isHidden()) {
                o.render(g);
            }
        }
    }
    
    public void checkMouseOverAll() {
        for (int i = 0; i < this.uiComponents.size(); ++i) {
            final UiComponent o = this.uiComponents.get(i);
            if (!o.isInActive()) {
                o.checkMouseHover();
            }
        }
    }
    
    public void checkMousePressAll() {
        for (int i = 0; i < this.uiComponents.size(); ++i) {
            final UiComponent o = this.uiComponents.get(i);
            if (!o.isInActive()) {
                o.checkMousePress();
            }
        }
    }
    
    public void checkMouseReleaseAll() {
        for (int i = 0; i < this.uiComponents.size(); ++i) {
            final UiComponent o = this.uiComponents.get(i);
            if (!o.isInActive()) {
                o.checkMouseRelease();
            }
        }
    }
    
    public void updateAllWhenScreenResize() {
        for (int i = 0; i < this.uiComponents.size(); ++i) {
            final UiComponent o = this.uiComponents.get(i);
            o.updateWhenScreenResize();
        }
    }
    
    public void addAllUiComponent(final UiComponent... os) {
        this.uiComponents.addAll(Arrays.asList(os));
    }
    
    public void removeUiComponent(final UiComponent o) {
        this.uiComponents.remove(o);
    }
    
    static {
        UiManager.stateCompare = new Comparator<UiComponent>() {
            @Override
            public int compare(final UiComponent o1, final UiComponent o2) {
                if (o1.isInActive() && o2.isInActive()) {
                    return 0;
                }
                if (o1.isInActive()) {
                    return -1;
                }
                if (o2.isInActive()) {
                    return 1;
                }
                return 0;
            }
        };
    }
}
