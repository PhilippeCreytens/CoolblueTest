package nl.coolblue.mobile.productoverview.recyclerview;

import android.support.v7.widget.RecyclerView;

import nl.coolblue.mobile.databinding.ProductcellBinding;
import nl.coolblue.mobile.models.ProductModel;

/**
 * Created by philippecreytens on 04/01/2018.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {

    private final ProductcellBinding mBinding;

    public ProductViewHolder(ProductcellBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(ProductModel item) {
        mBinding.setProduct(item);
    }
}
