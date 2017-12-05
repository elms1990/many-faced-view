# Many Faced View

#### __"That man's life was not yours to take. A girl stole from the Many-Faced God. Now a debt is owed" - Jaqen H'ghar to Arya Stark__

# Description
[![](https://jitpack.io/v/elms1990/many-faced-view.svg)](https://jitpack.io/#elms1990/many-faced-view)

Simple library for handling view state switching, with minor transition customization.

Inspired by [MultiStateView](https://github.com/Kennyc1012/MultiStateView)

Features:
- 8 different states: content, loading, empty, error, extra1, extra2, extra3, extra4
- in and out transition customization

# XML Declaration

```xml
    <com.theycallmeerick.manyfacedview.view.ManyFacedView
        android:id="@+id/state_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:mfv_content="@layout/test_item_state_content_view"
        app:mfv_inAnimation="@anim/custom_fade_in"
        app:mfv_loading="@layout/test_item_state_loading_view"
        app:mfv_outAnimation="@anim/custom_fade_out"
        app:mfv_state="loading" />
```

# XML Attributes

```xml
    <!-- View states -->
    <attr name="mfv_content" format="reference" />
    <attr name="mfv_loading" format="reference" />
    <attr name="mfv_error" format="reference" />
    <attr name="mfv_empty" format="reference" />
    <attr name="mfv_extra1" format="reference" />
    <attr name="mfv_extra2" format="reference" />
    <attr name="mfv_extra3" format="reference" />
    <attr name="mfv_extra4" format="reference" />
    
    <!-- Initial view state -->
    <attr name="mfv_state" format="enum"/>
    
    <!-- Transition related attributes -->
    <attr name="mfv_animateChanges" format="boolean" />
    <attr name="mfv_outAnimation" format="reference" />
    <attr name="mfv_inAnimation" format="reference" />
```

# Java Usage

All XML attributes can be set using the Java API.
```java
     // Add state view
     manyFacedView.addStateView(@ViewState int state, View view);
     manyFacedView.addStateView(@ViewState int state, @LayoutRes int layoutId);
    
     // Get view for state
     manyFacedView.getView(@ViewState int state);
    
     // Get/Set current state
     manyFacedView.setState(@ViewState int state);
     manyFacedView.getState();
    
     // Animation related methods
     manyFacedView.enableTransitionAnimation(boolean enable);
     manyFacedView.setInAnimator(@NonNull Animator inAnimator);
     manyFacedView.setOutAnimator(@NonNull Animator outAnimator);
     manyFacedView.setOnStateChangedListener(@Nullable OnStateChangedListener listener);
```

# Using the Library

```groovy
repositories {
   maven { url 'https://jitpack.io'  }
}
```

```groovy
dependencies {
    compile 'com.github.elms1990:many-faced-view:1.0.3'
}
```

# Samples

You can find a few examples inside the [samples](https://github.com/elms1990/many-faced-view/tree/master/sample) project.

# Known Issues

Currently we have no known issues.

# License

    Copyright 2017 Erick Luis

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
