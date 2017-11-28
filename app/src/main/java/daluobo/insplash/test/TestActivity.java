package daluobo.insplash.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.img)
    ImageView mImg;

    boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        if (isShow) {
            mImg.animate().scaleX(0).scaleY(0).alpha(0).start();
        } else {
            mImg.animate().scaleX(1).scaleY(1).alpha(1).start();
        }
        isShow = !isShow;
    }
}
