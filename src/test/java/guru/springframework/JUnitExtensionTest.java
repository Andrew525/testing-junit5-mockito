package guru.springframework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JUnitExtensionTest {

    @Mock
    Map<String, Object> mapMock;

    @Test
    void testMocksAnnotation() {
        mapMock.put("key", "value");

        assertThat(mapMock) // we are not pre-defined any answers to invocation
                .hasSize(0); // the default int value returned
    }
}
