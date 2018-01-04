package nl.coolblue.mobile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import nl.coolblue.mobile.databinding.ProductcellBinding;
import nl.coolblue.mobile.models.ProductModel;
import nl.coolblue.mobile.viewholders.ProductViewHolder;

/**
 * Created by philippecreytens on 04/01/2018.
 */

public final class ProductsAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private final LayoutInflater mInflater;

    public ProductsAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ProductcellBinding binding = ProductcellBinding.inflate(mInflater, parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return 200;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(new ProductModel(String.valueOf(position)));
    }
}
