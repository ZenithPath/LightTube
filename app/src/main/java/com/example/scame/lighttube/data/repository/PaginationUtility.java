package com.example.scame.lighttube.data.repository;



public interface PaginationUtility {

    void saveCurrentPage(int page);

    void saveNextPageToken(String nextPageToken);

    int getSavedPage();

    String getNextPageToken(int page);

    void setPageStringId(int pageStringId);

    void setTokenStringId(int tokenStringId);

    int getPageStringId();

    int getTokenStringId();
}
