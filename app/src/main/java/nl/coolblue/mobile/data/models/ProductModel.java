package nl.coolblue.mobile.data.models;

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
    public final String price;
    public final String category;

    public ProductModel(String productName, String description, String imageUrl, String price, String category){
        this.productName = productName;
        this.productDescription = description;
        this.productImageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public String productPrice(){
        return this.price == null || this.price.isEmpty() ? "" : "Prijs: "+this.price;
    }

    public String productCategory(){
        return this.category == null || this.category.isEmpty() ? "" : "Categorie: "+this.category;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        if(imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(view.getContext()).load(imageUrl).into(view);
        }
    }
}
