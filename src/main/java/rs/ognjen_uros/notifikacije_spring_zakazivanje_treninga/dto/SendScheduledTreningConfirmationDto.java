package rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.dto;


import java.time.LocalDateTime;

public class SendScheduledTreningConfirmationDto {
    private String name;
    private String lastName;
    private String email;
    private LocalDateTime start;
    private LocalDateTime end;
    private String trainingName;
    private String salaName;
    private Integer price;
    private Integer toString;

    public SendScheduledTreningConfirmationDto(String name, String lastName, LocalDateTime start, LocalDateTime end, String trainingName, String salaName, Integer price) {
        this.name = name;
        this.lastName = lastName;
        this.start = start;
        this.end = end;
        this.trainingName = trainingName;
        this.salaName = salaName;
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getSalaName() {
        return salaName;
    }

    public void setSalaName(String salaName) {
        this.salaName = salaName;
    }

    public Integer getToString() {
        return toString;
    }

    public void setToString(Integer toString) {
        this.toString = toString;
    }

    @Override
    public String toString() {
        // -1 = cancellation
        // 1 = validation
        // 0 = reservation
        switch (toString){
            case 0:
                return "Postovani "+name+"."+"\n"+"Uspesno ste rezervisali trening "+trainingName+" sa pocetkom u"+start.toString();
            case 1:
                return "Postovani "+name+"."+"\n"+"Uspesno ste se registrovali, da biste uzivali u koriscenju naseg servisa molimo vas pritisnite link ispod da biste validirali ovaj mejl\n"+"Placeholderlink";
            case -1:
                return "Postovani "+name+"."+"\n"+"Uspesno ste otkazali trening "+trainingName+" sa pocetkom u"+start.toString();
        }
        return "luzeru";
    }
}

