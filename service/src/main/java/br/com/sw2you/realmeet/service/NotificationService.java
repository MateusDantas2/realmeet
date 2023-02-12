package br.com.sw2you.realmeet.service;

import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.email.EmailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final EmailSender emailSender;

    public NotificationService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void notifyAllocationCreated(Allocation allocation) {
        //TODO
    }

    public void notifyAllocationUpdated(Allocation allocation) {
        //TODO
    }

    public void notifyAllocationDeleted(Allocation allocation) {
        //TODO
    }
}
