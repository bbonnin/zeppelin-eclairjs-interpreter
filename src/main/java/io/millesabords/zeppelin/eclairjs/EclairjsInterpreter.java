package io.millesabords.zeppelin.eclairjs;

import java.io.StringWriter;
import java.util.Properties;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.apache.zeppelin.interpreter.Interpreter;
import org.apache.zeppelin.interpreter.InterpreterContext;
import org.apache.zeppelin.interpreter.InterpreterResult;
import org.apache.zeppelin.interpreter.InterpreterResult.Code;
import org.apache.zeppelin.interpreter.InterpreterResult.Type;
import org.eclairjs.nashorn.NashornEngineSingleton;
import org.eclairjs.nashorn.SparkJS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class EclairjsInterpreter extends Interpreter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EclairjsInterpreter.class);


    public EclairjsInterpreter(Properties property) {
        super(property);

        System.setProperty("spark.driver.allowMultipleContexts", "true");
    }

    @Override
    public void open() {
    }

    @Override
    public void cancel(InterpreterContext context) {
    }

    @Override
    public void close() {
    }

    @Override
    public FormType getFormType() {
        return FormType.NATIVE;
    }

    @Override
    public int getProgress(InterpreterContext context) {
        return 0;
    }

    @Override
    public InterpreterResult interpret(String script, InterpreterContext context) {
        final StringWriter sw = new StringWriter();
        final SparkJS sjs = new SparkJS() {
            @Override
            public Object eval(String javaScript) {
                Object ret;
                try {
                     ScriptEngine engine = NashornEngineSingleton.getEngine();
                     engine.getContext().setWriter(sw);
                     ret = engine.eval(javaScript);
                }  catch (ScriptException e) {
                    LOGGER.error("Eval script", e);
                    ret = e;
                }
                return ret;
            }
        };

        sjs.eval(script);
        final InterpreterResult res = new InterpreterResult(Code.SUCCESS, Type.TEXT, sw.toString());
        return res;
    }
}
