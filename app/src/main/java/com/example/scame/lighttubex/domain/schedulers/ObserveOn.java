package com.example.scame.lighttubex.domain.schedulers;

import rx.Scheduler;

public interface ObserveOn {
    Scheduler getScheduler();
}
