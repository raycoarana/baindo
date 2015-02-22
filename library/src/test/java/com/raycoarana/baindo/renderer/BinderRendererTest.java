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
import android.view.View;
import android.view.ViewGroup;

import com.raycoarana.baindo.BinderDelegate;
import com.raycoarana.baindo.UnbindableCollector;
import com.raycoarana.baindo.UnbindableCollectorProvider;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BinderRendererTest extends UnitTestSuite {

    private static final int SOME_VIEW_ID = 42;
    private static final Object SOME_CONTENT = new Object();
    @Mock
    private UnbindableCollector mFirstUnbindableCollector;

    @Mock
    private UnbindableCollector mSecondUnbindableCollector;

    @Mock
    private UnbindableCollectorProvider mUnbindableCollectorProvider;

    @Mock
    private BinderDelegate mBinderDelegate;

    @Mock
    private View mView;

    @Mock
    private LayoutInflater mLayoutInflater;

    @Mock
    private ViewGroup mViewGroup;

    private BinderRenderer mBinderRenderer;

    private Object mBinderRendererClone;

    @Test
    public void shouldUnbindOnRecycle() {
        givenAnUnbindableCollectorProvider();
        givenABinderRenderer();
        whenRecycle();
        thenRendererIsUnbinded();
    }

    @Test
    public void shouldCreateClone() throws CloneNotSupportedException {
        givenAnUnbindableCollectorProvider();
        givenABinderRenderer();
        whenClone();
        thenCloneWithNewUnbindableCollectorIsCreated();
    }

    @Test
    public void shouldFindViewsInRendererView() {
        givenAnUnbindableCollectorProvider();
        givenABinderRenderer();
        whenFindView();
        thenViewsAreFoundAtRendererView();
    }

    @Test
    public void shouldBindViewsWhenRender() {
        givenAnUnbindableCollectorProvider();
        givenABinderRenderer();
        whenRender();
        thenViewsAreBinded();
    }

    private void givenAnUnbindableCollectorProvider() {
        when(mUnbindableCollectorProvider.get()).thenReturn(mFirstUnbindableCollector,
                mSecondUnbindableCollector);
    }

    private void givenABinderRenderer() {
        mBinderRenderer = new BinderRenderer();
        mBinderRenderer.injectUnbindableCollectorProvider(mUnbindableCollectorProvider);
        mBinderRenderer.injectDelegate(mBinderDelegate);
        mBinderRenderer.onCreate(SOME_CONTENT, mLayoutInflater, mViewGroup);
    }

    private void whenRecycle() {
        mBinderRenderer.onRecycle(null);
    }

    private void whenRender() {
        mBinderRenderer.render();
    }

    private void whenClone() throws CloneNotSupportedException {
        mBinderRendererClone = mBinderRenderer.clone();
    }

    private void whenFindView() {
        mBinderRenderer.findViewById(SOME_VIEW_ID);
    }

    private void thenRendererIsUnbinded() {
        verify(mFirstUnbindableCollector).unbindAndReleaseAll();
    }

    private void thenCloneWithNewUnbindableCollectorIsCreated() {
        assertTrue(mBinderRendererClone instanceof com.raycoarana.baindo.renderer.BinderRenderer);
        ((com.raycoarana.baindo.renderer.BinderRenderer) mBinderRendererClone).unbind();
        verify(mSecondUnbindableCollector).unbindAndReleaseAll();
    }

    private void thenViewsAreBinded() {
        verify(mBinderDelegate).bind(mBinderRenderer, mFirstUnbindableCollector);
    }

    @SuppressWarnings("ResourceType")
    private void thenViewsAreFoundAtRendererView() {
        verify(mView).findViewById(SOME_VIEW_ID);
    }

    private class BinderRenderer extends com.raycoarana.baindo.renderer.BinderRenderer<Object> {

        @Override
        protected void bindViews() {
            bind();
        }

        @Override
        protected View inflate(LayoutInflater inflater, ViewGroup parent) {
            return mView;
        }
    }

}
