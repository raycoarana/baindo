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

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.raycoarana.baindo.Baindo;
import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.Binder;
import com.raycoarana.baindo.BinderDelegate;

public class BaindoActivity extends Activity implements BindableSource {

    private BinderDelegate mBinderDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinderDelegate = Baindo.buildBinderDelegate();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mBinderDelegate = Baindo.buildBinderDelegate();
        mBinderDelegate.onActivityCreate(getIntent(), savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinderDelegate.onSaveInstanceState(outState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mBinderDelegate.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mBinderDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mBinderDelegate.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mBinderDelegate.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mBinderDelegate.onStop();
    }

    /**
     * Creates and returns a new Binder ready to bind a view property with a ViewModel property.
     */
    public Binder bind() {
        return mBinderDelegate.bind(this);
    }

    /**
     * Unbind all views, use it with Transition API to do your animations
     */
    public void unbind() {
        mBinderDelegate.unbind();
    }

    @Override
    protected void onDestroy() {
        mBinderDelegate.onDestroy();
        super.onDestroy();
    }

}
