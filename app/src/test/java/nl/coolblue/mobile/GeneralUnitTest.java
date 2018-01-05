package nl.coolblue.mobile;

import org.junit.Test;

import nl.coolblue.mobile.data.bl.ProductOverviewRepository;
import nl.coolblue.mobile.data.ebay.AdvancedEbayResponse;
import nl.coolblue.mobile.data.ebay.CategoryEbayResponse;
import nl.coolblue.mobile.data.ebay.ConnectivityCheck;
import nl.coolblue.mobile.data.ebay.EbayService;
import nl.coolblue.mobile.productoverview.ProductOverviewViewModelImpl;
import retrofit2.Call;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GeneralUnitTest {

    private final ProductOverviewRepository productRepository = new ProductOverviewRepository(new internetMock());
    private final EbayService service = EbayService.retrofit.create(EbayService.class);
    private final String ebay_app_id = "Philippe-Coolblue-PRD-b5d7a0307-6c4fbd0f";

    @Test
    public void testOnlineCheck() throws Exception{
        // check online works
        assertThat(productRepository.loadAllProducts(), is(true));
    }

    @Test
    public void regularAPITest() throws Exception {
        Call<CategoryEbayResponse> call = service.getAllItemsForTech(ebay_app_id);
        CategoryEbayResponse response = call.execute().body();
        // checkk all fields are existing
        assertNotNull(response.getResponse());
        assertNotNull(response.getResponse().getSearchResult());
        assertNotNull(response.getResponse().getSearchResult().item);
        // check we have more than 1 result
        assertTrue(response.getResponse().getSearchResult().item.size() > 0);

    }

    @Test
    public void searchAPITest() throws Exception {
        Call<AdvancedEbayResponse> call = service.getAllItemsForTechWithSearch(ebay_app_id, "iphone");
        AdvancedEbayResponse response = call.execute().body();
        // checkk all fields are existing
        assertNotNull(response.getResponse());
        assertNotNull(response.getResponse().getSearchResult());
        assertNotNull(response.getResponse().getSearchResult().item);
        // check we have more than 1 result
        assertTrue(response.getResponse().getSearchResult().item.size() > 0);
    }

    @Test
    public void selectCorrectAPITest() throws Exception {
        assertTrue(productRepository.determineCorrectCall("","") == ProductOverviewRepository.SearchType.NO_PRICE);
        assertTrue(productRepository.determineCorrectCall("0","0") == ProductOverviewRepository.SearchType.NO_PRICE);
        assertTrue(productRepository.determineCorrectCall("10","") == ProductOverviewRepository.SearchType.MIN_PRICE);
        assertTrue(productRepository.determineCorrectCall("10","0") == ProductOverviewRepository.SearchType.MIN_PRICE);
        assertTrue(productRepository.determineCorrectCall("","10") == ProductOverviewRepository.SearchType.MAX_PRICE);
        assertTrue(productRepository.determineCorrectCall("0","10") == ProductOverviewRepository.SearchType.MAX_PRICE);
        assertTrue(productRepository.determineCorrectCall("10","15") == ProductOverviewRepository.SearchType.ALL_PRICES);

        // min price is higher than max price, must only use min price then
        assertTrue(productRepository.determineCorrectCall("10","1") == ProductOverviewRepository.SearchType.MIN_PRICE);
        assertTrue(productRepository.determineCorrectCall("10.1","10.1") == ProductOverviewRepository.SearchType.MIN_PRICE);

        assertTrue(productRepository.determineCorrectCall("10.1","10.2") == ProductOverviewRepository.SearchType.ALL_PRICES);
    }

    @Test
    public void priceTest() throws Exception {
        assertTrue(productRepository.isValidPrice("1"));
        assertTrue(productRepository.isValidPrice("0.1"));
        assertTrue(productRepository.isValidPrice("1000"));

        assertFalse(productRepository.isValidPrice("abs"));
        assertFalse(productRepository.isValidPrice("O"));
        assertFalse(productRepository.isValidPrice("0"));
        assertFalse(productRepository.isValidPrice("-1"));
    }

    class internetMock implements ConnectivityCheck {

        @Override
        public boolean isOnline() {
            return true;
        }
    }
}