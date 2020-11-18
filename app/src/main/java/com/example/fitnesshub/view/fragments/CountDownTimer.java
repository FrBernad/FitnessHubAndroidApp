package com.example.fitnesshub.view.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CountDownTimer {

    private long remainingTime = 0;
    private long interval = 0;
    private android.os.CountDownTimer countDownTimer;
    private final MutableLiveData<CountDownTimer.Status> countDownTimerStatus = new MutableLiveData<>();

    public void start(long time, long interval) {
        this.interval = interval;
        start(time);
    }

    public LiveData<CountDownTimer.Status> getStatus() {
        return countDownTimerStatus;
    }

    public void pause() {
        cancel();
    }

    public void resume() {
        start(remainingTime);
    }

    public void stop() {
        interval = 0;
        cancel();
    }

    private void start(long time) {
        countDownTimer = new android.os.CountDownTimer(time, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                CountDownTimer.this.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                CountDownTimer.this.onFinish();
            }
        }.start();
    }

    private void cancel() {
        countDownTimer.cancel();
    }

    private void onTick(long millisUntilFinished) {
        remainingTime = millisUntilFinished;
        countDownTimerStatus.setValue(new CountDownTimer.Status(remainingTime / 1000, false));
    }

    private void onFinish() {
        countDownTimerStatus.setValue(new CountDownTimer.Status(0, true));
    }

    public class Status {

        private boolean isFinished;
        private long remainingTime;

        public Status(long remainingTime, boolean isFinished) {
            this.remainingTime = remainingTime;
            this.isFinished = isFinished;
        }

        public long getRemainingTime() {
            return remainingTime;
        }


        public boolean isFinished() {
            return isFinished;
        }
    }
}