package com.fleet.fleet_maintenance_system.controller;

import com.fleet.fleet_maintenance_system.dto.VehicleRequest;
import com.fleet.fleet_maintenance_system.dto.VehicleResponse;
import com.fleet.fleet_maintenance_system.entity.Technician;
import com.fleet.fleet_maintenance_system.entity.Vehicle;
import com.fleet.fleet_maintenance_system.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import tools.jackson.databind.ObjectMapper;
import java.util.List;
import java.time.LocalDate;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;


@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VehicleService vehicleService;

    @MockitoBean
    private com.fleet.fleet_maintenance_system.security.JwtUtil jwtUtil;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getVehicles_returnsListOfVehicles() throws Exception{
        VehicleResponse vehicle1 = new VehicleResponse(1L, "CJ-01-ABC", "Dacia", "Duster", 2022, 15000, LocalDate.of(2026, 12, 1));

        when(vehicleService.findAll()).thenReturn(List.of(vehicle1));

        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].licensePlate").value("CJ-01-ABC"))
                .andExpect(jsonPath("$[0].make").value("Dacia"));
    }

    @Test
    void getVehicles_withoutAuthentication_isRejected() throws Exception {
        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "TECHNICIAN")
    void postVehicles_withoutPermission() throws Exception{

        VehicleRequest request = new VehicleRequest();
        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void postVehicles_invalidRequest() throws Exception{

        VehicleRequest request = new VehicleRequest();
        request.setMake("");
        request.setLicensePlate("");
        request.setModel("");

        mockMvc.perform(post("/api/vehicles")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}
