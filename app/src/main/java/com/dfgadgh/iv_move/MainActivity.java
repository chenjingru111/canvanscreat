package com.dfgadgh.iv_move;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import android.Manifest;


public class MainActivity extends AppCompatActivity {

    private ImageView iv_1;
    private int screenHeightHalf;

    private int originalLeft, originalTop, originalRight, originalBottom;
    private Switch switchButton;
    private ScaleGestureDetector scaleGestureDetector;
    private RelativeLayout rl_all;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // 已经被授权，可以执行相应的操作
        } else {
            // 未被授权，需要申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }


        screenHeightHalf = getScreenHeightHalf(MainActivity.this);
        switchButton = findViewById(R.id.switchButton);
        iv_1 = (ImageView) findViewById(R.id.iv_1);
        rl_all = (RelativeLayout) findViewById(R.id.rl_all);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        iv_1.setOnTouchListener(touchListener);


        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    iv_1.setOnTouchListener(touchListener2);
                }else {
                    iv_1.setOnTouchListener(touchListener);
                }
            }
        });

        findViewById(R.id.btn_download).setOnClickListener(v -> {
            Bitmap bitmap = CanvanCreat.captureBackgroundAndPicture(rl_all, iv_1);
            DownloadImage.saveBitmapToGallery_background(this,bitmap);
        });
    }

    private View.OnTouchListener touchListener2=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            scaleGestureDetector.onTouchEvent(event);
            return true;
        }
    };



    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        private int lastX, lastY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    originalLeft = v.getLeft();
                    originalTop = v.getTop();
                    originalRight = v.getRight();
                    originalBottom = v.getBottom();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //测试控件移动的位置
                    int top = v.getTop();
                    int screenHeight = screenHeightHalf * 2;
                    Log.d("TAG", "ImageView Top: " + top + ", Screen Height: " + screenHeight);

                    moveView(v, event.getRawX() - lastX, event.getRawY() - lastY);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    switch (v.getId()){
                        case R.id.iv_1:
                            //Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    // 处理抬起事件
                    /*if (v.getTop()<500) {
                        // 返回原始位置
                        Toast.makeText(MainActivity.this, "超过1/2屏幕高度", Toast.LENGTH_SHORT).show();
                    }else {
                        v.layout(originalLeft, originalTop, originalRight, originalBottom);
                    }*/

                    break;
            }
            return true;
        }
    };

    private void moveView(View view, float dx, float dy) {
        int left = view.getLeft() + (int) dx;
        int top = view.getTop() + (int) dy;
        int right = view.getRight() + (int) dx;
        int bottom = view.getBottom() + (int) dy;
        view.layout(left, top, right, bottom);
    }
    public int getScreenHeightHalf(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels / 2;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float scaleFactor = 1.0f;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));

            iv_1.setScaleX(scaleFactor);
            iv_1.setScaleY(scaleFactor);

            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            // 检查授权结果
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限被授予，可以执行相应的操作
            } else {
                // 权限被拒绝，需要提示用户手动授权或进行其他处理
            }
        }
    }


}