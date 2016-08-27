package com.example.scame.lighttube.data.mappers;

import com.example.scame.lighttube.data.entities.search.CategoriesEntity;
import com.example.scame.lighttube.data.entities.search.CategoryItem;
import com.example.scame.lighttube.data.entities.search.CategoryPairs;

import java.util.List;

public class CategoryPairsMapper {

    public CategoryPairs convert(CategoriesEntity categoriesEntity) {
        CategoryPairs categoryPairs = new CategoryPairs();

        List<CategoryItem> categoryItems = categoriesEntity.getItems();
        for (CategoryItem categoryItem : categoryItems) {
            categoryPairs.put(categoryItem.getSnippet().getTitle(), categoryItem.getId());
        }

        return categoryPairs;
    }
}
