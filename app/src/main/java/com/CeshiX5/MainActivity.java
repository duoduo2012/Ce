package com.CeshiX5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class MainActivity extends AppCompatActivity {
private WebView webview;
private int index=0;
    private TextView textView;
    private WebView webView;

    /** mUrl测试连接url   这个可以换成自己的html路径 ,
     *  自己服务器上html上面可以放pdf附件或者doc附件  图片附件。
     *  如果是文档文件 ，就是调用系统浏览器下载，如果安装了多个浏览器，会弹出选择界面；如果是图片就是直接打开效果*/
    private String mUrl = "http://as.baidu.com/a/rank?cid=101&s=1&f=web_alad";
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview=findViewById(R.id.webview);
        WebSettings webSetting = webview.getSettings();
        webSetting.setJavaScriptEnabled(true);
    //    webview.loadUrl("http://139.219.234.108/bpm/SEPC.Mobile.MobileWeb/build/homepagelist.html?modulecode=BPM&userad=19E344950725CCC3&lang=zh-CN");
     //   final Handler handler=new Handler();

        webview.loadUrl(mUrl);

              //  webView.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient(){
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
        webview.setWebChromeClient(new WebChromeClient(){
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                  //      textView.setText(title);
                        super.onReceivedTitle(view, title);
                    }
                });
      //  webview.setDownloadListener(new MyDownloadStart());







    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeCookie();
    }

    //删除Cookie
    private void removeCookie() {
        Log.i("sasasawawaw","sasasasasa");

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }

    }
    class MyDownloadStart implements DownloadListener{

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {

            Toast.makeText(MainActivity.this,url,Toast.LENGTH_SHORT).show();
            //调用自己的下载方式
//          new HttpThread(url).start();
            //调用系统浏览器下载
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
