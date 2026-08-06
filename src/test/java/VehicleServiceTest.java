import com.fleet.fleet_maintenance_system.dto.VehicleRequest;
import com.fleet.fleet_maintenance_system.dto.VehicleResponse;
import com.fleet.fleet_maintenance_system.entity.Vehicle;
import com.fleet.fleet_maintenance_system.repository.VehicleRepository;
import com.fleet.fleet_maintenance_system.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import static io.micrometer.observation.tck.ObservationContextAssert.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void findById_throwsWhenVehicleNotFound(){
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Vehicle not found");
    }

    @Test
    void findById_ReturnsVehicleWhenFound(){
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setLicensePlate("MS-46-WVW");
        vehicle.setYear(2014);
        vehicle.setModel("Audi a5");
        vehicle.setMake("VAG");
        vehicle.setMileageKm(300000);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        VehicleResponse response = vehicleService.findById(1L);
        assertThat(response.getLicensePlate()).isEqualTo("MS-46-WVW");
    }

    @Test
    void create_savesVehicleWithCorrectFields(){
        VehicleRequest request = new VehicleRequest();
        request.setLicensePlate("MS-46-WVW");
        request.setYear(2014);
        request.setModel("Audi a5");
        request.setMake("VAG");
        request.setMileageKm(300000);
        request.setNextServiceDue(LocalDate.of(2027, 3, 15));

        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));

        VehicleResponse response = vehicleService.create(request);

        assertThat(response.getLicensePlate()).isEqualTo("MS-46-WVW");
        assertThat(response.getYear()).isEqualTo(2014);
        assertThat(response.getModel()).isEqualTo("Audi a5");
        assertThat(response.getMake()).isEqualTo("VAG");
        assertThat(response.getMileageKm()).isEqualTo(300000);
        assertThat(response.getNextServiceDue()).isEqualTo(LocalDate.of(2027, 3, 15));

    }

    @Test
    void update_savesVehicleWithCorrectFields(){

//        Arrange
        VehicleRequest request = new VehicleRequest();
        request.setLicensePlate("MS-46-WVW");
        request.setYear(2014);
        request.setModel("Audi a5");
        request.setMake("VAG");
        request.setMileageKm(300000);
        request.setNextServiceDue(LocalDate.of(2027, 3, 15));

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setLicensePlate("MS-46-XXX");
        vehicle.setYear(2010);
        vehicle.setModel("Audi a4");
        vehicle.setMake("Vw");
        vehicle.setMileageKm(100000);

        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(invocation ->  invocation.getArgument(0));
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

//        Act

        VehicleResponse response = vehicleService.update(vehicle.getId(), request);

//        Assert
        assertThat(vehicle.getLicensePlate()).isEqualTo("MS-46-WVW");

        assertThat(response.getLicensePlate()).isEqualTo("MS-46-WVW");
        assertThat(response.getYear()).isEqualTo(2014);
        assertThat(response.getModel()).isEqualTo("Audi a5");
        assertThat(response.getMake()).isEqualTo("VAG");
        assertThat(response.getMileageKm()).isEqualTo(300000);
        assertThat(response.getNextServiceDue()).isEqualTo(LocalDate.of(2027, 3, 15));

    }

    @Test
    void delete_removesVehicle(){

//        Arrange

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

//        Act
        vehicleService.delete(1L);

//        Assert
        Mockito.verify(vehicleRepository, times(1)).delete(vehicle);

    }
}
