package com.example.projeeeeeeeeeect.admin.mainbtns;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.Models.User; // Assuming the existing User model is used
import com.example.projeeeeeeeeeect.Models.UserListResponse; // <-- NEW IMPORT
import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminViewUsersActivity extends AppCompatActivity {

    private ListView usersListView;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_users);

        usersListView = findViewById(R.id.usersListView);

        loadUsers();

        usersListView.setOnItemClickListener((parent, view, position, id) -> {
            User selectedUser = userList.get(position);
            // Since User model only has role_id, id, name, and email, we show the basics
            Toast.makeText(this,
                    "User: " + selectedUser.name +
                            " | Role ID: " + selectedUser.role_id,
                    Toast.LENGTH_LONG).show();
            // TODO: Implement navigation to user detail/management screen if needed.
        });
    }

    private void loadUsers() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<UserListResponse> call = apiService.getAllUsers(); // Use the new API call

        call.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList.clear();
                    userList.addAll(response.body().getUsers());

                    List<String> displayUsers = new ArrayList<>();
                    // The User model in your project only has role_id, id, name, and email.
                    // Assuming role_id 3=Admin, 2=Counselor, 1=User.
                    for (User user : userList) {
                        String role = "";
                        switch (user.role_id) {
                            case 3: role = "ADMIN"; break;
                            case 2: role = "COUNSELOR"; break;
                            case 1: role = "USER"; break;
                            default: role = "Unknown"; break;
                        }
                        displayUsers.add(role + ": " + user.name + " (" + user.email + ")");
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            AdminViewUsersActivity.this,
                            android.R.layout.simple_list_item_1,
                            displayUsers
                    );
                    usersListView.setAdapter(adapter);
                    Toast.makeText(AdminViewUsersActivity.this, "Total users loaded: " + userList.size(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AdminViewUsersActivity.this, "Failed to load users. Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                Toast.makeText(AdminViewUsersActivity.this, "Network Error loading users: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}