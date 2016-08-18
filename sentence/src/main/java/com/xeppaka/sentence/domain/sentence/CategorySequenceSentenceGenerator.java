package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.AssertionConcern;
import com.xeppaka.sentence.domain.word.Word;
import com.xeppaka.sentence.domain.word.Word.WordCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public abstract class CategorySequenceSentenceGenerator extends AssertionConcern implements SentenceGenerator {
    private List<WordCategory> categories;

    public CategorySequenceSentenceGenerator(List<? extends WordCategory> categories) {
        assertArgumentNotNull(categories, "categories must not be null.");

        this.categories = new ArrayList<>(categories);
    }


}
