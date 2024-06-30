package dinhhonganh.cnnt1.interfaces.administrative.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.interfaces.administrative.create.UpdateBookActivity;
import dinhhonganh.cnnt1.model.Book;

public class BookAdapter extends ArrayAdapter<Book> {
    private final Activity context;
    private final DBHelperDatabase dbHelperDatabase;
    private final ArrayList<Book> books;

    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, R.layout.book_adapter_view, books);
        this.context = context;
        this.books = books;
        this.dbHelperDatabase = new DBHelperDatabase(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.book_adapter_view, null, true);
        Book book = books.get(position);

        Log.d("BookAdapter", "Image path: " + book.getBook_image_path());

        TextView txtid = rowView.findViewById(R.id.txtid);
        TextView txtbookname = rowView.findViewById(R.id.txtbookname);
        TextView txttype = rowView.findViewById(R.id.txttype);
        ImageView avatar = rowView.findViewById(R.id.avatar);
        TextView bDelete = rowView.findViewById(R.id.bDelete);
        TextView bUpdate = rowView.findViewById(R.id.bUpdate);

        txtid.setText(String.valueOf(book.getBook_id()));
        txtbookname.setText(book.getBook_name());

        String typeOfBookName = dbHelperDatabase.getTypeOfBookNameById(book.getType_of_book_id() + 1);
        txttype.setText(typeOfBookName);

        Log.d("typeOfBookName", "Hi " + typeOfBookName);

        Glide.with(context)
                .load(book.getBook_image_path())
                .placeholder(R.drawable.loading)
                .error(R.drawable.eror_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("Glide", "Loading failed", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(avatar);

        bDelete.setTag(position);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = (int) v.getTag();
                final Book bookToDelete = books.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có đồng ý xóa sách này không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean isDeleted = dbHelperDatabase.deleteBook(bookToDelete.getBook_id());

                        if (isDeleted) {
                            books.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa sách thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa sách thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        bUpdate.setTag(position);
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Book bookToUpdate = books.get(position);

                Intent intent = new Intent(context, UpdateBookActivity.class);
                intent.putExtra("book_id", bookToUpdate.getBook_id());
                intent.putExtra("book_name", bookToUpdate.getBook_name());
                intent.putExtra("book_page", bookToUpdate.getBook_page());
                intent.putExtra("book_quantity", bookToUpdate.getStock_quantity());
                intent.putExtra("book_price", bookToUpdate.getBook_price());
                intent.putExtra("book_image_path", bookToUpdate.getBook_image_path());
                intent.putExtra("author_id", bookToUpdate.getAuthor_id());
                intent.putExtra("publisher_id", bookToUpdate.getPublisher_id());
                intent.putExtra("type_of_book_id", bookToUpdate.getType_of_book_id());
                intent.putExtra("store_id", bookToUpdate.getStore_id());
                context.startActivity(intent);
            }
        });


        return rowView;
    }
}
