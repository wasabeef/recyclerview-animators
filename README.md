RecyclerView Animators
======================

[![Download](https://api.bintray.com/packages/wasabeef/maven/recyclerview-animators/images/download.svg)](https://bintray.com/wasabeef/maven/recyclerview-animators/_latestVersion)

RecyclerView Animators is an Android library that allows developers to easily create RecyclerView with animations.
Please feel free to use this.

# Demo ([Play Store Demo](https://play.google.com/store/apps/details?id=jp.wasabeef.example.recyclerview))

![](art/demo.gif)

<a href="https://play.google.com/store/apps/details?id=jp.wasabeef.example.recyclerview"><img src="http://www.android.com/images/brand/get_it_on_play_logo_large.png"/></a>

# How do I use it?

## Step 1

#### Gradle
```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'jp.wasabeef:recyclerview-animators:1.0.0'
}
```

## Step 2

set RecyclerView ItemAnimator.

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

### Animator

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
