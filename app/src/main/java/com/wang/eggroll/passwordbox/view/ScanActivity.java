package com.wang.eggroll.passwordbox.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.decoding.Intents;
import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.R;
import com.wang.eggroll.passwordbox.presenter.AddPresenter;
import com.wang.eggroll.passwordbox.presenter.IAddPresenter;
import com.wang.eggroll.passwordbox.utils.ImageHelper;

/**
 * Created by eggroll on 15/04/2017.
 */

public class ScanActivity extends AppCompatActivity {

    private CaptureFragment captureFragment;
    public static boolean isFlashLightOpen = false;
    int IMAGE_REQUEST = 3;
    int IMAGE_ANALYZED_SUCCESS = 104;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.scan_container, captureFragment).commit();

        LinearLayout flashlightLayout = (LinearLayout) findViewById(R.id.flash_light);
        flashlightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFlashLightOpen){
                    CodeUtils.isLightEnable(true);
                    isFlashLightOpen = true;
                }else {
                    CodeUtils.isLightEnable(false);
                    isFlashLightOpen = false;
                }
            }
        });

        LinearLayout openGalleryLayout = (LinearLayout) findViewById(R.id.open_gallery);
        openGalleryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:从系统图库选择
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST);
            }
        });
    }

    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();

            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            intent.putExtras(bundle);
            ScanActivity.this.setResult(RESULT_OK, intent);
            ScanActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            intent.putExtras(bundle);
            ScanActivity.this.setResult(RESULT_OK, intent);
            ScanActivity.this.finish();
        }
    };

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    //如果文件名中有冒号会无视冒号后面的那一部分
                    CodeUtils.analyzeBitmap(ImageHelper.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                            Toast.makeText(App.getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            intent.putExtra("result", result);
                            setResult(IMAGE_ANALYZED_SUCCESS, intent);
//                            MainActivity.onAnalizeSuccess(result);
                            finish();
                        }

                        @Override
                        public void onAnalyzeFailed() {
//                            Toast.makeText(App.getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                            MainActivity.onAnalizeFailed();
                            finish();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
