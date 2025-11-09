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
import com.example.projeeeeeeeeeect.Models.SendMessageRequest;
import com.example.projeeeeeeeeeect.Models.SendMessageResponse;
import com.example.projeeeeeeeeeect.Models.SubmitReportRequest;
import com.example.projeeeeeeeeeect.Models.SubmitReportResponse;
import com.example.projeeeeeeeeeect.Models.UserLoginRequest;
import com.example.projeeeeeeeeeect.Models.UserLoginResponse;
import com.example.projeeeeeeeeeect.Models.IncidentTypesResponse;
import com.google.gson.annotations.SerializedName;
import com.example.projeeeeeeeeeect.Models.AssignReportRequest;
import com.example.projeeeeeeeeeect.Models.AssignReportResponse;
import com.example.projeeeeeeeeeect.Models.CounsellorsResponse;
import com.example.projeeeeeeeeeect.Models.SignUpRequest;
import com.example.projeeeeeeeeeect.Models.SignUpResponse;
import com.example.projeeeeeeeeeect.Models.ReportsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

// --- Your API Interface (This part was already correct) ---
public interface ApiService {

    // --- AUTH ---
    @POST("api/auth/login")
    Call<UserLoginResponse> loginUser(@Body UserLoginRequest loginRequest);

    @POST("api/roles/admin")
    Call<CreateUserResponse> createUser(@Body CreateUserRequest createUserRequest);


    // --- REPORTS ---

    // 1. View all reports
    @GET("api/reports")
    Call<List<Report>> getAllReports();

    // 2. View reports by type
    @GET("api/reports/type")
    Call<List<ReportTypeStat>> getReportsByType();

    // 3. View incident types by location
    @GET("api/reports/incident-types/location/{location}")
    Call<List<ReportTypeStat>> getReportsByLocation(@Path("location") String location);

    // 4. View reports by status
    @GET("api/reports/status")
    Call<List<ReportStatusStat>> getReportsByStatus();

    @POST("api/resources") // <-- NEW ENDPOINT
    Call<PublishArticleResponse> publishResource(@Body PublishArticleRequest request);

    @POST("api/reports") // <-- NEW ENDPOINT: SUBMIT A REPORT
    Call<SubmitReportResponse> submitReport(@Body SubmitReportRequest request);

    @POST("api/chat/start")
    Call<ChatStartResponse> startChat(@Body ChatStartRequest request);

    @POST("api/chat/send")
    Call<SendMessageResponse> sendMessage(@Body SendMessageRequest request);

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



}

//package com.example.projeeeeeeeeeect.Models;
//import com.google.gson.annotations.SerializedName;
//import java.util.List;



