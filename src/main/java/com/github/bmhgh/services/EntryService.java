package com.github.bmhgh.services;

import com.github.bmhgh.models.Entry;

public class EntryService {
    public static Entry generateNew() {
        return new Entry("", "", "");
    }
}
