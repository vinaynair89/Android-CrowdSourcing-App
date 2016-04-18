/* new_auth_data=1&     - If there is new user ot password changes
 * debug_data=1&        - Debugging data sent from terminal
 * client_api_ver=1.5.1.422&    - API version of clients terminal
 * timestamp=1314195661&        - THE timestamp of first sync of new device 
 * password_hash=d2824b50d07cfed3d82a480d5d87437af11a4f7e&      - A valid password hash
 * set_locale=en_US&        - 
 * device_os_type=iPhone%20Simulator%204.3.2&       - The device OS code - for mobiles
 * username_hash=6d229fe6593f5250653e3b29184a6e370fc7ffe5&      - A valid username hash
 * device_sync_type=3&      - 3 is for iphone, 4 will be for android
 * device_identification_string=iPhone%20Simulator%204.3.2&     - Device identificator (friendly name)
 * device_resolution=320x480&       - No need of explanation
 * device_identificator=255634997       - This identificator is catched with getDeviceId() function

*/
package com.example.vinay.project239;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vinay.project239.R;

public class Test extends Activity {

    String username,password;
    Button login;
    TextView error;
    ArrayList<NameValuePair> postParameters;
    LocationNot l;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = "vinay";
        password = "vinay";
        //error = (TextView) findViewById(R.id.error);
        final HttpClient httpclient = new DefaultHttpClient();
        final HttpPost httppost = new HttpPost("http://localhost:8000/index.php");
        l.running();

        postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username_hash", username));
        postParameters.add(new BasicNameValuePair("password_hash", password));
    /*postParameters.add(new BasicNameValuePair("debug_data","1"));
    postParameters.add(new BasicNameValuePair("client_api_ver","1.5.1.422"));
    postParameters.add(new BasicNameValuePair("timestamp","1314195661"));
    postParameters.add(new BasicNameValuePair("set_locale","en_US"));
    postParameters.add(new BasicNameValuePair("device_os_type","Android 2.2"));
    postParameters.add(new BasicNameValuePair("device_sync_type","3"));
    postParameters.add(new BasicNameValuePair("device_identificator","255634997"));
    postParameters.add(new BasicNameValuePair("device_identification_string",deviceId));*/
        login = (Button) findViewById(R.id.bnLogin);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    httppost.setEntity(new UrlEncodedFormEntity(postParameters));

                    HttpResponse response = httpclient.execute(httppost);
                    Log.d("myapp", "response " + response.getEntity());

                    String responseBody = EntityUtils.toString(response.getEntity());

                    error.setText(responseBody);
                } catch (Exception e) {
                   System.out.println("error");
                }

            }
        });

    }

}