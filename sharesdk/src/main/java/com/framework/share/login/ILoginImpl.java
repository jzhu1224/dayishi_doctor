package com.framework.share.login;

import com.chairoad.framework.util.LogUtil;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qq.QQClientNotExistException;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.utils.WechatClientNotExistException;

/**
 * Created by yanxin on 2018/3/14.
 */

public class ILoginImpl implements ILogin {

    @Override
    public void login(final LoginType loginType,final ILoginCallback callback) {

        Platform platform = ShareSDK.getPlatform(getPlatform(loginType));
        platform.SSOSetting(false);  //设置false表示使用SSO授权方式
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                LogUtil.i("LOGIN", "onComplete res: " + i);
                PlatformDb platDB = platform.getDb();//获取数平台数据DB
                ILoginResult loginBean = new ILoginResult();
                loginBean.setCode(ILoginCode.SUCCESS);
                loginBean.setUserId(platDB.getUserId());
                loginBean.setToken(platDB.getToken());
                loginBean.setUserName(platDB.getUserName());
                if (callback != null) {
                    callback.onLogin(loginBean);
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtil.i("LOGIN", "onError res: " + i);
                platform.removeAccount(true);
                if (callback != null) {
                    ILoginResult loginBean = new ILoginResult();
                    if (throwable instanceof WechatClientNotExistException || throwable instanceof QQClientNotExistException) {
                        loginBean.setCode(ILoginCode.NOT_INSTALL);
                        loginBean.setMsg("未安装"+loginType.getName()+"客户端");
                    } else {
                        loginBean.setCode(ILoginCode.FAIL);
                        loginBean.setMsg(loginType.getName()+"登录失败");
                    }
                    callback.onLogin(loginBean);
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtil.i("LOGIN", "onCancel res: " + i);
                platform.removeAccount(true);
                if (callback != null) {
                    ILoginResult loginBean = new ILoginResult();
                    loginBean.setCode(ILoginCode.CANCEL);
                    loginBean.setMsg("取消"+loginType.getName()+"登录");
                    callback.onLogin(loginBean);
                }
            }
        });
        platform.showUser(null);//授权并获取用户信息
    }

    private String getPlatform(LoginType loginType) {
        if (LoginType.WECHAT.equals(loginType)) {
            return Wechat.NAME;
        } else if (LoginType.QQ.equals(loginType)) {
            return QQ.NAME;
        }
        return "";
    }

}
