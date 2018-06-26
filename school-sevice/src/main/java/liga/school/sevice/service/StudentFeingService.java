package liga.school.sevice.service;

import liga.student.service.api.StudentControllerApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("student-service")
public interface StudentFeingService extends StudentControllerApi {
}
