package com.duanqu.qupaisample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.FailureCallback;
import com.duanqu.qupai.sdk.android.QupaiService;
import com.duanqu.qupai.sdk.callback.SaveFileCallback;
import com.duanqu.qupaisample.common.Contant;
import com.duanqu.qupaisample.common.RequestCode;
import com.duanqu.qupaisample.utils.AppConfig;
import com.duanqu.qupaisample.utils.AppGlobalSetting;
import com.duanqu.qupaisample.utils.FileUtils;

public class MainActivity extends Activity {
    Button btn_record;
    EditText edit_time;
    EditText edit_rate;
    EditText edit_watermark;
    private double mDurationLimit;
    private int mVideoBitrate;
    private int mWaterMark = -1;
    private String waterMarkPath ;
    private Switch st_more_music;
    private Switch st_lead_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_main);
        btn_record = (Button) findViewById(R.id.record);
        edit_rate = (EditText) findViewById(R.id.edit_rate);
        edit_time = (EditText) findViewById(R.id.edit_time);
        st_more_music = (Switch) findViewById(R.id.st_more_music);
        st_lead_in = (Switch) findViewById(R.id.st_lead_in);
        edit_watermark = (EditText) findViewById(R.id.edit_watermark);

        btn_record.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                QupaiService qupaiService = AlibabaSDK
                        .getService(QupaiService.class);

                if (qupaiService == null) {
                    Toast.makeText(MainActivity.this, "插件没有初始化，无法获取 QupaiService",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (!TextUtils.isEmpty(edit_time.getText())) {
                    mDurationLimit = Double.valueOf(edit_time.getText().toString());
                } else {
                    mDurationLimit = Contant.DEFAULT_DURATION_LIMIT;
                }
                if (!TextUtils.isEmpty(edit_rate.getText())) {
                    mVideoBitrate = Integer.valueOf(edit_rate.getText().toString());
                } else {
                    mVideoBitrate = Contant.DEFAULT_BITRATE;
                }


                Intent moreMusic = new Intent();
                if (st_more_music.isChecked()) {
                    //是否需要更多音乐页面--如果不需要填空
                    moreMusic.setClass(MainActivity.this, MoreMusicActivity.class);
                } else {
                    moreMusic = null;
                }

                mWaterMark = Integer.valueOf(TextUtils.isEmpty(edit_watermark.getText())?"1":edit_watermark.getText().toString());
                waterMarkPath = Contant.WATER_MARK_PATH ;

                Contant.VIDEOPATH = FileUtils.newOutgoingFilePath();
                Contant.THUMBPATH = FileUtils.newOutgoingFilePath()+ ".jpg";

                qupaiService.initRecord( mDurationLimit, mVideoBitrate, moreMusic, st_lead_in.isChecked(),waterMarkPath,mWaterMark);


                final AppGlobalSetting sp = new AppGlobalSetting(getApplicationContext());
                Boolean isGuideShow = sp.getBooleanGlobalItem(
                        AppConfig.PREF_VIDEO_EXIST_USER, true);

                /**
                 * 建议上面的initRecord只在application里面调用一次。这里为了能够开发者直观看到改变所以可以调用多次
                 */
                qupaiService.showRecordPage(MainActivity.this, RequestCode.RECORDE_SHOW, isGuideShow,
                        new FailureCallback() {
                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(MainActivity.this, "onFailure:"+ s + "CODE"+ i, Toast.LENGTH_LONG).show();
                            }
                        });

                sp.saveGlobalConfigItem(
                        AppConfig.PREF_VIDEO_EXIST_USER, false);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Log.d("onActivityResult", "requestCode" + requestCode + "data" + data);
            /**
             * 提供拷贝文件的接口，将拍摄视频和拷贝文件分开
             */
            QupaiService qupaiService = AlibabaSDK
                    .getService(QupaiService.class);
            qupaiService.copyVideoFile(data, Contant.VIDEOPATH, Contant.THUMBPATH, new SaveFileCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(MainActivity.this, "onSuccess", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(MainActivity.this, "onSuccess", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "RESULT_CANCELED", Toast.LENGTH_LONG).show();
            }
        }


    }

}
