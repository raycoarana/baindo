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

package com.raycoarana.baindo;

import android.os.Handler;

import java.util.concurrent.CountDownLatch;

/**
 * Dispatches work to the UI thread or the ViewModel's Background thread. It detects the
 * current thread and executes or dispatches the work no the requested thread as needed.
 */
public class BaindoWorkDispatcher implements WorkDispatcher {

    private final AndroidProvider mAndroidProvider;
    private final Handler mHandler;
    private Handler mBackgroundHandler;

    final CountDownLatch mBackgroundThreadReady = new CountDownLatch(1);

    public BaindoWorkDispatcher(AndroidProvider androidProvider) {
        mAndroidProvider = androidProvider;
        mHandler = androidProvider.buildHandlerWithMainLooper();
        mBackgroundThread.start();
        waitUntilBackgroundThreadIsReady();
    }

    /**
     * Waits for the background thread to be ready to start receiving events.
     */
    private void waitUntilBackgroundThreadIsReady() {
        try {
            mBackgroundThreadReady.await();
        } catch (InterruptedException e) {
            throw new RuntimeException("Couldn't initialize background thread.", e);
        }
    }

    /**
     * @see com.raycoarana.baindo.WorkDispatcher#doInUIThread(Runnable)
     */
    @Override
    public void doInUIThread(Runnable runnable) {
        if(mAndroidProvider.isCurrentTheMainThread()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    /**
     * @see com.raycoarana.baindo.WorkDispatcher#doInBackgroundThread(Runnable)
     */
    @Override
    public void doInBackgroundThread(Runnable runnable) {
        if(mAndroidProvider.isCurrentTheMainThread()) {
            mBackgroundHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    @Override
    public void onDestroy() {
        mBackgroundHandler.getLooper().quit();
    }

    /**
     * Executes a loop that process all notifications sent to the ViewModel.
     */
    final Thread mBackgroundThread = new Thread(new Runnable() {

        @Override
        public void run() {
            mBackgroundHandler = mAndroidProvider.buildHandler();
            mBackgroundThreadReady.countDown();
            mAndroidProvider.loop();
        }

    });

}
