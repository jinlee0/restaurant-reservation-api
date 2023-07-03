package com.restaurantreservation.api.service.dto.reservation;

import com.restaurantreservation.api.service.dto.PageDtoBase;
import com.restaurantreservation.api.service.entity.reservation.Reservation;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@NoArgsConstructor
public class ReservationRetrieveDto extends PageDtoBase<ReservationRetrieveDto.ReservationInfo> {
    private ReservationRetrieveDto(Page<Reservation> page) {
        super(page, page.getContent().stream().map(ReservationInfo::new).toList());
    }

    public static ReservationRetrieveDto from(Page<Reservation> page) {
        return new ReservationRetrieveDto(page);
    }
    @Data
    public static class ReservationInfo {
        private String id;
        private LocalDateTime createdAt;
        private LocalDateTime dateTime;
        private Integer numberOfPeople;
        private String requirements;
        private String contactPhoneNumber;
        private String restaurantId;
        private String customerId;

        public ReservationInfo(Reservation r) {
            id = r.getId();
            createdAt = r.getCreatedAt();
            dateTime = r.getDateTime();
            numberOfPeople = r.getNumberOfPeople();
            requirements = r.getRequirements();
            contactPhoneNumber = r.getContactPhoneNumber();
            restaurantId = r.getRestaurant().getId();
            customerId = r.getCustomer().getId();
        }

    }
}
