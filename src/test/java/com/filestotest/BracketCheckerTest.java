package com.filestotest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BracketCheckerTest {

    BracketChecker bc = new BracketChecker();

    @Test
    void checkTrue() {
        assertThat(bc.checkBracket("({()})")).isTrue();
        assertThat(bc.checkBracket("()")).isTrue();
        assertThat(bc.checkBracket("<{[()]}>")).isTrue();
        assertThat(bc.checkBracket("<>{}")).isTrue();
    }

    @Test
    void checkFalse() {
        assertThat(bc.checkBracket("(<)>")).isFalse();
        assertThat(bc.checkBracket("({( })")).isFalse();
        assertThat(bc.checkBracket("([()]")).isFalse();
    }

    @Test
    void emptyStringReturnsFalse() {
        assertThat(bc.checkBracket("")).isFalse();
    }

    @Test
    void notBracketsReturnFalse() {
        assertThat(bc.checkBracket("abc123")).isFalse();
        assertThat(bc.checkBracket(";'.,")).isFalse();
    }
}
