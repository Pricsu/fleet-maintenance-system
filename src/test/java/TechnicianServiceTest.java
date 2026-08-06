import com.fleet.fleet_maintenance_system.dto.TechnicianRequest;
import com.fleet.fleet_maintenance_system.dto.TechnicianResponse;

import com.fleet.fleet_maintenance_system.entity.Technician;

import com.fleet.fleet_maintenance_system.repository.TechnicianRepository;
import com.fleet.fleet_maintenance_system.service.TechnicianService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TechnicianServiceTest {

    @Mock
    private TechnicianRepository technicianRepository;

    @InjectMocks
    private TechnicianService technicianService;

    @Test
    void findById_ThrowsWhenTechnicianNotFound(){

//        Arrange
        when(technicianRepository.findById(99L)).thenReturn(Optional.empty());

//        Act and Assert
        assertThatThrownBy(() -> technicianService.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Technician not found: ");
    }

    @Test
    void findById_ReturnTechnicianFound(){

//        Arrange
        Technician technician = new Technician();
        technician.setId(1L);
        technician.setEmail("elekesattila@gmail.com");
        technician.setFullName("Elekes Attila");
        technician.setSpecialty("Software");

        when(technicianRepository.findById(1L)).thenReturn(Optional.of(technician));

//        Act
        TechnicianResponse response = technicianService.findById(1L);

//        Assert

        assertThat(response.getEmail()).isEqualTo("elekesattila@gmail.com");
        assertThat(response.getFullName()).isEqualTo("Elekes Attila");
        assertThat(response.getSpecialty()).isEqualTo("Software");
    }

    @Test
    void create_savesTechnicianWithCorrectFields(){

//        Arrange
        TechnicianRequest request = new TechnicianRequest();
        request.setEmail("elekesattila@gmail.com");
        request.setFullName("Elekes");
        request.setSpecialty("IT");

        when(technicianRepository.save(any(Technician.class))).thenAnswer(invocation -> invocation.getArgument(0));

//        Act

        TechnicianResponse response = technicianService.create(request);

//        Assert

        assertThat(response.getFullName()).isEqualTo("Elekes");
        assertThat(response.getEmail()).isEqualTo("elekesattila@gmail.com");
        assertThat(response.getSpecialty()).isEqualTo("IT");
    }

    @Test
    void update_savesTechnicianChanges(){
        TechnicianRequest request = new TechnicianRequest();
        request.setEmail("elekesattila@gmail.com");
        request.setFullName("Elekes");
        request.setSpecialty("IT");

        Technician technician = new Technician();
        technician.setId(1L);
        technician.setEmail("elekesalfred@gmail.com");
        technician.setSpecialty("Software");
        technician.setFullName("Alfred");

        when(technicianRepository.findById(1L)).thenReturn(Optional.of(technician));
        when(technicianRepository.save(any(Technician.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TechnicianResponse response = technicianService.update(request, 1L);

        assertThat(response.getFullName()).isEqualTo("Elekes");
        assertThat(response.getEmail()).isEqualTo("elekesattila@gmail.com");
        assertThat(response.getSpecialty()).isEqualTo("IT");
    }

    @Test
    void delete_removesTechnician(){
        Technician technician = new Technician();
        technician.setId(1L);
        when(technicianRepository.findById(1L)).thenReturn(Optional.of(technician));
        technicianService.delete(1L);

        Mockito.verify(technicianRepository, times(1)).delete(technician);


    }
}
