package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//https://api.themoviedb.org/3/movie/550?api_key=95698422c92c26aafa3cd91d99619da1
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener , PopupMenu.OnMenuItemClickListener {

    RequestQueue requestQueue;
    TextView tvr;
    Button reintentar,more,lupa;
    String x="";
    RecyclerView recycleView;
    Handler handler = new Handler();
    ProgressBar progressBar;
    SearchView searchView;
    ArrayList<ItemList> items;
    public ArrayList<String> N_foto,N_titulo,N_des,N_score,N_year;
    public RecycleView recycle;
    int a=0,z=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        N_foto=new ArrayList<>();
        N_titulo=new ArrayList<>();
        N_des=new ArrayList<>();
        N_year=new ArrayList<>();
        N_score=new ArrayList<>();
        recycleView= (RecyclerView)findViewById(R.id.rv);
        tvr= findViewById(R.id.tvr);
        progressBar=findViewById(R.id.progressBar);
        reintentar= findViewById(R.id.reintentar);
        more= findViewById(R.id.more);
        lupa= findViewById(R.id.lupa);
        searchView=findViewById(R.id.searchView);
        searchlistener();
        items=new ArrayList<>();
        z=0;
        get_consultas("https://api.themoviedb.org/3/trending/all/week?api_key=95698422c92c26aafa3cd91d99619da1",0);


        reintentar.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                get_consultas("https://api.themoviedb.org/3/trending/all/week?api_key=95698422c92c26aafa3cd91d99619da1",0);
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        });
        lupa.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                if(a==0){
                    searchView.setVisibility(View.VISIBLE);
                    a=1;
                }
                else{
                    searchView.setVisibility(View.GONE);
                    a=0;
                }

            }

        });
    }

    private void get_consultas(String s ,int z1) {
        progressBar.setVisibility(View.VISIBLE);
        recycleView.setVisibility(View.VISIBLE);
        tvr.setVisibility(View.INVISIBLE);
        reintentar.setVisibility(View.INVISIBLE);
        //progress = ProgressDialog.show(this, "Conectando","Obteniendo datos...", true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject object = null; //Creamos un objeto JSON a partir de la cadena
                        try {

                            object = new JSONObject(response);
                            JSONArray json_array = object.optJSONArray("results");
                            for(int i=0;i<json_array.length();i++){
                                if(z1==0){
                                    if(json_array.getJSONObject(i).getString("media_type").equals("movie")){

                                        N_titulo.add(json_array.getJSONObject(i).getString("original_title"));
                                        N_year.add(json_array.getJSONObject(i).getString("release_date"));
                                    }else if(json_array.getJSONObject(i).getString("media_type").equals("tv")){

                                        N_titulo.add(json_array.getJSONObject(i).getString("original_name"));
                                        N_year.add(json_array.getJSONObject(i).getString("first_air_date"));
                                    }
                                }
                                else if (z1 == 1){
                                    N_titulo.add(json_array.getJSONObject(i).getString("original_name"));
                                    N_year.add(json_array.getJSONObject(i).getString("first_air_date"));

                                }
                                else {
                                    N_titulo.add(json_array.getJSONObject(i).getString("original_title"));
                                    N_year.add(json_array.getJSONObject(i).getString("release_date"));


                                }

                                N_foto.add(json_array.getJSONObject(i).getString("poster_path"));
                                N_des.add(json_array.getJSONObject(i).getString("overview"));
                                N_score.add(json_array.getJSONObject(i).getString("vote_average"));


                            }llenarLista();
                            System.out.println(json_array);

                            recycleView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
                            recycle= new RecycleView(items,getApplicationContext());
                            recycle.notifyDataSetChanged();
                            recycle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ArrayList<String> lista = new ArrayList<String>();
                                    lista.add(N_des.get(recycleView.getChildAdapterPosition(v)));
                                    lista.add(N_foto.get(recycleView.getChildAdapterPosition(v)));
                                    lista.add(N_score.get(recycleView.getChildAdapterPosition(v)));
                                    lista.add(N_year.get(recycleView.getChildAdapterPosition(v)));
                                    lista.add(N_titulo.get(recycleView.getChildAdapterPosition(v)));



                /*aqui termine debia mandar la descripcion al otro activity.
                punto 5 de la guia y luego detalles de diseño*/
                                    Intent intent = new Intent(MainActivity.this,descripcion.class);
                                    intent.putStringArrayListExtra("lista", lista);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), N_titulo.get(recycleView.getChildAdapterPosition(v)), Toast.LENGTH_SHORT).show();
                                }
                            });
                            recycleView.setAdapter(recycle);
                            progressBar.setVisibility(View.INVISIBLE);

                        } catch (JSONException e) {
                            progressBar.setVisibility(View.INVISIBLE);

                            e.printStackTrace();
                            System.out.println(z1);
                            tvr.setText("UPS! SIN RESPUESTA");
                            tvr.setVisibility(View.VISIBLE);
                            reintentar.setVisibility(View.VISIBLE);

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        recycleView.setVisibility(View.INVISIBLE);
                        tvr.setText("UPS! OCURRIO UN ERROR INESPERADO COMPRUEBE SU CONEXIÓN A INTERNET E INTENTE DE NUEVO.");
                        tvr.setVisibility(View.VISIBLE);
                        reintentar.setVisibility(View.VISIBLE);
                        // Handle error
                    }
                });

        // Add the request to the RequestQueue.
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);





    }

    private void llenarLista() {
        for(int i=0;i<N_titulo.size();i++){
            items.add(new ItemList(N_titulo.get(i),N_des.get(i),N_foto.get(i)));
        }

    }

    private void searchlistener(){
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        recycle.filter(newText);
        return false;
    }
    public void showPopup(View view){

        PopupMenu popup= new PopupMenu(this,view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu);
        popup.show();


    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
//https://api.themoviedb.org/3/tv/popular?api_key=95698422c92c26aafa3cd91d99619da1&language=en-US&page=1
            case R.id.series:
                get_consultas("https://api.themoviedb.org/3/tv/popular?api_key=95698422c92c26aafa3cd91d99619da1&language=en-US&page=1",1);
                z=1;
                items.clear();
                N_foto.clear();
                N_titulo.clear();
                N_des.clear();
                N_year.clear();
                N_score.clear();
                return true;
            case R.id.top:
                get_consultas("https://api.themoviedb.org/3/trending/all/week?api_key=95698422c92c26aafa3cd91d99619da1",0);
                z=0;
                items.clear();
                N_foto.clear();
                N_titulo.clear();
                N_des.clear();
                N_year.clear();
                N_score.clear();
                return true;
            case R.id.peliculas:
                get_consultas("https://api.themoviedb.org/3/movie/popular?api_key=95698422c92c26aafa3cd91d99619da1&language=en-US&page=1",2);
                z=2;
                items.clear();
                N_foto.clear();
                N_titulo.clear();
                N_des.clear();
                N_year.clear();
                N_score.clear();
                return true;
            default:
                return false;
        }

    }
}
