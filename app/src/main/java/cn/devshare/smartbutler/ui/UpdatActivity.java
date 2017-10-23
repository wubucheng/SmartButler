package cn.devshare.smartbutler.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;

import cn.devshare.smartbutler.R;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.ui
 * Class describe: 更新
 * Author: cheng
 * Create time: 2017/7/5 15:56
 */
public class UpdatActivity extends BaseActivity{

    //正在下载
    public static final int HANDLER_LODING = 10001;
    //下载完成
    public static final int HANDLER_OK = 10002;
    //下载失败
    public static final int HANDLER_ON = 10003;


    private TextView sizeTv;
    private String url;
    private String path;
    //进度条
    private NumberProgressBar number_progress_bar;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case HANDLER_LODING:
                        Bundle bundle=msg.getData();
                        long transferredBytes=bundle.getLong("transferredBytes");
                        long totalSize=bundle.getLong("totalSize");
                        sizeTv.setText(transferredBytes+" / " + totalSize);
                        number_progress_bar.setProgress((int) (((float) transferredBytes / (float) totalSize) * 100));
                        break;
                    case HANDLER_OK:
                        startInstallApk();
                        break;
                    case HANDLER_ON:
                        sizeTv.setText("下载失败");
                        break;

                }
            }
        };

        initView();

    }

    //安装应用
    private void startInstallApk() {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

    private void initView() {
        sizeTv = (TextView) findViewById(R.id.tv_size);
        number_progress_bar = (NumberProgressBar) findViewById(R.id.number_progress_bar);
        number_progress_bar.setMax(100);

        path =FileUtils.getSDCardPath()+"/"+System.currentTimeMillis()+".apk";
        url=getIntent().getStringExtra("url");

        RxVolley.download(path, url, new ProgressListener() {
            @Override
            public void onProgress(long transferredBytes, long totalSize) {
                Message msg=new Message();
                msg.what=HANDLER_LODING;
                Bundle bundle=new Bundle();
                bundle.putLong("transferredBytes", transferredBytes);
                bundle.putLong("totalSize", totalSize);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                handler.sendEmptyMessage(HANDLER_OK);

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                handler.sendEmptyMessage(HANDLER_ON);
            }
        });
    }
}
