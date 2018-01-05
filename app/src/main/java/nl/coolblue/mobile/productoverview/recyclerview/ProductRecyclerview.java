package nl.coolblue.mobile.productoverview.recyclerview;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;


import nl.coolblue.mobile.BR;
import nl.coolblue.mobile.databinding.ProductRecyclerviewBinding;
import nl.coolblue.mobile.productoverview.ProductOverviewViewModel;

/**
 * Created by philippecreytens on 04/01/2018.
 */

public class ProductRecyclerview extends FrameLayout {

    private ProductRecyclerviewBinding binding;
    private ProductOverviewViewModel viewModel;
    private ProductsAdapter productsAdapter;

    public ProductRecyclerview(Context context){
        super(context);
        init();
    }

    public ProductRecyclerview(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public ProductRecyclerview(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setModel(ProductOverviewViewModel presenter){
        this.viewModel = presenter;
        ((BaseObservable)this.viewModel).addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable dataBindingObservable, int propertyId) {
                if (dataBindingObservable instanceof ProductOverviewViewModel && propertyId == BR.productsList) {
                    productsAdapter.setProductsList(viewModel.getProductsList());
                }
            }
        });
        binding.setVariable(BR.productOverviewViewModel, this.viewModel);
    }

    @Override
    protected void onFinishInflate() {
        // products recyclerview setup
        binding.productsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.productsRecyclerView.setAdapter(productsAdapter);
        // set drag down refresh
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                viewModel.loadAllProducts();
            }
        });
        super.onFinishInflate();
    }

    void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ProductRecyclerviewBinding.inflate(inflater, this, true);
        productsAdapter = new ProductsAdapter(getContext());
    }
}
