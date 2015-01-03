RecyclerViewAnimators
======================

# Demo

**Comming Soon**

# Status

Release Version: N/A
Pre-Release Version: N/A
Snapshot Version: N/A

# How do I use it?

## Step 1

#### Gradle
```groovy
dependencies {
        compile 'jp.wasabeef.recyclerview:animators:{Current Version}'
}
```

## Step 2

Just like play RecyclerView Animations.

```java
    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list);
    mRecyclerView.setItemAnimator(new FlipYAnimator());
```

### Animator

#### Scale
`ScaleAnimator`

#### Fade
`FadeAnimator`

#### Flip
`FlipXAnimator`, `FlipYAnimator`

#### Slide
`SlideLeftAnimator`, `SlideRightAnimator`  
`SlideTopAnimator`, `SlideBottomAnimator`

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

