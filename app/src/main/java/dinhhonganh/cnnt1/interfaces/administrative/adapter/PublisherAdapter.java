package dinhhonganh.cnnt1.interfaces.administrative.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import dinhhonganh.cnnt1.interfaces.administrative.create.CreateNewAuthor;
import dinhhonganh.cnnt1.model.Author;
import dinhhonganh.cnnt1.model.Publisher;
import dinhhonganh.cnnt1.model.Store;

public class PublisherAdapter extends ArrayAdapter<Publisher> {
    private final Activity context;
    DBHelperDatabase dbHelperDatabase;
    private ArrayList<Publisher> publishers;

    public PublisherAdapter(Activity context, ArrayList<Publisher> publishers) {
        super(context, R.layout.publisher_adapter_view, publishers);
        this.context = context;
        this.publishers = publishers;
        dbHelperDatabase = new DBHelperDatabase(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rollView = inflater.inflate(R.layout.publisher_adapter_view, null, true);
        Publisher publisher = publishers.get(position);


        TextView tvAuthorId = rollView.findViewById(R.id.tvAuthorId);
        TextView tvAuthorName = rollView.findViewById(R.id.tvAuthorName);
        Button bDelete = rollView.findViewById(R.id.bDelete);

        tvAuthorId.setText(String.valueOf(publisher.getPublisher_id()));
        tvAuthorName.setText(publisher.getPublisher_name());


        bDelete.setTag(position);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Publisher storeToDelete = publishers.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có đồng ý xóa cửa hàng này không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean isDeleted = dbHelperDatabase.deletePublisher(storeToDelete.getPublisher_id());

                        if (isDeleted) {
                            publishers.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa NXB thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa NXB thất bại!", Toast.LENGTH_SHORT).show();
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

