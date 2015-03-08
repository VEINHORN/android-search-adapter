# Android SearchAdapter
Simple way to filter your ListView or GridView content. Just extend parameterized SearchAdapter class and override getView() method.

#Example
![](http://i.imgur.com/WeYbnDC.gif)

#Usage
1 Extend your adapter class from SearchAdapter and override getView() method.
```Java
public class MyAdapter extends SearchAdapter<Movie> {
    class ViewHolder {
        @InjectView(R.id.serial_title) TextView title;
        @InjectView(R.id.serial_original_title) TextView enTitle;
        @InjectView(R.id.serial_poster) ImageView poster;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public MyAdapter(List<Movie> movies, Context context) {
        super(movies, context);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_view_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.title.setText(filteredContainer.get(position).getTitle());
        viewHolder.enTitle.setText(filteredContainer.get(position).getEnTitle());
        viewHolder.poster.setImageDrawable(context.getResources().getDrawable(filteredContainer.get(position).getPoster()));
        return convertView;
    }
}
```

2 Register filter for your content. Pass to method class type and field name for searching.
```Java
final SearchAdapter adapter = new MyAdapter(movies, this).registerFilter(Movie.class, "enTitle");
gridView.setAdapter(adapter);
```

3 Call filter method from anywhere you want.
```Java
adapter.filter(string);
```

Your class should contain getters for fields.
```Java
public class Movie {
    private String title, enTitle;
    private int poster;

    public String getTitle() {
        return title;
    }

    public String getEnTitle() {
        return enTitle;
    }

    public int getPoster() {
        return poster;
    }
}
```

For more details see [Example](https://github.com/VEINHORN/android-search-adapter/tree/master/app).

#License
===============
        Copyright 2015 Boris Korogvich
        
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        
        http://www.apache.org/licenses/LICENSE-2.0
        
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
