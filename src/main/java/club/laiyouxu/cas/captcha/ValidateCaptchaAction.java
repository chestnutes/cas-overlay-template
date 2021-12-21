package club.laiyouxu.cas.captcha;

import club.laiyouxu.cas.captcha.properties.CaptchaProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * This is {@link ValidateCaptchaAction}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Slf4j
@AllArgsConstructor
public class ValidateCaptchaAction extends AbstractAction {
    /**
     * Recaptcha response as a request parameter.
     */
    public static final String REQUEST_PARAM_RECAPTCHA_RESPONSE = "recaptcha";
    /**
     * Captcha error event.
     */
    public static final String EVENT_ID_ERROR = "captchaError";
    
    private static final ObjectReader READER = new ObjectMapper().findAndRegisterModules().reader();

    private final CaptchaProperties captchaProperties;

    @Override
    protected Event doExecute(final RequestContext requestContext) {
        final HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext);
        final String gRecaptchaResponse = request.getParameter(REQUEST_PARAM_RECAPTCHA_RESPONSE);

        if (StringUtils.isBlank(gRecaptchaResponse)) {
            log.warn("Recaptcha response is missing from the request");
            return getError(requestContext);
        }
        try {
            if ("code".equals(gRecaptchaResponse)){
                return null;
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        return getError(requestContext);
    }

    private Event getError(final RequestContext requestContext) {
        final MessageContext messageContext = requestContext.getMessageContext();
        messageContext.addMessage(new MessageBuilder().error().code(EVENT_ID_ERROR).defaultText(EVENT_ID_ERROR).build());
        return getEventFactorySupport().event(this, EVENT_ID_ERROR);
    }
}