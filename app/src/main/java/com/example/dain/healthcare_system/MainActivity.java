package www.h_g.co.kr.healthcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String myJSON;


    EditText ev_id;
    EditText ev_pwd;
    private static final String TAG_RESULT = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_PWD = "pwd";

    JSONArray peoples =null;
    ArrayList<HashMap<String,String>> personList;
    ListView list;

    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn_login = (Button)findViewById(R.id.btn_login);
        ev_id = (EditText)findViewById(R.id.edittext_id);
        ev_pwd = (EditText)findViewById(R.id.edittext_pwd);

        personList = new ArrayList<HashMap<String, String>>();
        //getData("35.194.224.80");
        GetData task = new GetData ();
        task.execute("http://35.194.224.80/App_ver.php");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(1==1) {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(MainActivity.this,"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*
    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params){
                String uri = params[0];
               // uri += "//?"+params[1]+"//="+ev_id.getText().toString();
               // uri += "//"+params[2]+"//="+ev_pwd.getText().toString();
                BufferedReader bufferedReader = null;

                try{
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String  json;
                    while ((json = bufferedReader.readLine()) != null){
                        sb.append(json + "/n");
                    }
                    return sb.toString().trim();
                }catch(Exception e){

                }
                return null;
            }
            @Override
            protected  void onPostExecute(String result){
                myJSON = result;
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();

    }
    */
    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this, "Please Wait", null, true,true);

        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d("", "response -" +result);

            if(result==null){

               // mTextViewResult.setText(errorString);
            }
            else{
                ev_id.setText(result);
                //mJsonString = result;
                //showResult();
            }
        }

        @Override
        protected String doInBackground(String... params){

            String get_prams = "0.2";
            String serverURL = params[0];
            serverURL+= "\\?app_ver="+get_prams;
            try{

                URL url=new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode= httpURLConnection.getResponseCode();
                Log.d("", "response code - " +responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode==httpURLConnection.HTTP_OK){
                    inputStream=httpURLConnection.getInputStream();

                }
                else{
                    inputStream=httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();
            }catch (Exception e) {

                Log.d("", "InsertData: Error",e);
                errorString=e.toString();

                return null;
            }
        }
    }
    /*
    private void showResult(){
        try{
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++) {

                JSONObject item = jsonArray.getJSONObject(i);
                String ver_no = item.getString(TAG_VER_NO);
                String version = item.getString(TAG_VERSION);
                String ver_date = item.getString(TAG_VER_DATE);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_VER_NO, ver_no);
                hashMap.put(TAG_VERSION, version);
                hashMap.put(TAG_VER_DATE, ver_date);

                mArrayList.add(hashMap);
            }
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, mArrayList, R.layout.item_list,
                    new String[]{TAG_VER_NO, TAG_VERSION,TAG_VER_DATE},
                    new int[]{R.id.textView_list_id,R.id.textView_list_name,R.id.textView_list_address}

            );
            mlistView.setAdapter(adapter);
        }catch(JSONException e){
            Log.d(TAG, "showResult : ",e);
        }
    }
    */

}
