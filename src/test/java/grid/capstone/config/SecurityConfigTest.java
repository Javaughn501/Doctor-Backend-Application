package grid.capstone.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import grid.capstone.dto.v1.DoctorSignUp;
import grid.capstone.dto.v1.PatientSignUp;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional @Rollback
class SecurityConfigTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    PatientSignUp patientSignUp;
    DoctorSignUp doctorSignUp;

    @BeforeEach
    void setUp() {

        patientSignUp = PatientSignUp.builder()
                .password("password")
                .name("name")
                .address("address")
                .phoneNumber("1234567890")
                .email("email@gmail.com")
                .age(20)
                .gender('M')
                .description("description")
                .build();

        doctorSignUp = DoctorSignUp.builder()
                .password("password")
                .name("name")
                .address("address")
                .phoneNumber("1234567890")
                .email("email@gmail.com")
                .age(20)
                .gender('M')
                .skills(List.of("Surgeon"))
                .build();

    }

    @Test
    @WithMockUser
    public void testCsrfIgnoreForDoctorEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorSignUp))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser
    public void testCsrfIgnoreForPatientEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients")
                        .param("doctorId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientSignUp))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser
    public void testCsrfProtectionForOtherEndpoints() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


}