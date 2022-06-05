package org.example;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

public class IndicatorAnimation extends AnimationTimer {

    private static final Random RANDOM = new Random();

    private final ProgressIndicator indicator;
    private final Stage stage;
    private final Parent nextRoot;

    private Long startTime;
    private Double maxDelta = 10000D;

    public IndicatorAnimation(ProgressIndicator indicator, Stage stage, Parent nextRoot) {
        this.indicator = indicator;
        this.stage = stage;
        this.nextRoot = nextRoot;
    }

    @Override
    public void handle(long now) {
        long time = System.currentTimeMillis();

        if (startTime == null) {
            startTime = time;
        }

        long delta = time - startTime;
        double percent = delta / maxDelta;

        if (percent > 0.3 && percent < 0.65) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (percent > 1) {
            stage.setScene(new Scene(nextRoot, 600, 400));
            stop();
        } else {
            indicator.setProgress(percent);
        }
    }

}
