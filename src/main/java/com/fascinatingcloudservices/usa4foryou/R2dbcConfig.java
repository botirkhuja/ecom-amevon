package com.fascinatingcloudservices.usa4foryou;

import com.fascinatingcloudservices.usa4foryou.converter.LocalDateTimeReadConverter;
import com.fascinatingcloudservices.usa4foryou.converter.LocalDateTimeWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class R2dbcConfig {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Object> converters = new ArrayList<>();
        converters.add(new LocalDateTimeReadConverter());
        converters.add(new LocalDateTimeWriteConverter());
        return new R2dbcCustomConversions(CustomConversions.StoreConversions.NONE, converters);
    }
}
