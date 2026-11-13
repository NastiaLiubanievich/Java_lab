package practic5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAutomatonTest {
    private final TestAutomaton automaton = new TestAutomaton();

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
            "X, S",       // S + інший символ
            "T, 1",       // S + T
            "TE, 2",      // 1 + E
            "TT, 1",      // 1 + T
            "TX, S",      // 1 + інший символ
            "TES, 3",     // 2 + S
            "TET, 1",     // 2 + T
            "TEX, S",     // 2 + інший символ
            "TEST, F",    // 3 + T
            "TESX, S",    // 3 + інший символ
            "TESTX, F"    // F + будь-який символ
    })
    void checksStateTransitions(String input, String expected) {
        assertEquals(expected, automaton.recognize(input));
    }

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
            "'', S",
            "abc, S",
            "abcT, 1",
            "abcTE, 2",
            "abcTES, 3",
            "abcTESTabc, F",
            "abcTESTT, F",
            "test, S",
            "ТEST, 1",    // Перша літера — кирилична Т
            "TESXTEST, F",
            "TTEST, F",
            "TETEST, F"
    })
    void recognizesSequenceInWholeInput(String input, String expected) {
        assertEquals(expected, automaton.recognize(input));
    }
}