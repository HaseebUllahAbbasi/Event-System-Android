package com.example.drawer_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CustomNotesAdapter extends ArrayAdapter<NotesModel> {
    List<NotesModel> list;
    Button removeButton;
    public CustomNotesAdapter(@NonNull Context context, int resource, @NonNull List<NotesModel> objects) {
        super(context, resource, objects);
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_all_note_layout, parent, false);
        NotesModel note = list.get(position);

        TextView note_text = convertView.findViewById(R.id.note_num);
        TextView note_desc = convertView.findViewById(R.id.note_desc);
        removeButton = convertView.findViewById(R.id.remove_note_btn);

        note_text.setText("Note "+(position+1));
        note_desc.setText(note.getNoteText());

        removeButton.setTag(position);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postUrl = HardCoded.apiLink+"/removeNote";
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());


                JSONObject postData = new JSONObject();
                try {
                    postData.put("plannerId", GlobalValues.user_id);
                    postData.put("noteId", list.get(Integer.parseInt(v.getTag().toString())).getNoteId());
                    postData.put("eventId",GlobalValues.eventId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Toast.makeText(getContext(), "Note has been Removed", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });

        return convertView;
    }
}
