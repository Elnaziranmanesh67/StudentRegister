package com.example.studentregister.search.filter;
import jakarta.persistence.criteria.Join;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SearchFilterModel implements Serializable {

    private List<SearchFilterBox> searchFilterBoxList = new ArrayList<>();
    private List<SearchFilterSortModel> sortList = new ArrayList<>();

    private Integer page = 0;

    private Integer rows = Integer.MAX_VALUE;
    public SearchFilterModel() {
    }
    public <T> Specification<T> makeSpecification(GenericSpecification<T> genericSpecification) {
        Specification<T> spec = genericSpecification;
        HashMap<String, Join> joins = new HashMap<>();
        if (!ObjectUtils.isEmpty(this.searchFilterBoxList)) {
            for (int i = 0; i < this.searchFilterBoxList.size(); i++) {
                GenericSpecification<T> gs = new GenericSpecification<>();
                gs.setJoins(joins);
                this.searchFilterBoxList.get(i).getRestrictionList().forEach(gs::add);
                if (i == 0) {
                    spec = gs;
                } else if (i < this.searchFilterBoxList.size()) {
                    if (this.searchFilterBoxList.get(i - 1).getNextConditionOperator().getValue().equals(SearchFilterNextConditionOperatorEnum.AND.getValue())) {
                        spec = spec.and(gs);
                    } else {
                        spec = spec.or(gs);
                    }
                }
                joins = gs.getJoins();
            }

        }
        return spec;
    }

    public Pageable makePageable() {

        Sort allSort = null;
        for (SearchFilterSortModel searchFilterSortModel : this.getSortList()) {
            Sort newSort = null;
            if (searchFilterSortModel.getType().equals(SearchFilterSortTypeEnum.ASC)) {
                newSort = Sort.by(searchFilterSortModel.getFieldName()).ascending();
            }
            if (searchFilterSortModel.getType().equals(SearchFilterSortTypeEnum.DSC)) {
                newSort = Sort.by(searchFilterSortModel.getFieldName()).descending();
            }
            if (ObjectUtils.isEmpty(allSort)) {
                allSort = Sort.by("id").ascending();
            }
        }
        if (ObjectUtils.isEmpty(allSort)) {
            return PageRequest.of(this.getPage(), this.getRows());
        } else {
            return PageRequest.of(this.getPage(), this.getRows(), allSort);
        }
    }

    @Override
    public String toString() {
        return "SearchFilterListModel{" +
                ", sortList=" + sortList.stream().map(item -> item.toString()).collect(Collectors.joining("\n")) +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }


}
