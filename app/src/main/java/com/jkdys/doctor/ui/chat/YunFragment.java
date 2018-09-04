package com.jkdys.doctor.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.jkdys.doctor.R;
import com.jkdys.doctor.core.chat.ChatConstant;
import com.jkdys.doctor.core.event.OnNewMessageArriveEvent;
import com.jkdys.doctor.event.SyncDataCompleteEvent;
import com.jkdys.doctor.ui.MvpFragment;
import com.jkdys.doctor.ui.chat.doctor.search.SearchDoctorActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

public class YunFragment extends MvpFragment<IYunView,YunPresenter> implements IYunView {

    private final static int MSG_REFRESH = 2;
    protected boolean hidden;

    @BindView(R.id.list)
    ListView conversationListView;
//    @BindView(R.id.fl_error_item)
//    FrameLayout errorItemContainer;
    @BindView(R.id.empty_view)
    View emptyView;

    protected boolean isConflict;
    private TextView errorText;

    private YunAdapter yunAdapter;

    @Inject
    YunPresenter yunPresenter;


    @Override
    protected void initViews(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_yun;
    }

    @NonNull
    @Override
    public YunPresenter createPresenter() {
        getActivityComponent().inject(this);
        return yunPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        super.onActivityCreated(savedInstanceState);
    }


    public void initView() {
        toolbar.setTitle("消息");
        View rightView = LayoutInflater.from(getContext()).inflate(R.layout.layout_yun_right_view, null);
        toolbar.addRightView(rightView,R.id.right_layout);
        rightView.findViewById(R.id.btn_friends).setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MyFriendsActivity.class);
            startActivity(intent);
        });

        rightView.findViewById(R.id.btn_add_friends).setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SearchDoctorActivity.class);
            startActivity(intent);
        });

        conversationListView.setEmptyView(emptyView);
//        View errorView = View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
//        errorItemContainer.addView(errorView);
//        errorText = errorView.findViewById(R.id.tv_connect_errormsg);

        yunAdapter = new YunAdapter(getContext());
        conversationListView.setAdapter(yunAdapter);

        conversationListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra(ChatConstant.EXTRA_USER_ID, yunAdapter.getItem(i).conversationId());
            startActivity(intent);
        });
        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED
                    || error == EMError.USER_KICKED_BY_CHANGE_PASSWORD || error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                isConflict = true;
            } else {
                handler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onConnected() {
            handler.sendEmptyMessage(1);
        }
    };

    protected Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    onConnectionDisconnected();
                    break;
                case 1:
                    onConnectionConnected();
                    break;
                case MSG_REFRESH:
                    yunPresenter.loadList();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * connected to server
     */
    protected void onConnectionConnected() {
        //errorItemContainer.setVisibility(View.GONE);
    }

    /**
     * disconnected with server
     */
    protected void onConnectionDisconnected() {
//        errorItemContainer.setVisibility(View.VISIBLE);
//        if (NetUtils.hasNetwork(getActivity())) {
//            errorText.setText(R.string.can_not_connect_chat_server_connection);
//        } else {
//            errorText.setText(R.string.the_current_network);
//        }
    }

    /**
     * refresh ui
     */
    public void refresh() {
        if (!handler.hasMessages(MSG_REFRESH)) {
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(connectionListener);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isConflict) {
            outState.putBoolean("isConflict", true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(OnNewMessageArriveEvent event) {
        refresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SyncDataCompleteEvent event) {
        refresh();
    }

    @Override
    public void onLoadSuccess(final List<EMConversation> list) {
        toolbar.post(() -> {
            yunAdapter.clear();
            yunAdapter.addData(list);
        });
    }

    @Override
    public void showLoading(boolean pullToRefresh) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showError(String message) {

    }
}
