package com.mercurows.domain.query;

import com.mercurows.domain.Student;

// 用来封装Student的查询条条件
public class StudentQuery extends Student {
    // Student中的age的上限，因此Student中的age为下限
    private Integer age2;

    public Integer getAge2() {
        return age2;
    }

    public void setAge2(Integer age2) {
        this.age2 = age2;
    }

    @Override
    public String toString() {
        return "StudentQuery [age2=" + age2 + "]";
    }

}
