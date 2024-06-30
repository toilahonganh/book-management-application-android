package dinhhonganh.cnnt1.interfaces;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dinhhonganh.cnnt1.MainActivity;
import dinhhonganh.cnnt1.R;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Kiểm tra trạng thái đăng nhập
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

                Intent intent;
                if (isLoggedIn) {
                    // Nếu người dùng đã đăng nhập, chuyển đến MainActivity
                    intent = new Intent(SplashScreen.this, MainActivity.class);
                } else {
                    // Nếu người dùng chưa đăng nhập, chuyển đến LoginActivity
                    intent = new Intent(SplashScreen.this, LoginActivity.class);
                }

                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
