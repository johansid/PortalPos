package cn.burgeon.core.ui.allot;

import android.os.Bundle;
import cn.burgeon.core.R;
import cn.burgeon.core.ui.BaseActivity;

public class AllotOutQueryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFullscreen();
        setContentView(R.layout.activity_allot_out_query);
    }
    
    /*
    {
	   "table":"M_TRANSFER",
	   "columns":["DOCNO","DATEOUT"],
	   "params":{"column":"DATEOUT","condition":"20091217~20091219"}
	}
	*/
}
