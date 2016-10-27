package com.tangce.fastcode;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.tangce.fastcode.routerlib.Router;
import com.tangce.fastcode.routerlib.exception.NotFoundException;
import com.tangce.fastcode.routerlib.module.RouteCreator;
import com.tangce.fastcode.routerlib.module.RouteMap;
import com.tangce.fastcode.routerlib.route.ActivityRouteBundleExtras;
import com.tangce.fastcode.routerlib.route.RouteCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tanck on 10/26/2016.
 * <p/>
 * Describe:
 */
public class App extends Application {

    private static final String TAG = "Tanck";

    @Override
    public void onCreate() {
        super.onCreate();
        initRouter();
    }

    private void initRouter() {
        Router.addRouteCreator(new RouterRule());
        Router.setGlobalRouteCallback(new RouteCallback() {
            @Override
            public boolean interceptOpen(Uri uri, Context context, ActivityRouteBundleExtras extras) {
                Log.d(TAG, "interceptOpen:" + uri.toString());
                return false;
            }

            @Override
            public void notFound(Uri uri, NotFoundException e) {
                Log.d(TAG, "notFound:" + uri.toString());
            }

            @Override
            public void onOpenSuccess(Uri uri, String clzName) {

            }

            @Override
            public void onOpenFailed(Uri uri, Exception e) {

            }
        });
    }

    private class RouterRule implements RouteCreator {

        @Override
        public Map<String, RouteMap> createRouteRules() {
            Map<String, RouteMap> rule = new HashMap<>();
            rule.put("Tanck://login", new RouteMap("com.tangce.fastcode.LoginActivity"));
            return rule;
        }
    }
}
