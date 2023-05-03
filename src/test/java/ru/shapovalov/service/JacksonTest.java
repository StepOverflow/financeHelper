package ru.shapovalov.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.Accessors;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

public class JacksonTest {
    ObjectMapper subj = new ObjectMapper();

    @Test
    public void testJackson_write() throws Exception {
        System.out.println(subj.writeValueAsString(new Transaction()
                .setDate(new Date())
                .setIn(new Account(123l, "AlfaBank"))
                .setSum(BigDecimal.valueOf(35.05))
                .setCategories(asList(
                        new Category(1l, "ЗП"),
                        new Category(2l, "March")))));
    }

    @Test
    public void testJackson_read() throws Exception {
        Transaction transaction = subj.readValue("{\n" + "  \"date\": 1683009795636,\n"
                + "  \"in\": {\n" + "    \"id\": 123,\n" + "    \"name\": \"AlfaBank\"\n" + "  },\n"
                + "  \"out\": null,\n" + "  \"sum\": 35.05,\n" + "  \"categories\": [\n" + "    {\n"
                + "      \"id\": 1,\n" + "      \"name\": \"ЗП\"\n" + "    }\n" + "  ]\n" + "} ", Transaction.class);

        System.out.println(transaction);
    }

    @Data
    @Accessors(chain = true)
    static class Transaction {
        private Date date;
        private Account in;
        private Account out;
        private BigDecimal sum;
        private List<Category> categories;
    }

    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    static class Category {
        private Long id;
        private String name;

    }

    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    static class Account {
        private Long id;
        private String name;

        public Account() {
        }
    }

}

