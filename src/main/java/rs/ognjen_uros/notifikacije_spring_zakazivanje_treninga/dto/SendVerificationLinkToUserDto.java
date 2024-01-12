package rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.dto;


public class SendVerificationLinkToUserDto {
    private Long userId;
    private String link;
    private String email;
    private String firstName;
    private String lastName;

    public SendVerificationLinkToUserDto(Long userId, String firstName, String lastName, String email, String link) {
        this.userId = userId;
        this.link = link;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public SendVerificationLinkToUserDto(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "SendVerificationLinkToUserDto{" +
                "userId=" + userId +
                ", link='" + link + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

