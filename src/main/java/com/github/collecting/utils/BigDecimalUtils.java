package com.github.collecting.utils;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.math.BigDecimal.ROUND_CEILING;
import static java.math.BigDecimal.ZERO;

/**
 * Copyright (C), 2014-2015, 深圳云集智造系统技术有限公司
 *
 * @Title: 四则运算
 * @Description : 四则运算工具类
 * @Author by yandw
 * @date on 2016/8/23
 */
public class BigDecimalUtils {

    /**
     * 通过字符串构建BigDecimal
     *
     * @param num
     * @return
     */
    public static final BigDecimal newBigDecimal(String num) {
        if (null == num || num.length() < 1) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(num);
    }

    /**
     * 加法
     *
     * @param num1
     * @param num2
     * @return
     */
    public static final BigDecimal add(BigDecimal num1, BigDecimal num2) {
        Assert.notNull(num1, "加法运算的第一个数值不能为空");
        Assert.notNull(num2, "加法运算的第二个数值不能为空");
        return num1.add(num2);
    }


    /**
     * 减法
     *
     * @param num1
     * @param num2
     * @return
     */
    @Deprecated
    public static final BigDecimal substract(BigDecimal num1, BigDecimal num2) {
        Assert.notNull(num1, "减法运算的第一个数值不能为空");
        Assert.notNull(num2, "减法运算的第二个数值不能为空");
        return num1.subtract(num2);
    }


    /**
     * 减法
     *
     * @param num1
     * @param num2
     * @return
     */
    public static final BigDecimal subtract(BigDecimal num1, BigDecimal num2) {
        Assert.notNull(num1, "减法运算的第一个数值不能为空");
        Assert.notNull(num2, "减法运算的第二个数值不能为空");
        return num1.subtract(num2);
    }


