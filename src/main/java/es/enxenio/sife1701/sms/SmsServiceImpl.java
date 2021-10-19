package es.enxenio.sife1701.sms;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import es.enxenio.sife1701.config.util.MyProperties;
import pl.smsapi.OAuthClient;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.api.action.sms.SMSSend;
import pl.smsapi.api.response.MessageResponse;
import pl.smsapi.api.response.StatusResponse;
import pl.smsapi.exception.ClientException;
import pl.smsapi.exception.SmsapiException;
import pl.smsapi.proxy.ProxyNative;

@Service
public class SmsServiceImpl implements SmsService {

     private final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

     @Inject
     private MyProperties properties;

     @Override
     public void sendSms(String[] phoneNumber, String text) {
          try {
               String oauthToken = properties.getSmsapi().getToken();
               String url = properties.getSmsapi().getUrl();

               log.debug("SmsAPI oauthToken: {}", oauthToken);
               log.debug("SmsAPI url: {}", url);

               OAuthClient client = new OAuthClient(oauthToken);

               ProxyNative proxyToPlOrComSmsapi = new ProxyNative(url);

               SmsFactory smsApi = new SmsFactory(client, proxyToPlOrComSmsapi);

               SMSSend action = smsApi.actionSend().setText(text).setTo(phoneNumber);

               StatusResponse result = action.execute();

               for (MessageResponse status : result.getList()) {
                    log.debug("Status of sending sms messages thought smsApi to, {}, {}",
                              status.getNumber(), status.getStatus());
               }
          } catch (ClientException e) {
               e.printStackTrace();
          } catch (SmsapiException e) {
               e.printStackTrace();
          }

     }

     @Override
     public void sendSms(String phoneNumber, String text) {
          this.sendSms(new String[] { phoneNumber }, text);
     }

}