package com.examination.api.repository.reserve;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;

import static com.examination.api.model.domain.QReserve.reserve;

@RequiredArgsConstructor
public class ReserveRepositoryImpl implements ReserveRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * BooleanBuilder는 다중 조건 처리를 할 때,
     * BooleanExpression은 단순한 쿼리나 단일 조건일 때쓰면 좋다.
     */
    @Override
    public Long countByReserveDate(LocalDate reserveStartDate, LocalDate reserveEndDate) {
        return jpaQueryFactory
                .select(reserve.count())
                .from(reserve)
                .where(search(reserveStartDate, reserveEndDate))
                .fetchFirst();
    }

    private BooleanExpression search(LocalDate startDate, LocalDate endDate) {
        BooleanExpression booleanExpression;

        if (startDate == null || endDate == null) return null;

        booleanExpression = reserve.reserveStartDate.between(startDate, endDate).or(reserve.reserveEndDate.between(startDate, endDate));

        return booleanExpression;
    }
}
