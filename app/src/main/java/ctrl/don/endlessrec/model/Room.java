package ctrl.don.endlessrec.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gideon on 20/11/17.
 */

public class Room {

    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("data")
    @Expose
    public Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("list-data")
        @Expose
        public ListData listData;

        public ListData getListData() {
            return listData;
        }

        public void setListData(ListData listData) {
            this.listData = listData;
        }


        public class ListData {

            @SerializedName("current_page")
            @Expose
            public Integer currentPage;
            @SerializedName("data")
            @Expose
            public List<RoomDetail> data = new ArrayList<>();
            @SerializedName("from")
            @Expose
            public Integer from;
            @SerializedName("last_page")
            @Expose
            public Integer lastPage;
            @SerializedName("next_page_url")
            @Expose
            public String nextPageUrl;
            @SerializedName("path")
            @Expose
            public String path;
            @SerializedName("per_page")
            @Expose
            public String perPage;
            @SerializedName("prev_page_url")
            @Expose
            public Object prevPageUrl;
            @SerializedName("to")
            @Expose
            public Integer to;
            @SerializedName("total")
            @Expose
            public Integer total;

            public Integer getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(Integer currentPage) {
                this.currentPage = currentPage;
            }

            public List<RoomDetail> getData() {
                return data;
            }

            public void setData(List<RoomDetail> data) {
                this.data = data;
            }

            public Integer getFrom() {
                return from;
            }

            public void setFrom(Integer from) {
                this.from = from;
            }

            public Integer getLastPage() {
                return lastPage;
            }

            public void setLastPage(Integer lastPage) {
                this.lastPage = lastPage;
            }

            public String getNextPageUrl() {
                return nextPageUrl;
            }

            public void setNextPageUrl(String nextPageUrl) {
                this.nextPageUrl = nextPageUrl;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getPerPage() {
                return perPage;
            }

            public void setPerPage(String perPage) {
                this.perPage = perPage;
            }

            public Object getPrevPageUrl() {
                return prevPageUrl;
            }

            public void setPrevPageUrl(Object prevPageUrl) {
                this.prevPageUrl = prevPageUrl;
            }

            public Integer getTo() {
                return to;
            }

            public void setTo(Integer to) {
                this.to = to;
            }

            public Integer getTotal() {
                return total;
            }

            public void setTotal(Integer total) {
                this.total = total;
            }
        }

    }


}
