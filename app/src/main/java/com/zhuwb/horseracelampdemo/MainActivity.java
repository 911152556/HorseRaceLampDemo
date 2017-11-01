package com.zhuwb.horseracelampdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhuwb.uikit.view.ScrollingText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.scrollingText)
    ScrollingText scrollingText;
    @BindView(R.id.scrollingText2)
    ScrollingText scrollingText2;
    @BindView(R.id.tv_marquee_normal)
    TextView tvMarqueeNormal;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.main_activity_ImageView)
    ImageView mainActivityImageView;

    private List<String> listText;
    private Context context;
    private OkHttpClient okHttpClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplication();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        ScrollingText scrollingText = new ScrollingText(context);
        listText = new ArrayList<>();
        listText.add("ABCDzx垂直第一条");
        listText.add("ABCDzx垂直第二条");
        scrollingText.setTextList(listText);


        //创建okHttpClient对象
        okHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("http://gank.io/api/data/福利/10/1")
                .build();
        //new call
        Call call = okHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().toString();
                Gson gson = new Gson();
//                List<Image> list = gson.fromJson(json, List.class);
//                Log.d(TAG, "onResponse: " + list.get(0).getUrl());
//                Glide.with(MainActivity.this).load(list.get(0).getUrl()).into(imageView);
            }
        });

    }

    @OnClick({R.id.button, R.id.button2, R.id.button3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(new Intent(MainActivity.this, ZoomPhotoViewActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(MainActivity.this, AutoShufflingActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(MainActivity.this, ModelActivity.class));
                break;
            default:
                break;
        }
    }
}
