package cn.devshare.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import cn.devshare.smartbutler.R;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.ui
 * Class describe:
 * Author: cheng
 * Create time: 2017/7/3 16:32
 */
public class WebViewActivity  extends BaseActivity{

    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initView();
    }

    private void initView() {

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        final String url=intent.getStringExtra("url");
        getSupportActionBar().setTitle(title);

        progressBar= (ProgressBar) findViewById(R.id.progressbar);
        webView= (WebView) findViewById(R.id.webview);

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webView.setWebChromeClient(new ChromeClient());
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    class ChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100){
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

}