    /**
     * 乘法
     *
     * @param num1
     * @param num2
     * @return
     */
    public static final BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
        Assert.notNull(num1, "乘法运算的第一个数值不能为空");
        Assert.notNull(num2, "乘法运算的第二个数值不能为空");
        return num1.multiply(num2);
    }


    /**
     * 除法
     *
     * @param num1
     * @param num2
     * @param scale
     * @return
     */
    public static final BigDecimal divide(BigDecimal num1, BigDecimal num2, Integer scale) {
        Assert.notNull(num1, "除法运算的第一个数值不能为空");
        Assert.notNull(num2, "除法运算的第二个数值不能为空");
        return num1.divide(num2, scale, ROUND_CEILING);
    }

    /**
     * 转成正数
     *
     * @param num
     * @return
     */
    public static final BigDecimal positive(BigDecimal num) {
        return num == null ? null : num.abs();
    }

    /**
     * 转成负数
     *
     * @param num
     * @return
     */
    public static final BigDecimal negative(BigDecimal num) {
        return num == null ? null : num.negate();
    }

    /**
     * 将Bigdecimal转换成指定的精度
     *
     * @param num
     * @param scale
     * @return
     */
    public static final BigDecimal getBigDecimal(BigDecimal num, Integer scale) {
        return num == null ? null : num.setScale(scale, ROUND_CEILING);
    }

    /**
     * 比较数值是否相等
     *
     * @param num1
     * @param num2
     * @return
     */
    public static final boolean isEquals(BigDecimal num1, BigDecimal num2) {
        if (num1 == null && num2 == null) {
            return true;
        }
        if (num1 == null || num2 == null) {
            return false;
        }
        return num1.compareTo(num2) == 0;
    }


    /**
     * 如果是负数归结为0,如果未空也归结为0
     *
     * @param num
     * @return
     */
    public static final BigDecimal negativeToZero(BigDecimal num) {
        if (num == null) {
            return ZERO;
        }
        if (isLessEqThan(num, BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        return num;
    }

    /**
     * 小于比较
     *
     * @param num1
     * @param num2
     * @return
     */
    public static final boolean isLessThan(BigDecimal num1, BigDecimal num2) {
        if (num1 == null || num2 == null) {
            return false;
        }
        return num1.compareTo(num2) < 0;
    }


    /**
     * 小于等于比较
     *
     * @param num1
     * @param num2
     * @return
     */
    public static final boolean isLessEqThan(BigDecimal num1, BigDecimal num2) {
        if (num1 == null || num2 == null) {
            return false;
        }
        return num1.compareTo(num2) <= 0;
    }

    /**
     * 大于比较
     *
     * @param num1
     * @param num2
     * @return
     */
    public static final boolean isGreaterThan(BigDecimal num1, BigDecimal num2) {
        if (num1 == null || num2 == null) {
            return false;
        }
        return num1.compareTo(num2) > 0;
    }

    /**
     * 大于等于比较
     *
     * @param num1
     * @param num2
     * @return
     */
    public static final boolean isGreaterEqThan(BigDecimal num1, BigDecimal num2) {
        if (num1 == null || num2 == null) {
            return false;
        }
        return num1.compareTo(num2) >= 0;
    }

    /**
     * 四舍五入
     *
     * @param num
     * @param scale
     * @return
     */
    public static final BigDecimal getBigDecimalHalfUp(BigDecimal num, Integer scale) {
        return num == null ? null : num.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 除以100
     *
     * @param num
     * @return
     */
    public static final BigDecimal divide100(BigDecimal num) {
        return divide(num, new BigDecimal("100"), num.scale());
    }


    /**
     * 获取最小值
     *
     * @return
     */
    public static BigDecimal getMinDecimal(BigDecimal baseQuantity, BigDecimal... quantities) {
        BigDecimal min = baseQuantity;
        for (BigDecimal quantity : quantities) {
            if (quantity == null) {
                continue;
            }
            if (quantity.compareTo(min) < 0) {
                min = quantity;
            }
        }
        return min;
    }

    /**
     * 获取最大值
     *
     * @return
     */
    public static BigDecimal getMaxDecimal(BigDecimal baseQuantity, BigDecimal... quantities) {
        BigDecimal max = baseQuantity;
        for (BigDecimal quantity : quantities) {
            if (quantity == null) {
                continue;
            }
            if (quantity.compareTo(max) > 0) {
                max = quantity;
            }
        }
        return max;
    }


    /**
     * 统计所有的数字之和 null不统计
     *
     * @param quantities
     * @return
     */
    public static BigDecimal getSumNoNullQuantity(BigDecimal... quantities) {
        BigDecimal total = BigDecimal.ZERO;

        for (BigDecimal quantity : quantities) {
            if (quantity == null) {
                continue;
            }
            total = total.add(quantity);
        }
        return total;
    }

    public static BigDecimal getSumNoNullQuantity(List<BigDecimal> quantities) {
        BigDecimal total = BigDecimal.ZERO;

        for (BigDecimal quantity : quantities) {
            if (quantity == null) {
                continue;
            }
            total = total.add(quantity);
        }
        return total;
    }

    public static String toPlainString(BigDecimal decimal) {
        String decimalStr = decimal != null ? decimal.toPlainString() : null;
        if (decimalStr != null && decimalStr.indexOf(".") > 0) {
            decimalStr = decimalStr.replaceAll("0+?$", "");
            decimalStr = decimalStr.replaceAll("[.]$", "");
        }
        return decimalStr;
    }


//    public static void main(String[] args) {
//        long start = System.nanoTime();
//        System.out.println(new BigDecimal("6.9"));
//        System.out.println(new BigDecimal("0.000000006"));
//        System.out.println(new BigDecimal("0.000000006").toPlainString());
//        System.out.println(new BigDecimal(0.4));
//        System.out.println(new BigDecimal("0.4"));
//        System.out.println(divide(new BigDecimal("0.6"), new BigDecimal("0.4"), 4));
//        System.out.println(isEquals(new BigDecimal("12.345"), new BigDecimal("12.3450")));
//        System.out.println(isLessEqThan(new BigDecimal("12.345"), new BigDecimal("12.345")));
//        System.out.println(isLessThan(new BigDecimal("12.345"), new BigDecimal("12.3451")));
//        System.out.println(multiply(new BigDecimal(9.85), new BigDecimal(100)));
//        System.out.println(multiply(new BigDecimal("9.85"), new BigDecimal("100")));
//        Long one = 127L;//-126 - 127
//        Long two = 127L;
//        System.out.println(one == two);
//
//        Long one1 = new Long(-126L);
//        Long two1 = new Long(-126L);
//        System.out.println(one1 == two1);
//
//        Long one2 = 1312321321343L;
//        Long two2 = 1312321321343L;
//        System.out.println(one2 == two2);
//
//        Long one3 = new Long(1312321321343L);
//        Long two3 = new Long(1312321321343L);
//        System.out.println(one3 == two3);
//        //System.out.println(System.nanoTime() - start);
//        System.out.println("aaa.bbb.ccc".split("\\.").length);
//    }
}
