package com.example.community.commontest;

import com.example.community.domain.account.repo.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListComparisonTest {

    @Test
    void test() {

        ArrayList<String> list1 = new ArrayList<>(List.of("#A", "#B", "#C"));
        ArrayList<String> list2 = new ArrayList<>(List.of("#A", "#B", "#D"));

        list1.retainAll(list2);
        list2.removeAll(list1);

        list1.addAll(list2);

        Assertions.assertThat(list1).contains("#A","#B","#D");

        // result = "#A","#C","#D"

    }
}
