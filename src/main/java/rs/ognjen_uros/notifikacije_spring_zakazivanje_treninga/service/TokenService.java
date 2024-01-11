package rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.service;

import io.jsonwebtoken.Claims;

public interface TokenService {

    String generate(Claims claims);

    Claims parseToken(String jwt);
}
