package com.jkdys.doctor.ui.mine.qrcode;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.chairoad.framework.util.DeviceUtil;
import com.jkdys.doctor.R;
import com.jkdys.doctor.utils.EncodingHandler;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyQRCodeFragment extends DialogFragment {

    @BindView(R.id.img_qrcode)
    ImageView imgQRCode;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    Bitmap mQRDBitmap, mLogoBitmap;
    String mUrl, mAvatar;


    private final static int QRD_WIDHT_AND_HEIGHT_DP = 200;
    private final static int QRD_LOGO_WIDTH_AND_HEIGHT_DP = 18;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qrcode, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new CreateQRCodeTask().execute();

        mUrl = getArguments().getString("QRCodeUrl");
        mAvatar = getArguments().getString("mAvatar");

    }

    @OnClick(R.id.fr_close)
    void onCloseClicked() {
        dismiss();
    }

    class CreateQRCodeTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mLogoBitmap = Picasso.get().load(mAvatar).get();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                if (mLogoBitmap == null) {
                    mQRDBitmap = EncodingHandler.createQRCode(mUrl, QRD_WIDHT_AND_HEIGHT_DP);
                } else {
                    mQRDBitmap = EncodingHandler.createQRCodeWithLogo(mUrl, mLogoBitmap,
                            DeviceUtil.getPixelFromDip(getActivity(), QRD_WIDHT_AND_HEIGHT_DP),
                            DeviceUtil.getPixelFromDip(getActivity(), QRD_LOGO_WIDTH_AND_HEIGHT_DP));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            if (mQRDBitmap != null) {
                imgQRCode.setImageBitmap(mQRDBitmap);
            }
        }
    }
}
