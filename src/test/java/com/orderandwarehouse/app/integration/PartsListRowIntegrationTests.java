package com.orderandwarehouse.app.integration;

import com.orderandwarehouse.app.controller.PartsListRowController;
import com.orderandwarehouse.app.model.*;
import com.orderandwarehouse.app.model.dto.ComponentDto;
import com.orderandwarehouse.app.model.dto.PartsListRowDto;
import com.orderandwarehouse.app.model.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class PartsListRowIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PartsListRowController partsListRowController;

    @LocalServerPort
    private int port;
    private String entityUrl;
    private String baseUrl;

    private Product product1;
    private Component component1;
    private Component component2;
    private Component component3;
    private PartsListRowDto partsListRowDto1;
    private PartsListRowDto partsListRowDto2;
    private PartsListRowDto partsListRowDto3;


    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        entityUrl = baseUrl + "/products/partslist";

        ProductDto productDto1 = ProductDto.builder().name("product1").build();
        product1 = restTemplate.postForObject(baseUrl + "/products", productDto1, Product.class);

        ComponentDto componentDto1 = ComponentDto.builder().name("component1").type(Type.SMD).build();
        ComponentDto componentDto2 = ComponentDto.builder().name("component2").type(Type.THD).build();
        ComponentDto componentDto3 = ComponentDto.builder().name("component3").type(Type.MECHANICAL).build();
        component1 = restTemplate.postForObject(baseUrl + "/components", componentDto1, Component.class);
        component2 = restTemplate.postForObject(baseUrl + "/components", componentDto2, Component.class);
        component3 = restTemplate.postForObject(baseUrl + "/components", componentDto3, Component.class);

        partsListRowDto1 = PartsListRowDto.builder().productId(product1.getId()).componentId(component1.getId()).quantity(11.0).build();
        partsListRowDto2 = PartsListRowDto.builder().productId(product1.getId()).componentId(component2.getId()).quantity(22.0).build();
        partsListRowDto3 = PartsListRowDto.builder().productId(product1.getId()).componentId(component3.getId()).quantity(33.0).build();

        product1.setPartsList(List.of());
        product1.setOrders(List.of());
        component1.setPartsListRows(List.of());
        component1.setStorageUnits(List.of());

    }


    @Test
    public void oneProductNoPartsList_getPartsListByProductId_returnEmptyList() {
        ResponseEntity<PartsListRow[]> response = restTemplate.getForEntity(entityUrl + "/" + product1.getId(), PartsListRow[].class);
        List<PartsListRow> result = List.of(Objects.requireNonNull(response.getBody()));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void oneProductNoPartsList_getPartsListByInvalidProductId_returnBAD_REQUEST() {
        final long invalidId = 0;
        ResponseEntity<Object> response = restTemplate.getForEntity(entityUrl + "/" + invalidId, Object.class);
        Object result = Objects.requireNonNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> expectedBody = new HashMap<>() {{
            put(String.valueOf(invalidId), "Needs to be greater or equal to 1!");
        }};
        assertEquals(expectedBody, result);
    }

    @Test
    public void oneProductNoPartsList_getPartsListByWrongProductId_returnNOT_FOUND() {
        final long wrongId = 2;
        ResponseEntity<String> response = restTemplate.getForEntity(entityUrl + "/" + wrongId, String.class);
        String result = Objects.requireNonNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        String expectedBody = String.format("Product '%d' not found!", wrongId);
        assertEquals(expectedBody, result);
    }

    @Test
    public void oneProductNoPartsList_addOneValidPartsListRow_returnsPartsListRow() {
        ResponseEntity<PartsListRow> response = partsListRowController.add(partsListRowDto1);
        PartsListRow result = Objects.requireNonNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, result.getId());
        assertEquals(product1, result.getProduct());
        assertEquals(component1, result.getComponent());
        assertEquals(partsListRowDto1.getQuantity(), result.getQuantity());
    }

    @Test
    public void oneProductInDatabase_addValidStorageUnit_returnsAddedStorageUnit() {
//TODO rename and implement
    }

    @Test
    public void emptyDatabase_addInvalidStorageUnitDto1_returnsBAD_REQUEST() {
//TODO rename and implement
    }

    @Test
    public void emptyDatabase_addInvalidStorageUnitDto2_returnsBAD_REQUEST() {
//TODO rename and implement
    }

    @Test
    public void emptyDatabase_addInvalidStorageUnitDto3_returnsBAD_REQUEST() {
//TODO rename and implement
    }

    @Test
    public void someStorageUnitsStored_getAll_returnsAll() {
//TODO rename and implement
    }

    @Test
    public void oneStorageUnitStored_getById_returnsStorageUnit() {
//TODO rename and implement
    }

    @Test
    public void emptyDatabase_getByInvalidId_returnsBAD_REQUEST() {
//TODO rename and implement
    }

    @Test
    public void emptyDatabase_getByNonExistentId_returnsNOT_FOUND() {
//TODO rename and implement
    }

    @Test
    public void oneEmptyStorageUnitStored_validUpdateRequest_returnsUpdatedStorageUnit() {
//TODO rename and implement
    }

    @Test
    public void oneOccupiedStorageUnitStored_validUpdateRequest_returnsUpdatedStorageUnit() {
//TODO rename and implement
    }

    @Test
    public void someEmptyStorageUnitStored_validDeleteRequest_getAllReturnsRemaining() {
//TODO rename and implement
    }

    @Test
    public void oneOccupiedStorageUnitStored_validDeleteRequest_returnsNOT_ACCEPTABLE() {
//TODO rename and implement
    }
}
