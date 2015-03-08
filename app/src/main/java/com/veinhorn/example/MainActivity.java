package com.veinhorn.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.veinhorn.searchadapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends Activity {
    private List<Movie> movies = new ArrayList<>();
    @InjectView(R.id.grid_view) GridView gridView;
    @InjectView(R.id.search_edit_text) EditText editText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        fillList(movies);
        final SearchAdapter adapter = new MyAdapter(movies, this).registerFilter(Movie.class, "enTitle")
                .setIgnoreCase(true);
        gridView.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void fillList(List<Movie> movies) {
        movies.add(new Movie("Во все тяжкие", "Breaking bad", R.drawable.poster_breaking_bad));
        movies.add(new Movie("12 обезьян", "12 Monkeys", R.drawable.poster___monkeys));
        movies.add(new Movie("Алькатраз", "Alcatraz", R.drawable.poster_alcatraz));
        movies.add(new Movie("Декстер", "Dexter", R.drawable.poster_dexter));
        movies.add(new Movie("Герои", "Heroes", R.drawable.poster_heroes));
        movies.add(new Movie("Город гангстеров", "Mob city", R.drawable.poster_mob_city));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}