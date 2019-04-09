package com.zxs.ssh.template.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param a     待查找数组
     * @param value 查找值
     * @return 若找到，返回值的索引；若未找到，返回-1
     */
    public static int sequenceSearch(int[] a, int value) {
        int res = -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == value) {
                res = i;
                break;
            }
        }
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
        while (leftIndex <= rightIndex) {
            //int midIndex = (leftIndex + rightIndex) / 2;
            int midIndex = leftIndex + (rightIndex - leftIndex) / 2;
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
    public static int insertSearch(int[] a, int leftIndex, int rightIndex, int value) {
        int res = -1;
        while (leftIndex <= rightIndex) {
            int midIndex = leftIndex + (value - a[leftIndex]) / (a[rightIndex] - a[leftIndex]) * (rightIndex - leftIndex);
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
        return res;
    }

    /**
     * 斐波那契查找
     * midIndex = leftIndex + f[k-1] - 1
     * 左边：n = rightIndex - leftIndex + 1 = midIndex - 1 - leftIndex + 1
     * = leftIndex + f[k-1] - 1 - leftIndex = f[k-1] - 1 所以 k = k -1
     * 右边：n = rightIndex - leftIndex + 1 = rightIndex - (midIndex + 1) + 1
     * = rightIndex - (leftIndex + f[k-1] - 1) = rightIndex - leftIndex + 1 - f[k-1] = f(k) -1 - f[k-1] = f(k-2)-1 所以 k = k-2
     *
     * @param a          待查找数组（升序）
     * @param leftIndex  查找范围左索引
     * @param rightIndex 查找范围右索引
     * @param value      查找值
     * @return 若找到，返回值的索引；若未找到，返回-1
     */
    public static int fibonacciSearch(int[] a, int leftIndex, int rightIndex, int value) {
        int res = -1;
        int[] f = fibonacci(45);  // 构造一个斐波那契数组
        int n = a.length;
        int k = 0;
        while (n > f[k] - 1) { // 计算n位于斐波那契数列的位置
            k++;
        }
        int[] temp = new int[f[k] - 1];  // 将数组a扩展到F[k]-1的长度
        for (int i = 0; i < f[k] - 1; i++) {
            if (i >= n - 1) {
                temp[i] = a[n - 1];
            } else {
                temp[i] = a[i];
            }
        }
        while (leftIndex <= rightIndex) {
            int midIndex = leftIndex + f[k - 1] - 1;
            if (temp[midIndex] == value) {
                res = midIndex;
                if (res > n - 1) {
                    res = n - 1;
                }
                break;
            }
            if (temp[midIndex] > value) {
                rightIndex = midIndex - 1;
                k = k - 1;
            }
            if (temp[midIndex] < value) {
                leftIndex = midIndex + 1;
                k = k - 2;
            }
        }
        return res;
    }

    /**
     * 获取斐波那契数组
     * fibonacci(45)[44] = 11 3490 3170
     *
     * @param size 数组大小
     * @return 斐波那契数组
     */
    private static int[] fibonacci(int size) {
        int[] res = new int[size];
        res[0] = 1;
        res[1] = 1;
        for (int i = 2; i < size; i++) {
            res[i] = res[i - 1] + res[i - 2];
        }
        return res;
    }

    /**
     * 分块查找
     * 将n个数据元素"按块有序"划分为m块（m ≤ n）。每一块中的结点不必有序，但块与块之间必须"按块有序"；
     * 即第1块中任一元素的关键字都必须小于第2块中任一元素的关键字；而第2块中任一元素又都必须小于第3块中的任一元素
     *
     * @param a     待查找数组（升序）
     * @param value 查找值
     * @return 若找到，返回值的索引；若未找到，返回-1
     */
    public static int blockSearch(int[] a, int value) {
        int res = -1;
        //建立索引表
        int indexSize = a.length % 10000 == 0 ? a.length / 10000 : a.length / 10000 + 1;
        int[] index = new int[indexSize];
        for (int i = 0; i < index.length; i++) {
            int temp = (i + 1) * 9999 > a.length - 1 ? a.length - 1 : (i + 1) * 9999;
            index[i] = a[temp];
        }
        //查找索引表
        int block = 0;
        for (int i = 0; i < index.length; i++) {
            if (value <= index[i]) {
                block = i;
                break;
            }
        }
        //查找块
        for (int i = block * 10000; i < (block + 1) * 10000 && i < a.length; i++) {
            if (a[i] == value) {
                res = i;
                break;
            }
        }
        return res;
    }
    /**
     * 哈希查找
     *
     * @param a     待查找数组（升序）
     * @param value 查找值
     * @return 若找到，返回值的索引；若未找到，返回-1
     */
    public static int hashSearch(int[] a, int value) {
        int res = -1;
        //构造哈希表 1.hash函数-》m = m%prime ; 2.冲突解决-》拉链法
        int prime = 9999;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            int temp = a[i] % prime;
            List<Integer> list;
            if(map.containsKey(temp)){
                list = map.get(temp);
            }else{
                list = new ArrayList<>();
            }
            list.add(i);
            map.put(temp,list);
        }
        //查找哈希表
        List<Integer> list = map.get(value%prime);
        for (int i = 0; i < list.size(); i++) {
            if(a[list.get(i)] == value){
                res = list.get(i);
                break;
            }
        }
        return res;
    }
    /**
     * 树表查找
     *
     * @param a     待查找数组（升序）
     * @param value 查找值
     * @return 若找到，返回值的索引；若未找到，返回-1
     */
    public static int treeSearch(int[] a, int value) {
        int res = -1;
        BinarySearchTree root = buildTree(a);
        res = searchTree(root,value);
        return res;
    }

    private static int searchTree(BinarySearchTree root,int value) {
        if(root == null){
            return -1;
        }
        if(value == root.value){
            return root.index;
        }else if(value < root.value){
            return searchTree(root.left,value);
        }else{
            return searchTree(root.right,value);
        }
    }

    private static BinarySearchTree buildTree(int[] a) {
        BinarySearchTree root = new BinarySearchTree(a[0],0);
        for (int i = 1; i < a.length; i++) {
            root.addNode(new BinarySearchTree(a[i],i));
        }
        return root;
    }

    private static class BinarySearchTree{
        int value;
        int index;
        BinarySearchTree left;
        BinarySearchTree right;
        BinarySearchTree(int value1, int index1){
            value = value1;
            index = index1;
        }
        void addNode(BinarySearchTree node){
            if(node.value <= value){
                if(left != null){
                    left.addNode(node);
                }else{
                    left = node;
                }
            }else{
                if(right != null){
                    right.addNode(node);
                }else{
                    right = node;
                }
            }
        }
    }

    public static void main(String[] args) {
        OriginData originData = buildOriginData();

        printExecuteTime(getMicroTime(),sequenceSearch(originData.a[0],originData.value),"顺序查找");
        printExecuteTime(getMicroTime(),binarySearch(originData.a[1], 0, originData.length - 1,originData.value),"二分查找");
        printExecuteTime(getMicroTime(),insertSearch(originData.a[2], 0, originData.length - 1,originData.value),"插值查找");
        printExecuteTime(getMicroTime(),fibonacciSearch(originData.a[3], 0, originData.length - 1,originData.value),"斐波那契查找");
        printExecuteTime(getMicroTime(),blockSearch(originData.a[4],originData.value),"分块查找");
        printExecuteTime(getMicroTime(),hashSearch(originData.a[5],originData.value),"哈希查找");
        printExecuteTime(getMicroTime(),treeSearch(originData.a[6],originData.value),"树表查找");
    }

    private static OriginData buildOriginData() {
        OriginData originData = new OriginData();
        int length = 20000;
        int[][] a = new int[8][length];//8种排序算法的初始数据
        for (int i = 0; i < length; i++) {
            a[0][i] = (new Double(Math.random() * length)).intValue();
        }
        SortUtil.heapSort(a[0], length);
        for (int i = 0; i < length; i++) {
            for (int j = 1; j < 8; j++) {
                a[j][i] = a[0][i];
            }
        }
        int value = (new Double(Math.random() * length)).intValue();
        originData.a = a;
        originData.length = length;
        originData.value = value;
        return originData;
    }

    private static class OriginData {
        int[][] a;  //初始数组
        int length; //数组长度
        int value;  //随机查找值
    }

    /**
     * 打印方法执行时间
     *
     * @param startTime 开始时间
     * @param result    执行方法
     * @param title     标题
     */
    private static void printExecuteTime(long startTime, int result, String title) {
        long endTime = getMicroTime();
        System.out.println("【"+title + "】执行时间：" + (endTime - startTime) + "微秒  result="+result);
    }

    /**
     * 获取微妙
     *
     * @return 微秒
     */
    private static long getMicroTime() {
        long time1 = System.currentTimeMillis() * 1000000;
        long time2 = System.nanoTime() % 1000000;
        return (time1 + time2) / 1000;
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
