package com.accherniakocich.android.findjob.social_networks.vk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.accherniakocich.android.findjob.R;

public class BuyPremium extends AppCompatActivity {

    private WebView web_qiwi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_premium);

        init();
    }

    private void init() {
        web_qiwi = (WebView) findViewById(R.id.web_qiwi);
        web_qiwi.getSettings().setJavaScriptEnabled(true);
        // указываем страницу загрузки
        web_qiwi.loadUrl("https://qiwi.com/payment/form/99");
        web_qiwi.setWebViewClient(new MyWebViewClient());
    }
    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if(web_qiwi.canGoBack()) {
            web_qiwi.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
