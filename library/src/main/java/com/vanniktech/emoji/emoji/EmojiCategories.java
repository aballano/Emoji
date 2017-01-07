package com.vanniktech.emoji.emoji;

import android.support.v4.util.Pair;

import com.vanniktech.emoji.emoji.category.ActivityCategory;
import com.vanniktech.emoji.emoji.category.FlagsCategory;
import com.vanniktech.emoji.emoji.category.FoodCategory;
import com.vanniktech.emoji.emoji.category.NatureCategory;
import com.vanniktech.emoji.emoji.category.ObjectsCategory;
import com.vanniktech.emoji.emoji.category.PeopleCategory;
import com.vanniktech.emoji.emoji.category.RegionalCategory;
import com.vanniktech.emoji.emoji.category.SymbolsCategory;
import com.vanniktech.emoji.emoji.category.TravelCategory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class EmojiCategories {

    private static final LinkedHashMap<String, EmojiCategory> categories;

    static {
        categories = new LinkedHashMap<>();

        categories.put("people", new PeopleCategory());
        categories.put("objects", new ObjectsCategory());
        categories.put("activity", new ActivityCategory());
        categories.put("nature", new NatureCategory());
        categories.put("travel", new TravelCategory());
        categories.put("symbols", new SymbolsCategory());
        categories.put("food", new FoodCategory());
        categories.put("flags", new FlagsCategory());
        categories.put("regional", new RegionalCategory());
    }

    protected EmojiCategories() {

    }

    public static List<Pair<String, EmojiCategory>> getCategories() {
        List<Pair<String, EmojiCategory>> result = new ArrayList<>();

        for (Map.Entry<String, EmojiCategory> entry : categories.entrySet()) {
            result.add(new Pair<>(entry.getKey(), entry.getValue()));
        }

        return result;
    }

}
