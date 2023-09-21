package ru.shapovalov.repository;

import lombok.RequiredArgsConstructor;
import ru.shapovalov.entity.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<Transaction> findByFilter(TransactionFilter filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM Transaction t WHERE 1 = 1");

        Map<String, Object> params = new HashMap<>();

        if (filter.getMinAmountPaid() != null) {
            queryBuilder.append(" AND t.amountPaid >= :minAmountPaid");
            params.put("minAmountPaid", filter.getMinAmountPaid());
        }

        if (filter.getMaxAmountPaid() != null) {
            queryBuilder.append(" AND t.amountPaid <= :maxAmountPaid");
            params.put("maxAmountPaid", filter.getMaxAmountPaid());
        }

        if (filter.getMinCreatedDate() != null) {
            queryBuilder.append(" AND t.createdDate >= :minCreatedDate");
            params.put("minCreatedDate", filter.getMinCreatedDate());
        }

        if (filter.getMaxCreatedDate() != null) {
            queryBuilder.append(" AND t.createdDate <= :maxCreatedDate");
            params.put("maxCreatedDate", filter.getMaxCreatedDate());
        }

        TypedQuery<Transaction> typedQuery = em.createQuery(queryBuilder.toString(), Transaction.class);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }

        return typedQuery.getResultList();
    }
}