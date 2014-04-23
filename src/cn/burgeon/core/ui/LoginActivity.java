package cn.burgeon.core.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import cn.burgeon.core.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private String[] stores = {"门店1", "门店2", "门店3"};
    private Spinner storeSpinner;
    private ImageView configBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        storeSpinner = (Spinner) findViewById(R.id.storeSpin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_item, stores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(adapter);

        configBtn = (ImageView) findViewById(R.id.configBtn);
        configBtn.setOnClickListener(this);
        loginBtn = (ImageView) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.configBtn:
                break;
            case R.id.loginBtn:
                forwardActivity(SystemActivity.class);
                break;
        }
    }

}
