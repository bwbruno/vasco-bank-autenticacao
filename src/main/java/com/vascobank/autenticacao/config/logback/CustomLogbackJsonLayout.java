package com.vascobank.autenticacao.config.logback;

import java.util.Map;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;

public class CustomLogbackJsonLayout extends JsonLayout {
    public static final String MICROSERVICE = "Autenticacao";
    public static final String CONTEXT = "default";

    public CustomLogbackJsonLayout() {
        this.setIncludeMDC(false);
    }

    @Override
    protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
        map.put("microservice", MICROSERVICE);
        map.put("context", CONTEXT);
        map.putAll(event.getMDCPropertyMap());
        super.addCustomDataToJsonMap(map, event);
    }
}
