package config;

import org.aeonbits.owner.Config;

    @Config.LoadPolicy(Config.LoadType.MERGE)
    @Config.Sources({
            "system:properties",
            "classpath:${env}.properties",
            "file:~/${env}.properties",
            "file:./${env}.properties"
    })
    public interface WebConfig extends Config {
        @Key("browser")
        Browser getBrowser();
        @Key("browserVersion")
        String getBrowserVersion();
        @Key("browserSize")
        String getBrowserSize();
        @Key("baseUrl")
        String getBaseUrl();
        @Key("isRemote")
        boolean isRemote();
        @Key("remoteUrl")
        String getRemoteUrl();
    }

