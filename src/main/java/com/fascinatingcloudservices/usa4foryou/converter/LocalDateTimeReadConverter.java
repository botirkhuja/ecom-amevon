package com.fascinatingcloudservices.usa4foryou.converter;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ReadingConverter
public class LocalDateTimeReadConverter implements Converter<Row, LocalDateTime> {
    @Override
    public LocalDateTime convert(Row row) {
        return row.get(0, LocalDateTime.class); // Adjust to your column index if needed
    }
}