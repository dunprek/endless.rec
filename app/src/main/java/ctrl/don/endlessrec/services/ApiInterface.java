package ctrl.don.endlessrec.services;


import ctrl.don.endlessrec.model.Room;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gideon on 17/11/2017.
 */

public interface ApiInterface {




    /***************************************** GET ************************************************/

    //SPACE LIST
    @GET("search-space/")
    Call<Room> getRoomList(@Query("isMobile") boolean isMobile,
                           @Query("page") int page);


}
