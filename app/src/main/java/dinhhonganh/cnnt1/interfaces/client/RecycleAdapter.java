package dinhhonganh.cnnt1.interfaces.client;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.List;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.model.Book;
import com.bumptech.glide.request.RequestListener;
import com.google.android.material.snackbar.Snackbar;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.BookViewHolder> {
    private List<Book> books;
    private Context context;

    public RecycleAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycle_book, parent, false);
        return new BookViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.txtBookName.setText(book.getBook_name());
        holder.txtBookPrice.setText(String.valueOf(book.getBook_price()) + ".000 VND");



        String imagePath = book.getBook_image_path();
        if (imagePath != null && !imagePath.isEmpty()) {
            Glide.with(context)
                    .load(imagePath) // Đổi từ đường dẫn thành File
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
                    .into(holder.bookImage);
        } else {
            holder.bookImage.setImageResource(R.drawable.thuchoem);
        }
        holder.btn_buy.setOnClickListener(v -> {
            insertOrder(book);
            Snackbar.make(holder.itemView, "Sách đã được thêm vào giỏ hàng.", Snackbar.LENGTH_SHORT).show();
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DeatilInforBook.class);
            intent.putExtra("BOOK_ID", book.getBook_id());
            intent.putExtra("BOOK_NAME", book.getBook_name());
            intent.putExtra("BOOK_PRICE", book.getBook_price());
            intent.putExtra("BOOK_IMAGE_PATH", book.getBook_image_path());
            intent.putExtra("BOOK_PUBLISHER", book.getPublisher_id());
            intent.putExtra("BOOK_AUTHOR", book.getAuthor_id());
            intent.putExtra("BOOK_TYPE", book.getType_of_book_id());
            intent.putExtra("BOOK_PAGE", book.getBook_page());

            context.startActivity(intent);
        });

    }
    private void insertOrder(Book book) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int userId = preferences.getInt("user_id", -1);

        DBHelperDatabase dbHelperDatabase = new DBHelperDatabase(context);
        SQLiteDatabase db = dbHelperDatabase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbHelperDatabase.COLUMN_USER_ID, userId);
        values.put(dbHelperDatabase.COLUMN_BOOK_ID_FK, book.getBook_id());
        values.put(dbHelperDatabase.COLUMN_QUANTITY, 1);

        long newRowId = db.insert(dbHelperDatabase.TABLE_ORDER, null, values);

        dbHelperDatabase.close();

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView txtBookName, txtBookPrice;

        Button btn_buy;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.book_image);
            txtBookName = itemView.findViewById(R.id.txt_book_name);
            txtBookPrice = itemView.findViewById(R.id.txt_book_price);
            btn_buy = itemView.findViewById(R.id.btn_buy);
        }
    }


}
