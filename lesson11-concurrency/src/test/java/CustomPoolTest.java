import org.junit.jupiter.api.Test;
import ru.otus.lesson11_concurrency.pool.CustomPool;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomPoolTest {

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
