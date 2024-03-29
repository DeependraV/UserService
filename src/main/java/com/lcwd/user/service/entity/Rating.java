package com.lcwd.user.service.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
@Builder
public class Rating {
    private String ratingId;
    private String userId;
    private String hotelId;
    private int rating;
    private String remark;
    private Hotel hotel;
}
