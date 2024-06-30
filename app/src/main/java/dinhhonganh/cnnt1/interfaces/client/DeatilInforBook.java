package dinhhonganh.cnnt1.interfaces.client;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import dinhhonganh.cnnt1.MainActivity;
import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.model.Book;

public class DeatilInforBook extends AppCompatActivity {
    DBHelperDatabase dbHelperDatabase;
    Toolbar tbAccount;
    LinearLayout linearLayout;
    private ImageView bookImage;
    private TextView txtBookName, txtBookPrice, txtPublisher, txtAuthor, txtType, txtPage;
    private Button bOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deatil_infor_book);

        dbHelperDatabase = new DBHelperDatabase(this);

        tbAccount = findViewById(R.id.tbAccount);

        setSupportActionBar(tbAccount);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            tbAccount.getNavigationIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setTitle("");
        }


        bookImage = findViewById(R.id.book_image);
        txtBookName = findViewById(R.id.txt_book_name);
        txtBookPrice = findViewById(R.id.txt_book_price);
        txtPublisher = findViewById(R.id.txt_book_publisher);
        txtAuthor = findViewById(R.id.txt_book_author);
        txtType = findViewById(R.id.txt_book_type);
        txtPage = findViewById(R.id.txt_book_page);
        bOrder = findViewById(R.id.bOrder);

        Intent intent = getIntent();
        if (intent != null) {
            int book_id = intent.getIntExtra("BOOK_ID", 0);
            String bookName = intent.getStringExtra("BOOK_NAME");
            String bookPrice = intent.getStringExtra("BOOK_PRICE");
            String bookImagePath = intent.getStringExtra("BOOK_IMAGE_PATH");
            int bookAuthor = intent.getIntExtra("BOOK_AUTHOR", 0);
            int bookPublisher = intent.getIntExtra("BOOK_PUBLISHER", 0);
            int bookType = intent.getIntExtra("BOOK_TYPE", 0);
            int bookPage = intent.getIntExtra("BOOK_PAGE", 0);


            String author =dbHelperDatabase.getAuthorNameById(bookAuthor + 1);
            String publisher =dbHelperDatabase.getPublisherNameById(bookPublisher + 1);
            String typeofbook =dbHelperDatabase.getTypeOfBookNameById(bookType + 1);

            txtBookName.setText(bookName);
            txtBookPrice.setText(String.valueOf(bookPrice) + ".000 VND");
            txtAuthor.setText(author);
            txtPublisher.setText(publisher);
            txtType.setText(typeofbook);
            txtPage.setText(String.valueOf(bookPage) + " trang");

            if (bookImagePath != null && !bookImagePath.isEmpty()) {
                Glide.with(this)
                        .load(bookImagePath)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.eror_image)
                        .into(bookImage);
            } else {
                bookImage.setImageResource(R.drawable.thuchoem);
            }
        }
        bOrder.setOnClickListener(v -> {
            int book_id = intent.getIntExtra("BOOK_ID", 0);
            String bookName = txtBookName.getText().toString();
            String bookPrice = txtBookPrice.getText().toString();
            String bookImagePath = intent.getStringExtra("BOOK_IMAGE_PATH");
            int bookAuthor = intent.getIntExtra("BOOK_AUTHOR", 0);
            int bookPublisher = intent.getIntExtra("BOOK_PUBLISHER", 0);
            int bookType = intent.getIntExtra("BOOK_TYPE", 0);
            int bookPage = intent.getIntExtra("BOOK_PAGE", 0);

            Book book = new Book(book_id, bookName, bookPage, bookImagePath,bookPrice, bookAuthor, bookPublisher, bookType, 1);
            insertOrder(book);
            Toast.makeText(this, "Đã thêm sách vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.red));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void insertOrder(Book book) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = preferences.getInt("user_id", -1);

        DBHelperDatabase dbHelperDatabase = new DBHelperDatabase(this);
        SQLiteDatabase db = dbHelperDatabase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbHelperDatabase.COLUMN_USER_ID, userId);
        values.put(dbHelperDatabase.COLUMN_BOOK_ID_FK, book.getBook_id());
        values.put(dbHelperDatabase.COLUMN_QUANTITY, 1);

        long newRowId = db.insert(dbHelperDatabase.TABLE_ORDER, null, values);

        dbHelperDatabase.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.img_shopping_cart) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("openFragment", "ShoppingCartFragment");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}