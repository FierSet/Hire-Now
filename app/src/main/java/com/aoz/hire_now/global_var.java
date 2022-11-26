package com.aoz.hire_now;

import android.app.Application;

public class global_var extends Application {

    public static String user, Link;


    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        global_var.user = user;
    }

    public static String getLink() {
        return Link;
    }

    public static void setLink(String link) {
        Link = link;
    }

}
