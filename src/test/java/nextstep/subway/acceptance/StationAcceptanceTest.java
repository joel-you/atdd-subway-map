package nextstep.subway.acceptance;

import static nextstep.subway.acceptance.SubwayApiCaller.지하철역_등록;
import static nextstep.subway.acceptance.SubwayApiCaller.지하철역_목록_조회;
import static nextstep.subway.acceptance.SubwayApiCaller.지하철역_삭제;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import nextstep.subway.common.BaseAcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("지하철역 관련 기능")
public class StationAcceptanceTest extends BaseAcceptanceTest {

    /**
     * When 지하철역을 생성하면
     * Then 지하철역이 생성된다
     * Then 지하철역 목록 조회 시 생성한 역을 찾을 수 있다
     */
    @DisplayName("지하철역을 생성한다.")
    @Test
    void createStation() {
        // when
        ExtractableResponse<Response> response = 지하철역_등록("강남역");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        // then
        List<String> stationNames = 지하철역_목록_조회();
        assertThat(stationNames).containsAnyOf("강남역");
    }

    /**
     * Given 2개의 지하철역을 생성하고
     * When 지하철역 목록을 조회하면
     * Then 2개의 지하철역을 응답 받는다
     */
    // TODO: 지하철역 목록 조회 인수 테스트 메서드 생성
    @DisplayName("지하철역을 조회한다.")
    @Test
    void getStations() {
        // given
        지하철역_등록("강남역");
        지하철역_등록("역삼역");

        //when
        List<String> stationNames = 지하철역_목록_조회();

        // then
        assertThat(stationNames).hasSize(2);
        assertThat(stationNames).containsExactly("강남역","역삼역");
    }

    /**
     * Given 지하철역을 생성하고
     * When 그 지하철역을 삭제하면
     * Then 그 지하철역 목록 조회 시 생성한 역을 찾을 수 없다
     */
    // TODO: 지하철역 제거 인수 테스트 메서드 생성
    @DisplayName("지하철역을 제거한다.")
    @Test
    void deleteStation() {
        //Given
        String url = 지하철역_등록("강남역")
                .header("Location");

        //when
        지하철역_삭제(url);

        //then
        List<String> stationNames = 지하철역_목록_조회();

        assertThat(stationNames).hasSize(0);
        assertThat(stationNames).doesNotContain("강남역");
    }
}