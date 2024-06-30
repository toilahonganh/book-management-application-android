package dinhhonganh.cnnt1.interfaces.client;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.model.Book;
import dinhhonganh.cnnt1.model.Order;

public class ShoppingCartFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ConstraintLayout shopping_constraint;

    private ListView lvOrder;
    private ImageView iv_empty;
    TextView tvSelectedBookPrice;
    private OrderAdapter orderAdapter;
    ConstraintLayout constraintLayout;
    private DBHelperDatabase dbHelperDatabase;
    private List<Order> orders;
    private List<Book> books;
    private SQLiteDatabase db;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    public static ShoppingCartFragment newInstance(String param1, String param2) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelperDatabase = new DBHelperDatabase(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        lvOrder = rootView.findViewById(R.id.lvOrder);
        iv_empty = rootView.findViewById(R.id.iv_empty);
        constraintLayout = rootView.findViewById(R.id.thanhtoan);
        tvSelectedBookPrice = rootView.findViewById(R.id.total);
        shopping_constraint = rootView.findViewById(R.id.shopping_constraint);



        loadOrders();
        return rootView;
    }

    private void loadOrders() {
        orders = dbHelperDatabase.getAllOrders();
        books = new ArrayList<>();

        for (Order order : orders) {
            Book book = dbHelperDatabase.getBookById(order.getBookId());

            if (book != null) {
                books.add(book);
            }
        }

        if (!books.isEmpty()) {
            iv_empty.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.VISIBLE);

            if (orderAdapter == null) {
                orderAdapter = new OrderAdapter(getActivity(), books, orders);
                orderAdapter.setSelectedBookPriceTextView(tvSelectedBookPrice);
                lvOrder.setAdapter(orderAdapter);
            } else {
                orderAdapter.setBooks(books);
                orderAdapter.setOrders(orders);
                orderAdapter.setSelectedBookPriceTextView(tvSelectedBookPrice);
                orderAdapter.notifyDataSetChanged();
            }
        } else {
            iv_empty.setVisibility(View.VISIBLE);
            constraintLayout.setVisibility(View.GONE);
            shopping_constraint.setBackgroundColor(getResources().getColor(R.color.mau));


        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelperDatabase.close();
    }

    private void showDataListView() {
        orders.clear();

        db = dbHelperDatabase.ketNoiDBRead();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelperDatabase.TABLE_ORDER, null);
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex(DBHelperDatabase.COLUMN_ORDER_ID));
                @SuppressLint("Range") int bookId = cursor.getInt(cursor.getColumnIndex(DBHelperDatabase.COLUMN_BOOK_ID_FK));
                @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex(DBHelperDatabase.COLUMN_USER_ID_FK));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(DBHelperDatabase.COLUMN_QUANTITY));

                Order order = new Order(orderId, userId, bookId, quantity);
                orders.add(order);
            }
        } finally {
            cursor.close();
        }

        orderAdapter.notifyDataSetChanged();
    }
}
