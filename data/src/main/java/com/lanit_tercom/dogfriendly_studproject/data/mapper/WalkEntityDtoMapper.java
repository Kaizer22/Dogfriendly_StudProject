package com.lanit_tercom.dogfriendly_studproject.data.mapper;

import com.lanit_tercom.dogfriendly_studproject.data.entity.WalkEntity;
import com.lanit_tercom.domain.dto.WalkDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WalkEntityDtoMapper {

    public WalkEntityDtoMapper(){}

    public WalkEntity mapDtoToEntity(WalkDto walkDto){
        if (walkDto == null){
            return null;
        }
        WalkEntity walkEntity = new WalkEntity();
        walkEntity.setWalkId(walkDto.getWalkId());
        walkEntity.setName(walkDto.getName());
        walkEntity.setFreeAccess(walkDto.isFreeAccess());
        walkEntity.setCreator(walkDto.getCreator());
        walkEntity.setDescription(walkDto.getDescription());
        walkEntity.setRadiusOfVisibility(walkDto.getRadiusOfVisibility());
        walkEntity.setTimeOfVisibility(walkDto.getTimeOfVisibility());

        List<HashMap<String, String>> members = new ArrayList<>();

        for(String id: walkDto.getMembers()){
            HashMap<String, String> pair = new HashMap<>();
            pair.put("userId", id);
            members.add(pair);
        }

        walkEntity.setMembers(members);

        return walkEntity;
    }

    public WalkDto mapEntityToDto(WalkEntity walkEntity){
        if (walkEntity == null){
            return null;
        }

        WalkDto walkDto = new WalkDto();
        walkDto.setWalkId(walkEntity.getWalkId());
        walkDto.setName(walkEntity.getName());
        walkDto.setFreeAccess(walkEntity.isFreeAccess());
        walkDto.setDescription(walkEntity.getDescription());
        walkDto.setCreator(walkEntity.getCreator());
        walkDto.setRadiusOfVisibility(walkEntity.getRadiusOfVisibility());
        walkDto.setTimeOfVisibility(walkEntity.getTimeOfVisibility());

        List<String> members = new ArrayList<>();
        for(HashMap<String, String> pair: walkEntity.getMembers())
            members.add(pair.get("userId"));

        walkDto.setMembers(members);

        return walkDto;
    }
}
