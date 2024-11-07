package com.examination.api.repository.hotel;

import com.examination.api.model.domain.Hotel;
import com.examination.api.model.types.GradeType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.examination.api.model.domain.QHotel.hotel;

@RequiredArgsConstructor
public class HotelRepositoryImpl implements HotelRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Hotel> searchPagination(String name, List<GradeType> grade, String area, Pageable pageable) {

        List<Hotel> queries = jpaQueryFactory
                .selectFrom(hotel)
                .where(search(name, grade, area))
                .orderBy(hotel.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // pagable 에서 사용 하기 위한 count
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(hotel.count())
                .from(hotel)
                .where(search(name, grade, area));

        return PageableExecutionUtils.getPage(queries, pageable, countQuery::fetchOne);
    }

    private BooleanBuilder search(String name, List<GradeType> grade, String area) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(name))
            builder.and(hotel.name.containsIgnoreCase(name));

        if (!CollectionUtils.isEmpty(grade))
            builder.and(hotel.grade.in(grade));

        if (StringUtils.hasText(area))
            builder.and(hotel.area.containsIgnoreCase(area));

        return builder;
    }
}
