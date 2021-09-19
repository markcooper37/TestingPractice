import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MainTest {

    @Test
    void myFirstTest() {
        assertThat("actual").isEqualTo("expected");
    }
}
