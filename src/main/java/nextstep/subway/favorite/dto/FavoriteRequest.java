package nextstep.subway.favorite.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class FavoriteRequest {
    public static final long STATION_ID_MIN_VALUE = 1L;
    public static final String INVALID_STATION_ID = "올바른 역 번호를 넣어주세요.";

    @Min(value = STATION_ID_MIN_VALUE, message = INVALID_STATION_ID)
    @NotNull(message = INVALID_STATION_ID)
    private Long source;

    @Min(value = STATION_ID_MIN_VALUE, message = INVALID_STATION_ID)
    @NotNull(message = INVALID_STATION_ID)
    private Long target;

    public FavoriteRequest() {
    }

    public FavoriteRequest(Long source, Long target) {
        this.source = source;
        this.target = target;
    }

    public Long getSource() {
        return source;
    }

    public Long getTarget() {
        return target;
    }
}
