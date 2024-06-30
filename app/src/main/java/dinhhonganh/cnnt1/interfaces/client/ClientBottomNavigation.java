package dinhhonganh.cnnt1.interfaces.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.databinding.ActivityMainBinding;

public class ClientBottomNavigation extends AppCompatActivity {
//    ActivityMainBinding binding;
//    @SuppressLint("NonConstantResourceId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//
//        replaceFragment(new HomeFragment());
//
//
//
//        setContentView(binding.getRoot());
//
//        binding.bottom.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.home) {
//                replaceFragment(new First());
//            } else if (itemId == R.id.notifications) {
//                replaceFragment(new Second());
//            } else if (itemId == R.id.cart || itemId == R.id.account) {
//                replaceFragment(new First());
//            }
//
//            return true;
//        });
//    }
//
//    private void replaceFragment (Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.client_fragment_id, fragment);
//        fragmentTransaction.commit();
//    }
}