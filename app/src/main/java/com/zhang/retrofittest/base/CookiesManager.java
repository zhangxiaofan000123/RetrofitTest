package com.zhang.retrofittest.base;

/**
 * Created by zhang on 2017/5/3.
 */


import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 */
public class CookiesManager implements CookieJar {
    private static PersistentCookieStore cookieStore;

    public CookiesManager(Context _context) {
        if (cookieStore == null) {
            synchronized (CookiesManager.class) {
                if (cookieStore == null) {
                    cookieStore = new PersistentCookieStore(_context);
                }
            }
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }

    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }

    public static PersistentCookieStore getCookieStore() {
        return cookieStore;
    }
}