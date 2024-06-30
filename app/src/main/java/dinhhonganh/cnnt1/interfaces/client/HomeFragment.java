package dinhhonganh.cnnt1.interfaces.client;

import android.graphics.Outline;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dinhhonganh.cnnt1.R;
import dinhhonganh.cnnt1.helper.DBHelperDatabase;
import dinhhonganh.cnnt1.model.Book;

public class HomeFragment extends Fragment {

    // Array of image resources
    private int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    private Handler handler;
    private Runnable runnable;
    private int delay = 3000;

    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecycleAdapter adapter;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private EditText editText_search;
    TextView textView17, textView18, textView19;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        textView17 = rootView.findViewById(R.id.textView17);
        textView18 = rootView.findViewById(R.id.textView18);
        textView19 = rootView.findViewById(R.id.textView19);

        editText_search = rootView.findViewById(R.id.editText_search);
        editText_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    searchBooks(editText_search.getText().toString());
                    return true;
                }
                return false;
            }
        });

        viewPager = rootView.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        startAutoScroll();

        recyclerView = rootView.findViewById(R.id.recycle_list_book);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new RecycleAdapter(requireContext(), getMockBooks());
        recyclerView.setAdapter(adapter);

        recyclerView1 = rootView.findViewById(R.id.recycle_list_book1);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new RecycleAdapter(requireContext(), getComicBooks());
        recyclerView1.setAdapter(adapter);

        recyclerView2 = rootView.findViewById(R.id.recycle_list_book2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new RecycleAdapter(requireContext(), getVHBooks());
        recyclerView2.setAdapter(adapter);

        return rootView;
    }

    private void startAutoScroll() {
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItems = viewPagerAdapter.getCount();
                if (currentItem == totalItems - 1) {
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(currentItem + 1);
                }
                handler.postDelayed(this, delay);
            }
        };
        handler.postDelayed(runnable, delay);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(requireContext());
            imageView.setImageResource(images[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            // Set border radius
            imageView.setClipToOutline(true); // Clip the image to the outline
            imageView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    int radius = getResources().getDimensionPixelOffset(R.dimen.banner_corner_radius);
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
                }
            });

            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }

    private List<Book> getMockBooks() {
        DBHelperDatabase dbHelperDatabase = new DBHelperDatabase(requireContext());
        return dbHelperDatabase.getAllBooks();
    }

    private List<Book> getComicBooks() {
        DBHelperDatabase dbHelperDatabase = new DBHelperDatabase(requireContext());
        return dbHelperDatabase.getBooksByTypeOfBookId(10);
    }
    private List<Book> getVHBooks() {
        DBHelperDatabase dbHelperDatabase = new DBHelperDatabase(requireContext());
        return dbHelperDatabase.getBooksByTypeOfBookId(9);
    }

    private void searchBooks(String query){
        DBHelperDatabase dbHelperDatabase = new DBHelperDatabase(requireContext());
        List<Book> searchResults = dbHelperDatabase.getBooksByName(query);
        adapter = new RecycleAdapter(requireContext(), searchResults);
        recyclerView.setAdapter(adapter);

        if (searchResults.isEmpty()) {
            recyclerView1.setVisibility(View.VISIBLE);
            textView17.setVisibility(View.VISIBLE);
            textView18.setVisibility(View.VISIBLE);
        } else {
            recyclerView1.setVisibility(View.GONE);
            textView17.setVisibility(View.GONE);
            textView18.setVisibility(View.GONE);
        }
    }
}
