package dinhhonganh.cnnt1.interfaces.administrative.create;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;

public class CreateNewBook extends AppCompatActivity {
    Bitmap myBitmap;
    Uri picUri;
    public ImageView imgbook;
    private DBHelperDatabase dbHelperDatabase;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_new_book);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.red));

        dbHelperDatabase = new DBHelperDatabase(this);
        EditText bookname = findViewById(R.id.txt_book_name);
        EditText bookprice = findViewById(R.id.txt_book_price);
        EditText bookpage = findViewById(R.id.txt_book_page);
        EditText bookquantity = findViewById(R.id.txt_book_quantity);
        EditText bookimage = findViewById(R.id.edtBookImage);



        Spinner spinnerauthor = findViewById(R.id.spinner_author);
        Spinner spinnerpublisher = findViewById(R.id.spinner_publisher);
        Spinner spinnerstore = findViewById(R.id.spinner_store);
        Spinner spinnercategory = findViewById(R.id.spinner_category);

        Button addBook = findViewById(R.id.btn_add_book);

        populateSpinner(spinnerauthor, dbHelperDatabase.getAuthors());
        populateSpinner(spinnerpublisher, dbHelperDatabase.getPublishers());
        populateSpinner(spinnercategory, dbHelperDatabase.getTypesOfBooks());
        populateSpinner(spinnerstore, dbHelperDatabase.getStores());




        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensach = bookname.getText().toString().trim();
                String sotrangText = bookpage.getText().toString().trim();
                String anhbiaText = bookimage.getText().toString().trim();
                String giasachText = bookprice.getText().toString().trim();
                String soluongText = bookquantity.getText().toString().trim();

                if (tensach.isEmpty() || sotrangText.isEmpty() || giasachText.isEmpty() || soluongText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa điền đầy đủ thông tin sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                int sotrang, soluong;
                double giasach;
                try {
                    sotrang = Integer.parseInt(sotrangText);
                    giasach = Double.parseDouble(giasachText);
                    soluong = Integer.parseInt(soluongText);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập các giá trị số hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                int authorPosition = spinnerauthor.getSelectedItemPosition();
                int publisherPosition = spinnerpublisher.getSelectedItemPosition();
                int typeOfBookPosition = spinnercategory.getSelectedItemPosition();
                int storePosition = spinnerstore.getSelectedItemPosition();

                if (authorPosition == 0 || publisherPosition == 0 || typeOfBookPosition == 0 || storePosition == 0) {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn các tùy chọn hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                int authorId = authorPosition - 1;
                int publisherId = publisherPosition - 1;
                int typeOfBookId = typeOfBookPosition - 1;
                int storeId = storePosition - 1;


                boolean result = dbHelperDatabase.addBook(tensach, sotrang, anhbiaText, giasachText, authorId, publisherId, typeOfBookId, storeId, soluong);

                if (result) {
                    Toast.makeText(getApplicationContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                    try {
                        Intent in = new Intent();
                        setResult(200, in);
                        finish();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Không thanh cong", Toast.LENGTH_LONG);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm sách thất bại!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bookimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                permissionsToRequest = findUnAskedPermissions(permissions);
                startActivityForResult(getPickImageChooserIntent(), 200);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (permissionsToRequest.size() > 0) {
                        requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
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
    public Intent getPickImageChooserIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select source");
        return chooserIntent;
    }
    private String generateRandomFileName() {
        return UUID.randomUUID().toString();
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        picUri = savedInstanceState.getParcelable("pic_uri");
    }
    private void populateSpinner(Spinner spinner, List<String> data) {
        data.add(0, "None");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                picUri = data.getData();
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                    imgbook.setImageBitmap(myBitmap);

                    // Save image to local storage
                    String imagePath = saveImageToInternalStorage(myBitmap);
                    // imagePath now contains the path where the image is saved
                } catch (IOException e) {

                    e.printStackTrace();
                }
            } else {
                Log.e("CreateNewBook", "Selected image URI is null");
            }
        } else {
            Log.e("CreateNewBook", "Activity result not OK");
        }
    }

    public String saveImageToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("custom_directory", Context.MODE_PRIVATE);
        String fileName = generateRandomFileName() + ".jpg"; // Sử dụng generateRandomFileName() ở đây
        File mypath = new File(directory, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            return mypath.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CreateNewBook", "Error saving image: " + e.getMessage());
            return null; // Trả về null nếu có lỗi xảy ra
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

                Log.e("CreateNewBook", "Error closing FileOutputStream: " + e.getMessage());
            }
        }
    }


}

