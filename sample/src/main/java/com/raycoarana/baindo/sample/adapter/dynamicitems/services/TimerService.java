/*
 * Copyright 2015 Rayco Araña
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.raycoarana.baindo.sample.adapter.dynamicitems.services;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService {

    private static final long NO_DELAY = 0;
    private static final long OND_SECOND = 1000;

    private static TimerService sTimerService;

    private Timer mTimer;

    public static synchronized TimerService getInstance() {
        if(sTimerService == null) {
            sTimerService = new TimerService();
        }
        return sTimerService;
    }

    private TimerService() {
        mTimer = new Timer();
    }

    public TimerTask create(final Runnable runnable) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        mTimer.schedule(timerTask, NO_DELAY, OND_SECOND);
        return timerTask;
    }

}
