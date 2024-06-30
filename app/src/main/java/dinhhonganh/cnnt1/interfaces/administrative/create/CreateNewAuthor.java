package dinhhonganh.cnnt1.interfaces.administrative.create;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.interfaces.administrative.adapter.AuthorApdapter;
import dinhhonganh.cnnt1.model.Author;

public class CreateNewAuthor extends AppCompatActivity {
    DBHelperDatabase dbHelperDatabase;
    ArrayList<Author> authors;
    AuthorApdapter authorApdapter;
    SQLiteDatabase db;
    ListView lvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_new_author);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.red));


        dbHelperDatabase = new DBHelperDatabase(this);
        authors = new ArrayList<>(dbHelperDatabase.getAllAuthors());

        EditText authorname = findViewById(R.id.txt_author_name);
        Button addAuthor = findViewById(R.id.btn_add_author);
        lvAuthor = findViewById(R.id.lvAuthor);

        authorApdapter = new AuthorApdapter(this, authors);
        lvAuthor.setAdapter(authorApdapter);

        addAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentacgia = authorname.getText().toString().trim();

                if (tentacgia.isEmpty()) {
                    Toast.makeText(CreateNewAuthor.this, "Bạn chưa điền đầy đủ thông tin tác giả", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelperDatabase.isAuthorExists(tentacgia)) {
                    Toast.makeText(CreateNewAuthor.this, "Tác giả đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else {
                    // Add the author to the database
                    boolean isAdded = dbHelperDatabase.addAuthor(tentacgia);
                    if (isAdded) {
                        Toast.makeText(CreateNewAuthor.this, "Thêm tác giả thành công!", Toast.LENGTH_SHORT).show();
                        authorname.setText("");
                        showDataListView(); // Clear the input field
                    } else {
                        Toast.makeText(CreateNewAuthor.this, "Thêm tác giả thất bại.", Toast.LENGTH_SHORT).show();
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

    private void showDialogconfirm(String s, String authorid) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Xác nhận");
        b.setMessage("Bạn có đồng ý xóa mục  " + s + " này không ?");
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d("xoa", authorid);
                String sql = "delete from Author where author_id ='" + authorid + "'";

                try {
                    SQLiteDatabase db1 = dbHelperDatabase.ketNoiDBRead();
                    db1.execSQL(sql);
                    showDataListView();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Khum thanh cong", Toast.LENGTH_LONG);
                }
            }
        });
        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog al = b.create();
        al.show();
    }

    private void showDataListView() {
        authors.clear();

        db = dbHelperDatabase.ketNoiDBRead();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelperDatabase.TABLE_AUTHOR, null);
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int authorId = cursor.getInt(cursor.getColumnIndex(DBHelperDatabase.COLUMN_AUTHOR_ID));
                @SuppressLint("Range") String authorName = cursor.getString(cursor.getColumnIndex(DBHelperDatabase.COLUMN_AUTHOR_NAME));
                Author author = new Author(authorId, authorName);
                authors.add(author);
            }
        } finally {
            cursor.close();
        }

        authorApdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 150 && resultCode == 200) {
            int position = data.getIntExtra("AuthorPosition", -1); // Lấy vị trí của tác giả
            if (position != -1) {
                // Lấy dữ liệu trả về từ Intent
                String id = data.getStringExtra("AuthorId");
                String name = data.getStringExtra("AuthorName");

                // Hiển thị hộp thoại xác nhận và xóa tác giả
                showDialogconfirm(name, id);
            }
        }
    }
}
