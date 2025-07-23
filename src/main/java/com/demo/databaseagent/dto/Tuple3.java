package com.demo.databaseagent.dto;

/**
 * @Description
 * @Author xr
 * @Date 2025/7/23 11:29
 */
public class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {

    private T3 value3;

    public Tuple3() {}

    public Tuple3(T1 value1, T2 value2, T3 value3) {
        super(value1, value2);
        this.value3 = value3;
    }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 value1, T2 value2, T3 value3) {
        return new Tuple3<>(value1, value2, value3);
    }

    public T3 getValue3() {
        return value3;
    }

    public void setValue3(T3 value3) {
        this.value3 = value3;
    }
}
