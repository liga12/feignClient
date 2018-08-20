package liga.student.service.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

/**
 * Use for get information about exist Schools by id.
 */
@RequestMapping("/student-api")
public interface StudentApi {

    /**
     * Return true if  each id from {@code ids} exist or false if any id from don't exist
     *
     * @param ids must be {@literal null}.
     * @return true or false
     */
    @PostMapping
    Boolean existsAllStudentsByIds(@RequestBody Set<String> ids);

}
