package com.main.voiceoperationapp.config;

import com.typesafe.config.Config;

public class IoConf {

    public static final String TEXT = "TEXT";

    public static final String AUDIO = "AUDIO";

    private String inputMode;

    private Boolean outputAudio;

    public IoConf(Config conf) {
        inputMode = conf.getString("inputMode");
        outputAudio = conf.getBoolean("outputAudio");
    }

    public String getInputMode() {
        return inputMode;
    }

    public void setInputMode(String inputMode) {
        this.inputMode = inputMode;
    }

    public Boolean getOutputAudio() {
        return outputAudio;
    }

    public void setOutputAudio(Boolean outputAudio) {
        this.outputAudio = outputAudio;
    }
}
