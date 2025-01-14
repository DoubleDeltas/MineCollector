package com.doubledeltas.minecollector.util;

import java.lang.constant.*;
import java.util.Optional;
import java.util.Objects;

/**
 * 10분의 1을 나타냅니다.
 */
public class Tenth extends Number implements Comparable<Tenth>, Constable {
    private final long ones;
    private final byte tenth;

    private Tenth(long ones, byte tenth) {
        this.ones = ones + tenth / (byte) 10;
        this.tenth = (byte) (tenth + tenth % (byte) 10);
    }

    /**
     * Tenth 객체를 생성하는 정적 팩토리 메서드.
     *
     * @param ones 정수 부분
     * @param tenth 소수점 첫째 자리 (0-9)
     * @return Tenth 객체
     */
    public static Tenth of(long ones, byte tenth) {
        return new Tenth(ones, tenth);
    }

    @Override
    public int intValue() {
        return (int) ones;
    }

    @Override
    public long longValue() {
        return ones;
    }

    @Override
    public float floatValue() {
        return ones + 0.1F * tenth;
    }

    @Override
    public double doubleValue() {
        return ones + 0.1 * tenth;
    }

    @Override
    public int compareTo(Tenth other) {
        int onesComparison = Long.compare(this.ones, other.ones);
        if (onesComparison != 0) {
            return onesComparison;
        } else {
            return Byte.compare(this.tenth, other.tenth);
        }
    }

    @Override
    public Optional<? extends ConstantDesc> describeConstable() {
        return Optional.of(
                DynamicConstantDesc.ofNamed(
                        ConstantDescs.BSM_INVOKE,
                        "Tenth",
                        ClassDesc.of(Tenth.class.getCanonicalName()),
                        ones, (int) tenth
                )
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tenth other = (Tenth) obj;
        return ones == other.ones && tenth == other.tenth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ones, tenth);
    }

    @Override
    public String toString() {
        return ones + "." + tenth;
    }

    /**
     * 두 Tenth 객체를 더합니다.
     *
     * @param other 더할 Tenth 객체
     * @return 합계 Tenth 객체
     */
    public Tenth add(Tenth other) {
        long totalOnes = this.ones + other.ones;
        byte totalTenths = (byte) (this.tenth + other.tenth);
        return Tenth.of(totalOnes, totalTenths);
    }

    /**
     * 두 Tenth 객체를 뺍니다.
     *
     * @param other 뺄 Tenth 객체
     * @return 차이 Tenth 객체
     */
    public Tenth minus(Tenth other) {
        long totalOnes = this.ones - other.ones;
        byte totalTenths = (byte) (this.tenth - other.tenth);
        if (totalTenths < 0) {
            totalOnes -= 1;
            totalTenths += 10;
        }
        return Tenth.of(totalOnes, totalTenths);
    }

    /**
     * Tenth 객체에 정수를 곱합니다.
     *
     * @param multiplier 곱할 정수
     * @return 곱한 결과 Tenth 객체
     */
    public Tenth multiply(int multiplier) {
        long totalOnes = this.ones * multiplier;
        int totalTenths = this.tenth * multiplier;
        return Tenth.of(totalOnes + totalTenths / 10, (byte) (totalTenths % 10));
    }
}
