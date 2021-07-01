package nextstep.subway.line.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.subway.error.CustomException;
import nextstep.subway.error.ErrorMessage;
import nextstep.subway.path.memberfarepolicy.NoneDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import nextstep.subway.path.domain.Fare;

class FareTest {

    @DisplayName("요금 생성")
    @Test
    void create() {
        //given
        Fare fare = new Fare();
        //when
        //then
        assertThat(fare).isNotNull();
    }

    @DisplayName("요금 생성 - 값 지정")
    @Test
    void createFeeAmount() {
        //given
        Fare fare = new Fare(100);
        //when
        Fare specifyFare = new Fare(100);
        //then
        assertThat(fare).isEqualTo(specifyFare);
    }

    @DisplayName("요금 비교")
    @Test
    void gt() {
        // given
        Fare fare = new Fare(100);
        Fare mostFare = new Fare(900);
        // when
        Fare expect = fare.gt(mostFare);
        // then
        assertThat(expect).isEqualTo(mostFare);
    }

    @DisplayName("요금 추가 금액 계산")
    @ParameterizedTest()
    @CsvSource({"10, 1250",
            "30, 1650",
            "50, 2050",
            "58, 2150",
            "106, 2750"
    })
    void calculateOverFare(int 거리, int 요금) {
        // given
        Fare fare = new Fare();
        // when
        Fare totalFare = fare.calculateTotalFare(new Distance(거리), new NoneDiscountPolicy());
        // then
        assertThat(totalFare.amount()).isEqualTo(요금);
    }

    @DisplayName("요금 음수 생성")
    @Test
    void invalidAmount() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Fare(-10))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.INVALID_FARE_AMOUNT.toString());

    }
}