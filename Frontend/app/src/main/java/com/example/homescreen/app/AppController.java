package com.example.homescreen.app;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.homescreen.net_utils.LruBitmapCache;

public class AppController extends Application {

    public static final String TAG = AppController.class
        .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() { return mInstance;
 
//    You don’t have to change anything in the class.

    }

    public RequestQueue getRequestQueue() { if (mRequestQueue == null) {
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() { getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) { req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) { if (mRequestQueue != null) {
        mRequestQueue.cancelAll(tag);
    }
    }

    /*
     * Global Variable for username
     * with getter and setter.
     *
     * The username is important for:
     *  - Maintaining Friends list
     *  - Assigning posts with a user
     *  - Populating "Home" feed
     *
     * - Jae Swanepoel
     */
    static private String username;

    static public String getUsername() {
        return username;
    }

    public static void setUsername(String _username) {
        username = _username;
    }

    /*
     * targetUser is a global variable that
     * we use for addressing other users (aside from the main user)
     * in the app. Specifically:
     * - Getting data for profile page
     * -
     *
     */

    static private String targetUser;

    static public String getTargetUser() {
        return targetUser;
    }

    static public void setTargetUser(String _targetUser) {
        targetUser = _targetUser;
    }
}