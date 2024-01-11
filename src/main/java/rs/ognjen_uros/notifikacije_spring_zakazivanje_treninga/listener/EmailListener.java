package rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.listener;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.dto.SendScheduledTreningConfirmationDto;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.dto.TerminDto;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.dto.UserDto;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.dto.UserTerminCreateDto;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.exception.NotFoundException;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.listener.helper.MessageHelper;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.service.EmailServiceImpl;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.List;

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
        SendScheduledTreningConfirmationDto sendScheduledTreningConfirmationDto = messageHelper.getMessage(message, SendScheduledTreningConfirmationDto.class);
        sendScheduledTreningConfirmationDto.setToString(-1);
        emailService.sendSimpleMessage(sendScheduledTreningConfirmationDto.getEmail(),"Uspesno ste se prijavila na nasu stranicu", sendScheduledTreningConfirmationDto.toString());
    }

    @Scheduled(cron = "5 8 * * *")
    public void sendReminderMessage()throws JMSException{
        ResponseEntity<UserTerminCreateDto> userTerminCreateDtoResponseEntity = null;
        ResponseEntity<TerminDto> terminDtoResponseEntity = null;
            try {
                userTerminCreateDtoResponseEntity = userTerminServiceRestTemplate.exchange("/usertermin", HttpMethod.GET, null, UserTerminCreateDto.class);

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
