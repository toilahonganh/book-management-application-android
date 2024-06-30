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
import dinhhonganh.cnnt1.interfaces.administrative.adapter.CategoryAdapter;
import dinhhonganh.cnnt1.model.Author;
import dinhhonganh.cnnt1.model.Category;

public class CreateNewCategory extends AppCompatActivity {
    private DBHelperDatabase dbHelperDatabase;
    ArrayList<Category> categories;
    SQLiteDatabase db;
    CategoryAdapter categoryAdapter;
    ListView lvCates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_new_category);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.red));

        dbHelperDatabase = new DBHelperDatabase(this);
        categories = new ArrayList<>(dbHelperDatabase.getAllCates());
        lvCates = findViewById(R.id.lvCates);
        EditText categoryname = findViewById(R.id.txt_category_name);
        Button addCategory = findViewById(R.id.btn_add_category);


        categoryAdapter = new CategoryAdapter(this, categories);
        lvCates.setAdapter(categoryAdapter);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentheloai = categoryname.getText().toString();
                if (tentheloai.isEmpty()) {
                    Toast.makeText(CreateNewCategory.this, "Bạn chưa điền đầy đủ thông tin thể loại", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dbHelperDatabase.isTypeOfBookExists(tentheloai)) {
                    Toast.makeText(CreateNewCategory.this, "Thể loại đã tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean isAdded = dbHelperDatabase.addTypeOfBook(tentheloai);

                    if (isAdded) {
                        Toast.makeText(CreateNewCategory.this, "Thêm thể loại thành công!", Toast.LENGTH_SHORT).show();
                        categoryname.setText("");
                        showDataListView();
                    } else {
                        Toast.makeText(CreateNewCategory.this, "Thêm thể loại thất bại!", Toast.LENGTH_SHORT).show();
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
        categories.clear();

        db = dbHelperDatabase.ketNoiDBRead();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelperDatabase.TABLE_TYPE_OF_BOOK, null);
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int authorId = cursor.getInt(cursor.getColumnIndex(DBHelperDatabase.COLUMN_TYPE_OF_BOOK_ID));
                @SuppressLint("Range") String authorName = cursor.getString(cursor.getColumnIndex(DBHelperDatabase.COLUMN_TYPE_OF_BOOK_NAME));


                Category store = new Category(authorId, authorName);
                categories.add(store);
            }
        } finally {
            cursor.close();
        }

        categoryAdapter.notifyDataSetChanged();
    }

}