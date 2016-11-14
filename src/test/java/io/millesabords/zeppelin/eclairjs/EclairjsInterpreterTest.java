package io.millesabords.zeppelin.eclairjs;

import static org.junit.Assert.assertEquals;

import org.apache.zeppelin.interpreter.InterpreterResult;
import org.junit.Test;

public class EclairjsInterpreterTest {

    private final EclairjsInterpreter interpreter = new EclairjsInterpreter(null);

    @Test
    public void testSimple() {
        final InterpreterResult res = interpreter.interpret("print('hello');", null);
        assertEquals("hello\n", res.message());
    }
}
