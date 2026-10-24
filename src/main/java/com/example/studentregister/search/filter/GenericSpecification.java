
package com.example.studentregister.search.filter;

import com.example.studentregister.calendar.CalendarTools;
import com.example.studentregister.calendar.CustomDate;
import com.example.studentregister.config.CustomObjectMapper;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.*;

public class GenericSpecification<T> implements Specification<T> {

    private final CustomObjectMapper customObjectMapper =
            new CustomObjectMapper();

    private List<SearchFilterRestrictionModel> searchFilterRestrictionModelList;

    private HashMap<String, Join> joins = new HashMap<>();

    public HashMap<String, Join> getJoins() {
        return joins;
    }

    public void setJoins(HashMap<String, Join> joins) {
        this.joins = joins;
    }

    public GenericSpecification() {
        this.searchFilterRestrictionModelList = new ArrayList<>();
    }

    public void add(SearchFilterRestrictionModel searchFilterRestrictionModel) {
        searchFilterRestrictionModelList.add(searchFilterRestrictionModel);
    }

    @Override
    public Predicate toPredicate(
            Root<T> root,
            CriteriaQuery<?> query,
            CriteriaBuilder builder) {

        Date date;
        String string;
        Predicate newPredicate;
        Predicate predicate = null;

        for (SearchFilterRestrictionModel restriction
                : searchFilterRestrictionModelList) {

            newPredicate = null;

            try {
                Path path = this.getPath(root, restriction.getFieldName());
                Object fieldValue = restriction.getFieldValue();

                if (fieldValue == null
                        && restriction.getFieldOperation() != SearchFilterOperationEnum.IS_NULL
                        && restriction.getFieldOperation() != SearchFilterOperationEnum.IS_NOT_NULL) {
                    continue;
                }

                switch (restriction.getFieldOperation()) {

                    case GREATER_THAN:
                        if (fieldValue instanceof Integer) {
                            newPredicate = builder.greaterThan(
                                    path, (Integer) fieldValue);
                        } else if (fieldValue instanceof Long) {
                            newPredicate = builder.greaterThan(
                                    path, (Long) fieldValue);
                        } else if (fieldValue instanceof Double) {
                            newPredicate = builder.greaterThan(
                                    path, (Double) fieldValue);
                        } else {
                            date = getDateFromFieldValue(fieldValue);
                            newPredicate = builder.greaterThan(path, date);
                        }
                        break;

                    case LESS_THAN:
                        if (fieldValue instanceof Integer) {
                            newPredicate = builder.lessThan(
                                    path, (Integer) fieldValue);
                        } else if (fieldValue instanceof Long) {
                            newPredicate = builder.lessThan(
                                    path, (Long) fieldValue);
                        } else if (fieldValue instanceof Double) {
                            newPredicate = builder.lessThan(
                                    path, (Double) fieldValue);
                        } else {
                            date = getDateFromFieldValue(fieldValue);
                            newPredicate = builder.lessThan(path, date);
                        }
                        break;

                    case GREATER_THAN_EQUAL:
                        if (fieldValue instanceof Integer) {
                            newPredicate = builder.greaterThanOrEqualTo(
                                    path, (Integer) fieldValue);
                        } else if (fieldValue instanceof Long) {
                            newPredicate = builder.greaterThanOrEqualTo(
                                    path, (Long) fieldValue);
                        } else if (fieldValue instanceof Double) {
                            newPredicate = builder.greaterThanOrEqualTo(
                                    path, (Double) fieldValue);
                        } else {
                            date = getDateFromFieldValue(fieldValue);
                            newPredicate = builder.greaterThanOrEqualTo(
                                    path, date);
                        }
                        break;

                    case LESS_THAN_EQUAL:
                        if (fieldValue instanceof Integer) {
                            newPredicate = builder.lessThanOrEqualTo(
                                    path, (Integer) fieldValue);
                        } else if (fieldValue instanceof Long) {
                            newPredicate = builder.lessThanOrEqualTo(
                                    path, (Long) fieldValue);
                        } else if (fieldValue instanceof Double) {
                            newPredicate = builder.lessThanOrEqualTo(
                                    path, (Double) fieldValue);
                        } else {
                            date = getDateFromFieldValue(fieldValue);
                            newPredicate = builder.lessThanOrEqualTo(
                                    path, date);
                        }
                        break;

                    case EQUAL:
                        newPredicate = builder.equal(path, fieldValue);
                        break;

                    case NOT_EQUAL:
                        newPredicate = builder.notEqual(path, fieldValue);
                        break;

                    case MATCH:
                        if (Date.class.equals(path.getJavaType())) {
                            string = (String) fieldValue;
                            string = CalendarTools.jalaliToGregorianDate(
                                    string, "/", "-");

                            newPredicate = builder.like(
                                    builder.function(
                                            "to_char",
                                            String.class,
                                            path,
                                            builder.literal("YYYY-MM-DD")),
                                    "%" + string + "%");
                        } else if (fieldValue instanceof String) {
                            string = (String) fieldValue;
                            newPredicate = builder.like(
                                    builder.lower(path),
                                    "%" + string.toLowerCase(Locale.ROOT) + "%");
                        }
                        break;

                    case MATCH_START:
                        if (fieldValue instanceof String) {
                            string = (String) fieldValue;
                            newPredicate = builder.like(
                                    builder.lower(path),
                                    string.toLowerCase(Locale.ROOT) + "%");
                        }
                        break;

                    case MATCH_END:
                        if (fieldValue instanceof String) {
                            string = (String) fieldValue;
                            newPredicate = builder.like(
                                    builder.lower(path),
                                    "%" + string.toLowerCase(Locale.ROOT));
                        }
                        break;

                    case IN:
                        newPredicate = path.in(fieldValue);
                        break;

                    case NOT_IN:
                        newPredicate = path.in(
                                ((List<?>) fieldValue).toArray()).not();
                        break;

                    case MEMBER_OF:
                        newPredicate = builder.isMember(fieldValue, path);
                        break;

                    case NOT_MEMBER_OF:
                        newPredicate = builder.isNotMember(fieldValue, path);
                        break;

                    case IS_NULL:
                        newPredicate = builder.isNull(path);
                        break;

                    case IS_NOT_NULL:
                        newPredicate = builder.isNotNull(path);
                        break;
                }

                if (!ObjectUtils.isEmpty(newPredicate)) {
                    predicate = this.addNextCondition(
                            builder,
                            restriction,
                            predicate,
                            newPredicate);
                }

            } catch (Exception e) {
                System.out.println(
                        "GenericSpecification.toPredicate Exception: "
                                + e);
            }
        }

        this.joins = new HashMap<>();
        return predicate;
    }

