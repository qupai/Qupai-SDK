package com.duanqu.qupaisample.app;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.duanqu.qupai.sdk.android.QupaiService;
import com.duanqu.qupaisample.MoreMusicActivity;
import com.duanqu.qupaisample.common.Contant;

/**
 * 初始化操作，更多音乐为可配置选项。
 * Created by Mulberry on 2015/7/7.
 */
public class TestApplicaticon extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 集成必须要做的初始化
         */
        AlibabaSDK.turnOnDebug();
        AlibabaSDK.asyncInit(this, new InitResultCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(TestApplicaticon.this, "初始化成功 ", Toast.LENGTH_SHORT)
                        .show();
                QupaiService qupaiService = AlibabaSDK
                        .getService(QupaiService.class);

                Intent moreMusic = new Intent();
                    //是否需要更多音乐页面--如果不需要填空
                moreMusic.setClass(TestApplicaticon.this, MoreMusicActivity.class);

                qupaiService.initRecord(Contant.DEFAULT_DURATION_LIMIT, Contant.DEFAULT_DURATION_LIMIT, moreMusic, true, Contant.WATER_MARK_PATH, 1);

                if(qupaiService != null){
                    qupaiService.addMusic(0, "Athena", "assets://Qupai/music/Athena");
                    qupaiService.addMusic(1, "Box Clever", "assets://Qupai/music/Box Clever");
                    qupaiService.addMusic(2, "Byebye love", "assets://Qupai/music/Byebye love");
                    qupaiService.addMusic(3, "chuangfeng", "assets://Qupai/music/chuangfeng");
                    qupaiService.addMusic(4, "Early days", "assets://Qupai/music/Early days");
                    qupaiService.addMusic(5, "Faraway", "assets://Qupai/music/Faraway");
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(TestApplicaticon.this, "初始化失败 " + s, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
