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
import dinhhonganh.cnnt1.interfaces.administrative.create.CreateNewAuthor;
import dinhhonganh.cnnt1.model.Author;
import dinhhonganh.cnnt1.model.Book;
import dinhhonganh.cnnt1.model.Publisher;

public class AuthorApdapter  extends ArrayAdapter<Author> {
    private final Activity context;
    DBHelperDatabase dbHelperDatabase;
    private ArrayList<Author> authors;
    private View.OnClickListener onDeleteClickListener;

    public AuthorApdapter(Activity context, ArrayList<Author> authors) {
        super(context, R.layout.author_adapter_view, authors);
        this.context = context;
        this.authors = authors;
        dbHelperDatabase = new DBHelperDatabase(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rollView = inflater.inflate(R.layout.author_adapter_view, null, true);
        Author author = authors.get(position);


        TextView tvAuthorId = rollView.findViewById(R.id.tvAuthorId);
        TextView tvAuthorName = rollView.findViewById(R.id.tvAuthorName);
        Button bDelete = rollView.findViewById(R.id.bDelete);

        tvAuthorId.setText(String.valueOf(author.getAuthorId()));
        tvAuthorName.setText(author.getAuthorName());

        bDelete.setTag(position);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Author storeToDelete = authors.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có đồng ý xóa cửa hàng này không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean isDeleted = dbHelperDatabase.deleteAuthor(storeToDelete.getAuthorId());

                        if (isDeleted) {
                            authors.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa tác giả thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa tác giả thất bại!", Toast.LENGTH_SHORT).show();
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



        return rollView;
    }
}
