import org.junit.jupiter.api.Test;
import pool.CustomPool;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CustomPoolTest {

    @Test
    void IllegalStateExceptionTest() {
        //given
        CustomPool customThreadPool = new CustomPool(50);
        customThreadPool.shutdown();
        Runnable additionalTask = new Main.Task();

        //when
        //then
        assertThatThrownBy(() -> customThreadPool.execute(additionalTask)).isInstanceOf(IllegalStateException.class);
    }
}
