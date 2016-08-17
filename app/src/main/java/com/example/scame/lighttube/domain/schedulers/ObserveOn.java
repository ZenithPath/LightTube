package com.example.scame.lighttube.domain.schedulers;

import rx.Scheduler;

public interface ObserveOn {
    Scheduler getScheduler();
}
