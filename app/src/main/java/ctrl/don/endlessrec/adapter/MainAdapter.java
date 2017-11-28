package ctrl.don.endlessrec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ctrl.don.endlessrec.Constants;
import ctrl.don.endlessrec.R;
import ctrl.don.endlessrec.interfaces.OnLoadMoreListener;
import ctrl.don.endlessrec.model.RoomDetail;

/**
 * Created by don on 11/28/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_DATA = 0;
    private final int TYPE_PROGRESS = 1;

    private Context context;
    private List<RoomDetail> data;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    static Listener listener;


    public interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public MainAdapter(Context context, List<RoomDetail> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_DATA) {
            return new HistoryHolder(inflater.inflate(R.layout.item_room_list, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.item_progress_bar, parent, false));
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == TYPE_DATA) {
            ((HistoryHolder) holder).bindData(data.get(position),context);

        }


    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).type.equals("Data")) {
            return TYPE_DATA;
        } else {
            return TYPE_PROGRESS;
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    private static class HistoryHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvPrice;
        TextView tvCity;
        TextView tvPerson;


        ImageView iv;

        Button btnOrder;


        public HistoryHolder(View view) {
            super(view);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city);

            tvPerson = (TextView) itemView.findViewById(R.id.tv_person);

            iv = (ImageView) itemView.findViewById(R.id.iv_preview);

            btnOrder = (Button) itemView.findViewById(R.id.btn_order);
        }

        private void bindData(final RoomDetail result, final Context context) {


            tvTitle.setText(result.getSpaceTitle());
            tvPrice.setText("IDR" + " " + String.valueOf(result.getPriceAmount()) + "/" + result.getUnitTitle());
            tvCity.setText(String.valueOf(result.getSpaceCity()));
            tvPerson.setText(String.valueOf(result.getSpaceCapacity()));

            String function = result.getSpaceFunction();
            final String intentFunction;
            switch (function) {
                case "2":
                    intentFunction ="Meeting Room";
                    break;
                case "3":
                    intentFunction ="Service Office";
                    break;
                case "4":
                    intentFunction ="Coworking Space";
                    break;
                default:
                    intentFunction ="Event Space";
                    break;
            }


            Picasso.with(context)
                    .load(Constants.BASE_IMAGE_URL + result.getSpaceImage())
//                    .load(Constants.BASE_IMAGE_URL + "default_image_space.png")
                    .placeholder(R.drawable.ic_android)
                    .fit()
                    .into(iv);


        }
    }


    private static class LoadHolder extends RecyclerView.ViewHolder {
        LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
