package com.easymoney;

import com.easymoney.model.Account;
import com.easymoney.model.User;
import com.easymoney.repository.AccountRepository;
import com.easymoney.repository.BankTransactionRepository;
import com.easymoney.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankTransactionRepository transactionRepository;

    private UUID user1Uuid;
    private UUID user2Uuid;
    private Long account1Id;
    private Long account2Id;

    @BeforeEach
    void setup() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testFullWorkflow() {
        User user1 = new User();
        user1.setUsername("john_doe");
        user1.setPassword("password123");
        user1.setEmail("john@example.com");
        user1.setPhone("+33123456789");
        user1.setAge(30);

        ResponseEntity<User> responseUser1 = restTemplate.postForEntity("/api/users", user1, User.class);
        assertThat(responseUser1.getStatusCode()).isEqualTo(HttpStatus.OK);
        user1Uuid = UUID.fromString(responseUser1.getBody().getUuid());

        ResponseEntity<Account> responseAccount1 = restTemplate.postForEntity(
                "/api/accounts?userUuid=" + user1Uuid + "&currency=EUR", null, Account.class);
        assertThat(responseAccount1.getStatusCode()).isEqualTo(HttpStatus.OK);
        account1Id = responseAccount1.getBody().getId();

        ResponseEntity<Void> depositResponse = restTemplate.postForEntity(
                "/api/transactions/deposit?accountId=" + account1Id + "&amount=1000&description=InitialDeposit",
                null, Void.class);
        assertThat(depositResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Void> withdrawResponse = restTemplate.postForEntity(
                "/api/transactions/withdraw?accountId=" + account1Id + "&amount=200&description=ATMWithdrawal",
                null, Void.class);
        assertThat(withdrawResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        User user2 = new User();
        user2.setUsername("jane_doe");
        user2.setPassword("password456");
        user2.setEmail("jane@example.com");
        user2.setPhone("+33987654321");
        user2.setAge(28);

        ResponseEntity<User> responseUser2 = restTemplate.postForEntity("/api/users", user2, User.class);
        assertThat(responseUser2.getStatusCode()).isEqualTo(HttpStatus.OK);
        user2Uuid = UUID.fromString(responseUser2.getBody().getUuid());

        ResponseEntity<Account> responseAccount2 = restTemplate.postForEntity(
                "/api/accounts?userUuid=" + user2Uuid + "&currency=EUR", null, Account.class);
        assertThat(responseAccount2.getStatusCode()).isEqualTo(HttpStatus.OK);
        account2Id = responseAccount2.getBody().getId();

        ResponseEntity<Void> transferResponse = restTemplate.postForEntity(
                "/api/transactions/transfer?fromAccountId=" + account1Id + "&toAccountId=" + account2Id +
                        "&amount=150&description=Payment", null, Void.class);
        assertThat(transferResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<BigDecimal> balanceResponse = restTemplate.getForEntity(
                "/api/transactions/account/" + account1Id + "/balance", BigDecimal.class);
        assertThat(balanceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balanceResponse.getBody()).isEqualByComparingTo(new BigDecimal("650"));

        ResponseEntity<Void> exchangeResponse = restTemplate.postForEntity(
                "/api/exchanges?accountId=" + account1Id + "&from=EUR&to=USD&rate=1.1&amount=100",
                null, Void.class);
        assertThat(exchangeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Object[]> exchangesList = restTemplate.getForEntity(
                "/api/exchanges/account/" + account1Id, Object[].class);
        assertThat(exchangesList.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(exchangesList.getBody()).hasSize(1);
    }
}