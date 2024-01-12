package rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.listener;

import org.apache.catalina.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.dto.*;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.exception.NotFoundException;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.listener.helper.MessageHelper;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.service.EmailServiceImpl;

import javax.jms.JMSException;
import javax.jms.Message;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Component
public class EmailListener {
    private MessageHelper messageHelper;
    private EmailServiceImpl emailService;
    private RestTemplate userTerminServiceRestTemplate;
    public EmailListener(MessageHelper messageHelper, EmailServiceImpl emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

    @JmsListener(destination="send_verification_for_user", concurrency = "5-10")
    public void sendVerificationMessage(Message message)throws JMSException {
        SendVerificationLinkToUserDto sendVerificationLinkToUserDto = messageHelper.getMessage(message, SendVerificationLinkToUserDto.class);
        System.out.println(sendVerificationLinkToUserDto.toString());
        emailService.sendSimpleMessage(sendVerificationLinkToUserDto.getEmail(),"Uspesno ste se prijavila na nasu stranicu " + sendVerificationLinkToUserDto.getFirstName() + " " + sendVerificationLinkToUserDto.getLastName(), sendVerificationLinkToUserDto.getLink());
    }

    @Scheduled(cron = "6 21 * * *")
    public void sendReminderMessage()throws JMSException{
        ResponseEntity<List<UserTerminCreateDto>> userTerminCreateDtoResponseEntity;
        ResponseEntity<UserDto> userDtoResponseEntity;
        ResponseEntity<TerminDto> terminDtoResponseEntity;
        try {
            ParameterizedTypeReference<List<UserTerminCreateDto>> responseType = new ParameterizedTypeReference<List<UserTerminCreateDto>>() {};
            userTerminCreateDtoResponseEntity = userTerminServiceRestTemplate.exchange("/userTermin", HttpMethod.GET, null, responseType);
            for (UserTerminCreateDto utcd:userTerminCreateDtoResponseEntity.getBody()){
                userDtoResponseEntity = userTerminServiceRestTemplate.exchange("/user/"+utcd.getUserId(),HttpMethod.GET,null,UserDto.class);
                terminDtoResponseEntity = userTerminServiceRestTemplate.exchange("/termin/"+utcd.getTerminId(),HttpMethod.GET,null, TerminDto.class);
                if(terminDtoResponseEntity.getBody().getStart().isAfter(LocalDateTime.now())){
                    String poruka = "Postovani, imate termin za trening u";
                    poruka = poruka+terminDtoResponseEntity.getBody().getStart().toString();
                    emailService.sendSimpleMessage(userDtoResponseEntity.getBody().getEmail(),"Podsecamo vas da imate trening!",poruka);
                }
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new NotFoundException(String.format("Termini i korisnici mapirani na njih se ne postoje"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @JmsListener(destination="send_cancellation_trening_for_user", concurrency = "5-10")
    public void successfulCancelation(Message message)throws JMSException {
        SendScheduledTreningConfirmationDto sendScheduledTreningConfirmationDto = messageHelper.getMessage(message, SendScheduledTreningConfirmationDto.class);
        sendScheduledTreningConfirmationDto.setToString(0);
        emailService.sendSimpleMessage(sendScheduledTreningConfirmationDto.getEmail(),"Uspesno ste otkazali trening", sendScheduledTreningConfirmationDto.toString());
    }
    @JmsListener(destination="send_scheduled_trening_for_user", concurrency = "5-10")
    public void successfulReservation(Message message)throws JMSException {
        SendScheduledTreningConfirmationDto sendScheduledTreningConfirmationDto = messageHelper.getMessage(message, SendScheduledTreningConfirmationDto.class);
        sendScheduledTreningConfirmationDto.setToString(1);
        emailService.sendSimpleMessage(sendScheduledTreningConfirmationDto.getEmail(),"Uspesno ste zakazali trening", sendScheduledTreningConfirmationDto.toString());
    }
}
