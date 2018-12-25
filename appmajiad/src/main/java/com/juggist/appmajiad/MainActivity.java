package com.juggist.appmajiad;

import android.os.Bundle;
import android.view.View;

import com.luojilab.component.componentlib.router.ui.UIRouter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIRouter.getInstance().openUri(MainActivity.this,"ZZMK://user/loginOrRegister",null);
            }
        });
    }
}
