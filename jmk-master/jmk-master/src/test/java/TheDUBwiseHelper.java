import org.junit.Test;
import org.ligi.ufo.DUBwiseHelper;

import static org.fest.assertions.Assertions.assertThat;


public class TheDUBwiseHelper {

    @Test
    public void should_generate_2_chars_for_a_0() {
        assertThat(DUBwiseHelper.make2charsStr(0).length()).isEqualTo(2);
    }

    @Test
    public void should_generate_2_chars_for_a_5() {
        assertThat(DUBwiseHelper.make2charsStr(5).length()).isEqualTo(2);
    }

    @Test
    public void should_generate_2_chars_for_a_23() {
        assertThat(DUBwiseHelper.make2charsStr(23).length()).isEqualTo(2);
    }

    @Test
    public void should_generate_230_for150() {
        assertThat(DUBwiseHelper.seconds2str(150)).isEqualTo("2:30");
    }

    @Test
    public void should_generate_030_for30() {
        assertThat(DUBwiseHelper.seconds2str(30)).isEqualTo("0:30");
    }

    @Test
    public void should_generate_000_for0() {
        assertThat(DUBwiseHelper.seconds2str(0)).isEqualTo("0:00");
    }

    @Test
    public void should_generate_10000_for6000() {
        assertThat(DUBwiseHelper.seconds2str(6000)).isEqualTo("100:00");
    }

}
