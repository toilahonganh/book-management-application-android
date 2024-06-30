package dinhhonganh.cnnt1.interfaces.administrative;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.interfaces.LoginActivity;
import dinhhonganh.cnnt1.interfaces.administrative.create.CreateNewAuthor;
import dinhhonganh.cnnt1.interfaces.administrative.create.CreateNewBook;
import dinhhonganh.cnnt1.interfaces.administrative.create.CreateNewCategory;
import dinhhonganh.cnnt1.interfaces.administrative.create.CreateNewPublisher;
import dinhhonganh.cnnt1.interfaces.administrative.create.CreateNewStore;
import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.interfaces.administrative.adapter.BookAdapter;
import dinhhonganh.cnnt1.model.Book;

public class AdministrativeActivity extends AppCompatActivity {

    ListView lvBook;
    ArrayList<Book> books;
    BookAdapter bookAdapter;
    DBHelperDatabase dbHelperDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administrative);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.orange));

        lvBook = findViewById(R.id.lvBook);
        dbHelperDatabase = new DBHelperDatabase(this);
        books = new ArrayList<>(dbHelperDatabase.getAllBooks());
        Spinner boloc = findViewById(R.id.boloc);
        populateSpinner(boloc, dbHelperDatabase.getTypesOfBooks());
        TextView tvLoc = findViewById(R.id.tvLoc);
        TextView tvLogout = findViewById(R.id.tvLogout);


        bookAdapter = new BookAdapter(this, books); // Khởi tạo adapter với danh sách sách và context hiện tại
        lvBook.setAdapter(bookAdapter); // Gán adapter cho ListView


        tvLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int catePosition = boloc.getSelectedItemPosition();

                if (catePosition == 0) {
                    List<Book> filteredBooks = dbHelperDatabase.getAllBooks();
                    books.clear();
                    books.addAll(filteredBooks);
                    bookAdapter.notifyDataSetChanged();
                } else {
                    int cateId = catePosition - 1;

                    List<Book> filteredBooks = dbHelperDatabase.getBooksByType(cateId);
                    books.clear();
                    books.addAll(filteredBooks);
                    bookAdapter.notifyDataSetChanged();

                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void populateSpinner(Spinner spinner, List<String> data) {
        data.add(0, "Tất cả");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void moveToAuthor(View view) {
        Intent intent = new Intent(this, CreateNewAuthor.class);
        startActivity(intent);
    }
    public void moveToTypeOfBook(View view) {
        Intent intent = new Intent(this, CreateNewCategory.class);
        startActivity(intent);
    }
    public void moveToStore(View view) {
        Intent intent = new Intent(this, CreateNewStore.class);
        startActivity(intent);
    }
    public void moveToPublisher(View view) {
        Intent intent = new Intent(this, CreateNewPublisher.class);
        startActivity(intent);
    }
    public void moveToBook(View view) {
        Intent intent = new Intent(this, CreateNewBook.class);
        startActivity(intent);
    }
    public void moveToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}