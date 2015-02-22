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

package com.raycoarana.baindo.renderer;

import android.view.LayoutInflater;

import com.pedrogomez.renderers.AdapteeCollection;
import com.raycoarana.baindo.BinderDelegate;
import com.raycoarana.baindo.UnbindableCollectorProvider;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class BinderRendererAdapterTest extends UnitTestSuite {

    @Mock
    private LayoutInflater mLayoutInflater;

    @Mock
    private BinderRendererBuilder<Object> mBinderRendererBuilder;

    @Mock
    private AdapteeCollection<Object> mAdapteeCollection;

    @Mock
    private BinderDelegate mBinderDelegate;

    @Mock
    private UnbindableCollectorProvider mUnbindableCollectorProvider;

    private BinderRendererAdapter<Object> mBinderRendererAdapter;

    @Test
    public void shouldDestroyRendererBuilderWhenUnbind() {
        givenBinderRendererAdapter();
        whenUnbind();
        thenRendererBuilderIsDestroyed();
    }

    @Test
    public void shouldInjectRendererBuilder() {
        givenBinderRendererAdapter();
        whenInject();
        thenRendererBuilderIsInjected();
    }

    private void givenBinderRendererAdapter() {
        mBinderRendererAdapter = new BinderRendererAdapter<>(mLayoutInflater, mBinderRendererBuilder, mAdapteeCollection);
    }

    private void whenUnbind() {
        mBinderRendererAdapter.unbind();
    }

    private void whenInject() {
        mBinderRendererAdapter.injectBinderDelegate(mBinderDelegate);
        mBinderRendererAdapter.injectUnbindableCollectorProvider(mUnbindableCollectorProvider);
    }

    private void thenRendererBuilderIsDestroyed() {
        verify(mBinderRendererBuilder).destroy();
    }

    private void thenRendererBuilderIsInjected() {
        verify(mBinderRendererBuilder).injectDelegate(mBinderDelegate);
        verify(mBinderRendererBuilder).injectUnbindableCollectorProvider(mUnbindableCollectorProvider);
    }

}
