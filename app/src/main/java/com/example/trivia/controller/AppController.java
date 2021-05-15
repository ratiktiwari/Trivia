//So we have our app control here which is going to govern the entirety of our application.
//
//144
//00:07:31,780 --> 00:07:35,260
//The whole project which is awesome.
//
//145
//00:07:35,260 --> 00:07:42,160
//Now how do we make sure that this indeed is known in the Android realm in our application that OK this
//
//146
//00:07:42,160 --> 00:07:48,910
//app controller here whenever I call this it's actually going to get the only one instance that is for
//
//147
//00:07:48,910 --> 00:07:50,380
//the entire application.
//
//148
//00:07:50,470 --> 00:07:54,720
//Well to do that we have to add this inside of our manifest.
//
//149
//00:07:54,790 --> 00:07:58,060
//Remember again the android manifest is what governs everything.
//
//150
//00:07:58,090 --> 00:08:04,650
//So anything that we want for the whole application to abide by we need to tell it.

//So  add this in AndroidManifest.xml inside of application -   android:name=".controller.AppController"

package com.example.trivia.controller;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends android.app.Application {   //Singleton class

    //Single Ton Class Definition:
//    In object-oriented programming, a singleton class is a class that can have only one object (an instance of the class) at a time.
//After first time, if we try to instantiate the Singleton class, the new variable also points to the first instance created. So whatever modifications we do to any variable inside the class through any instance, it affects the variable of the single instance created and is visible if we access that variable through any variable of that class type defined.
//To design a singleton class:
//
//Make constructor as private.
//Write a static method that has return type object of this singleton class. Here, the concept of Lazy initialization is used to write this static method.
//Normal class vs Singleton class: Difference in normal and singleton class in terms of instantiation is that, For normal class we use constructor, whereas for singleton class we use getInstance() method (Example code:I). In general, to avoid confusion we may also use the class name as method name while defining this method (Example code:II).

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;

    private RequestQueue mRequestQueue;

    public static synchronized AppController getInstance()
    {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue()  //function of return type RequestQueue
    {
        if(mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext()); 
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

//    Yes, TextUtils.isEmpty(string) is preferred.
//
//For string.isEmpty(), a null string value will throw a NullPointerException
//
//TextUtils will always return a boolean value.

    public <T> void addToRequestQueue(Request<T> req) {  //overloaded version of the above function
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
