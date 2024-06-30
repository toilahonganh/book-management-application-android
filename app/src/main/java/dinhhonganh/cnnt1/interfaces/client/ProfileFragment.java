package dinhhonganh.cnnt1.interfaces.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.interfaces.LoginActivity;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Toolbar tbAccount;
    Button bLogout;
    TextView tvUpdateProfile, tvUserAddress, tvUserBirthday, tvUserGender, tvUserPhoneNumber, tvUserName, tvUserGmail;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        bLogout = view.findViewById(R.id.bLogout);
        tvUpdateProfile = view.findViewById(R.id.tvUpdateProfile);
        tvUserAddress = view.findViewById(R.id.tvUserAddress);
        tvUserBirthday = view.findViewById(R.id.tvUserBirthday);
        tvUserGender = view.findViewById(R.id.tvUserGender);
        tvUserPhoneNumber = view.findViewById(R.id.tvUserPhoneNumber);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserGmail = view.findViewById(R.id.tvUserGmail);

        tvUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UpdateInformation();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.client_fragment_id, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Bạn cần gọi hàm fetchUserData() để lấy dữ liệu người dùng
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

                tvUserName.setText(name);
                tvUserGmail.setText(email);
                tvUserAddress.setText(address);
                tvUserBirthday.setText(birthday);
                tvUserGender.setText(gender);
                tvUserPhoneNumber.setText(phone);
            }
            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Người dùng không được tìm thấy. Vui lòng đăng nhập lại.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

}
