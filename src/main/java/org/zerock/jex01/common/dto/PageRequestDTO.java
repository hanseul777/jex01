package org.zerock.jex01.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1; //int는 처음부터 기본값을 가지고 있기 때문에 int로 선언
    @Builder.Default
    private int size = 10;
    // ---------------------------------------------------------------------p.293

    //검색을 위해 새로 추가해줌
    private String type; //검색조건(제목으로 찾을건지, 작성자로 찾을지 -> 어떤 방식으로 찾을지)
    private String keyword;

    public int getSkip(){
        return (page-1) * size;
    }

    public  String getType(){ // type을 지정하지 않고 검색했을 때
        if(type ==null || type.trim().length() == 0){ // type이 null || null은 아닌데 공백이라면
            return null;
        }
        return type; // if조건에 적합하지 않으면 그냥 type을 반환한다.
    }

    //검색키워드에 따라서 배열로 나눠줌 (type.split())
    public String[] getArr(){
        return type == null? new String[]{}: type.split("");
        // null이 나오면 sql에서 문제가 있음 -> 빈배열이라도 줘야해서 new String[]{}
    }
}
