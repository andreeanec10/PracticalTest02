package ro.pub.cs.systems.eim.practicaltest02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;

//nc 192.168.251.199 2027  <- DEFAULT GATEWAY
public class PracticalTest02MainActivity extends AppCompatActivity {

    private List<String> options = Arrays.asList("temperature", "wind_speed", "condition", "pressure", "humidity", "all");

    // Server widgets
    private EditText serverPortEditText = null;
    private Button connectButton = null;

    // Client widgets
    private EditText clientAddressEditText = null;
    private EditText clientPortEditText = null;
    private EditText putKey = null;
    private EditText putValue = null;
    private EditText getKey = null;
    private Button putButton = null;
    private Button getButton = null;
    private TextView weatherForecastTextView = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        Log.i("PracticalTest02", "[MAIN ACTIVITY] onCreate() callback method has been invoked");

        serverPortEditText = (EditText) findViewById(R.id.server_port_edit_text);
        connectButton = (Button) findViewById(R.id.connect_button);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String port = serverPortEditText.getText().toString().trim();
                if (port.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    serverThread = new ServerThread(Integer.parseInt(port));
                    if (serverThread.getServerSocket() == null) {
                        Log.e("MainActivity", "[MAIN ACTIVITY] Could not create server thread!");
                    } else {
                        serverThread.start();
                    }
                }
            }
        });

        clientAddressEditText = (EditText) findViewById(R.id.client_address_edit_text);
        clientPortEditText = (EditText) findViewById(R.id.client_port_edit_text);
        putKey = (EditText) findViewById(R.id.post_key);
        putValue = (EditText) findViewById(R.id.post_value);
        getKey = (EditText) findViewById(R.id.get_key);

        getButton = (Button) findViewById(R.id.get_button);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String address = clientAddressEditText.getText().toString().trim();
                String port = clientPortEditText.getText().toString().trim();
                String key = getKey.getText().toString();

                if (address.isEmpty() || port.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "[MAIN ACTIVITY] Client connection parameters should be filled!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (serverThread == null || !serverThread.isAlive()) {
                        Toast.makeText(getApplicationContext(),
                                "[MAIN ACTIVITY] There is no server to connect to!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        if (key.isEmpty()) {
                            Toast.makeText(getApplicationContext(),
                                    "[MAIN ACTIVITY] Parameters from client (city / information type) should be filled",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            weatherForecastTextView.setText(Constants.EMPTY_STRING);
                            clientThread = new ClientThread(
                                    address, Integer.parseInt(port), key, null, weatherForecastTextView);
                            clientThread.start();
                        }
                    }
                }
            }
        });
        putButton = (Button) findViewById(R.id.post_button);
        putButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String address = clientAddressEditText.getText().toString().trim();
                String port = clientPortEditText.getText().toString().trim();
                String key = putKey.getText().toString();
                String val = putValue.getText().toString();

                if (address.isEmpty() || port.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "[MAIN ACTIVITY] Client connection parameters should be filled!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (serverThread == null || !serverThread.isAlive()) {
                        Toast.makeText(getApplicationContext(),
                                "[MAIN ACTIVITY] There is no server to connect to!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        if (key.isEmpty()) {
                            Toast.makeText(getApplicationContext(),
                                    "[MAIN ACTIVITY] Parameters from client (city / information type) should be filled",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            weatherForecastTextView.setText(Constants.EMPTY_STRING);
                            clientThread = new ClientThread(
                                    address, Integer.parseInt(port), key, val, weatherForecastTextView);
                            clientThread.start();
                        }
                    }
                }
            }
        });
        weatherForecastTextView = (TextView) findViewById(R.id.weather_forecast_text_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onDestroy() method was invoked");
        if (serverThread != null) {
            serverThread.stopThread();
        }
    }
}