package com.example.bookStore.service;

import com.example.bookStore.exception.ErrorResponse;
import com.example.bookStore.response.Response;
import com.fanapium.keylead.client.KeyleadClient;
import com.fanapium.keylead.client.KeyleadClientFactory;
import com.fanapium.keylead.client.exception.ClientOperationException;
import com.fanapium.keylead.client.exception.UserOperationException;
import com.fanapium.keylead.common.oauth.exception.OAuthException;
import com.fanapium.keylead.common.oauth.vo.OAuthRequest;
import org.apache.commons.codec.EncoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

@Service
public class PodService {
    private final MessageSource messageSource;

    private static final Logger logger = LogManager.getLogger(PodService.class);

    @Value("${pod.client.id}")
    private String podClientId;

    @Value("${pod.client.secret}")
    private String podClientSecret;

    @Value("${pod.sso.server}")
    private String podSsoServer;

    @Value("${pod.redirect.uri}")
    private String podRedirectUri;

    @Value("${pod.api.token}")
    private String podApiToken;

    public PodService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Response loginInitialize() throws OAuthException, ClientOperationException {
        KeyleadClient keyleadClient = KeyleadClientFactory.createClient(podSsoServer);
        OAuthRequest oAuthRequest = new OAuthRequest();
        oAuthRequest.setRedirect_uri(podRedirectUri);
        oAuthRequest.setClient_id(podClientId);
        oAuthRequest.setResponse_type("code");
        oAuthRequest.setScope("profile");
        String authUri = keyleadClient.getAuthorizationUrl(oAuthRequest);
        return new Response(HttpStatus.OK,
                messageSource.getMessage("login.to.pod.successfully", null, LocaleContextHolder.getLocale()),
                authUri,
                1
        );
    }
    public String login(String code) throws OAuthException, ClientOperationException, UserOperationException, EncoderException, NoSuchAlgorithmException, URISyntaxException, MalformedURLException, UnsupportedEncodingException {
        String credentials = podClientId + ":" + podClientSecret;
        String encodedCredentials = "Basic " +Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", encodedCredentials);
        headers.set("Accept","application/json");
        headers.set("Content-Type","application/x-www-form-urlencoded");
        headers.set("Accept-Language","en");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://accounts.pod.ir/oauth2/token?grant_type=authorization_code&code="+code+"&client_id="+podClientId+"&redirect_uri="+podRedirectUri+"&linkDeliveryType=SMS", HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

}
