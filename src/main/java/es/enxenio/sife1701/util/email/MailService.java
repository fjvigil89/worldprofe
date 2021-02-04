package es.enxenio.sife1701.util.email;

import es.enxenio.sife1701.config.util.MyProperties;
import es.enxenio.sife1701.model.compra.Compra;
import es.enxenio.sife1701.model.configuracion.ConfiguracionService;
import es.enxenio.sife1701.model.reserva.Estado;
import es.enxenio.sife1701.model.reserva.Reserva;
import es.enxenio.sife1701.model.usuario.Usuario;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Properties;

/**
 * Service for sending e-mails.
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

    private static final String USER = "user";
    private static final String COMPRA = "compra";
    private static final String RESERVA = "reserva";
    private static final String HASTA = "hasta";
    private static final String TIEMPO_RESPUESTA = "tiempoRespuesta";
    private static final String BASE_URL = "baseUrl";
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Inject
    private MyProperties properties;


    @Autowired
    private JavaMailSenderImpl mailSender;


    @Autowired
    private MessageSource messageSource;

    @Inject
    private ConfiguracionService configuracionService;

    @Inject
    private SpringTemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml)
        throws EnvioEmailException {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}", isMultipart,
            isHtml, to, subject, content);

        // Prepare message using a Spring helper
        prepareAndSendEmail(content, to,subject);
        /*javaMailSender.setHost("smtp.google.com");
        javaMailSender.setPort(465);
        //setting username and password
        javaMailSender.setUsername("rosel11078");
        javaMailSender.setPassword("");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(new InternetAddress(properties.getMail().getFrom(), properties.getMail().getName()));
            message.setSubject(subject);
            message.setText(content, isHtml);
            //javaMailSender.setHost(properties.);
            javaMailSender.send(mimeMessage);
            log.debug("Enviado email al Usuario '{}'", to);
        } catch (Exception e) {
            log.warn("El email no se ha podido enviar a '{}', la excepción es: {}", to, e.getMessage());
            throw new EnvioEmailException(to, e);
        }*/
    }
    
    public final void prepareAndSendEmail(String htmlMessage, String toMailId,String subject) throws EnvioEmailException{

        //final OneMethod oneMethod = new OneMethod();
        //final List<char[]> resourceList = oneMethod.getValidatorResource();

        //Spring Framework JavaMailSenderImplementation    

        //JavaMailSenderImpl mailSender = new JavaMailSenderImpl();       


        mailSender.setHost("smtp.ionos.es");
        mailSender.setPort(465);
        //setting username and password
        mailSender.setUsername("admin@worldprofe.com");
        mailSender.setPassword("i80k8WP46$");

        //setting Spring JavaMailSenderImpl Properties
        Properties mailProp = mailSender.getJavaMailProperties();
        mailProp.put("mail.transport.protocol", "smtp");
        mailProp.put("mail.smtp.auth", "true");
        mailProp.put("mail.smtp.starttls.enable", "true");
        mailProp.put("mail.smtp.starttls.required", "true");
        mailProp.put("mail.debug", "true");
        mailProp.put("mail.smtp.ssl.enable", "true");
        mailProp.put("mail.smtp.user", "admin@worldprofe.com");

        //preparing Multimedia Message and sending
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false,CharEncoding.UTF_8);
            helper.setFrom(new InternetAddress(properties.getMail().getFrom(), properties.getMail().getName()));
            helper.setTo(toMailId);
            helper.setSubject(subject);
            helper.setText(htmlMessage, true);//setting the html page and passing argument true for 'text/html'

            //Checking the internet connection and therefore sending the email
            //if(OneMethod.isNetConnAvailable())
            mailSender.send(mimeMessage);
            //else
                //JOptionPane.showMessageDialog(null, "No Internet Connection Found...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Email para verificar la cuenta de email del usuario. No es asíncrono ya que si el email no se envía, no se va a
    // poder validar la cuenta.
    @Async
    public void sendValidationEmail(Usuario user, String baseUrl, Locale locale) throws EnvioEmailException {
        Context context = new Context();
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("emailValidacion", context);
        String subject = messageSource.getMessage("email.validacion.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    // Email de aviso de que la cuenta ha sido validada por el administrador
    @Async
    public void sendActivationEmail(Usuario user, String baseUrl, Locale locale) throws EnvioEmailException {
        Context context = new Context();
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("emailActivacion", context);
        String subject = messageSource.getMessage("email.activacion.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    // Email de registro con red social
    @Async
    public void sendRegistroSocialEmail(Usuario user, String provider, Locale locale) throws EnvioEmailException {
        log.debug("Sending social registration validation e-mail to '{}'", user.getEmail());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable("provider", StringUtils.capitalize(provider));
        String content = templateEngine.process("emailRegistroSocial", context);
        String subject = messageSource.getMessage("email.social.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    // Email para la recuperación de la contraseña
    @Async
    public void sendPasswordResetMail(Usuario user, String baseUrl, Locale locale) throws EnvioEmailException {
        log.debug("Sending password reset e-mail to '{}'", user.getEmail());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("emailResetPassword", context);
        String subject = messageSource.getMessage("email.reset.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    // Email de solicitud de contraseña a nuevos usuarios
    @Async
    public void sendCreacionMail(Usuario user, String baseUrl, Locale locale) throws EnvioEmailException{
        log.debug("Sending creation e-mail to '{}'", user.getEmail());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("emailCreacion", context);
        String subject = messageSource.getMessage("email.creacion.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    // Email de confirmación de una compra, con todos los detalles de la misma
    @Async
    public void sendConfirmacionCompraEmail(Compra compra, String baseUrl, Locale locale) throws EnvioEmailException {
        Context context = new Context();
        context.setVariable(USER, compra.getUsuario());
        context.setVariable(COMPRA, compra);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("emailConfirmacionCompra", context);
        String subject = messageSource.getMessage("email.compra.title", null, locale);
        sendEmail(compra.getUsuario().getEmail(), subject, content, false, true);
    }

    // Email de notificación de una compra al administrador, con todos los detalles de la misma y los datos de
    // facturación
    @Async
    public void sendAdminConfirmacionCompraEmail(Compra compra, String to, String baseUrl, Locale locale)
        throws EnvioEmailException{
        Context context = new Context();
        context.setVariable(USER, compra.getUsuario());
        context.setVariable(COMPRA, compra);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("emailAdminConfirmacionCompra", context);
        String subject = messageSource.getMessage("email.admin.compra.title", null, locale);
        sendEmail(to, subject, content, false, true);
    }

    // Email de confirmación de una reserva
    @Async
    public void sendReservaEmail(Reserva reserva, Instant hasta, String baseUrl, Locale locale)
        throws EnvioEmailException {
        Context context = new Context();
        context.setVariable(USER, reserva.getProfesor());
        context.setVariable(RESERVA, reserva);
        context.setVariable(HASTA, ZonedDateTime.ofInstant(hasta, ZoneId.systemDefault()));
        context.setVariable(BASE_URL, baseUrl);
        context.setVariable(TIEMPO_RESPUESTA, configuracionService.get().getTiempoMaximoRespuesta());
        String content = templateEngine.process("emailReserva", context);
        String subject = messageSource.getMessage("email.reserva.title", null, locale);
        sendEmail(reserva.getProfesor().getEmail(), subject, content, false, true);
    }

    // Email de aceptación de una reserva
    @Async
    public void sendAceptacionReservaEmail(Reserva reserva, String baseUrl, Locale locale)
        throws EnvioEmailException{
        Context context = new Context();
        context.setVariable(USER, reserva.getAlumno());
        context.setVariable(RESERVA, reserva);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("emailAceptarReserva", context);
        String subject = messageSource.getMessage("email.reserva.aceptacion.title", null, locale);
        sendEmail(reserva.getAlumno().getEmail(), subject, content, false, true);
    }

    // Email de rechazo de una reserva
    @Async
    public void sendRechazoReservaEmail(Reserva reserva, String baseUrl, Locale locale)
        throws EnvioEmailException {
        Context context = new Context();
        context.setVariable(USER, reserva.getAlumno());
        context.setVariable(RESERVA, reserva);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("emailRechazarReserva", context);
        String subject = messageSource.getMessage("email.reserva.rechazo.title", null, locale);
        sendEmail(reserva.getAlumno().getEmail(), subject, content, false, true);
    }

    // Email en caso de que el profesor supere el plazo para contestar a una reserva
    @Async
    public void sendPlazoSuperadoContestacionReservaEmail(Reserva reserva, String baseUrl, Locale locale)
        throws EnvioEmailException {
        Context context = new Context();
        context.setVariable(USER, reserva.getAlumno());
        context.setVariable(RESERVA, reserva);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("emailPlazoSuperadoContestacionReserva", context);
        String subject = messageSource.getMessage("email.reserva.nocontestada.title", null, locale);
        sendEmail(reserva.getAlumno().getEmail(), subject, content, false, true);
    }

    // Email en caso de que profesor o alumno cancele la reserva -> se le enviará un correo al otro
    @Async
    public void sendCancelacionReservaEmail(Reserva reserva, String baseUrl, Locale locale) throws EnvioEmailException {
        Usuario usuario = null;
        if (reserva.getEstado().equals(Estado.CANCELADA_POR_PROFESOR)) {
            usuario = reserva.getAlumno();
        } else if (reserva.getEstado().equals(Estado.CANCELADA_POR_ALUMNO)) {
            usuario = reserva.getProfesor();
        }
        Context context = new Context();
        context.setVariable(USER, usuario);
        context.setVariable(RESERVA, reserva);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("emailCancelacionReserva", context);
        String subject = messageSource.getMessage("email.reserva.cancelacion.title", null, locale);
        sendEmail(usuario.getEmail(), subject, content, false, true);
    }

    // Email de devolución de un crédito
    @Async
    public void sendDevolucionCreditoEmail(Reserva reserva, String baseUrl, Locale locale) throws EnvioEmailException {
        Context context = new Context();
        context.setVariable(USER, reserva.getAlumno());
        context.setVariable(RESERVA, reserva);
        context.setVariable(BASE_URL, baseUrl);
        String content = templateEngine.process("emailDevolucionCredito", context);
        String subject = messageSource.getMessage("email.credito.devolucion.title", null, locale);
        sendEmail(reserva.getAlumno().getEmail(), subject, content, false, true);
    }

}
