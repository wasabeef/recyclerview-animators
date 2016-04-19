Change Log
==========

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
