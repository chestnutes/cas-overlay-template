package club.laiyouxu.cas.custom.properties;

import club.laiyouxu.cas.custom.properties.captcha.RecaptchaProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

/**
 * @author laiyouxu
 */
@ConfigurationProperties(value = CustomCasConfigurationProperties.PREFIX, ignoreUnknownFields = false)
@Getter
@Setter
public class CustomCasConfigurationProperties implements Serializable {
    public static final String PREFIX = "scp.cas";
    private static final long serialVersionUID = -8620267783496072683L;

    private CasConfigurationProperties casProperties;

    public CustomCasConfigurationProperties(CasConfigurationProperties casProperties) {
        this.casProperties = casProperties;
    }

    /**
     * Google reCAPTCHA settings.
     */
    @NestedConfigurationProperty
    private RecaptchaProperties recaptcha = new RecaptchaProperties();

}
