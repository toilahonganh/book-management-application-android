package dinhhonganh.cnnt1;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import dinhhonganh.cnnt1.databinding.ActivityMainBinding;
import dinhhonganh.cnnt1.interfaces.client.HomeFragment;
import dinhhonganh.cnnt1.interfaces.client.NotificationFragment;
import dinhhonganh.cnnt1.interfaces.client.OrderBookFragment;
import dinhhonganh.cnnt1.interfaces.client.ProfileFragment;
import dinhhonganh.cnnt1.interfaces.client.ShoppingCartFragment;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;


    ActivityMainBinding binding;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.red));


        replaceFragment(new HomeFragment());

        setContentView(binding.getRoot());


        ImageView home = findViewById(R.id.img_home);
        ImageView account = findViewById(R.id.img_account);
        ImageView shopping = findViewById(R.id.img_shopping_cart);
        ImageView notifis = findViewById(R.id.img_notification);

        TextView txt_trangchu = findViewById(R.id.txt_trangchu);
        TextView txt_taikhoan = findViewById(R.id.txt_taikhoan);
        TextView txt_thongbao = findViewById(R.id.txt_thongbao);
        TextView txt_giohang = findViewById(R.id.txt_giohang);

        home.setColorFilter(Color.RED);
        txt_trangchu.setTextColor(Color.RED);

        LinearLayout linear_home = findViewById(R.id.first_linear_layout);
        LinearLayout linear_account = findViewById(R.id.second_linear_layout);
        LinearLayout linear_notification = findViewById(R.id.third_linear_layout);
        LinearLayout linear_shopping_cart = findViewById(R.id.fourth_linear_layout);


        linear_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HomeFragment());
                home.setColorFilter(Color.RED);
                txt_trangchu.setTextColor(Color.RED);
                notifis.setColorFilter(Color.BLACK);
                txt_thongbao.setTextColor(Color.BLACK);
                account.setColorFilter(Color.BLACK);
                txt_taikhoan.setTextColor(Color.BLACK);
                shopping.setColorFilter(Color.BLACK);
                txt_giohang.setTextColor(Color.BLACK);
            }
        });

        linear_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ProfileFragment());

                account.setColorFilter(Color.RED);
                txt_taikhoan.setTextColor(Color.RED);
                notifis.setColorFilter(Color.BLACK);
                txt_thongbao.setTextColor(Color.BLACK);
                home.setColorFilter(Color.BLACK);
                txt_trangchu.setTextColor(Color.BLACK);
                shopping.setColorFilter(Color.BLACK);
                txt_giohang.setTextColor(Color.BLACK);
            }
        });

        linear_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new NotificationFragment());
                notifis.setColorFilter(Color.RED);
                txt_thongbao.setTextColor(Color.RED);
                home.setColorFilter(Color.BLACK);
                txt_trangchu.setTextColor(Color.BLACK);
                account.setColorFilter(Color.BLACK);
                txt_taikhoan.setTextColor(Color.BLACK);
                shopping.setColorFilter(Color.BLACK);
                txt_giohang.setTextColor(Color.BLACK);
            }
        });
        linear_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ShoppingCartFragment());
                shopping.setColorFilter(Color.RED);
                txt_giohang.setTextColor(Color.RED);
                home.setColorFilter(Color.BLACK);
                txt_trangchu.setTextColor(Color.BLACK);
                account.setColorFilter(Color.BLACK);
                txt_taikhoan.setTextColor(Color.BLACK);
                notifis.setColorFilter(Color.BLACK);
                txt_thongbao.setTextColor(Color.BLACK);
            }
        });
        handleIntent(getIntent());

    }

    private void replaceFragment (Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.client_fragment_id, fragment);
        fragmentTransaction.commit();
    }
    private void handleIntent(Intent intent) {
        if (intent != null) {
            String fragmentToOpen = intent.getStringExtra("openFragment");
            if ("ShoppingCartFragment".equals(fragmentToOpen)) {
                replaceFragment(new ShoppingCartFragment());
            } else {
                replaceFragment(new HomeFragment());
            }
        }
    }


}

