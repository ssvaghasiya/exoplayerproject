package com.newproject.network

import com.newproject.base.datamodel.AllArea
import com.newproject.ui.clients.datamodel.Client
import com.newproject.ui.clients.datamodel.ClientDetails
import com.newproject.ui.clients.datamodel.VisitHistoryData
import com.newproject.ui.createnewtask.datamodel.CreateTask
import com.newproject.ui.home.datamodel.*
import com.newproject.ui.login.datamodel.LoginData
import com.newproject.ui.login.datamodel.LoginDataModel
import com.newproject.ui.login.datamodel.LogoutData
import com.newproject.ui.taskdetail.taskdetaildesc.datamodel.TaskDetails
import com.newproject.ui.taskdetail.taskdetailstep1.datamodel.StartVisit
import com.newproject.ui.taskdetail.taskdetailstep2.datamodel.Product
import com.newproject.ui.taskdetail.taskdetailstep2.datamodel.TaskStep2Details
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface APIinterface {

    @POST("auth/login")
    fun login(@Body loginDataModel: LoginDataModel): Call<LoginData>

    @POST("listing/visit")
    fun createTask(@Body param: RequestBody): Call<CreateTask>

    @GET("listing/visit/")
    fun getAllVisits(): Call<AllVisitsData>

    @GET("listing/visit/today-visits")
    fun getTodaysVisits(): Call<TodaysVisitData>

    @GET("auth/logout")
    fun logOut(): Call<LogoutData>

    @GET("listing/visit/visit-detail")
    fun getTask(@QueryMap options: Map<String, String>): Call<TaskDetails>

    @GET("listing/target/all-targets")
    fun getAllTargets(): Call<AllTarget>

    @GET("listing/target/successful-monthly-visit")
    fun getSuccessfulMonthlyVisits(): Call<SuccessfulMonthlyVisitsData>

    @GET("listing/target/revenue/monthly")
    fun getMonthlyRevenue(): Call<MonthlyRevenueData>

    @GET("listing/target/monthly-visit")
    fun getMonthlyVisits(): Call<MonthlyVisitsData>

    @GET("listing/area/{id}")
    fun getArea(
        @Path(
            value = "id",
            encoded = true
        ) id: String
    ): Call<AllArea>

    @GET("listing/client")
    fun getClients(): Call<Client>

    @GET("listing/client/client-detail")
    fun getClientDetails(@QueryMap options: Map<String, String>): Call<ClientDetails>

    @GET("listing/visit/start-visit")
    fun startVisit(@QueryMap options: Map<String, String>): Call<StartVisit>

//    @Headers("Content-Type: application/json")
    @POST("listing/visit/update")
    fun updateVisit(
        @Query(
            value = "visit_id"
        ) visit_id: String,
        @Body param: RequestBody
    ): Call<TaskStep2Details>

    @GET("listing/product/all-products")
    fun getAllProducts(): Call<Product>

    @GET("listing/visit/visit-history")
    fun getVisitHistory(@QueryMap options: Map<String, String>): Call<VisitHistoryData>

    @GET("attendance/check-in/22.258652/71.192383")
    fun getCheckIn(): Call<CheckInData>

    @GET("attendance/check-out/22.258652/71.192383")
    fun getCheckOut(): Call<CheckOutData>

    @GET("attendance/break-start/22.258652/71.192383")
    fun getBreakStart(): Call<BreakStartData>

    @GET("attendance/break-end/22.258652/71.192383")
    fun getBreakEnd(): Call<BreakEndData>

    @GET("attendance")
    fun getEmployeeAttendance(): Call<AttendanceData>

    @GET("listing/visit/visit-search")
    fun getSearchVisit(@QueryMap options: Map<String, String>): Call<SearchVisitData>
}