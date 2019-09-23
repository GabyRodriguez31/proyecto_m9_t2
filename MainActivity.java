package com.rafa.igenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EVENT_POSITION = "com.rafa.agenda.EVENT_POSITION";
    List<Event> events;
    Button addEvent;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences("Events", MODE_PRIVATE);

        String json = sharedPreferences.getString("events", "");
        Event event = new Event();

        if (json != null && !json.isEmpty()) {
            Type listType = new TypeToken<List<Event>>(){}.getType();
            events = event.fromJson(json, listType);
        }

        if (events == null) {
            events = new ArrayList<>();
        }

        // Capture the layout's TextView and set the string as its text
        ListView listView = findViewById(R.id.events);
        final EventAdapter adapter = new EventAdapter(events, this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*events.remove(position);
                String json = new Event().toJson(events);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("events", json);
                editor.apply();
                adapter.notifyDataSetChanged();*/
                Intent intent = new Intent(getApplicationContext(), MapsActivityOptions.class);
                intent.putExtra(EVENT_POSITION, position);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }

    public void addEvent(View view) {
        Intent intent = new Intent(this, ActivityPickers.class);
        startActivity(intent);
    }
}