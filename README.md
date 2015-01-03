RecyclerViewAnimators
======================

[![Build Status](https://travis-ci.org/wasabeef/RecyclerViewAnimators.svg)](https://travis-ci.org/wasabeef/RecyclerViewAnimators)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/jp.wasabeef.recyclerview/animators/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.malinskiy/superrecyclerview) 

RecyclerViewAnimators is an Android library that allows developers to easily create RecyclerView with animations.
Please feel free to use this.

# Demo

![](art/demo.gif)

# Status

  - ***Release Version:* N/A**
  - ***Pre-Release Version:* N/A**
  - ***Snapshot Version:* N/A**

# How do I use it?

## Step 1

#### Gradle
```groovy
dependencies {
        compile 'jp.wasabeef.recyclerview:animators:{Current Version}'
}
```

## Step 2

Just like play RecyclerView animations.

```java
    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list);
    mRecyclerView.setItemAnimator(new FlipYAnimator());
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

#### Scale
`ScaleInAnimator`

#### Fade
`FadeInAnimator`

#### Flip
`FlipInTopXAnimator`, `FlipInBottomXAnimator`  
`FlipInLeftYAnimator`, `FlipInRightYAnimator`

#### Slide
`SlideInLeftAnimator`, `SlideInRightAnimator`, `OvershootInLeftAnimator`, `OvershootInRightAnimator`
`SlideInTopAnimator`, `SlideInBottomAnimator`

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

