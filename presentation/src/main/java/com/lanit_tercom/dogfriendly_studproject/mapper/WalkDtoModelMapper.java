package com.lanit_tercom.dogfriendly_studproject.mapper;

import com.lanit_tercom.dogfriendly_studproject.mvp.model.Point;
import com.lanit_tercom.dogfriendly_studproject.mvp.model.WalkModel;
import com.lanit_tercom.domain.dto.WalkDto;

import java.util.Random;

public class WalkDtoModelMapper {

    public WalkDtoModelMapper(){}

    public WalkModel mapToModel(WalkDto walkDto){
        if (walkDto == null){
            return null;
        }

        Random random = new Random();
        long x = 20 + (long) (Math.random() * (50 - 20));
        long y = 20 + (long) (Math.random() * (50 - 20));

        WalkModel walkModel = new WalkModel();
        walkModel.setId(walkDto.getWalkId());
        walkModel.setWalkName(walkDto.getName());
        walkModel.setFreeAccess(walkDto.isFreeAccess());
        walkModel.setDescription(walkDto.getDescription());
        walkModel.setCreator(walkDto.getCreator());
        walkModel.setRadiusOfVisibility(walkDto.getRadiusOfVisibility());
        walkModel.setTimeOfVisibility(walkDto.getTimeOfVisibility());
        walkModel.setLocation(new Point(x, y));
        walkModel.setMembers(walkDto.getMembers());

        return walkModel;
    }

    public WalkDto mapToDto(WalkModel walkModel){
        if (walkModel == null){
            return null;
        }

        WalkDto walkDto = new WalkDto();
        walkDto.setWalkId(walkModel.getId());
        walkDto.setName(walkModel.getWalkName());
        walkDto.setFreeAccess(walkModel.isFreeAccess());
        walkDto.setDescription(walkModel.getDescription());
        walkDto.setCreator(walkModel.getCreator());
        walkDto.setRadiusOfVisibility(walkModel.getRadiusOfVisibility());
        walkDto.setTimeOfVisibility(walkModel.getTimeOfVisibility());
        walkDto.setMembers(walkModel.getMembers());

        return walkDto;
    }
}
