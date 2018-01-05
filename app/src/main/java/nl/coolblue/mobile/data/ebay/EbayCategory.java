package nl.coolblue.mobile.data.ebay;

import java.util.List;

/**
 * Created by philippecreytens on 05/01/2018.
 */

public class EbayCategory {
    List<String> categoryId;

    List<String> categoryName;
    public String getCategoryName(){return categoryName.get(0);}
}
