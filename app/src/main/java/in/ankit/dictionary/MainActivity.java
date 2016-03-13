package in.ankit.dictionary;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.techmighty.dictionary_task.R;

public class MainActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener {

    private String TAG = "MAIN_ACTIVITY";

    private List<Model> dictionaryList;
    private RecyclerView recyclerView;
    private DictionaryAdapter mAdapter;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        if(null != pDialog && !pDialog.isShowing())
        pDialog.show();

        dictionaryList = new ArrayList<>();
        mAdapter = new DictionaryAdapter(dictionaryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        AppController.getInstance().addToRequestQueue(customJSONRequest(URLConstants.LIST));
    }

    private CustomJSONRequest customJSONRequest(String url) {
        CustomJSONRequest customJSONRequestVolley = new CustomJSONRequest(url, this, this);
        return customJSONRequestVolley;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();

        if (error instanceof NoConnectionError)
            Toast.makeText(getBaseContext(), "No Internet Connection.", Toast.LENGTH_SHORT).show();

        if (error instanceof ServerError)
            Toast.makeText(getBaseContext(), "Please Try Again.", Toast.LENGTH_SHORT).show();

        if (error instanceof ParseError)
            Toast.makeText(getBaseContext(), "Please Try Again.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Object response) {
        try {
            hidePDialog();
            JSONObject jsonResponse = new JSONObject(response.toString());
            Log.e(TAG, jsonResponse.toString());


            JSONArray jsonArray = jsonResponse.optJSONArray(URLConstants.WORDS);

            ArrayList<Model> arrayList = new ArrayList<>();
            if (null != jsonArray && jsonArray.length() > 0) {
                for (int i = 0, size = jsonArray.length(); i < size; i++) {
                    Model model = new Model();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    model.setId(jsonObject.getLong(URLConstants.ID));
                    model.setWord(jsonObject.getString(URLConstants.WORD));
                    model.setVariant(jsonObject.getLong(URLConstants.VARIANT));
                    model.setMeaning(jsonObject.getString(URLConstants.MEANING));
                    model.setRatio(jsonObject.getDouble(URLConstants.RATIO));
                    model.setUrl(URLConstants.FETCH_IMAGE + jsonObject.getLong(URLConstants.ID)+".png");

                    if (jsonObject.getDouble(URLConstants.RATIO) > 0)
                        arrayList.add(model);

                }
            }

            Log.e(TAG, "Size:- " + arrayList.size());
            dictionaryList.addAll(arrayList);
            mAdapter.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hidePDialog() {
        if (null != pDialog && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
