package com.mukmenev.android.findjob.classes;

public class UserSingleton{
    public static User user;

    public User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserSingleton.user = user;
    }
}
