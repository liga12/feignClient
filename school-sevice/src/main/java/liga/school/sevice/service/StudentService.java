package liga.school.sevice.service;

import liga.student.service.api.StudentApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("student-service")
public interface StudentService extends StudentApi {
}
