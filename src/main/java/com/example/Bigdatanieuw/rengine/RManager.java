package com.example.Bigdatanieuw.rengine;

import org.renjin.script.RenjinScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

// TODO: Voor als we een gedeelde R-manager willen. moet nog ff 1 instance hebben. (singleton?)
public class RManager {
    /**
     * Maintain one instance of Renjin for each request thread.
     */
    private static final ThreadLocal<ScriptEngine> ENGINE = new ThreadLocal<ScriptEngine>();

    public ScriptEngine initEngine() {
        ScriptEngine engine;
        engine = new RenjinScriptEngineFactory().getScriptEngine();
        // Nog hier voor het geval we data in een keer in willen laden
//            engine.eval("load('res:titlesModel.rData')");
        return engine;
    }
}
