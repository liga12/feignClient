package liga.school.sevice.util;

import liga.school.sevice.entity.School;
import liga.school.sevice.dto.PaginationSchoolDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;

public class SchoolSearchSpecification {
    public static Specification<School> schoolFilter(PaginationSchoolDto dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "id", dto.getId()));
            predicates.add(toLikePredicate(root, criteriaBuilder, "name", dto.getName()));
            predicates.add(toLikePredicate(root, criteriaBuilder, "address", dto.getAddress()));
            predicates.add(toEqualsPredicate(root, criteriaBuilder, "studentIds", dto.getStudentIds()));
            Object[] rawPredicates = predicates.stream().filter(Objects::nonNull).toArray();
            return criteriaBuilder.and(Arrays.copyOf(rawPredicates, rawPredicates.length, Predicate[].class));
        };
    }

    private static Predicate toEqualsPredicate(Root<School> root, CriteriaBuilder criteriaBuilder, String param, Object paramValue) {
        return paramValue != null ? criteriaBuilder.equal(root.get(param), paramValue) : null;
    }

    private static Predicate toLikePredicate(Root<School> root, CriteriaBuilder criteriaBuilder, String param, Object paramValue) {
        return paramValue != null ? criteriaBuilder.like(root.get(param), "%" + paramValue + "%") : null;
    }

    private static Predicate toEqualsPredicate(Root<School> root, CriteriaBuilder criteriaBuilder, String param, List<String> paramValue) {
        return paramValue != null ?root.join(param).in(paramValue):null;
    }
}
