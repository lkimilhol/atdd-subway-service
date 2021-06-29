package nextstep.subway.path.farePolicy;

import nextstep.subway.path.domain.Fare;

public interface MemberDiscountPolicyService {
    Fare applyDiscount(Fare fare);

    static MemberDiscountPolicyService getPolicy(int age) {
        if (KidsDiscountPolicy.MIN_AGE <= age && age < KidsDiscountPolicy.MAX_AGE) {
            return new KidsDiscountPolicy();
        }
        if (TeenagersDiscountPolicy.MIN_AGE <= age && age < TeenagersDiscountPolicy.MAX_AGE) {
            return new TeenagersDiscountPolicy();
        }
        return new NoneDiscountPolicy();
    }
}