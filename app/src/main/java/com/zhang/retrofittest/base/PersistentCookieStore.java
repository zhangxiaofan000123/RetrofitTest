package com.zhang.retrofittest.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by zhang on 2017/5/3.
 */
public class PersistentCookieStore {
    private static final String TAG = "PersistentCookieStore";
    private static final String COOKIE_PREFS = "Cookies_Prefs";

    private final Map<String, Hashtable<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;
    private Context context ;


    public PersistentCookieStore(Context context) {
        this.context = context;
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        cookies = new Hashtable<>();

        //将持久化的cookies缓存到内存中 即map cookies
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
            for (String name : cookieNames) {
                String encodedCookie = cookiePrefs.getString(name, null);
                if (encodedCookie != null) {
                    Cookie decodedCookie = decodeCookie(encodedCookie);
                    if (decodedCookie != null) {
                        if (!cookies.containsKey(entry.getKey())) {
                            cookies.put(entry.getKey(), new Hashtable<String, Cookie>());
                        }
                        cookies.get(entry.getKey()).put(name, decodedCookie);
                    }
                }
            }
        }
    }

    protected String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    public void add(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);

        //将cookies缓存到内存中 如果缓存过期 就重置此cookie
        if (!cookie.persistent()) {
            if (!cookies.containsKey(url.host())) {
                cookies.put(url.host(), new Hashtable());
            }
            cookies.get(url.host()).put(name, cookie);
        } else {
            if (cookies.containsKey(url.host())) {
                cookies.get(url.host()).remove(name);
            }
        }


        //讲cookies持久化到本地
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
        prefsWriter.putString(name, encodeCookie(new SerializableOkHttpCookies(cookie)));
        prefsWriter.apply();

        try {
            saveCookie2WebKit(url, cookie);
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "add: " + "存儲cookie2webKit 報錯");
//            ToastUtils.showToastByRunnable(myApplication.getAppContext(), "存儲cookie2webKit 報錯", Toast.LENGTH_SHORT);
        }


    }

    /**
     * 将cookie保存到webKit中
     *
     * @param url
     * @param cookie
     */
    private void saveCookie2WebKit(HttpUrl url, Cookie cookie) {
        //将cookies持久化到webKit
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();

        cookieManager.setAcceptCookie(true);
        String cookieString = cookie
                .name()
                + "="
                + cookie.value()
                + "; domain="
                + cookie.domain();
        cookieManager.setCookie(url.host(), cookieString);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

    public List<Cookie> get(HttpUrl url) {
        ArrayList<Cookie> ret = new ArrayList<>();
        if (cookies.containsKey(url.host()))
            ret.addAll(cookies.get(url.host()).values());
        return ret;
    }

    public boolean removeAll() {
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.clear();
        prefsWriter.apply();
        cookies.clear();
        return true;
    }

    public boolean remove(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);

        if (cookies.containsKey(url.host()) && cookies.get(url.host()).containsKey(name)) {
            cookies.get(url.host()).remove(name);

            SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
            if (cookiePrefs.contains(name)) {
                prefsWriter.remove(name);
            }
            prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
            prefsWriter.apply();

            return true;
        } else {
            return false;
        }
    }

    public List<Cookie> getCookies() {
        ArrayList<Cookie> ret = new ArrayList<>();
        for (String key : cookies.keySet())
            ret.addAll(cookies.get(key).values());

        return ret;
    }

    /**
     * cookies 序列化成 string
     *
     * @param cookie 要序列化的cookie
     * @return 序列化之后的string
     */
    protected String encodeCookie(SerializableOkHttpCookies cookie) {
        if (cookie == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (IOException e) {
            Log.d(TAG, "IOException in encodeCookie", e);
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    /**
     * 将字符串反序列化成cookies
     *
     * @param cookieString cookies string
     * @return cookie object
     */
    protected Cookie decodeCookie(String cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableOkHttpCookies) objectInputStream.readObject()).getCookies();
        } catch (IOException e) {
            Log.d(TAG, "IOException in decodeCookie", e);
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "ClassNotFoundException in decodeCookie", e);
        }

        return cookie;
    }

    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len-1; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}