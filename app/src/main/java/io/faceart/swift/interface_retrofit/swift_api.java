package io.faceart.swift.interface_retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface swift_api {

    @POST("Riders/login?include=user")
    Call<Rider> getRider(@Body login credentials);


    @PATCH("Riders/{riderId}")
    Call<RiderActivity> setRiderOnlineStatus(@Header("Authorization") String Authorization,@Path("riderId") String riderId,@Body online status);

    @GET("Riders/get-tasks")
    Call<List<DeliveryParcel>> getParcelsByRiders(@Header("Authorization") String Authorization, @Query("riderId") String riderId);

    @POST("Parcels/{parcelid}/scan-parcel")
    Call<List<DeliveryParcel>> scanParcels(@Header("Authorization") String Authorization, @Path("parcelid") String parcelid,@Body parcel_scan user_task);

    @POST("RiderTasks/{tasklid}")
    Call<List<DeliveryParcel>> RiderTasksupdate(@Header("Authorization") String Authorization, @Path("tasklid") String tasklid);

    @POST("RiderTasks/{taskid}/manage-task")
    Call<List<DeliveryParcel>> manageTask(@Header("Authorization") String Authorization, @Path("taskid") String parcelid,@Body manage_task user_task);

}
