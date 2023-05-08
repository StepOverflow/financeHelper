package ru.shapovalov.converter;

public class ConverterFactory {
    private static AccountModelToAccountDtoConverter accountDtoConverter;
    private static UserModelToUserDtoConverter userDtoConverter;
    private static CategoryModelToCategoryDtoConverter categoryDtoConverter;
    private static TransactionModelToTransactionDtoConverter transactionDtoConverter;

    public static AccountModelToAccountDtoConverter getAccountDtoConverter() {
        if (accountDtoConverter == null) {
            accountDtoConverter = new AccountModelToAccountDtoConverter();
        }
        return accountDtoConverter;
    }

    public static UserModelToUserDtoConverter getUserDtoConverter() {
        if (userDtoConverter == null) {
            userDtoConverter = new UserModelToUserDtoConverter();
        }
        return userDtoConverter;
    }

    public static CategoryModelToCategoryDtoConverter getCategoryDtoConverter() {
        if (categoryDtoConverter == null) {
            categoryDtoConverter = new CategoryModelToCategoryDtoConverter();
        }
        return categoryDtoConverter;
    }

    public static TransactionModelToTransactionDtoConverter getTransactionDtoConverter() {
        if (transactionDtoConverter == null) {
            transactionDtoConverter = new TransactionModelToTransactionDtoConverter();
        }
        return transactionDtoConverter;
    }
}

