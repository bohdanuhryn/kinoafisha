package com.bohdanuhryn.kinoafisha;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by BohdanUhryn on 24.03.2016.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String CODE_RESULT = "code_result";

    private WebView webView;

    private long clientId = 4978448;
    private String redirectUri = "https://oauth.vk.com/blank.html";
    private String display = "mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupWebView();
        reloadAuth();
    }

    private void setupWebView() {
        webView = (WebView) findViewById(R.id.login_web_view);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                handleCode(url);
            }
        };
        webView.setWebViewClient(wvc);
    }

    private void handleCode(String url) {
        if (url.startsWith("https://oauth.vk.com/blank.html")) {
            int codePos = url.indexOf("code");
            String code = "";
            if (codePos > -1) {
                codePos += 5;
                code = url.substring(codePos);
                Intent data = new Intent();
                data.putExtra(CODE_RESULT, code);
                setResult(RESULT_OK, data);
            } else {
                setResult(RESULT_CANCELED);
            }
            finish();
        }
    }

    private void reloadAuth() {
        webView.loadUrl("https://oauth.vk.com/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&display=" + display);
    }
}
