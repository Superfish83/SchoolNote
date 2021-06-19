/*
 *
 * 소프트웨어 이름: SchoolNote
 * 작성 시작일: 2021-06-13
 * 코드 작성자: 김연준,김연건,김보석,강시윤 (충남삼성고 8기)
 * Frag_Goals.java
 *
 */

package com.example.schoolnote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_Goals extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_goals, container, false);
    }
}
