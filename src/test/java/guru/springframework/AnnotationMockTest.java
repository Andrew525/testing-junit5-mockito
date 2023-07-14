package guru.springframework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class AnnotationMockTest {

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Mock
    Map<String, Object> mapMock;

    @Test
    void testMocksAnnotation() {
        mapMock.put("key", "value");

        assertThat(mapMock) // we are not pre-defined any answers to invocation
                .hasSize(0); // the default int value returned
    }
}
