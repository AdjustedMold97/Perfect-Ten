package com.example.homescreen.app;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.homescreen.net_utils.LruBitmapCache;

/**
 * The AppController is responsible for much of
 * the background activity on the client side
 * of Perfect Ten.
 *
 * Particularly, the getInstance().getRequestQueue() method
 * is used throughout many of the Perfect Ten activities
 * to add HTTP requests to the Request Queue and make
 * HTTP requests to the Perfect Ten server.
 *
 * Additionally, the AppController is used to store
 * the global variables username and targetUser.
 *
 * @author Jae Swanepoel
 */
public class AppController extends Application {

    public static final String TAG = AppController.class
        .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;

    /**
     * Basic initialization.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * Returns this specific instance of the AppController.
     * @return mInstance This instance of AppController.
     */
    public static synchronized AppController getInstance() { return mInstance; }

    /**
     * Returns the Request Queue.
     * Used throughout Perfect Ten to send
     * HTTP requests to the server.
     * @return mRequestQueue The AppController's RequestQueue
     */
    public RequestQueue getRequestQueue() { if (mRequestQueue == null) {
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

        return mRequestQueue;
    }

    /**
     * Seldom used in most of the Perfect Ten code.
     * @return mImageLoader
     */
    public ImageLoader getImageLoader() { getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    /**
     * Adds an HTTP Request to the RequestQueue.
     *
     * @param req The HTTP request being sent to the server.
     * @param tag The tag attached to the HTTP request.
     * @param <T> The kind of request being attached.
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Adds an HTTP Request to the RequestQueue
     * without a tag.
     *
     * @param req The HTTP request being sent to the server.
     * @param <T> The specific kind of request being sent.
     */
    public <T> void addToRequestQueue(Request<T> req) { req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancels all HTTP requests currently in the queue.
     *
     * @param tag The tag attached.
     */
    public void cancelPendingRequests(Object tag) { if (mRequestQueue != null) {
        mRequestQueue.cancelAll(tag);
    }
    }

    /**
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

    /**
     * Getter for username.
     * @return username The global username variable.
     */
    static public String getUsername() {
        return username;
    }

    /**
     * Setter for username.
     * @param _username The username being set.
     */
    public static void setUsername(String _username) {
        username = _username;
    }

    /*
     * targetUser is a global variable that
     * we use for addressing other users (aside from the main user)
     * in the app. Specifically:
     *
     * - Getting data for profile page
     * -
     *
     */

    static private String targetUser;

    /**
     * The getter for targetUser
     *
     * @return targetUser
     */
    static public String getTargetUser() {
        return targetUser;
    }

    /**
     * The setter for targetUser.
     *
     * @param _targetUser The String being specified for targetUser.
     */
    static public void setTargetUser(String _targetUser) {
        targetUser = _targetUser;
    }

    /*
     * Used to retrieve post information
     * for the PostView
     */
    private static int postID;

    public static int getPostID() { return postID; }
    public static void setPostID(int _postID) { postID = _postID; }
}