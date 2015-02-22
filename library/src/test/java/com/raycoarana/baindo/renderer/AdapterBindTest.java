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

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.BinderDelegate;
import com.raycoarana.baindo.UnbindableCollectorProvider;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.observables.AbstractCollectionProperty;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

public class AdapterBindTest extends UnitTestSuite {

    private static final int SOME_VIEW_RES_ID = 42;

    @Mock
    private BinderDelegate mBinderDelegate;

    @Mock
    private WorkDispatcher mWorkDispatcher;

    @Mock
    private BindableSource mBindableSource;

    @Mock
    private AdapterFactory<Object> mAdapterFactory;

    @Mock
    private UnbindableCollectorProvider mUnbindableCollector;

    @Mock
    private AdapterView<BaseAdapter> mSomeAdapterView;

    @Mock
    private AbstractCollectionProperty<Object> mSomeCollectionProperty;

    @Mock
    private BinderRendererAdapter mBinderRendererAdapter;

    @Captor
    private ArgumentCaptor<BaseAdapter> mBaseAdapterArgumentCaptor;

    private AdapterBind<Object> mAdapterBind;

    @Test
    public void shouldBindAdapterToTarget() {
        givenAnAdapterView();
        givenAnAdapterFactory();
        givenAnAdapterBind();
        whenBindSomeViewToSomeProperty();
        thenAdapterViewReceivedBaseAdapter();
    }

    @Test
    public void shouldUnbindAdpaterFromTarget() {
        givenAnAdapterView();
        givenAnAdapterFactory();
        givenAnAdapterBind();
        whenBindSomeViewToSomeProperty();
        whenUnbind();
        thenAdapterViewIsUnbindedFromProperty();
    }

    @Test
    public void shouldGetAdapterViewFromBindableSource() {
        givenAnAdapterView();
        givenAnAdapterFactory();
        givenAnAdapterBind();
        whenBindSomeViewResourceToSomeProperty();
        thenBindableSourceFindViewByIdIsCalled();
    }

    @Test
    public void shouldUpdateAdapterWhenCollectionPropertyChanges() {
        givenAnAdapterView();
        givenAnAdapterFactory();
        givenAnAdapterBind();
        whenBindSomeViewToSomeProperty();
        whenPropertyChanges();
        thenAdapterIsNotifiedOfChanges();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenIncompatibleViewIsUsed() {
        givenAnIncompatibleView();
        givenAnAdapterFactory();
        givenAnAdapterBind();
        whenBindSomeViewResourceToSomeProperty();
    }

    private void givenAnAdapterView() {
        when(mBindableSource.findViewById(SOME_VIEW_RES_ID)).thenReturn(mSomeAdapterView);
        doNothing().when(mSomeAdapterView).setAdapter(mBaseAdapterArgumentCaptor.capture());
        when(mSomeAdapterView.getAdapter()).thenAnswer(new Answer<BaseAdapter>() {

            @Override
            public BaseAdapter answer(InvocationOnMock invocationOnMock) throws Throwable {
                return mBaseAdapterArgumentCaptor.getValue();
            }
        });
    }

    private void givenAnIncompatibleView() {
        when(mBindableSource.findViewById(SOME_VIEW_RES_ID)).thenReturn(mock(View.class));
    }

    private void givenAnAdapterFactory() {
        when(mAdapterFactory.build(mSomeCollectionProperty)).thenReturn(mBinderRendererAdapter);
    }

    private void givenAnAdapterBind() {
        mAdapterBind = new AdapterBind<>(mBindableSource,
                                         mWorkDispatcher,
                                         mBinderDelegate,
                                         mAdapterFactory,
                                         mUnbindableCollector);
    }

    private void whenBindSomeViewToSomeProperty() {
        mAdapterBind.of(mSomeAdapterView).to(mSomeCollectionProperty);
    }

    private void whenBindSomeViewResourceToSomeProperty() {
        mAdapterBind.of(SOME_VIEW_RES_ID).to(mSomeCollectionProperty);
    }

    private void whenUnbind() {
        mAdapterBind.unbind();
    }

    private void whenPropertyChanges() {
        mAdapterBind.update(mSomeCollectionProperty, null);
    }

    private void thenBindableSourceFindViewByIdIsCalled() {
        verify(mBindableSource).findViewById(SOME_VIEW_RES_ID);
    }

    private void thenAdapterViewReceivedBaseAdapter() {
        verify(mAdapterFactory).build(mSomeCollectionProperty);
        verify(mBinderRendererAdapter).injectBinderDelegate(mBinderDelegate);
        verify(mBinderRendererAdapter).injectUnbindableCollectorProvider(mUnbindableCollector);
        verify(mSomeAdapterView).setAdapter(mBinderRendererAdapter);
        thenAdapterIsNotifiedOfChanges();
    }

    private void thenAdapterIsNotifiedOfChanges() {
        verify(mBinderRendererAdapter).notifyDataSetChanged();
    }

    private void thenAdapterViewIsUnbindedFromProperty() {
        verify(mBinderRendererAdapter).unbind();
    }

}
