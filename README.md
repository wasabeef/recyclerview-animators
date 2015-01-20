RecyclerView Animators
======================
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/wasabeef/recyclerview-animators?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)  

[![Build Status](https://travis-ci.org/wasabeef/recyclerview-animators.svg?branch=master)](https://travis-ci.org/wasabeef/recyclerview-animators)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-recyclerview--animators-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1327)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://api.bintray.com/packages/wasabeef/maven/recyclerview-animators/images/download.svg)](https://bintray.com/wasabeef/maven/recyclerview-animators/_latestVersion)

RecyclerView Animators is an Android library that allows developers to easily create RecyclerView with animations.

Please feel free to use this.

# Demo

![](art/demo.gif) ![](art/demo2.gif)  

# Samples

<a href="https://play.google.com/store/apps/details?id=jp.wasabeef.example.recyclerview"><img src="http://www.android.com/images/brand/get_it_on_play_logo_large.png"/></a>

# How do I use it?

## Step 1

#### Gradle
```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'jp.wasabeef:recyclerview-animators:1.0.2'
}
```

## Step 2

Set RecyclerView ItemAnimator.

```java
    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list);
    mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
```

## Advanced Step 3

You can change the durations.

```java
    mRecyclerView.getItemAnimator().setAddDuration(1000);
    mRecyclerView.getItemAnimator().setRemoveDuration(1000);
    mRecyclerView.getItemAnimator().setMoveDuration(1000);
    mRecyclerView.getItemAnimator().setChangeDuration(1000);
```

## Animators

### Cool
`LandingAnimator`

#### Scale
`ScaleInAnimator`, `ScaleInTopAnimator`, `ScaleInBottomAnimator`  
`ScaleInLeftAnimator`, `ScaleInRightAnimator`


#### Fade
`FadeInAnimator`, `FadeInDownAnimator`, `FadeInUpAnimator`  
`FadeInLeftAnimator`, `FadeInRightAnimator`

#### Flip
`FlipInTopXAnimator`, `FlipInBottomXAnimator`  
`FlipInLeftYAnimator`, `FlipInRightYAnimator`

#### Slide
`SlideInLeftAnimator`, `SlideInRightAnimator`, `OvershootInLeftAnimator`, `OvershootInRightAnimator`  
`SlideInUpAnimator`, `SlideInDownAnimator`

Developed By
-------
Daichi Furiya (Wasabeef) - <dadadada.chop@gmail.com>

<a href="https://twitter.com/wasabeef_jp">
<img alt="Follow me on Twitter"
src="https://raw.githubusercontent.com/wasabeef/art/master/twitter.png" width="75"/>
</a>

Thanks
-------

* Inspired by `AndroidViewAnimations` in [daimajia](https://github.com/daimajia).

License
-------

    Copyright 2015 Wasabeef

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
