package tr.xip.prayertimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import tr.xip.prayertimes.manager.PrefManager;

public class MainActivity extends ActionBarActivity {

    private PrefManager prefMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefMan = new PrefManager(this);

        if (!prefMan.isSetupCompleted())
            startActivity(new Intent(this, CountryChooserActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                // ...
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
