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
        @DefaultValue("chrome")
        Browser getBrowser();

        @Key("browserVersion")
        @DefaultValue("127.0")
        String getBrowserVersion();

        @Key("browserSize")
        @DefaultValue("1920x1080")
        String getBrowserSize();

        @Key("baseUrl")
        @DefaultValue("https://demoqa.com")
        String getBaseUrl();

        @Key("isRemote")
        @DefaultValue("false")
        boolean isRemote();

        @Key("remoteUrl")
        @DefaultValue("https://demoqa.com")
        String getRemoteUrl();
    }

