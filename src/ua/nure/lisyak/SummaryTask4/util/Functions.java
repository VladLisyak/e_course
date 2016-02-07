package ua.nure.lisyak.SummaryTask4.util;

/**
 * Contains functions used for EL function in JSP.
public final class Functions {

   *//* *//**//**
     * Defines whether the specified entity presents in the collection.
     * Entity is considered as found if the entity with the same id is found.
     *
     * @param collection collection to look in
     * @param item       entity to look for.
     * @param <E>        type of the entity
     * @return {@code true} if entity found, {@code false} otherwise
     *//**//*
    public static <E extends Entity> boolean contains(Collection<E> collection, Entity item) {
        for (Entity entity : collection) {
            if (entity.getId() == item.getId()) {
                return true;
            }
        }
        return false;
    }

    *//**//**
     * Identifies a number group of a word. Needed for localization fix in cyrillic languages.
     *
     * @param value number
     * @return number group of a word
     *//**//*
    public static int getNumberGroup(Integer value) {
        if (value <= 0 || (value >= 11 && value <= 19)) {
            return 1;
        }
        int lastDigit = value % 10;
        if ((lastDigit >= 5 && lastDigit <= 9) || lastDigit == 0) {
            return 1;
        }
        return lastDigit == 1 ? 2 : 3;
    }

    public static int sumFee(List<Order> orders) {
        int sum = 0;
        for (Order order : orders) {
            sum += order.getFee();
        }
        return sum;
    }

    private Functions() {
    }*//*

}*/
