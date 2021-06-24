package nextstep.subway.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nextstep.subway.path.application.PathService;
import nextstep.subway.path.dto.PathRequest;

@RestController
@RequestMapping("/paths")
public class PathController {
    public static final long STATION_ID_MIN_VALUE = 1L;
    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping
    public ResponseEntity findPath(@RequestBody PathRequest pathRequest) {
        if (pathRequest.getSource().equals(pathRequest.getTarget())) {
            throw new IllegalArgumentException("같은 역입니다.");
        }
        if (pathRequest.getSource() < STATION_ID_MIN_VALUE || pathRequest.getTarget() < STATION_ID_MIN_VALUE) {
            throw new IllegalArgumentException("올바른 역 번호를 넣어주세요.");
        }
        return ResponseEntity.ok(pathService.findPath(pathRequest));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity handleRuntimeException() {
        return ResponseEntity.badRequest()
                .build();
    }
}
