package com.zxs.ssh.template.util;

/**
 * Project Name:search-algorithm
 * File Name:SearchUtil
 * Package Name:com.zxs.ssh.template.util
 * Date:2018/11/30
 * Author:zengxueshan
 * Description:查找算法
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */


public class SearchUtil {

    /**
     * 二分查找
     *
     * @param a          待查找数组（升序）
     * @param leftIndex  查找范围左索引
     * @param rightIndex 查找范围右索引
     * @param value      查找值
     * @return 若找到，返回值的索引；若未找到，返回-1
     */
    public static int binarySearch(int[] a, int leftIndex, int rightIndex, int value) {
        int res = -1;
        long startTime = System.currentTimeMillis();
        while (leftIndex <= rightIndex) {
            int midIndex = (leftIndex + rightIndex) / 2;
            if (a[midIndex] == value) {
                res = midIndex;
                break;
            }
            if (a[midIndex] > value) {
                rightIndex = midIndex - 1;
            }
            if (a[midIndex] < value) {
                leftIndex = midIndex + 1;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("二分查找花费时间：" + (endTime - startTime) + "ms\t\t\t数组大小：" + a.length + "\t\t\t查找结果(索引值)：" + res);
        return res;
    }

    public static void main(String[] args) {
        int length = 10000000;
        int[][] a = new int[8][length];//8种排序算法的初始数据
        for (int i = 0; i < length; i++) {
            a[0][i] = (new Double(Math.random() * 100)).intValue();
            for (int j = 1; j < 8; j++) {
                a[j][i] = a[0][i];
            }
        }
        SortUtil.radixSort(a[0], length);
        //printArr(a[0]);
        binarySearch(a[0], 0, length, (new Double(Math.random() * 100)).intValue());
    }

    /**
     * 打印数组
     *
     * @param a 数组
     */
    private static void printArr(int[] a) {
        String res = "";
        for (int i = 0; i < a.length; i++) {
            res = res + a[i] + "\t";
        }
        System.out.println(res);
    }
}
