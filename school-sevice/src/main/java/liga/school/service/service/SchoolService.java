package liga.school.service.service;

import liga.school.service.transport.dto.SchoolCreateDto;
import liga.school.service.transport.dto.SchoolOutcomeDto;
import liga.school.service.transport.dto.SchoolFindDto;
import liga.school.service.transport.dto.SchoolUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Use for create, update , save and get data of {@link liga.school.service.domain.entity.School School}.
 */
public interface SchoolService {

    /**
     * Returns all instances of the {@link liga.school.service.transport.dto.SchoolOutcomeDto SchoolOutcomeDto}
     * with the given {@link liga.school.service.transport.dto.SchoolFindDto SchoolFindDto}
     * and  {@link org.springframework.data.domain.Pageable Pageable}.
     *
     * @param dto      for filtration, can be {@literal null}.
     * @param pageable for sorting and pagination, can be {@literal null}.
     * @return {@link org.springframework.data.domain.Page Page}
     * by number of {@link liga.school.service.transport.dto.SchoolOutcomeDto SchoolOutcomeDto} by filtered
     */
    Page<SchoolOutcomeDto> getAll(SchoolFindDto dto, Pageable pageable);

    /**
     * Return a {@link liga.school.service.transport.dto.SchoolOutcomeDto SchoolOutcomeDto}
     * by its {@link liga.school.service.domain.entity.School#id id}.
     *
     * @param id {@link liga.school.service.domain.entity.School#id id}
     * @return the {@link liga.school.service.transport.dto.SchoolOutcomeDto SchoolOutcomeDto} with
     * the given {@link liga.school.service.domain.entity.School#id id}
     * @throws liga.school.service.exception.SchoolNotFoundException if {@link liga.school.service.domain.entity.School#id id} is {@literal null} or don't exist.
     */
    SchoolOutcomeDto getById(Long id);

    /**
     * Saves a given {@link liga.school.service.transport.dto.SchoolCreateDto SchoolCreateDto}
     *
     * @param dto must not be {@literal null}.
     * @return the {@link liga.school.service.transport.dto.SchoolOutcomeDto SchoolOutcomeDto}
     * will never be {@literal null}.
     * @throws liga.school.service.exception.StudentNotFoundException if {@link liga.school.service.domain.entity.School#studentIds ids} is  don't exist.
     */
    SchoolOutcomeDto create(SchoolCreateDto dto);

    /**
     * Update a given {@link liga.school.service.transport.dto.SchoolUpdateDto SchoolUpdateDto}
     *
     * @param dto must not be {@literal null}.
     * @return the {@link liga.school.service.transport.dto.SchoolOutcomeDto SchoolOutcomeDto}
     * will never be {@literal null}.
     * @throws liga.school.service.exception.SchoolNotFoundException  if {@link liga.school.service.domain.entity.School#id id} is {@literal null} or don't exist.
     * @throws liga.school.service.exception.StudentNotFoundException if {@link liga.school.service.domain.entity.School#studentIds ids} is  don't exist.
     */
    SchoolOutcomeDto update(SchoolUpdateDto dto);

    /**
     * Deletes a given {@link liga.school.service.domain.entity.School School}
     * by {@link liga.school.service.domain.entity.School#id id}
     *
     * @param id must not be {@literal null}.
     * @throws liga.school.service.exception.SchoolNotFoundException if {@link liga.school.service.domain.entity.School#id id} is {@literal null} or don't exist.
     */
    void remove(Long id);
}
