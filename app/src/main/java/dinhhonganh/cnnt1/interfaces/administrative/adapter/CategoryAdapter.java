package dinhhonganh.cnnt1.interfaces.administrative.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.model.Category;
import dinhhonganh.cnnt1.model.Publisher;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private final Activity context;
    DBHelperDatabase dbHelperDatabase;
    private ArrayList<Category> categories;

    public CategoryAdapter(Activity context, ArrayList<Category> categories) {
        super(context, R.layout.category_adapter_view, categories);
        this.context = context;
        this.categories = categories;
        dbHelperDatabase = new DBHelperDatabase(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rollView = inflater.inflate(R.layout.category_adapter_view, null, true);
        Category category = categories.get(position);


        TextView tvAuthorId = rollView.findViewById(R.id.tvAuthorId);
        TextView tvAuthorName = rollView.findViewById(R.id.tvAuthorName);
        Button bDelete = rollView.findViewById(R.id.bDelete);

        tvAuthorId.setText(String.valueOf(category.getCate_id()));
        tvAuthorName.setText(category.getCate_name());


        bDelete.setTag(position);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Category storeToDelete = categories.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có đồng ý xóa cửa hàng này không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean isDeleted = dbHelperDatabase.deleteCates(storeToDelete.getCate_id());

                        if (isDeleted) {
                            categories.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thể loại sách thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa thể loại sách thất bại!", Toast.LENGTH_SHORT).show();
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
