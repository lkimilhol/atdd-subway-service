package nextstep.subway.path.memberfarepolicy;

import nextstep.subway.path.domain.Fare;

public class KidsDiscountPolicy implements MemberDiscountPolicy {
    public static final int MIN_AGE = 6;
    public static final int MAX_AGE = 13;
    private static final double DISCOUNT_PER = 0.5d;
    private static final int DEFAULT_DISCOUNT_FARE = 350;

    @Override
    public Fare applyDiscount(Fare fare) {
        return new Fare(fare.amount() - DEFAULT_DISCOUNT_FARE - discount(fare));
    }

    public static boolean isAvailable(int age) {
        return KidsDiscountPolicy.MIN_AGE <= age && age < KidsDiscountPolicy.MAX_AGE;
    }

    private int discount(Fare fare) {
        return (int) Math.ceil(((fare.amount() - DEFAULT_DISCOUNT_FARE) * DISCOUNT_PER));
    }
}