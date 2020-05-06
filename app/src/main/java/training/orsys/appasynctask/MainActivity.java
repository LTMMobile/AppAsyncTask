package training.orsys.appasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickButton(View v) {
        new Compteur().execute();
    }

    class Compteur extends AsyncTask<Void, Integer, Void> {

        TextView tv = null;
        long temps;

        @Override
        protected void onPreExecute() { // thread UI
            super.onPreExecute();
            tv = findViewById(R.id.textView);
            tv.setText("Démarrage");
            temps = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... voids) { // thread worker
            for(int t = 0; t < 10; t++) {
                try {
                    Thread.sleep(500);

                    // tv.setText( String.valueOf(t) );

                    publishProgress(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) { // thread UI
            super.onProgressUpdate(values);
            // Je peux toucher à l'UI
            tv.setText( String.valueOf(values[0]) );
        }

        @Override
        protected void onPostExecute(Void aVoid) { // thread UI
            super.onPostExecute(aVoid);
            temps = System.currentTimeMillis() - temps;
            tv.setText(String.format("temps total = %d ms", temps));
        }
    }
}
