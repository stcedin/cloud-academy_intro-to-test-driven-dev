package com.cloudacademy.bitcoin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;

import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cloudacademy.bitcoin.ConverterSvc.Currency;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@ExtendWith(MockitoExtension.class)

public class ConverterSvcTest {

    @Mock
    private CloseableHttpClient client;
    @Mock
    private CloseableHttpResponse response;
    @Mock
    private StatusLine statusLine;
    @Mock
    private HttpEntity entity;
    
    private InputStream stream;
    
    @BeforeEach
    public void setUp() {
        stream = new ByteArrayInputStream("{\"time\": {\"updated\": \"Oct 15, 2020 22:55:00 UTC\",\"updatedISO\": \"2020-10-15T22:55:00+00:00\",\"updateduk\": \"Oct 15, 2020 at 23:55 BST\"},\"chartName\": \"Bitcoin\",\"bpi\": {\"USD\": {\"code\": \"USD\",\"symbol\": \"&#36;\",\"rate\": \"11,486.5341\",\"description\": \"United States Dollar\",\"rate_float\": 11486.5341},\"GBP\": {\"code\": \"GBP\",\"symbol\": \"&pound;\",\"rate\": \"8,900.8693\",\"description\": \"British Pound Sterling\",\"rate_float\": 8900.8693},\"EUR\": {\"code\": \"EUR\",\"symbol\": \"&euro;\",\"rate\": \"9,809.3278\",\"description\": \"Euro\",\"rate_float\": 9809.3278}}}".getBytes());
    }    

    @Test
    public void getExchangeRate_USD_ReturnsUSDExchangeRate() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.getExchangeRate(Currency.USD);

        //assert
        double expected = 11486.5341;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getExchangeRate_GBP_ReturnsGBPExchangeRate() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.getExchangeRate(Currency.GBP);
    
        //assert
        double expected = 8900.8693;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getExchangeRate_EUR_ReturnsEURExchangeRate() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.getExchangeRate(Currency.EUR);
    
        //assert
        double expected = 9809.3278;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getExchangeRate_closingResponseThrowsIOException_ReturnsCorrectErrorCode() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        doThrow(IOException.class).when(response).close();
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(Currency.EUR, 0);
    
        //assert
        double expected = -1;
        Assertions.assertEquals(expected, actual);
    }





    @Test
    public void convertBitcoins_1BitCoinToUSD_ReturnsUSDDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(Currency.USD, 1);
    
        //assert
        double expected = 11486.5341;
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void convertBitcoins_2BitCoinToUSD_ReturnsUSDDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);

        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(Currency.USD, 2);
    
        //assert
        double expected = 22973.0682;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_0BitCoinToUSD_ReturnsZeroUSDDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(Currency.USD, 0);
    
        //assert
        double expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_1BitCoinToGBP_ReturnsGBPDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(Currency.GBP, 1);
    
        //assert
        double expected = 8900.8693;
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void convertBitcoins_2BitCoinToGBP_ReturnsGBPDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(Currency.GBP, 2);
    
        //assert
        double expected = 17801.7386;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_0BitCoinToGBP_ReturnsZeroGBPDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(Currency.GBP, 0);
    
        //assert
        double expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_1BitCoinToEUR_ReturnsEURDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(Currency.EUR, 1);
    
        //assert
        double expected = 9809.3278;
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void convertBitcoins_2BitCoinToEUR_ReturnsEURDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(Currency.EUR, 2);
    
        //assert
        double expected = 19618.6556;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_0BitCoinToEUR_ReturnsZeroEURDollars() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContent()).thenReturn(stream);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(Currency.EUR, 0);
    
        //assert
        double expected = 0;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void convertBitcoins_NegativeBitCoinToUSD_ThrowsException() throws IOException {
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        Assertions.assertThrows(IllegalArgumentException.class, () -> converterSvc.convertBitcoins(Currency.USD, -1));
    }

    @Test
    public void convertBitcoins_503ServiceUnavailable_ReturnsCorrectErrorCode() throws IOException {
        //arrange
        when(statusLine.getStatusCode()).thenReturn(503);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(client.execute(any(HttpGet.class))).thenReturn(response);
    
        //act
        ConverterSvc converterSvc = new ConverterSvc(client);
        var actual = converterSvc.convertBitcoins(Currency.USD, 2);
    
        //assert
        double expected = -1;
        Assertions.assertEquals(expected, actual);
    }

}