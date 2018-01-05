package nl.coolblue.mobile.data.ebay;

import java.util.List;

/**
 * Created by philippecreytens on 05/01/2018.
 */

public class EbayProduct {
    List<String> itemId;

    List<String> title;
    public String getTitle(){return title != null && title.size() > 0 ? title.get(0) : "";}

    List<String> galleryURL;
    public String getImageUrl(){return galleryURL != null && galleryURL.size() > 0 ? galleryURL.get(0) : "";}
}
