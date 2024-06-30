package dinhhonganh.cnnt1.interfaces.client;

import static dinhhonganh.cnnt1.R.id.etUserGender;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.interfaces.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateInformation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateInformation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText etUserAddress, etUserBirthday, etUserGender, etUserPhoneNumber, etUserName, etUserGmail, etUserPwd;
    Button bSave;

    public UpdateInformation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateInformation.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateInformation newInstance(String param1, String param2) {
        UpdateInformation fragment = new UpdateInformation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_information, container, false);

        bSave = view.findViewById(R.id.bSave);
        etUserAddress = view.findViewById(R.id.etUserAddress);
        etUserBirthday = view.findViewById(R.id.etUserBirthday);
        etUserGender = view.findViewById(R.id.etUserGender);
        etUserPhoneNumber = view.findViewById(R.id.etUserPhoneNumber);
        etUserName = view.findViewById(R.id.etUserName);
        etUserGmail = view.findViewById(R.id.etUserGmail);
        etUserPwd = view.findViewById(R.id.etUserPwd);


        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData();
            }
        });

        fetchUserData();

        return view;
    }

    private void fetchUserData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int userId = preferences.getInt("user_id", -1);

        if (userId != -1) {
            DBHelperDatabase dbHelper = new DBHelperDatabase(getActivity());
            Cursor cursor = dbHelper.getUserData(userId); // Implement this method in DBHelperDatabase

            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperDatabase.COLUMN_USER_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperDatabase.COLUMN_USER_GMAIL));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperDatabase.COLUMN_USER_ADDRESS));
                String birthday = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperDatabase.COLUMN_USER_BIRTHDAY));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperDatabase.COLUMN_USER_GENDER));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperDatabase.COLUMN_USER_PHONE_NUMBER));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(DBHelperDatabase.COLUMN_USER_PASSWORD));
                etUserName.setText(name);
                etUserGmail.setText(email);
                etUserAddress.setText(address);
                etUserBirthday.setText(birthday);
                etUserGender.setText(gender);
                etUserPhoneNumber.setText(phone);
                etUserPwd.setText(password);
            }
            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Người dùng không được tìm thấy. Vui lòng đăng nhập lại.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void updateUserData() {
        String name = etUserName.getText().toString().trim();
        String email = etUserGmail.getText().toString().trim();
        String address = etUserAddress.getText().toString().trim();
        String birthday = etUserBirthday.getText().toString().trim();
        String gender = etUserGender.getText().toString().trim();
        String phone = etUserPhoneNumber.getText().toString().trim();
        String password = etUserPwd.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || birthday.isEmpty() || gender.isEmpty() || phone.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int userId = preferences.getInt("user_id", -1);

        if (userId != -1) {
            DBHelperDatabase dbHelper = new DBHelperDatabase(getActivity());
            boolean isSuccess = dbHelper.updateUser(userId, name, email, birthday, gender,  address, phone, password);

            if (isSuccess) {
                Toast.makeText(getActivity(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                // Sau khi cập nhật thành công, bạn có thể chuyển Fragment hoặc thực hiện các hành động khác tại đây

                Fragment fragment = new ProfileFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.client_fragment_id, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                Toast.makeText(getActivity(), "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Người dùng không được tìm thấy. Vui lòng đăng nhập lại.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
