package dinhhonganh.cnnt1.interfaces.administrative.create;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.interfaces.administrative.adapter.AuthorApdapter;
import dinhhonganh.cnnt1.interfaces.administrative.adapter.StoreAdapter;
import dinhhonganh.cnnt1.model.Author;
import dinhhonganh.cnnt1.model.Store;

public class CreateNewStore extends AppCompatActivity {
    private DBHelperDatabase dbHelperDatabase;
    ListView lvStore;
    ArrayList<Store> stores;
    StoreAdapter storeAdapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_new_store);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.red));

        dbHelperDatabase = new DBHelperDatabase(this);
        stores = new ArrayList<>(dbHelperDatabase.getAllStores());

        lvStore = findViewById(R.id.lvStore);
        EditText storename = findViewById(R.id.txt_store_name);
        EditText storeaddress = findViewById(R.id.txt_store_address);
        Button addStore = findViewById(R.id.btn_add_store);

        storeAdapter = new StoreAdapter(this, stores);
        lvStore.setAdapter(storeAdapter);

        addStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tencuahang = storename.getText().toString();
                String diachicuahang = storeaddress.getText().toString();

                if (tencuahang.isEmpty() || diachicuahang.isEmpty()) {
                    Toast.makeText(CreateNewStore.this, "Bạn chưa điền đầy đủ thông tin của cửa hàng", Toast.LENGTH_SHORT).show();
                    return;
                } if (dbHelperDatabase.isStoreExists(tencuahang)) {
                    Toast.makeText(CreateNewStore.this, "Cửa hàng đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isAdded = dbHelperDatabase.addStore(tencuahang, diachicuahang);

                    if(isAdded) {
                        Toast.makeText(CreateNewStore.this, "Thêm cửa hàng thành công!", Toast.LENGTH_SHORT).show();
                        storename.setText(""); // Clear the input field
                        storeaddress.setText("");
                        showDataListView();
                    } else {
                        Toast.makeText(CreateNewStore.this, "Thêm cửa hàng thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showDataListView() {
        stores.clear();

        db = dbHelperDatabase.ketNoiDBRead();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelperDatabase.TABLE_STORE, null);
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int storeId = cursor.getInt(cursor.getColumnIndex(DBHelperDatabase.COLUMN_STORE_ID));
                @SuppressLint("Range") String storeName = cursor.getString(cursor.getColumnIndex(DBHelperDatabase.COLUMN_STORE_NAME));
                @SuppressLint("Range") String storeAddress = cursor.getString(cursor.getColumnIndex(DBHelperDatabase.COLUMN_STORE_ADDRESS));
                Store store = new Store(storeId, storeName, storeAddress);
                stores.add(store);
            }
        } finally {
            cursor.close();
        }

        storeAdapter.notifyDataSetChanged();
    }
}