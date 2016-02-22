package org.scardiecat.styx.pingpong.di;

import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigModule extends AbstractModule {
    private Config config = null;

    public ConfigModule(Config config, String actorSystemName) {
        this.config = ConfigFactory.parseString("styx.actor-system-name=" + actorSystemName)
                .withFallback(config);
    }

    protected void configure() {
        bind(Config.class).toInstance(this.config);
    }
}

