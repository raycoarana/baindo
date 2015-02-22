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

package com.raycoarana.baindo.app;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.raycoarana.baindo.Baindo;
import com.raycoarana.baindo.BinderDelegate;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Baindo.class, BinderDelegate.class})
public class BaindoActivityTest extends UnitTestSuite {

    @Mock
    private BinderDelegate mBinderDelegate;

    private BaindoActivity mBaindoActivity;

    @Test
    public void shouldInstanceBaindoDelegateOnCreate() {
        givenABinderDelegate();
        givenABaindoActivity();
        whenOnCreate();
        thenANewBinderDelegateIsCreated();
    }

    @Test
    public void shouldInstanceBaindoDelegateOnCreateWithPersistableBundle() {
        givenABinderDelegate();
        givenABaindoActivity();
        whenOnCreateWithPersistableBundle();
        thenANewBinderDelegateIsCreated();
    }

    @Test
    public void shouldDelegateBind() {
        givenABinderDelegate();
        givenABaindoActivity();
        whenOnCreate();
        whenBind();
        thenBindIsDelegated();
    }

    @Test
    public void shouldDelegateUnbind() {
        givenABinderDelegate();
        givenABaindoActivity();
        whenOnCreate();
        whenUnbind();
        thenUnbindIsDelegated();
    }

    @Test
    public void shouldDestroyBaindoOnDestroy() {
        givenABinderDelegate();
        givenABaindoActivity();
        whenOnCreate();
        whenOnDestroy();
        thenBinderDelegateIsDestroyed();
    }

    private void givenABinderDelegate() {
        PowerMockito.mockStatic(Baindo.class);
        PowerMockito.when(Baindo.buildBinderDelegate()).thenReturn(mBinderDelegate);
    }

    private void givenABaindoActivity() {
        mBaindoActivity = new BaindoActivity();
    }

    private void whenOnCreate() {
        mBaindoActivity.onCreate(mock(Bundle.class));
    }

    private void whenOnCreateWithPersistableBundle() {
        mBaindoActivity.onCreate(mock(Bundle.class), mock(PersistableBundle.class));
    }

    private void whenOnDestroy() {
        mBaindoActivity.onDestroy();
    }

    private void whenBind() {
        mBaindoActivity.bind();
    }

    private void whenUnbind() {
        mBaindoActivity.unbind();
    }

    private void thenANewBinderDelegateIsCreated() {
        PowerMockito.verifyStatic();
        Baindo.buildBinderDelegate();
    }

    private void thenBinderDelegateIsDestroyed() {
        verify(mBinderDelegate).onDestroy();
    }

    private void thenBindIsDelegated() {
        verify(mBinderDelegate).bind(mBaindoActivity);
    }

    private void thenUnbindIsDelegated() {
        verify(mBinderDelegate).unbind();
    }

}
