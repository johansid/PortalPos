package cn.burgeon.core.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import cn.burgeon.core.R;
import cn.burgeon.core.adapter.SystemAdapter;

public class SystemActivity extends BaseActivity {

    private GridView sysGV;
    private SystemAdapter mAdapter;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_system);

        init();
    }

    private void init() {
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        sysGV = (GridView) findViewById(R.id.sysGV);
        mAdapter = new SystemAdapter(this);
        sysGV.setAdapter(mAdapter);
        sysGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
