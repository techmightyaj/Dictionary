package in.ankit.dictionary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import in.techmighty.dictionary_task.R;

/**
 * Created by Ankit Varia on 12/03/16.
 */
public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {

    private List<Model> dictionaryList;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView word, mean;
        public NetworkImageView thumbNail;

        public ViewHolder(View view) {
            super(view);
            word = (TextView) view.findViewById(R.id.single_row_tv_word);
            mean = (TextView) view.findViewById(R.id.single_row_tv_mean);

            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
            thumbNail = (NetworkImageView) view
                    .findViewById(R.id.thumbnail);

        }
    }

    public DictionaryAdapter(List<Model> List) {
        this.dictionaryList = List;
    }

    @Override
    public DictionaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DictionaryAdapter.ViewHolder holder, int position) {
        Model movie = dictionaryList.get(position);
        holder.word.setText(movie.getWord());
        holder.mean.setText(movie.getMeaning());
        holder.thumbNail.setImageUrl(movie.getUrl(), imageLoader);
    }

    @Override
    public int getItemCount() {
        return dictionaryList.size();
    }
}
