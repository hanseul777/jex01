package org.zerock.jex01.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PageResponseDTO<E> {

    private List<E> dtoList; //List<> 안에 들어갈 값이 확실히 정해지지 않음- > element라는 의미로 E를 넣어줌
    private int count;
}
