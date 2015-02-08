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

import com.raycoarana.baindo.test.IntegrationTestSuite;

import org.junit.Test;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Config(emulateSdk = 18)
public class AndroidProviderIT extends IntegrationTestSuite {

    private static final long ONE_SECOND = 1000;
    private CountDownLatch mCountDownLatch = new CountDownLatch(1);
    private AndroidProvider mAndroidProvider;
    private Handler mHandler;
    private Thread mThread;
    private boolean mIsMainThread;
    private Exception mException;

    @Test
    public void shouldCreateAMainLooperHandler() {
        givenAnAndroidProvider();
        whenBuildHandlerWithMainLooper();
        thenAHandlerWithMainLooperIsCreated();
    }

    @Test
    public void shouldBeInMainThread() {
        givenAnAndroidProvider();
        assertTrue(mAndroidProvider.isCurrentTheMainThread());
    }

    @Test(timeout = ONE_SECOND)
    public void shouldCreateAHandler() throws Exception {
        givenAnAndroidProvider();
        givenAThreadThatGetsAHandlerAndLoop();
        whenStartThreadAndQuitLooper();
        thenThreadStops();
    }

    private void givenAThreadThatGetsAHandlerAndLoop() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mHandler = mAndroidProvider.buildHandler();
                    mCountDownLatch.countDown();
                    mIsMainThread = mAndroidProvider.isCurrentTheMainThread();
                    mAndroidProvider.loop();
                } catch (Exception ex) {
                    mException = ex;
                }
            }
        });
    }

    private void whenStartThreadAndQuitLooper() throws Exception {
        mThread.start();
        mCountDownLatch.await(ONE_SECOND / 2, TimeUnit.MILLISECONDS);
        if(mException != null) {
            throw mException;
        }
        mHandler.getLooper().quit();
    }

    private void thenThreadStops() {
        try {
            mThread.join(ONE_SECOND);
        } catch (InterruptedException e) {
            //Nothing to do
        }
        assertFalse(mThread.isAlive());
        assertFalse(mIsMainThread);
    }

    private void givenAnAndroidProvider() {
        mAndroidProvider = new AndroidProvider();
    }

    private void whenBuildHandlerWithMainLooper() {
        mHandler = mAndroidProvider.buildHandlerWithMainLooper();
    }

    private void thenAHandlerWithMainLooperIsCreated() {
        assertThat(Looper.getMainLooper(), is(mHandler.getLooper()));
    }

}
