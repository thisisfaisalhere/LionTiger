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
                        binding.firstPlayerImg.setImageResource(R.drawable.tiger);
                        binding.playerOneImg.setImageResource(R.drawable.tiger);
                        binding.secondPlayerImg.setImageResource(R.drawable.android);
                        binding.playerTwoImg.setImageResource(R.drawable.android);
                        break;
                    case R.id.twoPlayerRBtn:
                        binding.gameStrengthRGrp.setVisibility(View.GONE);
                        setImage();
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

        if(binding.twoPlayerRBtn.isChecked()) {
            setImage();
        }

        binding.resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMechanics.resetTheGame();
            }
        });

        gameMechanics = new GameMechanics(binding, this);
    }

    private void setImage() {
        binding.firstPlayerImg.setImageResource(R.drawable.tiger);
        binding.playerOneImg.setImageResource(R.drawable.tiger);
        binding.secondPlayerImg.setImageResource(R.drawable.lion);
        binding.playerTwoImg.setImageResource(R.drawable.lion);
    }

    public void tappedOnGrid(View tappedView) {
        ImageView tappedImgView = (ImageView) tappedView;
        gameMechanics.run(tappedImgView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSystemUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameMechanics.resetTheGame();
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
