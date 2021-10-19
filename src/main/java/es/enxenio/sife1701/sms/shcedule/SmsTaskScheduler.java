package es.enxenio.sife1701.sms.shcedule;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.enxenio.sife1701.model.reserva.Estado;
import es.enxenio.sife1701.model.reserva.Reserva;
import es.enxenio.sife1701.model.reserva.ReservaRepository;
import es.enxenio.sife1701.model.usuario.alumno.Alumno;
import es.enxenio.sife1701.model.usuario.profesor.Profesor;
import es.enxenio.sife1701.sms.SmsService;

@Component
public class SmsTaskScheduler {

     private static final int SIX_MINUTES_EXECUTION_RATE = 360000;

     private static final long FROM_MINUTES = 30;

     private static final long TO_MINUTES = 35;

     private static final String SMS_TEACHER_TEXT = "Greetings, you have class at %s. Regards, WordProfe.";

     private static final String SMS_STUDENT_TEXT = "Greetings, you have classes in half an hour minutes with Professor %s. Regards, WordProfe.";

     private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
               .ofPattern("HH:mm");

     private final Logger log = LoggerFactory.getLogger(SmsTaskScheduler.class);

     @Autowired
     private SmsService smsService;

     @Autowired
     private ReservaRepository reservaRepository;

     @Scheduled(fixedRate = SIX_MINUTES_EXECUTION_RATE)
     public void checkForRerservationOfConfirmedClassesForSendSmsNotification() {

          log.debug("Check For Reservation of Confirmed Classes For Send Sms Notification");

          List<Reserva> confirmedClasses = getConfirmedClasses();

          sendSmsToTeacherAndStudents(confirmedClasses);
     }

     private void sendSmsToTeacherAndStudents(List<Reserva> confirmedClasses) {

          Map<Profesor, List<Reserva>> studentsByProffesorMap = confirmedClasses.stream()
                    .collect(Collectors.groupingBy(Reserva::getProfesor));

          for (Map.Entry<Profesor, List<Reserva>> entry : studentsByProffesorMap.entrySet()) {
               final Profesor teacher = entry.getKey();

               List<Reserva> reservas = entry.getValue();

               String date = reservas.get(0).getFecha().format(DATE_TIME_FORMATTER);

               String smsTeacherText = String.format(SMS_TEACHER_TEXT, date);
               this.smsService.sendSms(teacher.getTelefonoMovil(), smsTeacherText);

               List<String> studentsPhonesNumbers = getStudentsPhonesNumbers(reservas);
               String teacherName = teacher.getNombre().concat(" ").concat(teacher.getApellidos());
               String smsStudentText = String.format(SMS_STUDENT_TEXT, teacherName);
               smsService.sendSms(studentsPhonesNumbers.toArray(new String[0]), smsStudentText);
          }

     }

     private List<String> getStudentsPhonesNumbers(List<Reserva> reservas) {
          List<String> result = new LinkedList<>();

          for (Reserva reserva : reservas) {
               Alumno alumno = reserva.getAlumno();
               result.add(alumno.getTelefonoMovil());
          }
          return result;
     }

     private List<Reserva> getConfirmedClasses() {
          List<Reserva> confirmedClasses = this.reservaRepository
                    .findAllByEstado(Estado.CONFIRMADA);

          confirmedClasses = confirmedClasses.stream()
                    .filter(this::isBetweenThirtyAndThirtyFiveMinutes).collect(Collectors.toList());

          return confirmedClasses;
     }

     private boolean isBetweenThirtyAndThirtyFiveMinutes(Reserva confirmedClass) {
          Instant now = ZonedDateTime.now().toInstant();
          Instant fromDate = now.plus(FROM_MINUTES, ChronoUnit.MINUTES);
          Instant toDate = now.plus(TO_MINUTES, ChronoUnit.MINUTES);
          Instant reservationDate = confirmedClass.getFecha().toInstant();

          return isBetween(reservationDate, fromDate, toDate);
     }

     private boolean isBetween(Instant date, Instant fromDate, Instant toDate) {
          return (date.equals(fromDate) || date.isAfter(fromDate))
                    && (date.equals(toDate) || date.isBefore(toDate));
     }

}
