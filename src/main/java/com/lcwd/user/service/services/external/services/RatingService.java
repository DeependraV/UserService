package com.lcwd.user.service.services.external.services;

import com.lcwd.user.service.entity.Hotel;
import com.lcwd.user.service.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
    @GetMapping("/ratings/user/{ratingId}")
    List<Rating> getRatings(@PathVariable String ratingId);
}