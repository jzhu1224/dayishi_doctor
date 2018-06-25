package com.jkdys.doctor.core.chat;

import android.content.Context;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.List;

public class UserProfileManager {

	/**
	 * application context
	 */
	protected Context appContext = null;

	/**
	 * init flag: test if the sdk has been inited before, we don't need to init
	 * again
	 */
	private boolean sdkInited = false;

	/**
	 * HuanXin sync contact nick and avatar listener
	 */
	private List<ChatHelper.DataSyncListener> syncContactInfosListeners;

	private boolean isSyncingContactInfosWithServer = false;

	private EaseUser currentUser;

	public UserProfileManager() {
	}

	public synchronized boolean init(Context context) {
		if (sdkInited) {
			return true;
		}
		//ParseManager.getInstance().onInit(context);
		syncContactInfosListeners = new ArrayList<ChatHelper.DataSyncListener>();
		sdkInited = true;
		return true;
	}

	public void addSyncContactInfoListener(ChatHelper.DataSyncListener listener) {
		if (listener == null) {
			return;
		}
		if (!syncContactInfosListeners.contains(listener)) {
			syncContactInfosListeners.add(listener);
		}
	}

	public void removeSyncContactInfoListener(ChatHelper.DataSyncListener listener) {
		if (listener == null) {
			return;
		}
		if (syncContactInfosListeners.contains(listener)) {
			syncContactInfosListeners.remove(listener);
		}
	}

	public void asyncFetchContactInfosFromServer(List<String> usernames, final EMValueCallBack<List<EaseUser>> callback) {
		if (isSyncingContactInfosWithServer) {
			return;
		}
		isSyncingContactInfosWithServer = true;
//		ParseManager.getInstance().getContactInfos(usernames, new EMValueCallBack<List<EaseUser>>() {
//
//			@Override
//			public void onSuccess(List<EaseUser> value) {
//				isSyncingContactInfosWithServer = false;
//				// in case that logout already before server returns,we should
//				// return immediately
//				if (!DemoHelper.getInstance().isLoggedIn()) {
//					return;
//				}
//				if (callback != null) {
//					callback.onSuccess(value);
//				}
//			}
//
//			@Override
//			public void onError(int error, String errorMsg) {
//				isSyncingContactInfosWithServer = false;
//				if (callback != null) {
//					callback.onError(error, errorMsg);
//				}
//			}
//
//		});

	}

	public void notifyContactInfosSyncListener(boolean success) {
		for (ChatHelper.DataSyncListener listener : syncContactInfosListeners) {
			listener.onSyncComplete(success);
		}
	}

	public boolean isSyncingContactInfoWithServer() {
		return isSyncingContactInfosWithServer;
	}

	public synchronized void reset() {
		isSyncingContactInfosWithServer = false;
		currentUser = null;
		PreferenceManager.getInstance().removeCurrentUserInfo();
	}

	public synchronized EaseUser getCurrentUserInfo() {
		if (currentUser == null) {
			String username = EMClient.getInstance().getCurrentUser();
			currentUser = new EaseUser(username);
			String nick = getCurrentUserNick();
			currentUser.setNick((nick != null) ? nick : username);
			currentUser.setAvatar(getCurrentUserAvatar());
		}
		return currentUser;
	}

	public boolean updateCurrentUserNickName(final String nickname) {
		boolean isSuccess = true;//ParseManager.getInstance().updateParseNickName(nickname);
		if (isSuccess) {
			setCurrentUserNick(nickname);
		}
		return isSuccess;
	}

//	public String uploadUserAvatar(byte[] data) {
//		String avatarUrl = ParseManager.getInstance().uploadParseAvatar(data);
//		if (avatarUrl != null) {
//			setCurrentUserAvatar(avatarUrl);
//		}
//		return avatarUrl;
//	}

	public void asyncGetCurrentUserInfo() {
//		UserInfo userInfo = UserInfoUtil.instence().get();
//		if (userInfo == null)
//			return;
//		setCurrentUserNick(userInfo.getRealName());
//		int drawableResourceId;
//		if (userInfo.getGender() == 0) {
//			drawableResourceId = HXApplication.getApplication().getResources().getIdentifier("female", "drawable", HXApplication.getApplication().getPackageName());
//		} else {
//			drawableResourceId = HXApplication.getApplication().getResources().getIdentifier("male", "drawable", HXApplication.getApplication().getPackageName());
//		}
//		setCurrentUserAvatar(String.valueOf(drawableResourceId));
//		ParseManager.getInstance().asyncGetCurrentUserInfo(new EMValueCallBack<EaseUser>() {
//
//			@Override
//			public void onSuccess(EaseUser value) {
//			    if(value != null){
//    				setCurrentUserNick(value.getNick());
//    				setCurrentUserAvatar(value.getAvatar());
//			    }
//			}
//
//			@Override
//			public void onError(int error, String errorMsg) {
//
//			}
//		});

	}
	public void asyncGetUserInfo(final String username,final EMValueCallBack<EaseUser> callback){
		//ParseManager.getInstance().asyncGetUserInfo(username, callback);
	}
	public void setCurrentUserNick(String nickname) {
		getCurrentUserInfo().setNick(nickname);
		PreferenceManager.getInstance().setCurrentUserNick(nickname);
	}

	public void setCurrentUserAvatar(String avatar) {
		getCurrentUserInfo().setAvatar(avatar);
		PreferenceManager.getInstance().setCurrentUserAvatar(avatar);
	}

	private String getCurrentUserNick() {
		return PreferenceManager.getInstance().getCurrentUserNick();
	}

	private String getCurrentUserAvatar() {
		return PreferenceManager.getInstance().getCurrentUserAvatar();
	}

}