package com.shop.dto;

import com.shop.Entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemimgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemimgDto of(ItemImg itemimg){
        return modelMapper.map(itemimg, ItemimgDto.class);
    }
}
