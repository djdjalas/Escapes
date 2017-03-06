package com.secretescapes;


import com.secretescapes.entitiy.Account;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = SecretScapesApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
@RunWith(SpringRunner.class)
public class IntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void itShouldUserSeeSomeTransactionsAllWithInitialBalanceOf200() throws JSONException {
        String expectedResponse = "[{\"accountType\":\"Savings\",\"accountHolder\":{\"name\":\"Annabela\",\"surname\":\"Jones\"},\"overdraftLimit\":0.0,\"balance\":200.0,\"email\":{\"emailOwner\":null,\"emailAddress\":\"jones@gmail.com\"}},{\"accountType\":\"Current\",\"accountHolder\":{\"name\":\"Alex\",\"surname\":\"Song\"},\"overdraftLimit\":0.0,\"balance\":200.0,\"email\":{\"emailOwner\":null,\"emailAddress\":\"song@gmail.com\"}},{\"accountType\":\"Current\",\"accountHolder\":{\"name\":\"Anna\",\"surname\":\"Smith\"},\"overdraftLimit\":0.0,\"balance\":200.0,\"email\":{\"emailOwner\":null,\"emailAddress\":\"smith@gmail.com\"}}]";
        ResponseEntity<String> response = testRestTemplate.exchange("/accounts", HttpMethod.GET, null, String.class);
        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @Test
    public void itShouldShouldListOfTransactionsForSelectedAccount() throws Throwable {
        String accountId = "song@gmail.com";
        ResponseEntity<String> response = testRestTemplate.exchange("/transaction/" + accountId, HttpMethod.GET, null, String.class);
        String expectedResponse = "[{\"payment\":{\"from\":{\"accountType\":\"Current\",\"accountHolder\":{\"name\":\"Anna\",\"surname\":\"Smith\"},\"overdraftLimit\":0.0,\"balance\":200.0,\"email\":{\"emailOwner\":null,\"emailAddress\":\"smith@gmail.com\"}},\"to\":{\"accountType\":\"Current\",\"accountHolder\":{\"name\":\"Alex\",\"surname\":\"Song\"},\"overdraftLimit\":0.0,\"balance\":200.0,\"email\":{\"emailOwner\":null,\"emailAddress\":\"song@gmail.com\"}},\"amount\":80000.0}}]";
        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @Test
    public void itShouldNotShouldListOfTransactionsForSelectedAccount() {
        String accountId = "jones@gmail.com";
        ResponseEntity<List<Account>> response = testRestTemplate.exchange("/transaction/" + accountId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});
        assertThat(response.getBody().size(), equalTo(0));
    }

    @Test
    public void itShouldTransferMoneyFromOneAccountToAnother() {
        //check annabela has initial 200 bill balance
        ResponseEntity<List<Account>> accountsResponse = testRestTemplate.exchange("/accounts", HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});
        double annabellaJonesBalance = accountsResponse.getBody().get(0).getBalance();
        assertThat(annabellaJonesBalance, equalTo(200.00));

        //check she has 0 transactions
        String accountId = "jones@gmail.com";
        ResponseEntity<List<Account>> transactionResponse = testRestTemplate.exchange("/transaction/" + accountId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});
        assertThat(transactionResponse.getBody().size(), equalTo(0));

        // transfer money 100
        String requestUrl = "/transaction/pay/100/from/jones@gmail.com/to/song@gmail.com";
        ResponseEntity<String> response = testRestTemplate.exchange(requestUrl, HttpMethod.POST, null, new ParameterizedTypeReference<String>() {});
        assertThat(response.getBody(), equalTo("successful"));

        // check she has 1 transaction
        transactionResponse = testRestTemplate.exchange("/transaction/" + accountId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});
        assertThat(transactionResponse.getBody().size(), equalTo(1));

        //check her new balance
        accountsResponse = testRestTemplate.exchange("/accounts", HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});
        annabellaJonesBalance = accountsResponse.getBody().get(0).getBalance();
        assertThat(annabellaJonesBalance, equalTo(100.00));

    }

    public void itShouldNoTransferMoneyFromOneAccountToAnother() {
        //check annabela has initial 200 bill balance
        ResponseEntity<List<Account>> accountsResponse = testRestTemplate.exchange("/accounts", HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});
        double annabellaJonesBalance = accountsResponse.getBody().get(0).getBalance();
        assertThat(annabellaJonesBalance, equalTo(200.00));

        //check she has 0 transactions
        String accountId = "jones@gmail.com";
        ResponseEntity<List<Account>> transactionResponse = testRestTemplate.exchange("/transaction/" + accountId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});
        assertThat(transactionResponse.getBody().size(), equalTo(0));

        // transfer money 300
        String requestUrl = "/transaction/pay/300/from/jones@gmail.com/to/song@gmail.com";
        ResponseEntity<String> response = testRestTemplate.exchange(requestUrl, HttpMethod.POST, null, new ParameterizedTypeReference<String>() {});
        assertThat(response.getBody(), equalTo("unsuccessful"));

        // check she has 0 transaction
        transactionResponse = testRestTemplate.exchange("/transaction/" + accountId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});
        assertThat(transactionResponse.getBody().size(), equalTo(0));

        //check her new balance
        accountsResponse = testRestTemplate.exchange("/accounts", HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>() {});
        annabellaJonesBalance = accountsResponse.getBody().get(0).getBalance();
        assertThat(annabellaJonesBalance, equalTo(200));
    }

}
