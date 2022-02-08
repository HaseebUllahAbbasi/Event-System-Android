package com.example.drawer_activity.ui.home;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.GET;
import static com.example.drawer_activity.HardCoded.apiLink;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.example.drawer_activity.GlobalValues;
import com.example.drawer_activity.LoginPage;
import com.example.drawer_activity.R;
import com.example.drawer_activity.ShowEvents;
import com.example.drawer_activity.ViewEventRequests;
import com.example.drawer_activity.databinding.FragmentHomeBinding;
import com.ramotion.circlemenu.CircleMenuView;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s)
            {
                onload();

                final CircleMenuView menu = getActivity().findViewById(R.id.circle_menu);
                menu.setEventListener(new CircleMenuView.EventListener(){
                    @Override
                    public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {
                        Log.d("D","onMenuOpenAnimationStart");
                    }
                    @Override
                    public void onMenuOpenAnimationEnd(@NonNull CircleMenuView view) {
                        Log.d("D","onMenuOpenAnimationEnd");
                    }
                    @Override
                    public void onMenuCloseAnimationStart(@NonNull CircleMenuView view) {
                        Log.d("D","onMenuCloseAnimationStart");
                    }
                    @Override
                    public void onMenuCloseAnimationEnd(@NonNull CircleMenuView view) {
                        Log.d("D","onMenuCloseAnimationEnd");
                    }
                    @Override
                    public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
                        Log.d("D","onButtonClickAnimationStart|index: "+index);

                    }
                    @Override
                    public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                        Log.d("D","onButtonClickAnimationEnd|index: "+index);
                        switch (index){
                            case 0:
                                Intent createEvent = new Intent(getContext(), com.example.drawer_activity.createEvent.class);
                                startActivity(createEvent);
                                break;
                            case 1:
                                Intent showEvents = new Intent(getContext(), ShowEvents.class);
                                startActivity(showEvents);
                                break;
                            case 2:
                                Intent viewRequests = new Intent(getContext(), ViewEventRequests.class);
                                startActivity(viewRequests);
                                break;
                            case 4:
                                Intent login = new Intent(getContext(), LoginPage.class);
                                startActivity(login);
                                break;
                        }
                    }
                    @Override
                    public boolean onButtonLongClick(@NonNull CircleMenuView view, int buttonIndex) {
                        Log.d("D","onButtonLongClick|index: "+buttonIndex);
                        return true;
                    }
                    @Override
                    public void onButtonLongClickAnimationStart(@NonNull CircleMenuView view, int buttonIndex) {
                        Log.d("D","onButtonLongClickAnimationStart|index: "+buttonIndex);
                    }
                    @Override
                    public void onButtonLongClickAnimationEnd(@NonNull CircleMenuView view, int buttonIndex) {
                        Log.d("D","onButtonLongClickAnimationEnd|index: "+buttonIndex);
                    }
                });
            }
        });

        return root;
    }
    RequestQueue queue;
    private void onload()
    {

        final JSONObject[] jsonObject = new JSONObject[1];
        queue = Volley.newRequestQueue(getContext());
        String url = apiLink+"/getDetails/"+ GlobalValues.user_id;
        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener() {
            @Override
            public synchronized void onResponse(Object response)
            {
                try {
                    jsonObject[0] = new JSONObject(response.toString());
                    Log.d(TAG, "onResponse: "+jsonObject[0].toString());

                    JSONObject personFetched = jsonObject[0];

                    int completed = personFetched.getInt("completed");
                    int unCompleted = personFetched.getInt("unCompleted");
                    int requests = personFetched.getInt("requests");
                    int events = personFetched.getInt("events");

                    TextView name = getActivity().findViewById(R.id.ViewNameHome);
                    name.setText("Hi "+GlobalValues.user_name+ ", You have ");
                    TextView complete = getActivity().findViewById(R.id.ViewCompletedTasksHome);
                    TextView Incomplete = getActivity().findViewById(R.id.ViewUnCompletedTasksHome);
                    TextView eventsHome = getActivity().findViewById(R.id.ViewEventsHome);
                    TextView req = getActivity().findViewById(R.id.ViewRequestsHome);
                    complete.setText(""+completed);
                    Incomplete.setText(""+unCompleted);
                    req.setText(""+requests);
                    eventsHome.setText(""+events);



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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}