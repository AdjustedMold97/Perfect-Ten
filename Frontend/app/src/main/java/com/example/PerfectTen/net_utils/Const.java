package com.example.PerfectTen.net_utils;

/**
 * Class responsible for storing many of the
 * constants used throughout our code.
 *
 * This includes urls, common responses,
 * and tags.
 *
 * @author Jae Swanepoel
 */
public class Const {

    //========================================= URLs ===============================================

    public static final String POSTING_URL = "http://coms-309-060.cs.iastate.edu:8080/posts/new/";
    public static final String LOGIN_URL = "http://coms-309-060.cs.iastate.edu:8080/login";
    public static final String POST_LIST_URL = "http://coms-309-060.cs.iastate.edu:8080/posts/list";
    public static final String USER_POST_URL_1 = "http://coms-309-060.cs.iastate.edu:8080/user/";
    public static final String USER_POST_URL_2 = "/posts/list";
    public static final String SIGN_UP_URL = "http://coms-309-060.cs.iastate.edu:8080/user/new";
    public static final String ADD_FRIEND_URL_1 = "http://coms-309-060.cs.iastate.edu:8080/user/";
    public static final String ADD_FRIEND_URL_2 = "/friends/new";
    public static final String FRIEND_LIST_URL_1 = "http://coms-309-060.cs.iastate.edu:8080/user/";
    public static final String FRIEND_LIST_URL_2 = "/friends/list/usernames";
    public static final String REMOVE_FRIEND_URL_1 = "http://coms-309-060.cs.iastate.edu:8080/user/";
    public static final String REMOVE_FRIEND_URL_2 = "/friends/remove";
    public static final String BLOCK_USER_URL_1 = "http://coms-309-060.cs.iastate.edu:8080/user/";
    public static final String BLOCK_USER_URL_2 = "/blocked/new";
    public static final String UNBLOCK_USER_URL_1 = "http://coms-309-060.cs.iastate.edu:8080/user/";
    public static final String UNBLOCK_USER_URL_2 = "/blocked/remove";
    public static final String BLOCKED_LIST_URL_1 = "http://coms-309-060.cs.iastate.edu:8080/user/";
    public static final String BLOCKED_LIST_URL_2 = "/blocked/list/usernames";
    public static final String USER_LIST_URL = "http://coms-309-060.cs.iastate.edu:8080/user/list/usernames";
    public static final String GET_POST_URL = "http://coms-309-060.cs.iastate.edu:8080/posts/";
    public static final String CREATE_COMMENT_URL = "http://coms-309-060.cs.iastate.edu:8080/posts/new/comment/";
    public static final String COMMENT_LIST_URL_1 = "http://coms-309-060.cs.iastate.edu:8080/posts/";
    public static final String COMMENT_LIST_URL_2 = "/all";


    //TODO these
    public static final String CHANGE_USERNAME_URL = "";
    public static final String CHANGE_PASSWORD_URL = "";
    public static final String CHANGE_EMAIL_URL = "";
    public static final String CHANGE_PFP_URL = "";

    public static final String DELETE_USER_URL = "";
    public static final String DELETE_POST_URL = "";
    public static final String DELETE_COMMENT_URL = "http://coms-309-060.cs.iastate.edu:8080/posts/rm/";

    //public static final String USER_PRIVILEGE_LEVEL_1 = "http://coms-309-060.cs.iastate.edu:8080/user/";
    //public static final String USER_PRIVILEGE_LEVEL_2 = "/privilege";

    public static final String USER_USERNAME = "http://coms-309-060.cs.iastate.edu:8080/user/";




    //======================================== JSON ================================================

    public static final String SUCCESS_MSG = "success";
    public static final String RESPONSE_TAG = "Server Response ";
    public static final String ERROR_RESPONSE_TAG = "Error Response ";
    public static final String RESULT_TAG = "Result ";

    //===================================== ERROR MESSAGES =========================================

    public static final String GENERIC_ERROR = "error1";
    public static final String USER_ERROR = "error2";
    public static final String EMAIL_ERROR = "error3";
    public static final String PASSWORD_ERROR = "error4";
    public static final String GENERIC_ERROR_TEXT = "Something went wrong...";

    //===================================== KEYS =================================================

    public static final String MESSAGE_KEY = "message";
    public static final String PASSWORD_KEY = "password";
    public static final String EMAIL_KEY = "email";
    public static final String USERNAME_KEY = "username";
    public static final String TITLE_KEY = "title";
    public static final String POST_USER_KEY = "uname";
    public static final String ID_KEY = "id";
    public static final String USER_KEY = "user";
    public static final String TIME_KEY = "time";
    public static final String PLEVEL_KEY = "plevel";

}