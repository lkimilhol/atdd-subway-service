package nextstep.subway.line.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.subway.station.domain.Station;

class SectionsTest {
    Sections sections;
    Line line;
    Station upStation;
    Station downStation;

    @BeforeEach
    void setup() {
        sections = new Sections();
        line = new Line("분당선", "노랑색");
        upStation = new Station("선릉역");
        downStation = new Station("한티역");
    }

    @DisplayName("구간 add")
    @Test
    void add() {
        //given
        //when
        sections.add(new Section());
        //then
        assertThat(sections.isEmpty()).isFalse();
    }

    @DisplayName("구간 일급 컬렉션 빈 값 리턴")
    @Test
    void isEmpty() {
        //given
        //when
        //then
        assertThat(sections.isEmpty()).isTrue();
    }

    @DisplayName("사이즈 구하기")
    @Test
    void size() {
        //given
        //when
        sections.add(new Section());
        //then
        assertThat(sections.size()).isEqualTo(1);
    }

    @DisplayName("구간 추가")
    @Test
    void addLineStation() {
        //given
        //when
        sections.addSection(new Section(line, upStation, downStation, new Distance(5)));
        //then
        assertThat(sections.size()).isEqualTo(1);
    }

    @DisplayName("모든 역 찾기")
    @Test
    void getStations() {
        //given
        //when
        sections.addSection(new Section(line, upStation, downStation, new Distance(5)));
        List<Station> stations = sections.getStations();
        //then
        assertThat(stations.size()).isEqualTo(2);
        assertThat(stations.get(0)).isEqualTo(upStation);
        assertThat(stations.get(1)).isEqualTo(downStation);
    }

    @DisplayName("역 제거")
    @Test
    void deleteStation() {
        //given
        Station middleStation = new Station("수서역");
        sections.addSection(new Section(line, upStation, middleStation, new Distance(5)));
        sections.addSection(new Section(line, middleStation, downStation, new Distance(5)));
        //when
        sections.removeStation(line, downStation);
        //then
        List<Station> stations = sections.getStations();
        assertThat(stations.size()).isEqualTo(2);
        assertThat(stations.get(0)).isEqualTo(upStation);
        assertThat(stations.get(1)).isEqualTo(middleStation);
    }

    @DisplayName("역 제거 실패 - 구간이 하나")
    @Test
    void deleteStationFailed() {
        //given
        sections.addSection(new Section(line, upStation, downStation, new Distance(5)));
        //when
        //then
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> sections.removeStation(line, downStation))
                .withMessage(Sections.SECTIONS_HAVE_ONLY_ONE);
    }

    @DisplayName("구간 추가 실패 - 역이 이미 구간에 등록 됨")
    @Test
    void addSectionFailedByStation() {
        //given
        sections.addSection(new Section(line, upStation, downStation, new Distance(5)));
        //when
        //then
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> sections.addSection(new Section(line, upStation, downStation, new Distance(5))))
                .withMessage(Sections.SECTION_IS_ALREADY_ADD);
    }

    @DisplayName("구간 추가 실패 - 구간이 연결되지 않음")
    @Test
    void addSectionFailedBySection() {
        //given
        Station newUpStation = new Station("수서역");
        Station newDownStation = new Station("복정역");
        sections.addSection(new Section(line, upStation, downStation, new Distance(5)));
        //when
        //then
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> sections.addSection(new Section(line, newUpStation, newDownStation, new Distance(5))))
                .withMessage(Sections.CANT_ADD_THIS_SECTION);
    }
}