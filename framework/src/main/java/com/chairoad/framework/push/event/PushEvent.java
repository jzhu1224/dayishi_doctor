package com.chairoad.framework.push.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.chairoad.framework.push.PushMessage;

/**
 * Created by yanxin on 2017/12/8.
 */

public class PushEvent {

    public PushMessage pushMessage;

    public PushEvent(String pushCont) {
        try {
            this.pushMessage = JSON.parseObject(pushCont, PushMessage.class, Feature.IgnoreNotMatch, Feature.AllowISO8601DateFormat);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
