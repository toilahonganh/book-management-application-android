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
import dinhhonganh.cnnt1.interfaces.administrative.adapter.PublisherAdapter;
import dinhhonganh.cnnt1.interfaces.administrative.adapter.StoreAdapter;
import dinhhonganh.cnnt1.model.Publisher;
import dinhhonganh.cnnt1.model.Store;

public class CreateNewPublisher extends AppCompatActivity {
    private DBHelperDatabase dbHelperDatabase;

    ListView lvPublisher;
    ArrayList<Publisher> publishers;
    PublisherAdapter publisherAdapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_new_publisher);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.red));

        dbHelperDatabase = new DBHelperDatabase(this);
        EditText publishername = findViewById(R.id.txt_publisher_name);
        Button addPublisher = findViewById(R.id.btn_add_publisher);
        lvPublisher = findViewById(R.id.lvPublisher);

        publishers = new ArrayList<>(dbHelperDatabase.getAllPublisher());
        publisherAdapter = new PublisherAdapter(this, publishers);
        lvPublisher.setAdapter(publisherAdapter);

        addPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tennhaxuatban = publishername.getText().toString();

                if (tennhaxuatban.isEmpty()) {
                    Toast.makeText(CreateNewPublisher.this, "Bạn chưa điền đầy đủ thông tin nhà sản xuất", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dbHelperDatabase.isPublisherExists(tennhaxuatban)) {
                    Toast.makeText(CreateNewPublisher.this, "Nhà xuất bản đã tồn tại!", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean isAdded = dbHelperDatabase.addPublisher(tennhaxuatban);
                    if (isAdded) {
                        Toast.makeText(CreateNewPublisher.this, "Thêm nhà xuất bản thành công", Toast.LENGTH_SHORT).show();
                        publishername.setText("");
                        showDataListView();
                    } else {
                        Toast.makeText(CreateNewPublisher.this, "Thêm nhà xuất bản thất bại.", Toast.LENGTH_SHORT).show();
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
        publishers.clear();

        db = dbHelperDatabase.ketNoiDBRead();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelperDatabase.TABLE_PUBLISHER, null);
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int storeId = cursor.getInt(cursor.getColumnIndex(DBHelperDatabase.COLUMN_PUBLISHER_ID));
                @SuppressLint("Range") String storeName = cursor.getString(cursor.getColumnIndex(DBHelperDatabase.COLUMN_PUBLISHER_NAME));
                Publisher publisher = new Publisher(storeId, storeName);
                publishers.add(publisher);
            }
        } finally {
            cursor.close();
        }

        publisherAdapter.notifyDataSetChanged();
    }
}