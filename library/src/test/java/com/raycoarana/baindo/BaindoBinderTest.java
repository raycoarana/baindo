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

import com.raycoarana.baindo.binding.BindTarget;
import com.raycoarana.baindo.binding.UIAction;
import com.raycoarana.baindo.binding.ViewToBindSelector;
import com.raycoarana.baindo.properties.CheckedBind;
import com.raycoarana.baindo.properties.CommandBind;
import com.raycoarana.baindo.properties.EnabledBind;
import com.raycoarana.baindo.properties.InvisibilityBind;
import com.raycoarana.baindo.properties.ProgressBind;
import com.raycoarana.baindo.properties.SelectedIndexBind;
import com.raycoarana.baindo.properties.TextBind;
import com.raycoarana.baindo.properties.UIActionBind;
import com.raycoarana.baindo.properties.VisibilityBind;
import com.raycoarana.baindo.renderer.AdapterBind;
import com.raycoarana.baindo.renderer.AdapterFactory;
import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaindoBinderTest extends UnitTestSuite {

    @Mock
    private BinderDelegate mBinderDelegate;

    @Mock
    private WorkDispatcher mWorkDispatcher;

    @Mock
    private BindableSource mBindableSource;

    @Mock
    private Unbindable mSourceOfBind;

    @Mock
    private UnbindableCollector mUnbindableCollector;

    @Mock
    private UnbindableCollectorProvider mUnbindableCollectorProvider;

    private BaindoBinder mBaindoBinder;
    private ViewToBindSelector<?> mViewToBindSelector;
    private BindTarget<?> mBindTarget;

    @Test
    public void shouldBuildAnIsCheckedBind() {
        givenABaindoBinder();
        whenIsChecked();
        thenAnIsCheckedBindIsBuilt();
    }

    @Test
    public void shouldBuildAnEnabledBind() {
        givenABaindoBinder();
        whenEnabled();
        thenAnEnabledBindIsBuilt();
    }

    @Test
    public void shouldBuildAnVisibilityBind() {
        givenABaindoBinder();
        whenVisibility();
        thenAVisibilityBindIsBuilt();
    }

    @Test
    public void shouldBuildAnInvisibilityBind() {
        givenABaindoBinder();
        whenInvisibility();
        thenAnInvisibilityBindIsBuilt();
    }

    @Test
    public void shouldBuildAClickBind() {
        givenABaindoBinder();
        whenClick();
        thenAClickBindIsBuilt();
    }

    @Test
    public void shouldBuildAProgressBind() {
        givenABaindoBinder();
        whenProgress();
        thenAProgressBindIsBuilt();
    }

    @Test
    public void shouldBuildATextBind() {
        givenABaindoBinder();
        whenText();
        thenATextBindIsBuilt();
    }

    @Test
    public void shouldBuildAnUIActionBind() {
        givenABaindoBinder();
        whenUIAction();
        thenAnUIActionBindIsBuilt();
    }

    @Test
    public void shouldBuildAnAdapterWithFactoryBind() {
        givenABaindoBinder();
        whenAdapterWithFactory();
        thenAnAdapterWithFactoryBindIsBuilt();
    }

    @Test
    public void shouldBuildASelectedIndexBind() {
        givenABaindoBinder();
        whenSelectedIndex();
        thenASelectedIndexBindIsBuilt();
    }

    private void givenABaindoBinder() {
        when(mUnbindableCollector.collect(any(Unbindable.class))).thenAnswer(answerWithFirstArgument());
        mBaindoBinder = new BaindoBinder(mBindableSource,
                                         mWorkDispatcher,
                                         mBinderDelegate,
                                         mUnbindableCollector,
                                         mUnbindableCollectorProvider);
    }

    private void whenIsChecked() {
        mViewToBindSelector = mBaindoBinder.isChecked();
    }

    private void whenEnabled() {
        mViewToBindSelector = mBaindoBinder.enabled();
    }

    private void whenVisibility() {
        mViewToBindSelector = mBaindoBinder.visibility();
    }

    private void whenInvisibility() {
        mViewToBindSelector = mBaindoBinder.invisibility();
    }

    private void whenClick() {
        mViewToBindSelector = mBaindoBinder.click();
    }

    private void whenProgress() {
        mViewToBindSelector = mBaindoBinder.progress();
    }

    private void whenText() {
        mViewToBindSelector = mBaindoBinder.text();
    }

    @SuppressWarnings("unchecked")
    private void whenUIAction() {
        mBindTarget = mBaindoBinder.uiAction(mock(UIAction.class));
    }

    @SuppressWarnings("unchecked")
    private void whenAdapterWithFactory() {
        mViewToBindSelector = mBaindoBinder.adapterWithFactory(mock(AdapterFactory.class));
    }

    private void whenSelectedIndex() {
        mViewToBindSelector = mBaindoBinder.selectedIndex();
    }

    private void thenAnIsCheckedBindIsBuilt() {
        verifyViewToBindSelectorIsCollected();
        assertThat(mViewToBindSelector, instanceOf(CheckedBind.class));
    }

    private void thenAnEnabledBindIsBuilt() {
        verifyViewToBindSelectorIsCollected();
        assertThat(mViewToBindSelector, instanceOf(EnabledBind.class));
    }

    private void thenAVisibilityBindIsBuilt() {
        verifyViewToBindSelectorIsCollected();
        assertThat(mViewToBindSelector, instanceOf(VisibilityBind.class));
    }

    private void thenAnInvisibilityBindIsBuilt() {
        verifyViewToBindSelectorIsCollected();
        assertThat(mViewToBindSelector, instanceOf(InvisibilityBind.class));
    }

    private void thenAClickBindIsBuilt() {
        verifyViewToBindSelectorIsCollected();
        assertThat(mViewToBindSelector, instanceOf(CommandBind.class));
    }

    private void thenAProgressBindIsBuilt() {
        verifyViewToBindSelectorIsCollected();
        assertThat(mViewToBindSelector, instanceOf(ProgressBind.class));
    }

    private void thenATextBindIsBuilt() {
        verifyViewToBindSelectorIsCollected();
        assertThat(mViewToBindSelector, instanceOf(TextBind.class));
    }

    private void thenAnUIActionBindIsBuilt() {
        verifyBindTargetIsCollected();
        assertThat(mBindTarget, instanceOf(UIActionBind.class));
    }

    private void thenAnAdapterWithFactoryBindIsBuilt() {
        verifyViewToBindSelectorIsCollected();
        assertThat(mViewToBindSelector, instanceOf(AdapterBind.class));
    }

    private void thenASelectedIndexBindIsBuilt() {
        verifyViewToBindSelectorIsCollected();
        assertThat(mViewToBindSelector, instanceOf(SelectedIndexBind.class));
    }

    private void verifyBindTargetIsCollected() {
        if(mBindTarget instanceof Unbindable) {
            verify(mUnbindableCollector).collect((Unbindable) mBindTarget);
        }
    }

    private void verifyViewToBindSelectorIsCollected() {
        if(mViewToBindSelector instanceof Unbindable) {
            verify(mUnbindableCollector).collect((Unbindable) mViewToBindSelector);
        }
    }

}
