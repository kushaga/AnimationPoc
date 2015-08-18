package com.example.akosha.sample1.animationpoc.tablayout;

/**
 * Created by kushagarlall on 8/18/15.
 */
class MathUtils {
    MathUtils() {
    }

    static int constrain(int amount, int low, int high) {
        return amount < low?low:(amount > high?high:amount);
    }

    static float constrain(float amount, float low, float high) {
        return amount < low?low:(amount > high?high:amount);
    }
}
