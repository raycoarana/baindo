Baindo
==========

[![Build Status](https://travis-ci.org/raycoarana/baindo.svg?branch=master)](https://travis-ci.org/raycoarana/baindo)
[![Coverage Status](https://coveralls.io/repos/raycoarana/baindo/badge.png)](https://coveralls.io/r/raycoarana/baindo)

Baindo is a library that let you implement the MVVM (Model View View-Model) pattern in a very simple and
efficient way. Contrary to other frameworks, Baindo is built with performance in mind, so it doesn't use any
kind of reflection. Bind your views and adapters with Baindo to your ViewModels and keep them away from any
dependency with you UI.

Baindo uses Universal Image Loader to bind images to ImageViews and Pedro Gome'z Renderers library as a base
to build the binders to adapters.

How to use it?
--------------

TODO


```java
public class TODO {

	@Override
	public void onCreate() {
		super.onCreate();
	}

}
```


Take a look at the Sample project!

Download
--------

Download via Maven:
```xml
<dependency>
  <groupId>com.raycoarana</groupId>
  <artifactId>baindo</artifactId>
  <version>1.0.0-SNAPSHOT/version>
</dependency>
```
or Gradle:
```groovy
compile 'com.raycoarana:baindo:1.0.0-SNAPSHOT'
```

License
-------

    Copyright 2015 Rayco Araña

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.