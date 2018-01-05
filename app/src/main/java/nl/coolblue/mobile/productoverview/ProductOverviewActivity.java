package nl.coolblue.mobile.productoverview;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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
        activityBinding.setModel((ProductOverviewViewModelImpl)model);

        // set model to recyclerview
        activityBinding.recyclerViewContent.setModel(model);

        // set toolbar & search
        setSupportActionBar(activityBinding.toolbar);

        // initiate load of all items
        model.loadAllProducts();

        // set price checks
        activityBinding.minprice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    activityBinding.maxprice.requestFocus();
                }
                return false;
            }
        });

        activityBinding.maxprice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    model.searchProductWithPrice(activityBinding.minprice.getText().toString(), activityBinding.maxprice.getText().toString());
                    activityBinding.maxprice.clearFocus();
                    removeKeyboard();
                }
                return false;
            }
        });

        activityBinding.setPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.searchProductWithPrice(activityBinding.minprice.getText().toString(), activityBinding.maxprice.getText().toString());
                activityBinding.minprice.clearFocus();
                activityBinding.maxprice.clearFocus();

                removeKeyboard();
            }
        });
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
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    void removeKeyboard(){
        // remove keyboard
        View focusField = getCurrentFocus();
        if (focusField != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusField.getWindowToken(), 0);
        }
    }

}
