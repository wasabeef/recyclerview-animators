RecyclerViewAnimators
======================

# Demo

![](aaaa.gif)

# Usage

## Step 1

#### Gradle
```groovy
dependencies {
        compile 'jp.wasabeef:recyclerview-animators:{Current Version}'
}
```

## Step 2

Just like play animators.

```java
    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list);
    mRecyclerView.setItemAnimator(new **************(mRecyclerView));
```

### Effects

#### Scale
`Scale`

#### Fade
`Fade`

#### Flip
`FlipX`, `FlipY`

#### Slide
`SlideLeft`, `SlideRight`, `SlideTop`, `SlideBottom`

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

