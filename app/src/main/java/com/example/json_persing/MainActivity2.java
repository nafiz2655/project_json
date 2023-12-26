package com.example.json_persing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;

    HashMap<String,String> hashMap = new HashMap<>();
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    ProgressBar progressBar;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recyclerveiw);
        progressBar = findViewById(R.id.progressBar);


        String url = "https://maltinamaxweb.000webhostapp.com/apps/text.php";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {

                    for (int x = 0; x < response.length(); x++) {
                        JSONObject jsonObject = response.getJSONObject(x);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String Roll = jsonObject.getString("Roll");
                        String College = jsonObject.getString("College");
                        String dep = jsonObject.getString("dep");
                        String img = jsonObject.getString("img");

                        hashMap = new HashMap<>();
                        hashMap.put("id",id);
                        hashMap.put("name",name);
                        hashMap.put("roll",Roll);
                        hashMap.put("college",College);
                        hashMap.put("dep",dep);
                        hashMap.put("img",img);
                        arrayList.add(hashMap);
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(MainActivity2.this, "" + id + "\n" + name + "\n" + Roll + "\n" + College + "\n" + dep, Toast.LENGTH_SHORT).show();



                    }

                    My_adater myAdater = new My_adater();
                    recyclerView.setAdapter(myAdater);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));


                } catch (Exception e) {

                }

            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this, "Error", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        requestQueue.add(jsonArrayRequest);




        Toast.makeText(MainActivity2.this, "Error"+arrayList.size(), Toast.LENGTH_SHORT).show();

    }

    public class My_adater extends RecyclerView.Adapter<My_adater.View_holder>{

        @NonNull
        @Override
        public View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View my_veiw = layoutInflater.inflate(R.layout.item_view,parent, false);

            return new View_holder(my_veiw);
        }

        @Override
        public void onBindViewHolder(@NonNull View_holder holder, int position) {

            HashMap<String,String> hashMap1 =arrayList.get(position);
            String id = hashMap1.get("id");
            String name = hashMap1.get("name");
            String roll = hashMap1.get("roll");
            String college = hashMap1.get("college");
            String dep = hashMap1.get("dep");
            String img = hashMap1.get("img");



            holder.name.setText(name);
            holder.roll.setText(roll);
            holder.coll.setText(college);
            holder.dep.setText(dep);
            Picasso.get().load(img).into(holder.imageView);

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class View_holder extends RecyclerView.ViewHolder{


            TextView name,roll,dep,coll;
            ImageView imageView;
            public View_holder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                roll = itemView.findViewById(R.id.roll);
                dep = itemView.findViewById(R.id.deperment);
                coll = itemView.findViewById(R.id.college);
                imageView = itemView.findViewById(R.id.imageView);
            }


        }
    }
}