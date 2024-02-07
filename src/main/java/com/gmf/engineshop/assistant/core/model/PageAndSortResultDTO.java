package com.gmf.engineshop.assistant.core.model;

import java.util.List;

import lombok.Data;

@Data
public class PageAndSortResultDTO<T> {
   private List<T> data;
   private Integer currentPage;
   private Integer totalItems;
   private Integer totalItemsPerPage;
   private Integer totalPage;
   private Boolean hasPrev;
   private Boolean hasNext;
   private Boolean lastPage;
   private String sortBy;
   private String sortOrder;
}
