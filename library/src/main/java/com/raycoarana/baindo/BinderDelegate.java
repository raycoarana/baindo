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
import android.os.Looper;

import java.util.ArrayList;

/**
 * BinderDelegate let you introduce Baindo in your Activities and Fragments in four simple steps.
 *
 * First make your Activity/Fragment implements BindableSource.
 * Then Instantiate a BinderDelegate in the onCreate() method of your Activity/Fragment.
 * Override onDestroy() method and all binderDelegate.onDestroy().
 * Finally create a method called bind() that calls binderDelegate.bind(this):
 *
 * public Binder bind() {
 *     return mBinderDelegate.bind(this);
 * }
 *
 * @see com.raycoarana.baindo.app.BaindoActivity
 * @see com.raycoarana.baindo.app.BaindoFragment
 *
 */
public class BinderDelegate {

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Handler mBackgroundHandler;
    private ArrayList<Unbindable> mUnbindables;

    public BinderDelegate() {
        mUnbindables = new ArrayList<>();
        mBackgroundThread.start();
        waitUntilBackgroundThreadIsReady();
    }

    /**
     * Waits for the background thread to be ready to start receiving events.
     */
    private void waitUntilBackgroundThreadIsReady() {
        synchronized (mBackgroundThread) {
            while(mBackgroundHandler == null) {
                try {
                    mBackgroundThread.wait();
                } catch (InterruptedException e) {
                    //Do nothing
                }
            }
        }
    }

    /**
     * Creates a binder, it's recommended not to use this method directly for your bindings, instead
     * create a bind() method in your Activity/Fragment that calls this method passing the
     * Activity/Fragment as a BindableSource.
     */
    public Binder bind(BindableSource bindableSource) {
        BinderImpl binder = new BinderImpl(bindableSource, mWorkDispatcher, this);
        mUnbindables.add(binder);
        return binder;
    }

    public Binder bindRenderer(BindableSource bindableSource) {
        return new BinderImpl(bindableSource, mWorkDispatcher, this);
    }

    /**
     * Unbinds all binds created by this delegate and exits the background thread that processes
     * all event notifications to the ViewModel.
     *
     * Call this method in the onDestroy() method of your Activity/Fragment.
     */
    public void onDestroy() {
        unbind();
        mBackgroundHandler.getLooper().quit();
    }

    public void unbind() {
        for(Unbindable unbindable : mUnbindables) {
            unbindable.unbind();
        }
        mUnbindables.clear();
    }

    /**
     * Executes a loop that process all notifications sent to the ViewModel.
     */
    private final Thread mBackgroundThread = new Thread(new Runnable() {

        @Override
        public void run() {
            synchronized (mBackgroundThread) {
                Looper.prepare();
                mBackgroundHandler = new Handler(Looper.myLooper());
                mBackgroundThread.notify();
            }
            Looper.loop();
        }

    });

    /**
     * Dispatches work to the UI thread or the ViewModel's Background thread. It detects the
     * current thread and executes or dispatches the work no the requested thread as needed.
     */
    private final WorkDispatcher mWorkDispatcher = new WorkDispatcher() {

        /**
         * @see com.raycoarana.baindo.WorkDispatcher#doInUIThread(Runnable)
         */
        @Override
        public void doInUIThread(Runnable runnable) {
            if(Looper.myLooper() == Looper.getMainLooper()) {
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
            if(Looper.myLooper() == Looper.getMainLooper()) {
                mBackgroundHandler.post(runnable);
            } else {
                runnable.run();
            }
        }

    };
}
