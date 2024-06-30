package dinhhonganh.cnnt1.interfaces.administrative.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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
import dinhhonganh.cnnt1.interfaces.administrative.create.CreateNewStore;
import dinhhonganh.cnnt1.model.Author;
import dinhhonganh.cnnt1.model.Store;

public class StoreAdapter extends ArrayAdapter<Store> {
    private final Activity context;
    DBHelperDatabase dbHelperDatabase;
    private ArrayList<Store> stores;
    private View.OnClickListener onDeleteClickListener;

    public StoreAdapter(Activity context, ArrayList<Store> stores) {
        super(context, R.layout.book_adapter_view, stores);
        this.context = context;
        this.stores = stores;
        dbHelperDatabase = new DBHelperDatabase(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rollView = inflater.inflate(R.layout.store_adapter_view, null, true);
        Store store = stores.get(position);


        TextView tvStoreId = rollView.findViewById(R.id.tvStoreId);
        TextView tvStoreName = rollView.findViewById(R.id.tvStoreName);
        TextView tvStoreAddress = rollView.findViewById(R.id.tvStoreAddress);

        Button bDelete = rollView.findViewById(R.id.bDelete);

        tvStoreId.setText(String.valueOf(store.getStore_id()));
        tvStoreName.setText(store.getStore_name());
        tvStoreAddress.setText(store.getStore_address());




        bDelete.setTag(position);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Store storeToDelete = stores.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có đồng ý xóa cửa hàng này không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean isDeleted = dbHelperDatabase.deleteStore(storeToDelete.getStore_id());

                        if (isDeleted) {
                            stores.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa cửa hàng thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa cửa hàng thất bại!", Toast.LENGTH_SHORT).show();
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
