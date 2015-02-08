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

import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Handler.class, Looper.class})
public class BaindoWorkDispatcherTest extends UnitTestSuite {

    private static final int NEVER = 0;
    private static final int ONCE = 1;
    private static final long ONE_SECOND = 1000;

    @Mock
    private AndroidProvider mAndroidProvider;

    @Mock
    private Runnable mSomeWork;

    private Looper mBackgroundThreadLooper = PowerMockito.mock(Looper.class);
    private Handler mBackgroundThreadHandler = PowerMockito.mock(Handler.class);
    private Handler mUIThreadHandler = PowerMockito.mock(Handler.class);

    private BaindoWorkDispatcher mBaindoWorkDispatcher;
    private Thread mSomeThread;
    private boolean mKeepLooping = true;

    @Test
    public void shouldInitializeTheBackgroundThread() {
        givenABinderDispatcher();
        thenBackgroundThreadIsInitialized();
    }

    @Test
    public void shouldAbortInitializeOfBackgroundThreadIfInterrupted() {
        givenThatBinderDispatcherIsWaitingToBeInitialized();
        whenThreadIsInterrupted();
        thenThreadFinishTheInitialization();
    }

    @Test(timeout = ONE_SECOND)
    public void shouldStopBackgroundThreadOnDestroy() {
        givenThatBackgroundThreadLooperKeepsLooping();
        givenALooperForBackgroundThread();
        givenABinderDispatcher();
        whenOnDestroy();
        thenBackgroundThreadIsFinished();
    }

    @Test
    public void shouldDispatchWorkToMainThreadWhenOnBackgroundThread() {
        givenThatCurrentThreadIsBackgroundThread();
        givenABinderDispatcher();
        whenDoInUIThread();
        thenWorkIsDispatchedToMainThread();
    }

    @Test
    public void shouldExecuteWorkDirectlyWhenOnBackgroundThread() {
        givenThatCurrentThreadIsBackgroundThread();
        givenABinderDispatcher();
        whenDoInBackgroundThread();
        thenWorkIsExecutedDirectly();
    }

    @Test
    public void shouldDispatchWorkToBackgroundThreadWhenOnMainThread() {
        givenThatCurrentThreadIsMainThread();
        givenABinderDispatcher();
        whenDoInBackgroundThread();
        thenWorkIsDispatchedToBackgroundThread();
    }

    @Test
    public void shouldExecuteWorkDirectlyWhenOnMainThread() {
        givenThatCurrentThreadIsMainThread();
        givenABinderDispatcher();
        whenDoInUIThread();
        thenWorkIsExecutedDirectly();
    }

    private void givenABinderDispatcher() {
        when(mAndroidProvider.buildHandlerWithMainLooper()).thenReturn(mUIThreadHandler);
        when(mAndroidProvider.buildHandler()).thenReturn(mBackgroundThreadHandler);
        givenAWaitingForBackgroundThreadBinderDispatcher();
    }

    @SuppressWarnings({"Anonymous2MethodRef", "Convert2Lambda"})
    private void givenThatBinderDispatcherIsWaitingToBeInitialized() {
        mSomeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                givenAWaitingForBackgroundThreadBinderDispatcher();
            }
        });
        mSomeThread.start();
    }

    @SuppressWarnings({"InfiniteLoopStatement", "StatementWithEmptyBody", "Convert2Lambda"})
    private void givenThatBackgroundThreadLooperKeepsLooping() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                mKeepLooping = false;
                return null;
            }
        }).when(mBackgroundThreadLooper).quit();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                while (mKeepLooping) {
                    //Do nothing
                }
                return null;
            }
        }).when(mAndroidProvider).loop();
    }

    private void givenALooperForBackgroundThread() {
        when(mBackgroundThreadHandler.getLooper()).thenReturn(mBackgroundThreadLooper);
    }

    private void givenAWaitingForBackgroundThreadBinderDispatcher() {
        mBaindoWorkDispatcher = new BaindoWorkDispatcher(mAndroidProvider);
    }

    private void givenThatCurrentThreadIsBackgroundThread() {
        mockIsCurrentTheMainThreadTo(false);
    }

    private void givenThatCurrentThreadIsMainThread() {
        mockIsCurrentTheMainThreadTo(true);
    }

    private void mockIsCurrentTheMainThreadTo(boolean value) {
        when(mAndroidProvider.isCurrentTheMainThread()).thenReturn(value);
    }

    private void whenThreadIsInterrupted() {
        mSomeThread.interrupt();
    }

    private void whenOnDestroy() {
        mBaindoWorkDispatcher.onDestroy();
    }

    private void whenDoInUIThread() {
        mBaindoWorkDispatcher.doInUIThread(mSomeWork);
    }

    private void whenDoInBackgroundThread() {
        mBaindoWorkDispatcher.doInBackgroundThread(mSomeWork);
    }

    private void thenBackgroundThreadIsInitialized() {
        verify(mAndroidProvider).loop();
    }

    private void thenThreadFinishTheInitialization() {
        try {
            mSomeThread.join(ONE_SECOND);
            assertFalse(mSomeThread.isAlive());
        } catch (InterruptedException e) {
            throw new IllegalStateException("BinderDispatcher doesn't finish it's initialization");
        }
    }

    private void thenBackgroundThreadIsFinished() {
        try {
            mBaindoWorkDispatcher.mBackgroundThread.join(ONE_SECOND);
            assertFalse(mBaindoWorkDispatcher.mBackgroundThread.isAlive());
        } catch (InterruptedException e) {
            throw new IllegalStateException("BinderDispatcher doesn't finish background thread.");
        }
    }

    private void thenWorkIsDispatchedToBackgroundThread() {
        verifyDispatch(ONCE, NEVER, NEVER);
    }

    private void thenWorkIsExecutedDirectly() {
        verifyDispatch(NEVER, NEVER, ONCE);
    }

    private void thenWorkIsDispatchedToMainThread() {
        verifyDispatch(NEVER, ONCE, NEVER);
    }

    private void verifyDispatch(int backgroundThreadExecutions, int uiThreadExecutions, int directExecutions) {
        verify(mBackgroundThreadHandler, times(backgroundThreadExecutions)).post(mSomeWork);
        verify(mUIThreadHandler, times(uiThreadExecutions)).post(mSomeWork);
        verify(mSomeWork, times(directExecutions)).run();
    }

}
