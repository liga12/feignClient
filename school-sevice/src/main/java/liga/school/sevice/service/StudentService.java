package liga.school.sevice.service;

import liga.student.service.api.StudentRelationApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient( "student-service")
public interface StudentService extends StudentRelationApi {
}
