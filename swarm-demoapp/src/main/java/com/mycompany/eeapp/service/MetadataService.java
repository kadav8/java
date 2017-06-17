package com.mycompany.eeapp.service;

import com.mycompany.eeapp.web.MetadataDto;

public class MetadataService {

    public MetadataDto getMetadataById(final Long id) {
        MetadataDto dto = new MetadataDto();
        dto.setId(id);
        dto.setTitle("Harry Potter and the Prisoner of Azkaban");
        dto.setDirector("Alfonso Cuaron");
        dto.setYear(2004);
        return dto;
    }
}
