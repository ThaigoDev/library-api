package com.thai.tec.librayapi.repositories;

import com.thai.tec.librayapi.service.TrasactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionsTest {
    @Autowired
    TrasactionService trasactionService;

    @Test
    void TransactionSimple() {
        trasactionService.execute();
    };
    @Test
    void updateTransactionWhithStatusManaged() {
        trasactionService.updateButNotUpdate();

    }
}
