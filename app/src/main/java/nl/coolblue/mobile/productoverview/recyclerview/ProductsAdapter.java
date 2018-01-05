package nl.coolblue.mobile.productoverview.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import nl.coolblue.mobile.databinding.ProductcellBinding;
import nl.coolblue.mobile.models.ProductModel;

/**
 * Created by philippecreytens on 04/01/2018.
 */

public final class ProductsAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private final LayoutInflater inflater;
    private List<ProductModel> productsList;

    public ProductsAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.productsList = Collections.emptyList();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ProductcellBinding binding = ProductcellBinding.inflate(inflater, parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return this.productsList.size();
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(productsList.get(position));
    }

    public void setProductsList(List<ProductModel> productsList){
        this.productsList = productsList;
        notifyDataSetChanged();
    }
}
