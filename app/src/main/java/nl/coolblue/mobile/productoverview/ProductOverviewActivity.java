package nl.coolblue.mobile.productoverview;

import android.app.SearchManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import nl.coolblue.mobile.R;
import nl.coolblue.mobile.data.ebay.ConnectivityCheckImpl;
import nl.coolblue.mobile.databinding.ActivityMainBinding;

public class ProductOverviewActivity extends AppCompatActivity {

    private ActivityMainBinding activityBinding;
    private ProductOverviewViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // bind all ui items
        activityBinding  = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // create model & add connectivity checker
        model = new ProductOverviewViewModelImpl(new ConnectivityCheckImpl(this));

        // set model to recyclerview
        activityBinding.recyclerViewContent.setModel(model);

        // set toolbar & search
        setSupportActionBar(activityBinding.toolbar);

        // initiate load of all items
        model.loadAllProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                model.searchProduct(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                model.loadAllProducts();
                return false;
            }
        });
     //   searchView.setIconified(false);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return true;
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
*/
    }

}
