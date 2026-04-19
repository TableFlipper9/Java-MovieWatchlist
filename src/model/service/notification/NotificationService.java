package model.service.notification;

import model.event.EventListener;
import model.event.MovieEvent;
import model.entity.User;

public class NotificationService implements EventListener {

    private final EmailService emailService;
    private final User currentUser;

    public NotificationService(User currentUser) {
        this.currentUser = currentUser;
        this.emailService = new EmailService();
    }

    @Override
    public void update(MovieEvent event) {
        sendNotification(event);
    }

    private void sendNotification(MovieEvent event) {

        String subject = "Movie " + event.getType();

        String content = "Action: " + event.getType() +
                "\nMovie: " + event.getMovie().getTitle() +
                "\nGenre: " + event.getMovie().getGenre();

        emailService.sendEmail(
                currentUser.getEmail(),
                subject,
                content
        );
    }
}