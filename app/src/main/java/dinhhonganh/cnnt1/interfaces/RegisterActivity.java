package dinhhonganh.cnnt1.interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;

public class RegisterActivity extends AppCompatActivity {
    private DBHelperDatabase dbHelperDatabase;
    public RadioButton gender_male, gender_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        dbHelperDatabase = new DBHelperDatabase(this);
        EditText username = findViewById(R.id.txt_user_name);
        EditText usergmail = findViewById(R.id.txt_user_gmail);
        EditText userpassword = findViewById(R.id.txt_pwd);
        EditText userconfirmpassword = findViewById(R.id.txt_confirm_pwd);
        EditText userbirthday = findViewById(R.id.txt_user_birthday);
        EditText userphonenumber = findViewById(R.id.txt_phone_number);
        EditText useraddress = findViewById(R.id.txt_user_address);

        Button register = findViewById(R.id.btn_register);
        TextView gotologin = findViewById(R.id.txt_move_to_login);

        gender_male = findViewById(R.id.rdMale);
        gender_female = findViewById(R.id.rdFemale);

        gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_female.setChecked(false);
            }
        });
        gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_male.setChecked(false);
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tennguoidung = username.getText().toString().trim();
                String gmailnguoidung = usergmail.getText().toString().trim();
                String matkhau = userpassword.getText().toString().trim();
                String matkhauxacthuc = userconfirmpassword.getText().toString().trim();
                String sinhnhat = userbirthday.getText().toString().trim();
                String sodienthoai = userphonenumber.getText().toString().trim();
                String diachi = useraddress.getText().toString().trim();
                String gender = "Nam";

                if(gender_female.isChecked()) {
                    gender = "Nữ";
                }
                if (tennguoidung.isEmpty() || gmailnguoidung.isEmpty() || matkhau.isEmpty() || matkhauxacthuc.isEmpty() ||
                        sinhnhat.isEmpty() || sodienthoai.isEmpty() || diachi.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập thông tin đăng ký!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!matkhau.equals(matkhauxacthuc)) {
                    Toast.makeText(RegisterActivity.this, "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isUserAdded = dbHelperDatabase.addUser(gmailnguoidung, tennguoidung, matkhau, sinhnhat, gender, diachi, sodienthoai);
                if (isUserAdded) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(in);

                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}