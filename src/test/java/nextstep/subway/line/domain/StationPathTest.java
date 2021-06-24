package nextstep.subway.line.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.subway.station.domain.Station;
import nextstep.subway.station.dto.StationResponse;

class StationPathTest {
    private Station 강남역;
    private Station 선릉역;
    private Line line;

    @BeforeEach
    void setup() {
        강남역 = new Station("강남역");
        선릉역 = new Station("선릉역");

        line = new Line("신분당선"
                , "빨간색"
                , 강남역
                , 선릉역
                , new Distance(5));
    }

    @DisplayName("라인 생성시 경로 추가")
    @Test
    void createLine() {
        //given
        //when
        List<StationResponse> shortestPath = StationPath.findShortestPath(강남역, 선릉역);
        //then
        assertThat(shortestPath).isNotNull();
        assertThat(shortestPath.size()).isEqualTo(2);
        assertThat(shortestPath.get(0).getName()).isEqualTo(강남역.getName());
        assertThat(shortestPath.get(1).getName()).isEqualTo(선릉역.getName());
    }

    @DisplayName("구간 추가시 경로 추가")
    @Test
    void addSection() {
        //given
        Station 교대역 = new Station("교대역");
        //when
        line.addSection(선릉역, 교대역, new Distance(10));
        List<StationResponse> shortestPath = StationPath.findShortestPath(강남역, 교대역);
        //then
        assertThat(shortestPath).isNotNull();
        assertThat(shortestPath.size()).isEqualTo(3);
        assertThat(shortestPath.get(0).getName()).isEqualTo(강남역.getName());
        assertThat(shortestPath.get(1).getName()).isEqualTo(선릉역.getName());
        assertThat(shortestPath.get(2).getName()).isEqualTo(교대역.getName());
    }

    @DisplayName("구간 삭제 후 경로 제거")
    @Test
    void removeSection() {
        //given
        Station 교대역 = new Station("교대역");
        //when
        line.addSection(선릉역, 교대역, new Distance(10));
        line.removeStation(교대역);
        List<StationResponse> shortestPath = StationPath.findShortestPath(강남역, 선릉역);
        //then
        assertThat(shortestPath).isNotNull();
        assertThat(shortestPath.size()).isEqualTo(2);
        assertThat(shortestPath.get(0).getName()).isEqualTo(강남역.getName());
        assertThat(shortestPath.get(1).getName()).isEqualTo(선릉역.getName());
    }
}