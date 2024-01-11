package rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.dto;

public class UserTerminCreateDto {
    private Long userId;
    private Long terminId;

    public UserTerminCreateDto(Long userId, Long terminId) {
        this.userId = userId;
        this.terminId = terminId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTerminId() {
        return terminId;
    }

    public void setTerminId(Long terminId) {
        this.terminId = terminId;
    }
}
