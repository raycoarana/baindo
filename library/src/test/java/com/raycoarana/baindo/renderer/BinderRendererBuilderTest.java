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

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererTestHelper;
import com.raycoarana.baindo.BinderDelegate;
import com.raycoarana.baindo.UnbindableCollectorProvider;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BinderRendererBuilderTest extends UnitTestSuite {

    private static final String SOME_CONTENT = "some_content";
    private static final Integer OTHER_TYPE_CONTENT = 42;

    @Mock
    private BinderDelegate mBinderDelegate;

    @Mock
    private UnbindableCollectorProvider mUnbindableCollectorProvider;

    @Mock
    private BinderRenderer<Object> mBinderRendererPrototype;

    @Mock
    private View mConvertView;

    private ArrayList<Renderer<Object>> mBuiltRenderers = new ArrayList<>();
    private BinderRendererBuilder<Object> mBinderRendererBuilder;
    private Map<Class<?>, BinderRenderer<Object>> mContentToPrototypeMap = new HashMap<>();

    @Test
    public void shouldInjectPrototypes() {
        givenABinderRendererBuilder();
        thenAllPrototypesAreInjected();
    }

    @Test
    public void shouldRecycleRendererWithConvertView() {
        givenABinderRendererBuilder();
        whenBuildAnAlreadyBindedView();
        thenTheBinderRendererIsUnbind();
    }

    @Test
    public void shouldUnbindAllRendererWhenDestroy() {
        givenABinderRendererBuilder();
        givenSomeRenderersBuilt();
        whenDestroy();
        thenAllBindersAreUnbinded();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailWhenTryToBuildANonExisting() {
        givenABinderRendererBuilder();
        whenBuildANonExistingContent();
    }

    private void givenSomeRenderersBuilt() {
        buildRenderer(SOME_CONTENT, false);
        buildRenderer(SOME_CONTENT, false);
    }

    private void buildRenderer(Object content, boolean withConvertView) {
        View convertView = withConvertView ? mConvertView : null;
        ViewGroup parent = mock(ViewGroup.class);
        LayoutInflater layoutInflater = mock(LayoutInflater.class);

        when(layoutInflater.inflate(anyInt(), eq(parent), eq(false))).thenReturn(mConvertView);

        mBuiltRenderers.add(RendererTestHelper.build(mBinderRendererBuilder,
                content,
                convertView,
                parent,
                layoutInflater));
    }

    private void mockViewTagProperty() {
        when(mConvertView.getTag()).thenReturn(mBuiltRenderers.get(0));
    }

    @SuppressWarnings("unchecked")
    private void givenABinderRendererBuilder() {
        RendererTestHelper.mockRenderer(mBinderRendererPrototype);
        mContentToPrototypeMap.put(SOME_CONTENT.getClass(), mBinderRendererPrototype);
        mBinderRendererBuilder = new BinderRendererBuilder<Object>(mContentToPrototypeMap){};
        mBinderRendererBuilder.injectDelegate(mBinderDelegate);
        mBinderRendererBuilder.injectUnbindableCollectorProvider(mUnbindableCollectorProvider);
    }

    private void whenBuildAnAlreadyBindedView() {
        buildRenderer(SOME_CONTENT, false);
        mockViewTagProperty();
        buildRenderer(SOME_CONTENT, true);
    }

    private void whenBuildANonExistingContent() {
        buildRenderer(OTHER_TYPE_CONTENT, false);
    }

    private void whenDestroy() {
        mBinderRendererBuilder.destroy();
    }

    private void thenAllBindersAreUnbinded() {
        for(Renderer<Object> renderer : mBuiltRenderers) {
            BinderRenderer<Object> binderRenderer = (BinderRenderer<Object>) renderer;
            verify(binderRenderer).unbind();
        }
    }

    private void thenAllPrototypesAreInjected() {
        for(BinderRenderer<Object> binderRenderer : mContentToPrototypeMap.values()) {
            verify(binderRenderer).injectDelegate(mBinderDelegate);
            verify(binderRenderer).injectUnbindableCollectorProvider(mUnbindableCollectorProvider);
        }
    }

    private void thenTheBinderRendererIsUnbind() {
        BinderRenderer<Object> recycledBinderRenderer = (BinderRenderer<Object>) mBuiltRenderers.get(0);
        verify(recycledBinderRenderer).unbind();
    }

}
