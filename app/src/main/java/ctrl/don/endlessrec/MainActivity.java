package ctrl.don.endlessrec;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ctrl.don.endlessrec.adapter.MainAdapter;
import ctrl.don.endlessrec.interfaces.OnLoadMoreListener;
import ctrl.don.endlessrec.model.Room;
import ctrl.don.endlessrec.model.RoomDetail;
import ctrl.don.endlessrec.services.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MainAdapter adapter;
    List<RoomDetail> products ;
    ApiClient apiClient;


    int totalPage = 0;


    int index = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        products = new ArrayList<>();
        adapter = new MainAdapter(MainActivity.this, products);
        adapter.setLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (index == totalPage) {
                            adapter.setMoreDataAvailable(false);
                        } else {
                            // int index = products.size();
                            index = index +1;
                            loadMoreRoom(index);
                        }
                    }
                });
                //Calling loadMore function in Runnable to fix the
                // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        recyclerView.addItemDecoration(new VerticalLineDecorator(2));
        recyclerView.setAdapter(adapter);

        getRoom(index);
    }

    private void getRoom(int index) {
        apiClient = new ApiClient();
        Call<Room> call = apiClient.getApiInterface().getRoomList(true,index);
        call.enqueue(new Callback<Room>() {
                         @Override
                         public void onResponse(Call<Room> call, Response<Room> response) {
                             System.out.println(response.body());
                             if (response.isSuccessful()) {
                                 if (response.body().getData().getListData().getData().size() > 0) {

                                     products.clear();

                                     products.addAll(response.body().getData().getListData().getData());
                                     adapter.notifyDataChanged();
                                     //get total page
                                     totalPage = response.body().getData().getListData().getLastPage();
                                 } else if (response.code() == 200 && response.body().getData().getListData().getData().size() < 1) {
                                     adapter.setMoreDataAvailable(false);
                                 }
                             } else {
                                 adapter.setMoreDataAvailable(false);
                             }
                         }

                         @Override
                         public void onFailure(Call<Room> call, Throwable t) {
                             Log.d("TAG",t.getMessage());
                         }
                     }
        );
    }

    private void loadMoreRoom(int index) {
        products.add(new RoomDetail("load"));
        adapter.notifyItemInserted(products.size() - 1);

        Call<Room> call = apiClient.getApiInterface().getRoomList(true, index);
        call.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {

                    products.remove(products.size() - 1);

                    List<RoomDetail> results = response.body().getData().getListData().getData();

                    Log.d("TAG", String.valueOf(results.size()));

                    if (results.size() > 1) {
                        //add loaded data
                        products.addAll(results);
                    } else {
                        adapter.setMoreDataAvailable(false);
//                        Toast.makeText(getActivity(), "No More Data Available", Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataChanged();
                } else {
                    Toast.makeText(MainActivity.this, String.valueOf(response.errorBody()), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Log.e("TAGG", t.getMessage());
                adapter.setMoreDataAvailable(false);
                adapter.notifyDataChanged();
            }
        });
    }
}
