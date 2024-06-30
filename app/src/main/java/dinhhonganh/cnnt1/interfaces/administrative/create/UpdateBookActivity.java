package dinhhonganh.cnnt1.interfaces.administrative.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.interfaces.administrative.AdministrativeActivity;

public class UpdateBookActivity extends AppCompatActivity {

    private EditText editTextBookName;
    private EditText editTextBookPage;
    private EditText editTextBookQuantity;
    private EditText editTextBookPrice;
    private EditText editTextBookImage;

    private Button btn_add_book;


    private DBHelperDatabase dbHelperDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_book);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.red));

        dbHelperDatabase = new DBHelperDatabase(this);

        editTextBookName = findViewById(R.id.txt_book_name);
        editTextBookPage = findViewById(R.id.txt_book_page);
        editTextBookQuantity = findViewById(R.id.txt_book_quantity);
        editTextBookPrice = findViewById(R.id.txt_book_price);
        editTextBookImage = findViewById(R.id.edtBookImage);
        btn_add_book = findViewById(R.id.btn_add_book);


        Spinner spinnerauthor = findViewById(R.id.spinner_author);
        Spinner spinnerpublisher = findViewById(R.id.spinner_publisher);
        Spinner spinnerstore = findViewById(R.id.spinner_store);
        Spinner spinnercategory = findViewById(R.id.spinner_category);


        List<String> authors = dbHelperDatabase.getAuthors();
        ArrayAdapter<String> authorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, authors);
        authorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerauthor.setAdapter(authorAdapter);


        List<String> publisher = dbHelperDatabase.getPublishers();
        ArrayAdapter<String> publiserAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, publisher);
        publiserAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpublisher.setAdapter(publiserAdapter);


        List<String> cates = dbHelperDatabase.getTypesOfBooks();
        ArrayAdapter<String> cateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cates);
        cateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercategory.setAdapter(cateAdapter);

        List<String> stores = dbHelperDatabase.getStores();
        ArrayAdapter<String> storesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stores);
        storesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstore.setAdapter(storesAdapter);


//        List<String> authors = dbHelperDatabase.getAuthors();
//        populateSpinner(spinnerauthor, authors);
//
//        // Lấy danh sách nhà xuất bản từ cơ sở dữ liệu và thêm "None" vào đầu danh sách
//        List<String> publishers = dbHelperDatabase.getPublishers();
//        populateSpinner(spinnerpublisher, publishers);
//
//        // Lấy danh sách loại sách từ cơ sở dữ liệu và thêm "None" vào đầu danh sách
//        List<String> categories = dbHelperDatabase.getTypesOfBooks();
//        populateSpinner(spinnercategory, categories);
//
//        // Lấy danh sách cửa hàng từ cơ sở dữ liệu và thêm "None" vào đầu danh sách
//        List<String> stores = dbHelperDatabase.getStores();
//        populateSpinner(spinnerstore, stores);



        Intent intent = getIntent();
        int bookId = intent.getIntExtra("book_id", 0);
        String bookName = intent.getStringExtra("book_name");
        int bookPage = intent.getIntExtra("book_page", 0);
        int bookQuantity = intent.getIntExtra("book_quantity", 0);
        String bookPrice = intent.getStringExtra("book_price");
        String bookImagePath = intent.getStringExtra("book_image_path");

        int authorId = intent.getIntExtra("author_id", 0);
        int publisherId = intent.getIntExtra("publisher_id", 0);
        int typeOfBookId = intent.getIntExtra("type_of_book_id", 0);
        int storeId = intent.getIntExtra("store_id", 0);
//
//        List<String> authors = dbHelperDatabase.getAuthors();
//        List<String> publisher = dbHelperDatabase.getPublishers();
//        List<String> cates = dbHelperDatabase.getTypesOfBooks();
//        List<String> stores = dbHelperDatabase.getStores();

        editTextBookName.setText(bookName);
        editTextBookPage.setText(String.valueOf(bookPage));
        editTextBookQuantity.setText(String.valueOf(bookQuantity));
        editTextBookPrice.setText(bookPrice);
        editTextBookImage.setText(bookImagePath);

        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, authors);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerauthor.setAdapter(a);

        ArrayAdapter<String> p = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, publisher);
        p.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpublisher.setAdapter(p);

        ArrayAdapter<String> c = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cates);
        c.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercategory.setAdapter(c);

        ArrayAdapter<String> s = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stores);
        s.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstore.setAdapter(s);

        // Set giá trị cho spinner
        spinnerauthor.setSelection(findPositionInList(authors, dbHelperDatabase.getAuthorNameById(authorId + 1)));
        spinnerpublisher.setSelection(findPositionInList(publisher, dbHelperDatabase.getPublisherNameById(publisherId + 1)));
        spinnercategory.setSelection(findPositionInList(cates, dbHelperDatabase.getTypeOfBookNameById(typeOfBookId + 1)));
        spinnerstore.setSelection(findPositionInList(stores, dbHelperDatabase.getStoreNameById(storeId + 1)));


        btn_add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = editTextBookName.getText().toString();
                int bookPage = Integer.parseInt(editTextBookPage.getText().toString());
                int bookQuantity = Integer.parseInt(editTextBookQuantity.getText().toString());
                String bookPrice = editTextBookPrice.getText().toString();
                String bookImagePath = editTextBookImage.getText().toString();

                int authorPosition = spinnerauthor.getSelectedItemPosition();
                int publisherPosition = spinnerpublisher.getSelectedItemPosition();
                int typeOfBookPosition = spinnercategory.getSelectedItemPosition();
                int storePosition = spinnerstore.getSelectedItemPosition();

                int authorId = authorPosition;
                int publisherId = publisherPosition;
                int typeOfBookId = typeOfBookPosition;
                int storeId = storePosition;

                // Cập nhật thông tin sách trong cơ sở dữ liệu
                boolean isSuccess = dbHelperDatabase.updateBook(bookId, bookName, bookImagePath, bookPage , bookPrice, authorId, publisherId, typeOfBookId , storeId, bookQuantity);

                if (isSuccess) {
                    Toast.makeText(UpdateBookActivity.this, "Cập nhật thông tin sách thành công!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UpdateBookActivity.this, AdministrativeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UpdateBookActivity.this, "Cập nhật thông tin sách thất bại!", Toast.LENGTH_SHORT).show();
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
        data.add(0, "None");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    private int findPositionInList(List<String> list, String value) {
        return list.indexOf(value);
    }


}