package com.company.producthub.test;

import com.company.producthub.entities.ProductRequest;
import com.company.producthub.entities.ProductResponse;
import com.company.producthub.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Represents ProductController's end-to-end Test.
 * Mocks third party api calls.
 *
 * @author Petros Kolontis <petros.kolontis@gmail.com>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
 
    private static final Logger log = LoggerFactory.getLogger(ProductControllerTest.class);
    
    private ProductRequest prodReq;
    private String productHubUrl;
    
    @LocalServerPort
    private int apiPort;
    
    @Autowired
    private TestRestTemplate testTemplate;

    @MockBean
    private RestTemplate mockTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        public void starting(Description d) {
            log.info("Running Test {}", d.getMethodName());
        }

        @Override
        public void failed(Throwable e, Description d) {
            log.info("Failed Test {}", d.getMethodName());
        }

        @Override
        public void finished(Description d) {
            log.info("Finished Test {}", d.getMethodName());
        }
    };

    @Before
    public void setUp() throws IOException {
        prodReq = mapper.readValue(readResourceToString("client_request.json"), ProductRequest.class);
        productHubUrl = buildProductHubUrl();
    }
    
    /**
     * Tests the case described below:
     * All merchants respond with status 200 and a product response.
     * The product hub should respond with status 200 and the product response
     * containing the product with the minimum price among product responses.
     * 
     * @throws IOException 
     */
    @Test
    public void respond_200_and_product_lowest_price() throws IOException {
        //GIVEN
        mock_request_merchant_respond_200("http://merchant-1.com/api/product", "merchant1_response.json");
        mock_request_merchant_respond_200("http://merchant-2.com/api/product", "merchant2_response.json");
        mock_request_merchant_respond_200("http://merchant-3.com/api/product", "merchant3_response.json");

        // WHEN
        ResponseEntity<ProductResponse> actualProdRespEnt = requestToProductHub();

        // THEN
        assertNotNull(actualProdRespEnt);
        assertNotNull(actualProdRespEnt.getBody());
        assertEquals(HttpStatus.OK, actualProdRespEnt.getStatusCode());

        String expectedProdRespStr = readResourceToString("winner_response.json");
        ProductResponse expectedProdResp = mapper.readValue(expectedProdRespStr, ProductResponse.class);
        assertEquals(expectedProdResp, actualProdRespEnt.getBody());
    }
    
    /**
     * Tests the case described below:
     * All merchants respond with status 204, so there is no product response.
     * The product hub should respond with status 204 and there is no product response.
     * 
     * @throws IOException 
     */
    @Test
    public void respond_204_and_empty_body_if_all_merchants_didnt_send_products() throws IOException {
        // GIVEN
        mock_request_merchant_respond_204("http://merchant-1.com/api/product");
        mock_request_merchant_respond_204("http://merchant-2.com/api/product");
        mock_request_merchant_respond_204("http://merchant-3.com/api/product");

        // WHEN
        ResponseEntity<ProductResponse> actualProdRespEnt = requestToProductHub();

        // THEN
        assertNotNull(actualProdRespEnt);
        assertNull(actualProdRespEnt.getBody());
        assertEquals(HttpStatus.NO_CONTENT, actualProdRespEnt.getStatusCode());
    }
    
    /**
     * Tests the case described below:
     * At least one merchant responds with status 204, so there is no product response.
     * The product hub should respond with status 200 and the product response
     * containing the product with the minimum price among product responses.
     * 
     * @throws IOException 
     */
    @Test
    public void respond_200_and_product_lowest_price_if_any_merchant_didnt_send_product() throws IOException {
        // GIVEN
        mock_request_merchant_respond_204("http://merchant-1.com/api/product");
        mock_request_merchant_respond_200("http://merchant-2.com/api/product", "merchant2_response.json");
        mock_request_merchant_respond_200("http://merchant-3.com/api/product", "merchant3_response.json");

        // WHEN
        ResponseEntity<ProductResponse> actualProdRespEnt = requestToProductHub();

        // THEN
        assertNotNull(actualProdRespEnt);
        assertNotNull(actualProdRespEnt.getBody());
        assertEquals(HttpStatus.OK, actualProdRespEnt.getStatusCode());

        String expectedProdRespStr = readResourceToString("winner_response.json");
        ProductResponse expectedProdResp = mapper.readValue(expectedProdRespStr, ProductResponse.class);
        assertEquals(expectedProdResp, actualProdRespEnt.getBody());
    }
    
    /**
     * Tests the case described below:
     * At least one merchant failed to send a product response.
     * The product hub should respond with status 200 and the product response
     * containing the product with the minimum price among product responses.
     * 
     * @throws IOException 
     */
    @Test
    public void respond_200_and_product_lowest_price_if_any_merchant_failed_to_send_product() throws IOException {
        // GIVEN
        mock_request_call_real_method("http://merchant-1.com/api/product");
        mock_request_merchant_respond_200("http://merchant-2.com/api/product", "merchant2_response.json");
        mock_request_merchant_respond_200("http://merchant-3.com/api/product", "merchant3_response.json");

        // WHEN
        ResponseEntity<ProductResponse> actualProdRespEnt = requestToProductHub();

        // THEN
        assertNotNull(actualProdRespEnt);
        assertNotNull(actualProdRespEnt.getBody());
        assertEquals(HttpStatus.OK, actualProdRespEnt.getStatusCode());

        String expectedProdRespStr = readResourceToString("winner_response.json");
        ProductResponse expectedProdResp = mapper.readValue(expectedProdRespStr, ProductResponse.class);
        assertEquals(expectedProdResp, actualProdRespEnt.getBody());
    }
    
    private void mock_request_merchant_respond_200(String merchantUrl, String responseResource) throws IOException {
        String prodRespStr = readResourceToString(responseResource);
        ProductResponse prodResp = mapper.readValue(prodRespStr, ProductResponse.class);
        when(mockTemplate.postForEntity(merchantUrl, 
                HttpUtils.buildHttpEntityAppJson(prodReq), ProductResponse.class))
                .thenReturn(ResponseEntity.ok(prodResp));
    }

    private void mock_request_merchant_respond_204(String merchantUrl) {
        when(mockTemplate.postForEntity(merchantUrl, 
                HttpUtils.buildHttpEntityAppJson(prodReq), ProductResponse.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    private void mock_request_call_real_method(String merchantUrl) {
        when(mockTemplate.postForEntity(merchantUrl, 
                HttpUtils.buildHttpEntityAppJson(prodReq), ProductResponse.class))
                .thenCallRealMethod();
    }
    
    private ResponseEntity<ProductResponse> requestToProductHub() {
        return testTemplate.postForEntity(productHubUrl, 
                HttpUtils.buildHttpEntityAppJson(prodReq), ProductResponse.class);
    }
    
    private String readResourceToString(String name) {
        try {
            URI uri = Objects.requireNonNull(this.getClass().getClassLoader().getResource(name)).toURI();
            Path path = Paths.get(uri);
            return Files.readAllLines(path).stream().reduce("", String::concat);
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }
    
    private String buildProductHubUrl() {
        StringBuilder productHubUrlBuilder = new StringBuilder("http://localhost:");
        productHubUrlBuilder.append(apiPort);
        productHubUrlBuilder.append("/products");

        return productHubUrlBuilder.toString();
    }
}
