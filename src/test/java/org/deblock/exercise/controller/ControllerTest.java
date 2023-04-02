package org.deblock.exercise.controller;

import org.deblock.exercise.service.DeblockFlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeblockFlightService deblockFlightService;

    @Test
    public void controller_whenCallServiceWithExpectedData_thenReturnFlightsData() throws Exception {
        // arrange
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        final String origin = "LHR";
        final String destination = "BEY";
        final int noOfPassengers = 2;

        calendar.add(Calendar.DATE, 5);
        final String departureDate = String.format("%d-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.DATE, 10);
        final String returnDate = String.format("%d-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

        // act & assert
        mockMvc.perform(get("/api/flights")
                        .param("origin", origin)
                        .param("destination", destination)
                        .param("departureDate", departureDate)
                        .param("returnDate", returnDate)
                        .param("numberOfPassengers", String.valueOf(noOfPassengers)))
                .andExpect(status().is2xxSuccessful());
    }

    @ParameterizedTest
    @CsvSource({"LH,BEY,2023-10-12,2023-10-15,2",
            "LHR,BY,2023-10-12,2023-10-15,2",
            "LR,BY,2023-10-12,2023-10-15,2",
            "LHR,BEY,2021-10-12,2023-10-15,2",
            "LHR,BEY,2023-10-12,2021-10-15,2",
            "LH,BEY,2023-10-12,2023-10-15,0"})
    public void controller_whenCallServiceWithBadData_thenReturn400Error(
            String origin, String destination, String departureDate, String returnDate, String passengerCount) throws Exception {
        // act & assert
        mockMvc.perform(get("/api/flights")
                .param("origin", origin)
                .param("destination", destination)
                .param("departureDate", departureDate)
                .param("returnDate", returnDate)
                .param("numberOfPassengers", passengerCount))
                .andExpect(status().is4xxClientError());
    }
}
