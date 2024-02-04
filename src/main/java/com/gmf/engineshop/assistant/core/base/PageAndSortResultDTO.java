package com.gmf.engineshop.assistant.core.base;

import java.util.List;

import lombok.Data;

@Data
public class PageAndSortResultDTO<T> {
   private List<T> data;
   private Integer currentPage;
   private Integer totalItems;
   private Integer lastPage;
   private Integer totalItemsPerPage;
   private Boolean hasPrev;
   private Boolean hasNext;
   private String sortBy;
   private String sortOrder;
}