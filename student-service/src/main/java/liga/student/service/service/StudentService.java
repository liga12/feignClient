package liga.student.service.service;


import liga.student.service.transport.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Use for create, update , save and get data of {@link liga.student.service.domain.entity.Student Student}.
 */
public interface StudentService {

    /**
     * Returns all instances of the {@link liga.student.service.transport.dto.StudentOutcomeDto StudentOutcomeDto}
     * with the given {@link liga.student.service.transport.dto.StudentFindByTextSearchDto StudentFindByTextSearchDto}
     * and  {@link org.springframework.data.domain.Pageable Pageable}.
     *
     * @param dto      for filtration, must be not {@literal null}.
     * @param pageable for sorting and pagination, can be {@literal null}.
     * @return {@link org.springframework.data.domain.Page Page}
     * by number of {@link liga.student.service.transport.dto.StudentOutcomeDto StudentOutcomeDto} by filtered
     */
    List<StudentOutcomeDto> getAll(StudentFindByTextSearchDto dto, Pageable pageable);

    /**
     * Returns all instances of the {@link liga.student.service.transport.dto.StudentOutcomeDto StudentOutcomeDto}
     * with the given {@link liga.student.service.transport.dto.StudentFindDto StudentFindDto}
     * and  {@link org.springframework.data.domain.Pageable Pageable}.
     *
     * @param dto      for filtration, can be {@literal null}.
     * @param pageable for sorting and pagination, can be {@literal null}.
     * @return {@link org.springframework.data.domain.Page Page}
     * by number of {@link liga.student.service.transport.dto.StudentOutcomeDto StudentOutcomeDto} by filtered
     */
    List<StudentOutcomeDto> getAll(StudentFindDto dto, Pageable pageable);

    /**
     * Return a {@link liga.student.service.transport.dto.StudentOutcomeDto StudentOutcomeDto}
     * by its {@link liga.student.service.domain.entity.Student#id id}.
     *
     * @param id {@link liga.student.service.domain.entity.Student#id id}
     * @return the {@link liga.student.service.transport.dto.StudentOutcomeDto SchoolOutcomeDto} with
     * the given {@link liga.student.service.domain.entity.Student#id id}
     * @throws liga.student.service.exception.StudentNotFoundException if {@link liga.student.service.domain.entity.Student#id id} is {@literal null} or don't exist.
     */
    StudentOutcomeDto getById(String id);

    /**
     * Returns boolean result by exists {@link liga.student.service.domain.entity.Student Student} with the given ids .
     *
     * @param ids must not be {@literal null}.
     * @return {@literal true} if an {@link liga.student.service.domain.entity.Student Student} with the given ids exists,
     * {@literal false} otherwise
     *  @throws liga.student.service.exception.StudentNotFoundException if  any {@code id} is don't exist.
     *
     */
    boolean existsByIds(Set<String> ids);

    /**
     * Saves a given {@link liga.student.service.transport.dto.StudentCreateDto StudentCreateDto}
     *
     * @param dto must not be {@literal null}.
     * @return the {@link liga.student.service.transport.dto.StudentOutcomeDto StudentOutcomeDto}
     * will never be {@literal null}.
     */
    StudentOutcomeDto create(StudentCreateDto dto);

    /**
     * Update a given {@link liga.student.service.transport.dto.StudentUpdateDto StudentUpdateDto}
     *
     * @param dto must not be {@literal null}.
     * @return the {@link liga.student.service.transport.dto.StudentOutcomeDto StudentOutcomeDto}
     * will never be {@literal null}.
     * @throws liga.student.service.exception.StudentNotFoundException if {@link liga.student.service.domain.entity.Student#id id} is {@literal null} or don't exist.
     */
    StudentOutcomeDto update(StudentUpdateDto dto);

    /**
     * Deletes a given {@link liga.student.service.domain.entity.Student Student}
     * by {@link liga.student.service.domain.entity.Student#id id}
     *
     * @param id must not be {@literal null}.
     * @throws liga.student.service.exception.StudentNotFoundException if {@link liga.student.service.domain.entity.Student#id id} is {@literal null} or don't exist.
     */
    void remove(String id);
}
