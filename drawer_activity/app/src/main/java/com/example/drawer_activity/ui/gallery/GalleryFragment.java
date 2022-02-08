package com.example.drawer_activity.ui.gallery;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.example.drawer_activity.HardCoded.apiLink;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.drawer_activity.GlobalValues;
import com.example.drawer_activity.HardCoded;
import com.example.drawer_activity.ModifyProfile;
import com.example.drawer_activity.R;
import com.example.drawer_activity.databinding.FragmentGalleryBinding;
//import com.github.drjacky.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;


    Bitmap bitmap;
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
                ImageView profile = getActivity().findViewById(R.id.profile);
                profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA  )== PackageManager.PERMISSION_GRANTED)
                        {
                            startActivityForResult(intent,200);
                        }
                        else
                        {
//                            requestPermissions(new String[]{Manifest.permission.CAMERA},200);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA},200);
                            }
                        }

                    }
                });


                ImageView imageView = getActivity().findViewById(R.id.edit);
                imageView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view)
                    {
//                        Toast.makeText(getContext(),"SImple",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), ModifyProfile.class);
                        startActivity(intent);

//                        ImagePicker.Companion.with(getActivity()).crop().cropOval().maxResultSize(1080,1080,true).start();

//                        ImagePicker.Companion.with(getActivity())
//                                .crop()
//                                .cropOval()
//                                .maxResultSize(512, 512, true)
//                                .createIntentFromDialog((Function1) (new Function1() {
//                                    public Object invoke(Object var1) {
//                                        this.invoke((Intent) var1);
//                                        return Unit.INSTANCE;
//                                    }
//
//                                    public final void invoke(@NotNull Intent it) {
//                                        Intrinsics.checkNotNullParameter(it, "it");
//                                        launcher.launch(it);
//                                    }
//                                }));

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
    RequestQueue queue;
    private void onload()
    {

        final JSONObject[] jsonObject = new JSONObject[1];
        queue = Volley.newRequestQueue(getContext());
        String url = apiLink+"/person/"+ GlobalValues.user_id;
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200)
        {
            if(resultCode== RESULT_OK)
            {
                bitmap =  (Bitmap) data.getExtras().get("data");
                ImageView imageView = getActivity().findViewById(R.id.profile);
                imageView.setImageBitmap(bitmap);

//                StringRequest changeProfile = new StringRequest(POST, apiLink + "/changePic/"+GlobalValues.user_id, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        Log.d(TAG, "onResponse: "+response);
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG, "onErrorResponse: "+error.networkResponse.toString());
//
//                    }
//                }){
//                    @Nullable
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String,String> params = new HashMap<>();
//                        params.put("image",encodeTobase64(bitmap));
//                        return params;
//                    }
//
//                };
//                queue.add(changeProfile);

            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(getContext(),"Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }
    public  String encodeTobase64(Bitmap image)
    {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }
}

// Code for get image from gallery
/**
 *

 public void pickImage() {
 Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
 intent.setType("image/*");
 startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
 }

 @Override
 public void onActivityResult(int requestCode, int resultCode, Intent data) {
 super.onActivityResult(requestCode, resultCode, data);
 if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
 if (data == null) {
 //Display an error
 return;
 }
 InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
 //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
 }
 }
 **/