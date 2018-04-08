package www.h_g.co.kr.healthcare;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dain on 2018. 4. 8..
 */

public class URLConnect {

    String myJSON;
    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params){
                String uri = params[0];

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
    public String get_myJSON(){
        return myJSON;
    }
}
