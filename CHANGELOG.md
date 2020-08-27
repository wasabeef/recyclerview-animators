Change Log
==========

Version 4.0.0 *(2020-08-27)*
----------------------------

Update:
- Convert from all Java codes to Kotlin 1.3.72
- The minSdkVersion from 14 to 21
- The targetSdkVersion from 28 to 30
- The compileSdkVersion from 28 to 30
- Update project settings
  - Disable Jetifier
  - Add CodeStyle settings

Version 3.0.0 *(2018-11-15)*
----------------------------

Update:
- Migrate to AndroidX
- Remove novoda-bintray-plugin
- Fix a buf [#161](https://github.com/wasabeef/recyclerview-animators/pull/161)

Version 2.3.0 *(2018-02-07)*
----------------------------

Update:  
- Compile & Target SDK Version 25 -> 27  
- Build Tools 26.0.1 -> 27.0.3  
- Support Library 25.3.1 -> 27.0.2  

Version 2.2.7 *(2017-06-29)*
----------------------------

Update:
- Support Library 25.3.0 -> 25.4.0

Version 2.2.6 *(2017-03-17)*
----------------------------

Feature:
- [Changed Interpolator to DecelerateInterpolator #125](https://github.com/wasabeef/recyclerview-animators/pull/125)

Update:
- Build Tools 25 -> 25.0.2
- Support Library 24.2.0 -> 25.3.0

Bugfix:
- [Fix animations not fully canceled on endAnimations()  #86](https://github.com/wasabeef/recyclerview-animators/pull/86)


Version 2.2.5 *(2016-12-02)*
----------------------------

Update:
- Build Tools 24.0.2 -> 25
- Support Library 23.4.0 -> 24.2.0


Version 2.2.4 *(2016-08-03)*
----------------------------

Update:  
- Build Tools 23.0.1 -> 24.0.2  
- Support Library 23.0.1 -> 23.4.0  


Version 2.2.3 *(2016-04-19)*
----------------------------

Bug fix:  
 [Dispatch onViewRecycled event to wrapped adapter #80](https://github.com/wasabeef/recyclerview-animators/pull/80)  
 [Fix setStartDelay() is not reset by clear() #82](https://github.com/wasabeef/recyclerview-animators/pull/82)  
 
Update:  
 Support library 23.2.1

Version 2.2.2 *(2016-04-05)*
----------------------------

Bug fix

Version 2.2.1 *(2016-02-28)*
----------------------------

Bug fix: firstOnly

Version 2.2.0 *(2016-01-29)*
----------------------------

Bug fix: [issue #64](https://github.com/wasabeef/recyclerview-animators/issues/64) by [@emartynov](https://github.com/wasabeef/recyclerview-animators/issues/64)
Feature: [Motion Delay](https://github.com/wasabeef/recyclerview-animators/pull/66) by [@aphexcx](https://github.com/aphexcx)

Version 2.1.0 *(2015-11-25)*
----------------------------

Move the adapters of the package.  
Added BaseItemAnimator#setInterpolator.  

Version 2.0.2 *(2015-11-24)*
----------------------------

Added AnimationAdapter#getIemId  
 Fixed the getItemId() to return the nested adapter item id  

Version 2.0.1 *(2015-11-10)*
----------------------------

Bug fix registerAdapterDataObserver, unregisterAdapterDataObserver

Version 2.0.0 *(2015-10-16)*
----------------------------

Support RecyclerView 23.1.0+

**Use 1.3.0 If you are using a 23.0.1 or below.**

Version 1.3.0 *(2015-09-11)*
----------------------------

Added in ability to pass in interpolators to the four ItemAnimators.
Thanks to [@craya1982](https://github.com/craya1982)

Version 1.2.3 *(2015-09-07)*
----------------------------

Update support library.

Version 1.2.2 *(2015-08-19)*
----------------------------

Add tension in OvershootAnimators.

Version 1.2.1 *(2015-07-07)*
----------------------------

Update AnimationAdapter to allow grabbing the wrappedAdapter.

Version 1.2.0 *(2015-04-15)*
----------------------------

Support Multiple animators for ViewHolders.

Version 1.1.3 *(2015-04-08)*
----------------------------

Support Interpolator in AnimationAdapter.

Version 1.1.2 *(2015-03-23)*
----------------------------

Supports multiple viewTypes.

Version 1.1.1 *(2015-03-17)*
----------------------------

Improved reliability and speed.

Version 1.1.0 *(2015-01-21)*
----------------------------

add RecyclerView.Adapter support.


Version 1.0.3 *(2015-01-21)*
----------------------------

fix setting of pom.xml

Version 1.0.2 *(2015-01-20)*
----------------------------

fix setting of pom.xml

Version 1.0.1 *(2015-01-10)*
----------------------------

Attach a jar for non-Gradle Android users.

Version 1.0.0 *(2015-01-05)*
----------------------------

Initial release.
