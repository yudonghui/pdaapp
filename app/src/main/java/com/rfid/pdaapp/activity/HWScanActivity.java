package com.rfid.pdaapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huawei.hms.hmsscankit.OnLightVisibleCallBack;
import com.huawei.hms.hmsscankit.OnResultCallback;
import com.huawei.hms.hmsscankit.RemoteView;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.dialogs.EditeDialog;
import com.rfid.pdaapp.utils.DeviceUtils;
import com.rfid.pdaapp.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;

public class HWScanActivity extends BaseActivity {

    @BindView(R.id.rim)
    FrameLayout rim;
    @BindView(R.id.tv_open_hint)
    TextView tvOpenHint;
    @BindView(R.id.iv_open_light)
    ImageView ivOpenLight;
    @BindView(R.id.scan_auto_layout)
    LinearLayout scanAutoLayout;
    @BindView(R.id.iv_flash)
    ImageView ivFlash;
    private int mScreenWidth;
    private int mScreenHeight;
    private int SCAN_FRAME_SIZE = 240;
    private RemoteView remoteView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_h_w_scan;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        mScreenWidth = DeviceUtils.getDisplayWidth(this);
        mScreenHeight = DeviceUtils.getDisplayHeight(this);
        int scanFrameSize = (int) (SCAN_FRAME_SIZE * getResources().getDisplayMetrics().density);
        Rect rect = new Rect();
        rect.left = mScreenWidth / 2 - scanFrameSize / 2;
        rect.right = mScreenWidth / 2 + scanFrameSize / 2;
        rect.top = mScreenHeight / 2 - scanFrameSize / 2;
        rect.bottom = mScreenHeight / 2 + scanFrameSize / 2;
        Rect rect1 = new Rect(0, 0, mScreenWidth, mScreenHeight); // ???????????????????????????????????????????????????????????????
        /**
         *setContinuouslyScan ?????????true?????????????????????????????????????????????????????????????????????false?????????????????????????????????????????????????????????????????????
         */
        remoteView = new RemoteView.Builder().setContext(this)
                .setBoundingBox(rect1)
                .setFormat(HmsScan.ALL_SCAN_TYPE)
                .build();
        initListener();
        remoteView.onCreate(savedInstanceState);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        rim.addView(remoteView, layoutParams);

    }


    @OnClick({R.id.iv_open_light, R.id.iv_handle_input, R.id.iv_flash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_open_light:
                remoteView.switchLight();
                break;
            case R.id.iv_handle_input:
                new EditeDialog(mContext, "?????????", new EditeDialog.EditInterface() {
                    @Override
                    public void onClick(String s) {
                        if (TextUtils.isEmpty(s)) {
                            finish();
                        } else {
                            backResult(s);
                        }
                    }
                });
                break;
            case R.id.iv_flash:
                //?????????????????????????????????????????????????????????
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                Intent intent2 = Intent.createChooser(intent, "?????????????????????");
                startActivityForResult(intent2, Constant.REQUEST_CODE0);
                break;
        }
    }

    private void initListener() {
        //??????????????????
        remoteView.setOnResultCallback(new OnResultCallback() {
            @Override
            public void onResult(HmsScan[] result) {
                if (result != null && result.length > 0 && result[0] != null && !TextUtils.isEmpty(result[0].getOriginalValue())) {
                    LogUtils.e("???????????????" + result[0].getOriginalValue());
                    vibrate();
                    backResult(result[0].getOriginalValue());
                }
            }
        });
        // ????????????????????????????????????????????????????????????????????????
        remoteView.setOnLightVisibleCallback(new OnLightVisibleCallBack() {
            @Override
            public void onVisibleChanged(boolean b) {
              /*  if (b) {
                    tvOpenHint.setVisibility(View.VISIBLE);
                } else {
                    tvOpenHint.setVisibility(View.GONE);
                }*/
            }
        });
    }

    private void vibrate() {
        //??????????????????????????????setDataSource
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    private void backResult(String result) {
        Intent intent = getIntent();
        intent.putExtra("scanResult", result);
        setResult(Constant.RESULT_CODE0, intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        remoteView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        remoteView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        remoteView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        remoteView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remoteView.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE0 && resultCode == RESULT_OK) {
            // data???Intent?????????data.getData???????????????????????????Uri???
            Bitmap bitmap = null;
            try {
                // bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator()
                    .setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE, HmsScan.DATAMATRIX_SCAN_TYPE)
                    .setPhotoMode(true).create();
            HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(this, bitmap, options);
            // ?????????????????????
            if (hmsScans != null && hmsScans.length > 0) {
                backResult(hmsScans[0].getOriginalValue());
            }
        }
    }

}
