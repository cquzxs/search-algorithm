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
     * 顺序查找
     *
     * @param a 待查找数组
     * @param value 查找值
     * @return 若找到，返回值的索引；若未找到，返回-1
     */
    public static int sequenceSearch(int[] a, int value){
        int res = -1;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < a.length; i++) {
            if(a[i] == value){
                res = i;
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("顺序查找花费时间：" + (endTime - startTime) + "ms\t\t\t数组大小：" + a.length + "\t\t\t查找结果：" + value + "->" + (res > -1 ? a[res]:-1));
        return res;
    }

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
            //int midIndex = (leftIndex + rightIndex) / 2;
            int midIndex = leftIndex + (rightIndex - leftIndex)/2;
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
        System.out.println("二分查找花费时间：" + (endTime - startTime) + "ms\t\t\t数组大小：" + a.length + "\t\t\t查找结果：" + value + "->" + (res > -1 ? a[res]:-1));
        return res;
    }

    /**
     * 插值查找, 插值查找是二分查找的优化
     * midIndex = (leftIndex + rightIndex) / 2 即 midIndex = leftIndex + 1 / 2 * （rightIndex - leftIndex）
     * 针对1/2做如下改进 1/2 -》 (value-a[leftIndex])/(a[rightIndex]-a[leftIndex])
     * 查找值越接近于最小值，1/2越趋近于0；查找值越接近于最大值，1/2越趋近于1
     *
     * @param a          待查找数组（升序）
     * @param leftIndex  查找范围左索引
     * @param rightIndex 查找范围右索引
     * @param value      查找值
     * @return 若找到，返回值的索引；若未找到，返回-1
     */
    public static int insertSearch(int[] a, int leftIndex, int rightIndex, int value){
        int res = -1;
        long startTime = System.currentTimeMillis();
        while (leftIndex <= rightIndex) {
            int midIndex = leftIndex + (value-a[leftIndex])/(a[rightIndex]-a[leftIndex])*(rightIndex-leftIndex);
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
        System.out.println("插值查找花费时间：" + (endTime - startTime) + "ms\t\t\t数组大小：" + a.length + "\t\t\t查找结果：" + value + "->" + (res > -1 ? a[res]:-1));
        return res;
    }

    public static void main(String[] args) {
        int length = 10000000;
        int[][] a = new int[8][length];//8种排序算法的初始数据
        for (int i = 0; i < length; i++) {
            a[0][i] = (new Double(Math.random() * length)).intValue();
            for (int j = 1; j < 8; j++) {
                a[j][i] = a[0][i];
            }
        }
        SortUtil.heapSort(a[1], length);
        SortUtil.heapSort(a[2], length);
        int value = (new Double(Math.random() * length)).intValue();
        //sequenceSearch(a[0], value);
        binarySearch(a[1], 0, length-1, value);
        insertSearch(a[2], 0, length-1, value);
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
