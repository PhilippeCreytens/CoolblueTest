package nl.coolblue.mobile.models;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by philippecreytens on 04/01/2018.
 */

public class ProductModel {

    public final String productName;
    public final String productDescription;
    public final String productImageUrl;
    public final String productPrice;
    public final int productRating;

    public ProductModel(String productName, String description, String imageUrl, String price, int rating){
        this.productName = productName;
        this.productDescription = description;
        this.productImageUrl = imageUrl;
        this.productPrice = price;
        this.productRating = rating;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        if(imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(view.getContext()).load(imageUrl).into(view);
        }
    }
}
