package com.example.aftest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class PromotionDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_details);
        final Promotion bean = (Promotion)getIntent().getSerializableExtra(MainActivity.PROMTION);
        ImageLoader.getInstance().displayImage(bean.getImage(), (ImageView) findViewById(R.id.promotion_image));
        ((TextView)findViewById(R.id.promotion_details)).setText(
                Html.fromHtml(bean.toString()));
        Button shopNow = (Button)findViewById(R.id.shop_now);
        shopNow.setText(bean.getButtonTitle());
        shopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WebView) findViewById(R.id.web_view)).loadUrl(bean.getButtonTarget());
            }
        });

    }
}
