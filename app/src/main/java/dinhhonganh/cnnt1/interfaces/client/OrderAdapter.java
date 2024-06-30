package dinhhonganh.cnnt1.interfaces.client;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.model.Book;
import dinhhonganh.cnnt1.model.Order;

public class OrderAdapter extends ArrayAdapter<Book> {
    private final FragmentActivity context;
    DBHelperDatabase dbHelperDatabase;
    private List<Book> books;
    private List<Order> orders;
    SQLiteDatabase db;
    private TextView tvSelectedBookPrice;
    private double totalSelectedBookPrice = 0;

    public OrderAdapter(FragmentActivity context, List<Book> books, List<Order> orders) {
        super(context, R.layout.order_adapter, books);
        this.context = context;
        this.books = books;
        this.orders = orders;
        dbHelperDatabase = new DBHelperDatabase(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rollView = inflater.inflate(R.layout.order_adapter, null, true);
        Book book = books.get(position);
        dbHelperDatabase = new DBHelperDatabase(context);

        TextView tvBookId = rollView.findViewById(R.id.txtid);
        TextView tvBookPrice = rollView.findViewById(R.id.tvBookPrice);
        CheckBox checkBox = rollView.findViewById(R.id.checkBox);
        ImageView bDelete = rollView.findViewById(R.id.bDelete);
        ImageView avatar = rollView.findViewById(R.id.avatar);

        String imagePath = book.getBook_image_path();
        if (imagePath != null && !imagePath.isEmpty()) {
            Glide.with(context)
                    .load(imagePath)
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
        } else {
            avatar.setImageResource(R.drawable.thuchoem);
        }

        int author_id = book.getAuthor_id() + 1;
        String author = dbHelperDatabase.getAuthorNameById(author_id);

        tvBookId.setText(book.getBook_name());
        tvBookPrice.setText(book.getBook_price() + ".000 VND");

        bDelete.setTag(position);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Order orderToDelete = orders.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có đồng ý xóa đơn hàng này khỏi giỏ hàng không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean isDeleted = dbHelperDatabase.deleteOrder(orderToDelete.getOrderId());

                        if (isDeleted) {
                            orders.remove(position);
                            books.remove(position);
                            notifyDataSetChanged();
                            updateTotalPrice();
                            Toast.makeText(context, "Xóa đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa đơn hàng thất bại!", Toast.LENGTH_SHORT).show();
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

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                double bookPrice = Double.parseDouble(book.getBook_price()); // Lấy giá sách từ String và chuyển đổi thành double
                if (isChecked) {
                    totalSelectedBookPrice += bookPrice;
                } else {
                    totalSelectedBookPrice -= bookPrice;
                }
                if (tvSelectedBookPrice != null) {
                    tvSelectedBookPrice.setText(String.valueOf(totalSelectedBookPrice) + "00 VND"); // Hiển thị tổng giá trị mới trên textView
                }
            }
        });

        return rollView;
    }

    public void setSelectedBookPriceTextView(TextView textView) {
        this.tvSelectedBookPrice = textView;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    private void updateTotalPrice() {
        totalSelectedBookPrice = 0;
        for (Book book : books) {
            double bookPrice = Double.parseDouble(book.getBook_price());
            totalSelectedBookPrice += bookPrice;
        }
        if (tvSelectedBookPrice != null) {
            tvSelectedBookPrice.setText(String.valueOf(totalSelectedBookPrice) + "00 VND");
        }
    }
}
