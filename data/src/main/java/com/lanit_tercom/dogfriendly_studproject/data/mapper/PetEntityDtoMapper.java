package com.lanit_tercom.dogfriendly_studproject.data.mapper;

import com.lanit_tercom.dogfriendly_studproject.data.entity.PetEntity;
import com.lanit_tercom.dogfriendly_studproject.data.entity.UserEntity;
import com.lanit_tercom.domain.dto.PetDto;
import com.lanit_tercom.domain.dto.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public HashMap<String, PetEntity> fromDtoToEntityMap(HashMap<String, PetDto> pets){
        if(pets == null) return null;
        HashMap<String, PetEntity> petEntityHashMap = new HashMap<>();
        for(Map.Entry<String, PetDto> entry: pets.entrySet()){
            petEntityHashMap.put(entry.getKey(), map1(entry.getValue()));
        }
        return petEntityHashMap;
    }

    public HashMap<String, PetDto> fromEntityToDtoMap(HashMap<String, PetEntity> pets){
        if(pets == null) return null;
        HashMap<String, PetDto> petDtoHashMap = new HashMap<>();
        for(Map.Entry<String, PetEntity> entry: pets.entrySet()){
            petDtoHashMap.put(entry.getKey(), map2(entry.getValue()));
        }
        return petDtoHashMap;
    }


}
