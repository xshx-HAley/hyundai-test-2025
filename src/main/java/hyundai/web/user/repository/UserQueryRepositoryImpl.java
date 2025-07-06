package hyundai.web.user.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hyundai.web.user.domain.QUserEntity;
import hyundai.web.user.domain.UserEntity;
import hyundai.web.user.dto.UserSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository{
    private final JPAQueryFactory queryFactory;

    QUserEntity user = QUserEntity.userEntity;

    @Override
    public Page<UserEntity> searchUsersByConditions(UserSearchCondition condition, Pageable pageable) {

        List<UserEntity> results = queryFactory
                .selectFrom(user)
                .where( nameContains(condition.getName()),
                        userIdContains(condition.getUserId()),
                        phoneEqual(condition.getPhone()),
                        sidoEqual(condition.getSido()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(user.id.desc())
                .fetch();

        return new PageImpl<>(results, pageable, results.size());
    }

    private BooleanExpression nameContains(String name) {
        return name != null && !name.isBlank() ? user.name.containsIgnoreCase(name) : null;
    }

    private BooleanExpression userIdContains(String userId) {
        return userId != null && !userId.isBlank() ? user.userId.contains(userId) : null;
    }

    private BooleanExpression phoneEqual(String phone) {
        return phone != null && !phone.isBlank() ? user.phone.eq(phone) : null;
    }

    private BooleanExpression sidoEqual(String sido) {
        return sido != null && !sido.isBlank() ? user.address.sido.eq(sido) : null;
    }



}
