// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.utility;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import com.tuana9a.App;
import java.io.File;

public class Audio
{
    String fileName;
    File f;
    
    public Audio(final String fileName) {
        try {
            this.fileName = fileName;
            this.f = new File(App.class.getResource(fileName).toURI());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void play() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(Audio.this.f));
                    clip.start();
                    Thread.sleep(1000L);
                    clip.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
