package cn.burgeon.core.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import cn.burgeon.core.R;

public class LoginActivity extends BaseActivity {

    private String[] stores = {"门店1", "门店2", "门店3"};
    private Spinner storeSpinner;
    private Button configBtn;
    private Button loginBtn;
    private Button logoutBtn;

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
        storeSpinner.setAdapter(adapter);

        configBtn = (Button) findViewById(R.id.configBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new ClickEvent());
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginBtn:
                    forwardActivity(SystemActivity.class);
                    break;
                case R.id.logoutBtn:
                    break;
            }
        }
    }
}
