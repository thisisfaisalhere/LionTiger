package com.virusX.lionOrTiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import com.virusX.lionOrTiger.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private int strength = 1;
    private GameMechanics gameMechanics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.gameTypeRGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.onePlayerRBtn:
                        binding.gameStrengthRGrp.setVisibility(View.VISIBLE);
                        break;
                    case R.id.twoPlayerRBtn:
                        binding.gameStrengthRGrp.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        binding.gameStrengthRGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.easyRBtn:
                        strength = 1;
                        break;
                    case R.id.modRBtn:
                        strength = 2;
                        break;
                    case R.id.expRBtn:
                        strength = 3;
                        break;
                    default:
                        break;
                }
            }
        });

        binding.resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMechanics.resetTheGame();
            }
        });

        gameMechanics = new GameMechanics(binding, this);
    }

    public void tappedOnGrid(View tappedView) {
        ImageView tappedImgView = (ImageView) tappedView;
        gameMechanics.run(tappedImgView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
