package dinhhonganh.cnnt1.interfaces;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dinhhonganh.cnnt1.MainActivity;
import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.interfaces.administrative.AdministrativeActivity;

public class LoginActivity extends AppCompatActivity {
    private DBHelperDatabase dbHelperDatabase;
    CheckBox cbfpass;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        dbHelperDatabase = new DBHelperDatabase(this);
        TextView movetoregister = findViewById(R.id.txt_move_to_register);
        EditText gmail = findViewById(R.id.txt_user_gmail);
        EditText password = findViewById(R.id.txt_pwd);
        Button login = findViewById(R.id.btn_login);
        cbfpass = findViewById(R.id.cbfpass);

        // Lấy SharedPreferences
        settings = getSharedPreferences("Login", 0);
        // Lấy tên đăng nhập và mật khẩu đã lưu từ SharedPreferences
        String username = settings.getString("user", "");
        String pwd = settings.getString("password", "");

        // Nếu đã lưu thông tin đăng nhập trước đó, điền vào ô tương ứng và kiểm tra ô nhớ mật khẩu
        if (!username.isEmpty() && !pwd.isEmpty()) {
            gmail.setText(username);
            password.setText(pwd);
            cbfpass.setChecked(true);
        }

        // Xử lý sự kiện khi người dùng nhấn nút đăng nhập
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoan = gmail.getText().toString().trim();
                String matkhau = password.getText().toString().trim();

                // Kiểm tra nếu tên đăng nhập và mật khẩu không được nhập
                if (taikhoan.isEmpty() || matkhau.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập thông tin đăng nhập", Toast.LENGTH_LONG).show();
                    return;
                }

                // Kiểm tra xem thông tin đăng nhập có chính xác không
                boolean isAuthenticated = dbHelperDatabase.checkUser(taikhoan, matkhau);
                if (isAuthenticated) {
                    // Nếu đăng nhập thành công, lưu thông tin người dùng và chuyển sang màn hình tiếp theo
                    int userId = dbHelperDatabase.getUserId(taikhoan);
                    if (userId == -1) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    // Lưu thông tin người dùng vào SharedPreferences
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("userEmail", taikhoan);
                    editor.putInt("user_id", userId); // Lưu ID của người dùng

                    // Lưu tên đăng nhập và mật khẩu nếu người dùng chọn tùy chọn nhớ mật khẩu
                    if (cbfpass.isChecked()) {
                        editor.putString("user", taikhoan);
                        editor.putString("password", matkhau);
                    } else {
                        // Nếu không chọn, xóa thông tin đăng nhập từ SharedPreferences
                        editor.remove("user");
                        editor.remove("password");
                    }

                    // Áp dụng các thay đổi
                    editor.apply();

                    // Chuyển sang màn hình tiếp theo
                    Intent intent;
                    if ("admin".equalsIgnoreCase(taikhoan)) {
                        intent = new Intent(LoginActivity.this, AdministrativeActivity.class);
                    } else {
                        intent = new Intent(LoginActivity.this, SplashScreen.class);
                    }
                    startActivity(intent);
                    finish(); // Đóng màn hình hiện tại
                } else {
                    // Nếu thông tin đăng nhập không chính xác, hiển thị thông báo
                    Toast.makeText(LoginActivity.this, "Thông tin đăng nhập không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện khi người dùng chuyển sang màn hình đăng ký
        movetoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Đảm bảo layout hiển thị theo cạnh của màn hình
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}
