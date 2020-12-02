###Introduction###
This is a hiring test project for a vacancy at LeBonCoin.fr.

###Technical Choices###

##Architecture##
For the development of this app I tried to follow the clean architecture recommendations as it helps to build a clean, robust, easily understandable and modular code base. It is also advertised by google and widely adopted by android developers. 

##Pattern##
For the pattern, I choose VMMV as it is low depency ( especially with Dagger) , easily testable and modular.

##Libraries##

#Retrofit for networking#
It's a well designed, Easy to use and low resource consuming networking library. it might not be as configurable as Volley for example but it’s largely enough for what we need to do in this project. I had to us version2.6.0 because of a retrofit bug (java.lang.NoClassDefFoundError) in version 2.7+ for android api 19.

#Room for persistence#
It’s probably the most widely used database. It is fast and easy to use and provides full integration with livedata and coroutines which is what I need to load the heavy data list as fast as possible when  the app starts.

#Picasso for image loading#
Actually my first choice was Fresco as it tends to be faster but I got this weird http 410 when loading the thumbnails and I couldn’t find why. Still, Picasso did a perfect job and for a much lighter weight on the apk than Glide.

#Moshi for json parsing#
has it the fastest lib out there.

###Conclusion###
It has been a very funny and interesting project to make and I hope that this code will please you.
Also, I couldn't find the time to fully clean and  test this app. I sadly acknowledge that it was pretty hard to find free time to make this project as my current job is very demanding. Therefore I hope you will take it into account on your choice.adly acknowledge that it was pretty hard to find free time to make this project as my current job is very demanding. Therefore I hope you will take it into account on your choice.