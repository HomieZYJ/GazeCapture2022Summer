package com.test.cameraxdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class ViewPointActivity1_1 extends AppCompatActivity {

    //定义红点和数字的数组
    int[][] redpoint_id_array= {{R.id.RedPoint0_0,R.id.RedPoint0_1,R.id.RedPoint0_2,R.id.RedPoint0_3,R.id.RedPoint0_4},
            {R.id.RedPoint1_0,R.id.RedPoint1_1,R.id.RedPoint1_2,R.id.RedPoint1_3,R.id.RedPoint1_4},
            {R.id.RedPoint2_0,R.id.RedPoint2_1,R.id.RedPoint2_2,R.id.RedPoint2_3,R.id.RedPoint2_4},
            {R.id.RedPoint3_0,R.id.RedPoint3_1,R.id.RedPoint3_2,R.id.RedPoint3_3,R.id.RedPoint3_4},
            {R.id.RedPoint4_0,R.id.RedPoint4_1,R.id.RedPoint4_2,R.id.RedPoint4_3,R.id.RedPoint4_4},
            {R.id.RedPoint5_0,R.id.RedPoint5_1,R.id.RedPoint5_2,R.id.RedPoint5_3,R.id.RedPoint5_4},
            {R.id.RedPoint6_0,R.id.RedPoint6_1,R.id.RedPoint6_2,R.id.RedPoint6_3,R.id.RedPoint6_4},
            {R.id.RedPoint7_0,R.id.RedPoint7_1,R.id.RedPoint7_2,R.id.RedPoint7_3,R.id.RedPoint7_4},
            {R.id.RedPoint8_0,R.id.RedPoint8_1,R.id.RedPoint8_2,R.id.RedPoint8_3,R.id.RedPoint8_4},
            {R.id.RedPoint9_0,R.id.RedPoint9_1,R.id.RedPoint9_2,R.id.RedPoint9_3,R.id.RedPoint9_4},
            {R.id.RedPoint10_0,R.id.RedPoint10_1,R.id.RedPoint10_2,R.id.RedPoint10_3,R.id.RedPoint10_4}};

    int[][] num_id_array={{R.id.num0_0,R.id.num0_1,R.id.num0_2,R.id.num0_3,R.id.num0_4},
            {R.id.num1_0,R.id.num1_1,R.id.num1_2,R.id.num1_3,R.id.num1_4},
            {R.id.num2_0,R.id.num2_1,R.id.num2_2,R.id.num2_3,R.id.num2_4},
            {R.id.num3_0,R.id.num3_1,R.id.num3_2,R.id.num3_3,R.id.num3_4},
            {R.id.num4_0,R.id.num4_1,R.id.num4_2,R.id.num4_3,R.id.num4_4},
            {R.id.num5_0,R.id.num5_1,R.id.num5_2,R.id.num5_3,R.id.num5_4},
            {R.id.num6_0,R.id.num6_1,R.id.num6_2,R.id.num6_3,R.id.num6_4},
            {R.id.num7_0,R.id.num7_1,R.id.num7_2,R.id.num7_3,R.id.num7_4},
            {R.id.num8_0,R.id.num8_1,R.id.num8_2,R.id.num8_3,R.id.num8_4},
            {R.id.num9_0,R.id.num9_1,R.id.num9_2,R.id.num9_3,R.id.num9_4},
            {R.id.num10_0,R.id.num10_1,R.id.num10_2,R.id.num10_3,R.id.num10_4}};

    int[][] isVisited={{0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0}};

    //录像功能相关变量
    private static final String TAG = "CameraTestActivity";
    private PreviewView pvCameraPreview;
    private ImageCapture mImageCapture;
    private VideoCapture mVideoCapture;
    private Camera mCamera;
    private int CAMERA_PERMISSION_REQUEST_CODE = 1001;

    //本项目的文件保存目录
    private long time_mills=System.currentTimeMillis();
    private String savePath;
    private String txt_savePath;

    //输出txt文件所需的类
    private FileOperation mFileOperation=new FileOperation();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_point1_1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }


        pvCameraPreview = findViewById(R.id.act_cameraTest_pv_cameraPreview);
        savePath = this.getExternalFilesDir(null) + File.separator + "camera";
        txt_savePath=this.getExternalFilesDir(null) + File.separator + "text";
        checkPermission();

        findViewById(R.id.btn_start).setVisibility(View.VISIBLE);

    }

    /*----------------------------两个按钮实现主要功能--------------------------------*/
    //开始按钮
    public void start(View view) {
        //点击后隐藏开始按钮
        findViewById(R.id.btn_start).setVisibility(View.INVISIBLE);

        //开始摄像
        startRecord();

        //开始动画效果
        startAnimation(redpoint_id_array,num_id_array);

        //设置一段时间（动画结束）后结束按钮出现
        Handler mHandler3=new Handler();
        mHandler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.btn_stop).setVisibility(View.VISIBLE);
            }
        },110000);
    }

    //结束按钮
    public void stopVideo(View view) {
        //点击后结束录像
        stopRecord();

        //跳转到下个界面
        Intent intent = new Intent(this,SumActivity1_1.class);
        startActivity(intent);
    }

    /*----------------------------------动画效果的实现---------------------------------*/
    //整体动画效果的实现
    private void startAnimation (int[][] redpoint_id_array,int[][] num_id_array){
        Handler mHandler = new Handler();
        int max_i=11;
        int max_j=5;
        int order=0;
        int i,j;
        int []position;
        while(!isEnding(max_i,max_j)){
            position=random_i_j(max_i,max_j);
            i=position[0];
            j=position[1];
            isVisited[i][j]=1;
            mFileOperation.writeData(txt_savePath,"Txt1_1_"+time_mills,"位置："+"("+i+","+j+")");
            ImageView redpoint;
            TextView number;
            redpoint = findViewById(redpoint_id_array[i][j]);
            number = findViewById(num_id_array[i][j]);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //红点的缩小和消失
                    mFileOperation.writeData(txt_savePath,"Txt1_1_"+time_mills,"时刻1："+System.currentTimeMillis());
                    redpoint.setVisibility(View.VISIBLE);
                    scaleAnimation(redpoint);
                    viewDisappear(redpoint);

                    //数字变为不透明和消失
                    alphaAnimation(number);
                    viewDisappear(number);

                }
            }, order * 2000);//延迟(i*5+j)*2s进行，可以实现依次进行动画的效果。
            order++;
        }

    }

    //图像缩放动画的实现方法---红点
    private  void scaleAnimation(ImageView redpoint){
        ScaleAnimation scale_anim = new ScaleAnimation(1f, 0.5f, 1f, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF,0.5f,
                ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        scale_anim.setDuration(1000);
        scale_anim.setFillAfter(true);
        scale_anim.setFillBefore(false);
        scale_anim.setRepeatCount(0);
        redpoint.startAnimation(scale_anim);
    }
    //图像从透明变为不透明的实现方法---数字
    private  void alphaAnimation(TextView number){
        AlphaAnimation alpha_anim = new AlphaAnimation(0f,1f);
        alpha_anim.setDuration(1000);
        alpha_anim.setFillAfter(true);
        alpha_anim.setFillBefore(false);
        alpha_anim.setRepeatCount(0);
        number.startAnimation(alpha_anim);
    }

    //图像消失的实现方法----红点和数字
    private  void viewDisappear(View view){
        Handler mHandler2=new Handler();
        mHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                //mFileOperation.writeData(txt_savePath,"Txt1_1_"+time_mills,"时刻2："+System.currentTimeMillis());
                view.clearAnimation();
                view.setVisibility(View.INVISIBLE);
            }
        },2000);
    }




    /*-------------------------------录像功能的实现------------------------------------*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            Log.d(TAG, "onRequestPermissionsResult: " + grantResults[0]);
            for (int i : grantResults) {
                if (i == -1) {
                    //被禁止
                    Toast.makeText(this,"获取相机或录像权限失败，请重新进入或手动设置权限！",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            startCamera();
        }
    }



    @SuppressLint({"MissingPermission", "RestrictedApi"})
    private void startRecord() {
        if (mVideoCapture != null) {
            File dir = new File(savePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(savePath,"Video1_1_"+time_mills+".mp4");
            if (file.exists()) {
                file.delete();
            }
            VideoCapture.OutputFileOptions build = new VideoCapture.OutputFileOptions.Builder(file).build();
            mVideoCapture.startRecording(build, CameraXExecutors.mainThreadExecutor(), new VideoCapture.OnVideoSavedCallback() {
                @Override
                public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
                    Toast.makeText(ViewPointActivity1_1.this, "视频保存成功:保存位置-我的手机/Android/data/com.test.cameraxdemo/files/camera ", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                    Log.e(TAG, "onError: " + message);
                }
            });
        }
    }

    @SuppressLint("RestrictedApi")
    private void stopRecord() {
        mVideoCapture.stopRecording();
    }



    /**
     * 检查是否拥有权限
     */
    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= 23) {//6.0以上才用动态权限
            boolean cameraPermission = hasPermission(Manifest.permission.CAMERA);
            boolean recordAudio = hasPermission(Manifest.permission.RECORD_AUDIO);
            if (cameraPermission && recordAudio) {
                startCamera();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO},CAMERA_PERMISSION_REQUEST_CODE);
            }
        }
    }


    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                try {
                    //将相机的生命周期和activity的生命周期绑定，camerax 会自己释放
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    Preview preview = new Preview.Builder()
                            .build();
                    //创建图片的 capture
                    mImageCapture = new ImageCapture.Builder()
                            .setFlashMode(ImageCapture.FLASH_MODE_OFF)
                            .build();
                    mVideoCapture = new VideoCapture.Builder()
                            .setVideoFrameRate(25)
                            .setBitRate(3 * 1024 * 1024)
                            .build();//这里可以进行参数的设置
                    //选择前置摄像头
                    CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll();

                    // Bind use cases to camera
                    //参数中如果有mImageCapture才能拍照，否则会报下错
                    //Not bound to a valid Camera [ImageCapture:androidx.camera.core.ImageCapture-bce6e930-b637-40ee-b9b9-
                    mCamera = cameraProvider.bindToLifecycle(ViewPointActivity1_1.this, cameraSelector, preview,mImageCapture,mVideoCapture);
                    CameraControl mCameraControl = mCamera.getCameraControl();
                    mCameraControl.setLinearZoom(0);
                    preview.setSurfaceProvider(pvCameraPreview.getSurfaceProvider());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private boolean hasPermission(String permission) {
        //6.0以上才用动态权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /*-----------------------------------随机遍历二维数组的实现---------------------------------------------*/
    public int[] random_i_j(int max_i,int max_j){
        int [] position={0,0};
        while(isVisited[position[0]][position[1]]==1){
            position[0]=(int) (Math.random() * max_i);
            position[1]=(int) (Math.random() * max_j);
        }
        return position;
    }

    public  boolean isEnding(int max_i,int max_j){
        for(int i=0;i<max_i;i++){
            for(int j=0;j<max_j;j++){
                if(isVisited[i][j]==0){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}