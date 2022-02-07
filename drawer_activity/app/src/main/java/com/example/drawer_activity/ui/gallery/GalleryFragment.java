package com.example.drawer_activity.ui.gallery;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.GET;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.drawer_activity.HardCoded;
import com.example.drawer_activity.R;
import com.example.drawer_activity.GlobalValues;
import com.example.drawer_activity.databinding.FragmentGalleryBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s)
            {

                onload();
                ImageView imageView = getActivity().findViewById(R.id.edit);
                imageView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),"SImple",Toast.LENGTH_LONG).show();
                    }
                } );
//                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onload()
    {

        final JSONObject[] jsonObject = new JSONObject[1];
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = HardCoded.apiLink+"/person/"+ GlobalValues.user_id;
        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener() {
            @Override
            public synchronized void onResponse(Object response)
            {
                try {
                    jsonObject[0] = new JSONObject(response.toString());
                    Log.d(TAG, "onResponse: "+jsonObject[0].toString());
                    JSONObject personFetched = jsonObject[0].getJSONObject("personFetched");
                    String name = personFetched.getString("name");
                    String email = personFetched.getString("email");
                    String profile = personFetched.getString("profile");
                    String bio = personFetched.getString("bio");
                    String number = personFetched.getString("number");


                    TextView nameView = getActivity().findViewById(R.id.name_view_profile);
                    TextView emailView = getActivity().findViewById(R.id.email_view_profile);
                    TextView bioView = getActivity().findViewById(R.id.bio_view_profile);
                    TextView numberView = getActivity().findViewById(R.id.number_view_profile);
                    emailView.setText(email);
                    nameView.setText(name);
                    bioView.setText(bio);
                    numberView.setText(number);



                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public synchronized void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, "onErrorResponse: "+error.toString());

            }
        });
        queue.add(stringRequest);
    }

}