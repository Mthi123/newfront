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
import com.example.projeeeeeeeeeect.Models.IncidentType;
import com.example.projeeeeeeeeeect.Models.CounselorListResponse;
import com.example.projeeeeeeeeeect.Models.AssignReportRequest;
import com.example.projeeeeeeeeeect.Models.UserListResponse;
import com.example.projeeeeeeeeeect.Models.MessageHistoryResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

// --- Your API Interface ---
public interface ApiService {

    // ----------------------------------------------------
    // --- AUTHENTICATION & USER MANAGEMENT ---
    // ----------------------------------------------------

    // User Login
    @POST("api/auth/login")
    Call<UserLoginResponse> loginUser(@Body UserLoginRequest loginRequest);

    // Public User Registration
    @POST("api/auth/register")
    Call<CreateUserResponse> registerUser(@Body CreateUserRequest createUserRequest);

    // Admin-level User Creation (e.g., creating a Counselor manually)
    @POST("api/roles/admin")
    Call<CreateUserResponse> createUser(@Body CreateUserRequest createUserRequest);

    // Admin: View all users
    @GET("api/users")
    Call<UserListResponse> getAllUsers();

    // Admin: Fetch all users with the role of Counselor
    // --- FIX: Corrected API path from 'counselors' to 'counsellors' ---
    @GET("api/users/counsellors")
    Call<CounselorListResponse> getAllCounselors();

    // Admin: Fetch all organizations/NGOs (for counselor assignment)
   // @GET("api/organizations")
    //Call<OrganizationListResponse> getAllOrganizations();


    // ----------------------------------------------------
    // --- REPORTING & ASSIGNMENT ---
    // ----------------------------------------------------

    // Submit a new report (User action)
    @POST("api/reports")
    Call<SubmitReportResponse> submitReport(@Body SubmitReportRequest request);

    // Fetch incident types (for the submission form spinner)
    @GET("api/reports/types")
    Call<IncidentType> getIncidentType();

    // View all reports (Counselor/Admin default view)
    @GET("api/reports")
    Call<List<Report>> getAllReports();

    // Fetch all reports that are UNASSIGNED (Admin manage counselors view)
    @GET("api/reports/unassigned")
    Call<List<Report>> getUnassignedReports();

    // Assign a report to a counselor (Admin action)
    @POST("api/reports/assign")
    Call<SubmitReportResponse> assignReport(@Body AssignReportRequest request);

    // Report Stats: View reports by type
    @GET("api/reports/type")
    Call<List<ReportTypeStat>> getReportsByType();

    // Report Stats: View incident types by location
    @GET("api/reports/incident-types/location/{location}")
    Call<List<ReportTypeStat>> getReportsByLocation(@Path("location") String location);

    // Report Stats: View reports by status
    @GET("api/reports/status")
    Call<List<ReportStatusStat>> getReportsByStatus();

    // ----------------------------------------------------
    // --- CHAT & MESSAGING ---
    // ----------------------------------------------------

    // Start a new chat session (Counselor/User action)
    @POST("api/chat/start")
    Call<ChatStartResponse> startChat(@Body ChatStartRequest request);

    // Send a message within an existing channel
    @POST("api/chat/send")
    Call<SendMessageResponse> sendMessage(@Body SendMessageRequest request);

    // Load message history for a channel
    @GET("api/chat/history/{channelUrl}")
    Call<MessageHistoryResponse> getMessageHistory(@Path("channelUrl") String channelUrl);


    // ----------------------------------------------------
    // --- RESOURCES ---
    // ----------------------------------------------------

    // Publish a resource (Counselor action)
    @POST("api/articles")
    Call<PublishArticleResponse> publishResource(@Body PublishArticleRequest request);
}