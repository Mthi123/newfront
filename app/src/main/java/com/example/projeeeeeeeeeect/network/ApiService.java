package com.example.projeeeeeeeeeect.network;

// Import 'List' from java.util

import com.example.projeeeeeeeeeect.Models.ChatStartRequest;
import com.example.projeeeeeeeeeect.Models.ChatStartResponse;
import com.example.projeeeeeeeeeect.Models.CreateUserRequest;
import com.example.projeeeeeeeeeect.Models.CreateUserResponse;
import com.example.projeeeeeeeeeect.Models.PublishArticleRequest;
import com.example.projeeeeeeeeeect.Models.PublishArticleResponse;
import com.example.projeeeeeeeeeect.Models.Report;
import com.example.projeeeeeeeeeect.Models.ReportStatusStat;
import com.example.projeeeeeeeeeect.Models.ReportTypeStat;
import com.example.projeeeeeeeeeect.Models.Resource;
import com.example.projeeeeeeeeeect.Models.SubmitReportRequest;
import com.example.projeeeeeeeeeect.Models.SubmitReportResponse;
import com.example.projeeeeeeeeeect.Models.UserLoginRequest;
import com.example.projeeeeeeeeeect.Models.UserLoginResponse;
import com.example.projeeeeeeeeeect.Models.IncidentTypesResponse;
import com.example.projeeeeeeeeeect.Models.AssignReportRequest;
import com.example.projeeeeeeeeeect.Models.AssignReportResponse;
import com.example.projeeeeeeeeeect.Models.CounsellorsResponse;
import com.example.projeeeeeeeeeect.Models.SignUpRequest;
import com.example.projeeeeeeeeeect.Models.SignUpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Header;


// --- Your API Interface (This part was already correct) ---
public interface ApiService {

    // --- AUTH ---
    @POST("api/auth/login")
    Call<UserLoginResponse> loginUser(@Body UserLoginRequest loginRequest);

    @POST("api/roles/admin")
    Call<CreateUserResponse> createUser(@Body CreateUserRequest createUserRequest);

    @POST("api/auth/anonymous-login")
    Call<UserLoginResponse> anonymousLogin();


    // --- REPORTS ---

    // 1. View all reports
    @GET("api/reports")
    Call<List<Report>> getAllReports(@Header("Authorization") String authToken);

    // 2. View reports by type
    @GET("api/reports/type")
    Call<List<ReportTypeStat>> getReportsByType();

    // 3. View incident types by location
    @GET("api/reports/incident-types/location/{location}")
    Call<List<ReportTypeStat>> getReportsByLocation(@Path("location") String location);

    // 4. View reports by status
    @GET("api/reports/status")
    Call<List<ReportStatusStat>> getReportsByStatus();

    // 5. View Reports By Counsellor
    @GET("api/reports/counsellor/{id}")
    Call<List<Report>> getReportsByCounsellor(
            @Header("Authorization") String authToken,
            @Path("id") int counsellorId
    );
    @POST("api/articles") // <-- NEW ENDPOINT
    Call<PublishArticleResponse> publishResource(@Body PublishArticleRequest request);
    @POST("api/reports") // <-- NEW ENDPOINT: SUBMIT A REPORT
    Call<SubmitReportResponse> submitReport(@Body SubmitReportRequest request);

    @POST("api/chat/start")
    Call<ChatStartResponse> startChat(@Body ChatStartRequest request);

    @GET("api/reports/types")
    Call<IncidentTypesResponse> getIncidentTypes();

    @GET("api/reports")
    Call<List<Report>> getReports();

    @POST("api/reports/assign/{reportId}")
    Call<AssignReportResponse> assignCounsellor(
            @Path("reportId") int reportId,
            @Body AssignReportRequest request
    );

    @GET("api/users/counsellors")
    Call<CounsellorsResponse> getCounsellors();

    @POST("api/auth")
    Call<SignUpResponse>registerUser(@Body SignUpRequest request);

    // <---RESOURCES--->
    @GET("api/resources")
    Call<List<Resource>> getAllResources();

}