    /**
     * تبدیل مقدار ورودی به Date.
     * ورودی می‌تواند Date یا یک ساختار قابل تبدیل به CustomDate باشد.
     */
    private Date getDateFromFieldValue(Object fieldValue) {
        if (fieldValue instanceof Date) {
            return (Date) fieldValue;
        }

        CustomDate customDate = customObjectMapper.convertValue(
                fieldValue,
                CustomDate.class);

        return CalendarTools.getDateFromCustomDate(customDate);
    }

    /**
     * ترکیب شرط جدید با شرط قبلی براساس AND یا OR.
     */
    protected Predicate addNextCondition(
            CriteriaBuilder builder,
            SearchFilterRestrictionModel restriction,
            Predicate predicate,
            Predicate newPredicate) {

        if (predicate == null) {
            return newPredicate;
        }

        if (restriction.getNextConditionOperator()
                == SearchFilterNextConditionOperatorEnum.AND) {
            return builder.and(predicate, newPredicate);
        }

        return builder.or(predicate, newPredicate);
    }

    /**
     * دریافت مسیر فیلد، با پشتیبانی از فیلدهای تو در تو.
     */
    protected Path getPath(Root root, String path) {
        String[] pathArray = path.split("\\.");

        if (pathArray.length == 1) {
            return root.get(path);
        }

        String joinPath = StringUtils.join(
                pathArray, ".", 0, pathArray.length - 1);

        Join latestJoin = joins.get(joinPath);

        if (latestJoin != null) {
            return latestJoin.get(pathArray[pathArray.length - 1]);
        }

        Join join = null;

        for (int i = 0; i < pathArray.length - 1; i++) {
            String currentPath = StringUtils.join(
                    pathArray, ".", 0, i + 1);

            Join existingJoin = joins.get(currentPath);

            if (existingJoin != null) {
                join = existingJoin;
            } else if (join == null) {
                join = root.join(pathArray[i], JoinType.LEFT);
            } else {
                join = join.join(pathArray[i], JoinType.LEFT);
            }

            joins.put(currentPath, join);
        }

        return join.get(pathArray[pathArray.length - 1]);
    }
}
