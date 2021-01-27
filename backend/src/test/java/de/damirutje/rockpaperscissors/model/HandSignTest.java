package de.damirutje.rockpaperscissors.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HandSignTest {

    @Test
    public void testRock() {
        HandSign rock = HandSign.Rock;

        assertThat(rock.isBetterThan(HandSign.Paper))
                .withFailMessage("Hand sign Rock should be worse than Paper")
                .isFalse();

        assertThat(rock.isBetterThan(HandSign.Scissors))
                .withFailMessage("Hand sign Rock should be better than Scissors")
                .isTrue();

        assertThat(rock.isBetterThan(HandSign.Well))
                .withFailMessage("Hand sign Rock should be worse than Paper")
                .isFalse();
    }

    @Test
    public void testPaper() {
        HandSign paper = HandSign.Paper;

        assertThat(paper.isBetterThan(HandSign.Rock))
                .withFailMessage("Hand sign Paper should be better than Rock")
                .isTrue();

        assertThat(paper.isBetterThan(HandSign.Scissors))
                .withFailMessage("Hand sign Paper should be worse than Scissors")
                .isFalse();

        assertThat(paper.isBetterThan(HandSign.Well))
                .withFailMessage("Hand sign Paper should be better than Well")
                .isTrue();
    }

    @Test
    public void testScissors() {
        HandSign scissors = HandSign.Scissors;

        assertThat(scissors.isBetterThan(HandSign.Rock))
                .withFailMessage("Hand sign Scissors should be worse than Rock")
                .isFalse();

        assertThat(scissors.isBetterThan(HandSign.Paper))
                .withFailMessage("Hand sign Paper Scissors be better than Paper")
                .isTrue();

        assertThat(scissors.isBetterThan(HandSign.Well))
                .withFailMessage("Hand sign Scissors should be worse than Well")
                .isFalse();
    }

    @Test
    public void testWell() {
        HandSign well = HandSign.Well;

        assertThat(well.isBetterThan(HandSign.Rock))
                .withFailMessage("Hand sign Well should be better than Rock")
                .isTrue();

        assertThat(well.isBetterThan(HandSign.Paper))
                .withFailMessage("Hand sign Well Scissors be worse than Paper")
                .isFalse();

        assertThat(well.isBetterThan(HandSign.Scissors))
                .withFailMessage("Hand sign Well should be better than Scissors")
                .isTrue();
    }

}
