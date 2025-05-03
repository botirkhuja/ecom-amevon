package com.fascinatingcloudservices.usa4foryou.utils;

import java.util.List;

import com.fascinatingcloudservices.usa4foryou.entity.NoteEntity;
import com.fascinatingcloudservices.usa4foryou.model.NoteDto;

public class NoteMapper {
  public static NoteDto convertToDto(NoteEntity noteEntity) {
    return NoteDto.builder()
        .clientId(noteEntity.getClientId())
        .orderId(noteEntity.getOrderId())
        .note(noteEntity.getNote())
        .noteId(noteEntity.getNoteId())
        .build();
  }

  public static List<NoteDto> convertToDtoList(List<NoteEntity> noteEntities) {
    return noteEntities.stream()
        .map(NoteMapper::convertToDto)
        .toList();
  }
}
