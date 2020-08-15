package com.lanit_tercom.dogfriendly_studproject.data.mapper;

import com.lanit_tercom.dogfriendly_studproject.data.entity.PetEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.domain.dto.PetDto;
import com.lanit_tercom.domain.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class PetEntityDtoMapper {

    public PetEntityDtoMapper(){}

    public PetEntity map1(PetDto petDto){
        PetEntity petEntity = new PetEntity();

        petEntity.setId(petDto.getId());
        petEntity.setName(petDto.getName());
        petEntity.setAge(petDto.getAge());
        petEntity.setBreed(petDto.getBreed());
        petEntity.setAbout(petDto.getAbout());
        petEntity.setGender(petDto.getGender());
        petEntity.setAvatar(petDto.getAvatar());
        petEntity.setCharacter(petDto.getCharacter());
        petEntity.setPhotos(petDto.getPhotos());

        return petEntity;
    }

    public PetDto map2(PetEntity petEntity){
        PetDto petDto = new PetDto();

        petDto.setId(petEntity.getId());
        petDto.setName(petEntity.getName());
        petDto.setAge(petEntity.getAge());
        petDto.setBreed(petEntity.getBreed());
        petDto.setAbout(petEntity.getAbout());
        petDto.setGender(petEntity.getGender());
        petDto.setAvatar(petEntity.getAvatar());
        petDto.setCharacter(petEntity.getCharacter());
        petDto.setPhotos(petEntity.getPhotos());

        return petDto;
    }

    public List<PetEntity> fromDtoToEntityList(List<PetDto> pets){
        if(pets == null) return null;
        List<PetEntity> petEntityList = new ArrayList<>();
        for(PetDto petDto: pets){
            petEntityList.add(map1(petDto));
        }
        return petEntityList;
    }

    public List<PetDto> fromEntityToDtoList(List<PetEntity> pets){
        if(pets == null) return null;
        List<PetDto> petDtoList = new ArrayList<>();
        for(PetEntity petEntity: pets){
            petDtoList.add(map2(petEntity));
        }
        return petDtoList;
    }


}
