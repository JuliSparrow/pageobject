package ru.netology.web.data;

import java.util.HashMap;
import java.util.Map;

public class CacheStorage {
    private static String fromCardNumber;
    private static String toCardNumber;
    private static int amount;
    private static Map<String, Integer> balances = new HashMap<>();
    public static boolean isClear = true;

    public static String getFromCardNumber() {
        return fromCardNumber;
    }

    public static void setFromCardNumber(String fromCardNumber) {
        isClear = false;
        CacheStorage.fromCardNumber = fromCardNumber;
    }

    public static String getToCardNumber() {
        return toCardNumber;
    }

    public static void setToCardNumber(String toCardNumber) {
        isClear = false;
        CacheStorage.toCardNumber = toCardNumber;
    }

    public static int getAmount() {
        return amount;
    }

    public static void setAmount(int amount) {
        isClear = false;
        CacheStorage.amount = amount;
    }

    public static int getFirstBalance() {
        return balances.get(DataHelper.getFirstCard());
    }

    public static int getSecondBalance() {
        return balances.get(DataHelper.getSecondCard());
    }

    public static int getBalance(String cardNumber) {
        return balances.get(cardNumber);
    }

    public static void setBalance(String cardNumber, int amount) {
        isClear = false;
        balances.put(cardNumber, amount);
    }

    public static void clear() {
        fromCardNumber = null;
        toCardNumber = null;
        amount = 0;
        balances = new HashMap<>();
        isClear = true;
    }

}
