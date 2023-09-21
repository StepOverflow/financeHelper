package ru.shapovalov.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shapovalov.entity.Transaction;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository subj;
    @Autowired
    EntityManager em;

    @Test
    public void findByFilter_Success() {
        TransactionFilter filter = new TransactionFilter()
                .setMinAmountPaid(100)
                .setMaxAmountPaid(500)
                .setMinCreatedDate(Timestamp.valueOf("2023-01-01 00:00:00"))
                .setMaxCreatedDate(Timestamp.valueOf("2023-12-31 23:59:59"));

        List<Transaction> transactions = subj.findByFilter(filter);

        assertNotNull(transactions);
        assertEquals(1, transactions.size());
    }
}