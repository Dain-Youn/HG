package www.h_g.co.kr.healthcare;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yejin on 2018. 4. 8..
 */

public class GetData extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    String errorString = null;
    String result_JSON;
    @Override
    protected void onPreExecute(){
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);



        Log.d("", "response -" +result);
        result_JSON =result;
        if(result==null){

            // mTextViewResult.setText(errorString);
        }
        else{


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

    public String get_result(){
        return result_JSON;
    }
}
