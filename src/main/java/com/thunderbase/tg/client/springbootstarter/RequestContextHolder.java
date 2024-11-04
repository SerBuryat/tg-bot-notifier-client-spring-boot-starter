package com.thunderbase.tg.client.springbootstarter;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Setter
@Getter
@Component
@RequestScope
public class RequestContextHolder {

    private JsonNode requestBody;

}
