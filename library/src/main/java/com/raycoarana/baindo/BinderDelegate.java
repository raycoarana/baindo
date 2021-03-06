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

import android.content.Intent;
import android.os.Bundle;

/**
 * BinderDelegate let you introduce Baindo in your Activities and Fragments in four simple steps.
 *
 * First make your Activity/Fragment implements BindableSource.
 * Then get an instance of a BinderDelegate in the onFragmentCreate() method of your Activity/Fragment.
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

    private final BaindoBinderFactory mBaindoBinderFactory;
    private final WorkDispatcher mWorkDispatcher;
    private final UnbindableCollector mUnbindableCollector;
    private final LifecycleBinderCollector mLifecycleBinderCollector;

    BinderDelegate(BaindoBinderFactory baindoBinderFactory,
                   WorkDispatcher workDispatcher,
                   UnbindableCollector unbindableCollector,
                   LifecycleBinderCollector lifecycleBinderCollector) {
        mBaindoBinderFactory = baindoBinderFactory;
        mWorkDispatcher = workDispatcher;
        mUnbindableCollector = unbindableCollector;
        mLifecycleBinderCollector = lifecycleBinderCollector;
    }

    /**
     * Creates a binder, it's recommended not to use this method directly for your bindings, instead
     * create a bind() method in your Activity/Fragment that calls this method passing the
     * Activity/Fragment as a BindableSource.
     *
     * @param bindableSource source of views to bind
     * @return a new binder
     */
    public Binder bind(BindableSource bindableSource) {
        return bind(bindableSource, mUnbindableCollector);
    }

    /**
     * Creates a binder with the provided BindableSource and UnbindableCollector, for use in Adapters
     * like binder that have its own UnbindableCollector.
     *
     * @param bindableSource source of views to bind
     * @param unbindableCollector collection to store all created unbindable elements
     * @return a new binder
     */
    public Binder bind(BindableSource bindableSource, UnbindableCollector unbindableCollector) {
        return mBaindoBinderFactory.build(bindableSource, mWorkDispatcher, this, unbindableCollector, mLifecycleBinderCollector);
    }

    public void onResume() {
        mLifecycleBinderCollector.onResume();
    }

    public void onPause() {
        mLifecycleBinderCollector.onPause();
    }

    public void onStart() {
        mLifecycleBinderCollector.onStart();
    }

    public void onStop() {
        mLifecycleBinderCollector.onStop();
    }

    /**
     * Unbinds all binds created by this delegate and exits the background thread that processes
     * all event notifications to the ViewModel.
     *
     * Call this method in the onDestroy() method of your Activity/Fragment.
     */
    public void onDestroy() {
        mLifecycleBinderCollector.onDestroy();
        unbind();
        mWorkDispatcher.onDestroy();
    }

    /**
     * Unbind and destroy all binders created by this delegate.
     */
    public void unbind() {
        mUnbindableCollector.unbindAndReleaseAll();
    }

    /**
     * Call this method in the onCreate method of your activity
     *
     * @param intent Intent of onCreate()
     * @param savedInstanceState Bundle of onCreate()
     */
    public void onActivityCreate(Intent intent, Bundle savedInstanceState) {
        mLifecycleBinderCollector.updateIntent(intent);
        mLifecycleBinderCollector.updateSavedInstanceState(savedInstanceState);
        mLifecycleBinderCollector.onCreate();
    }

    /**
     * Call this method in the onNewIntent method of your activity
     *
     * @param intent Intent of onNewIntent()
     */
    public void onNewIntent(Intent intent) {
        mLifecycleBinderCollector.updateIntent(intent);
    }

    /**
     * Call this method in the onSaveInstanceState method
     *
     * @param outState Bundle of onSaveInstanceState()
     */
    public void onSaveInstanceState(Bundle outState) {
        mLifecycleBinderCollector.saveInstanceState(outState);
    }

    /**
     * Call this method in the onCreate method of your fragment
     *
     * @param savedInstanceState Bundle of onCreate()
     */
    public void onFragmentCreate(Bundle savedInstanceState) {
        mLifecycleBinderCollector.updateSavedInstanceState(savedInstanceState);
        mLifecycleBinderCollector.onCreate();
    }

    /**
     * Call this method in the onAttach method of your fragment
     *
     * @param intent Intent of onAttach() event
     */
    public void onFragmentAttach(Intent intent) {
        mLifecycleBinderCollector.updateIntent(intent);
    }
}
