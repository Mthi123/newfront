package com.example.projeeeeeeeeeect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeeeeeeeeeect.Adapters.ArticleAdapter;
import com.example.projeeeeeeeeeect.Models.Article;

import java.util.ArrayList;
import java.util.List;

public class Articles extends AppCompatActivity {

    private RecyclerView recyclerResources;
    private ArticleAdapter adapter;
    private List<Article> articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        recyclerResources = findViewById(R.id.recyclerResources);
        recyclerResources.setLayoutManager(new LinearLayoutManager(this));

        articleList = new ArrayList<>();
        adapter = new ArticleAdapter(this, articleList);
        recyclerResources.setAdapter(adapter);

        loadResources();
    }

    private void loadResources() {
        // Mock resources for now
        articleList.add(new Article("What is GBV?", "Gender-based violence (GBV) is any harmful act ..."));
        articleList.add(new Article("How to Stay Safe", "Always trust your instincts and ..."));
        articleList.add(new Article("Emergency Contacts", "Contact 0800-123-456 or 911 in emergencies"));

        adapter.notifyDataSetChanged();
    }
}