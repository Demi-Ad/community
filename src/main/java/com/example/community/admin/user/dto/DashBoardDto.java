package com.example.community.admin.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashBoardDto {
    private Long countAllPost;
    private Long countAllAccount;
    private Long countAllComment;


}
