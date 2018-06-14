package com.jkdys.doctor.utils;

import com.jkdys.doctor.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class AppLogger {
    public static void initLogger() {
//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                // (Optional) Whether to show thread info or not. Default true
//                .showThreadInfo(false)
//                // (Optional) How many method line to show. Default 2
//                .methodCount(0)
//                // (Optional) Hides internal method calls up to offset. Default 5
//                .methodOffset(5)
//                // .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
//                // .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .build();

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}