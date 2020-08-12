package com.lanit_tercom.dogfriendly_studproject.mapper;

import android.net.Uri;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.PetModel;
import com.lanit_tercom.domain.dto.PetDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PetDtoModelMapper {

    public PetDto map1(PetModel petModel){

        List<String> photo = new ArrayList<>();
        for(Uri uri: petModel.getPhotos())
            photo.add(uri.toString());

        return new PetDto(petModel.getId(),
                petModel.getName(),
                petModel.getAge(),
                petModel.getBreed(),
                petModel.getGender(),
                petModel.getAbout(),
                petModel.getCharacter(),
                photo,
                petModel.getAvatar());
    }

    public PetModel map2(PetDto petDto){

        List<Uri> photo = new ArrayList<>();
        for(String string: petDto.getPhotos())
            photo.add(Uri.parse(string));

        return new PetModel(petDto.getId(),
                petDto.getName(),
                petDto.getAge(),
                petDto.getBreed(),
                petDto.getGender(),
                petDto.getAbout(),
                petDto.getCharacter(),
                photo,
                petDto.getAvatar());
    }

    public List<PetModel> fromDtoToModelList(HashMap<String, PetDto> pets){
        if(pets == null) return null;
        List<PetModel> petModelList = new ArrayList<>();
        for(PetDto petDto: pets.values()){
            petModelList.add(map2(petDto));
        }
        return petModelList;
    }

    public HashMap<String, PetDto> fromModelToDtoMap(List<PetModel> pets){
        if(pets == null) return null;
        HashMap<String, PetDto> petDtoMap = new HashMap<>();
        for(PetModel petModel: pets){
            petDtoMap.put(petModel.getId(), map1(petModel));
        }
        return petDtoMap;
    }
}

