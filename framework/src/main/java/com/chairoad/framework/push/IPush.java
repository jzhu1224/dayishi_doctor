package com.chairoad.framework.push;

import android.content.Context;

/**
 * Created by yanxin on 17/9/21.
 */

public interface IPush {

    void connect(Context context);

    void reconnect(Context context);

    void registToken(Context context);

}
