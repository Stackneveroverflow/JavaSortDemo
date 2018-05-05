/*
 *　　　　　　　 ┏┓      ┏┓+ +
 *           ┏━┛┻━━━━━━┛┻━┓ + +
 *           ┃　　　　　　　┃ + + +
 *           ┃     ━      ┃ + + + +
 *           ┃ ████━████  ┃
 *           ┃            ┃ + +
 *           ┃     ┻      ┃
 *           ┃            ┃ +
 *           ┗━━┓      ┏━━┛
 *              ┃      ┃
 *              ┃      ┃ + + + +
 *              ┃      ┃　　　　 Code is far away from bug with the animal protecting
 *              ┃      ┃ + 　　　　 神兽保佑,代码无bug
 *              ┃      ┃
 *              ┃      ┃　　+
 *              ┃      ┗━━━━━━━┓ + +
 *              ┃              ┣━┓
 *              ┃              ┏━┛
 *              ┗━┓ ┓ ┏━━┳━┓ ┏━┛ + + + +
 *                ┃ ┫ ┫  ┃ ┫ ┫
 *                ┗━┻━┛  ┗━┻━┛+ + + +
 */

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * 多种排序算法集合
 * 为什么快排在实际使用中通常优于堆排序？
 * 局部性原理，越靠近的数据比较和交换用时越少，而且CPU的Cache中缓存了最近频繁读写的数据。
 * 在堆排中，每一个操作都是不利于程序的局部性原理的，每次元素间的比较、数的调整等，
 * 最大堆调整每次都从堆底拿元素换到堆顶然后又下滤下去，在堆底一般都比较小，很大可能再次下滤到底层，
 * 这个过程做了很多无用功，需要不断地跨度大地读写数据。
 * 反观快速排序和归并排序，利用分而治之的方法，元素间的比较都在某个段内，操作比较集中，局部性相当好。
 * <p>
 * 快排<归并<堆排<希尔<<<插入<选择<冒泡
 *
 * @author Macer
 * @version V1.1
 * @date 2018/04/30 23:08
 */
public class SortDemo {
    /**
     * timer 计时用
     */
    private static long timer;

    /**
     * Simple insertion sort 直接插入排序
     * 每步将一个待排序的元素，按大小插入前面已经排序的序列中适当位置上，直到全部插入完为止
     * 时间复杂度O(n^2)，空间复杂度O(1)，稳定
     * 适用场合：数据量不大，对算法的稳定性有要求，且数据局部或者整体有序的情况，小规模如10个数平均时间最快，可以在快排中调用
     *
     * @param a   an array of Comparable items.
     * @param <T> 实现Comparable接口的类
     */
    public static <T extends Comparable<? super T>> void insertionSort(T[] a) {
        long start = System.currentTimeMillis();
        int j;
        T tmp;
//        从第二个元素开始，向前试探性插入
        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(a[i - 1]) < 0) {
//                将当前元素暂存
                tmp = a[i];
//                向前比较，若符合要求则将比较项往右移存
                for (j = i; j > 0 && tmp.compareTo(a[j - 1]) < 0; j--) {
                    a[j] = a[j - 1];
                }
//                插入
                a[j] = tmp;
            }
        }
        timer = System.currentTimeMillis() - start;
    }

    /**
     * ShellSort 希尔排序
     * 是插入排序的一种又称"缩小增量排序"（Diminishing Increment Sort）
     * 使用简单的希尔增量序列，复杂的增量序列中Sedgewick 增量序列最坏时间复杂度达到O(n^1.3)
     * 时间复杂度取决于增量序列，空间复杂度O(1)，不稳定！！！
     * 适用场合：数据量较大，不要求稳定性
     *
     * @param a   an array of Comparable items.
     * @param <T> 实现Comparable接口的类
     */
    public static <T extends Comparable<? super T>> void shellSort(T[] a) {
        long start = System.currentTimeMillis();
        T tmp;
        int j;
//        使用希尔增量序列
        for (int gap = a.length / 2; gap > 0; gap /= 2) {
//            里层用的是直接插入排序算法
            for (int i = gap; i < a.length; i++) {
                if (a[i].compareTo(a[i - gap]) < 0) {
                    tmp = a[i];
                    for (j = i; j >= gap && tmp.compareTo(a[j - gap]) < 0; j -= gap) {
                        a[j] = a[j - gap];
                    }
                    a[j] = tmp;
                }
            }
        }
        timer = System.currentTimeMillis() - start;
    }

    /**
     * SelectionSort 直接选择排序
     * 每一次从待排序的数据元素中选出最小的一个元素，存放在序列的起始位置，直到全部待排序的数据元素排完
     * 时间复杂度O(n^2)，空间复杂度O(1)，不稳定
     * 适用场合：当数据量不大，且对稳定性没有要求的时候
     *
     * @param a   an array of Comparable items.
     * @param <T> 实现Comparable接口的类
     */
    public static <T extends Comparable<? super T>> void selectionSort(T[] a) {
        long start = System.currentTimeMillis();
//        第 i+1 个最小元素位置标记
        int minIndex;
        T tmp;
        for (int i = 0; i < a.length; i++) {
            minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
//                比标记的最小元素还小，则将标记让出
                if (a[j].compareTo(a[minIndex]) < 0) {
                    minIndex = j;
                }
            }
//            如果标记位置改变，则将第 i+1 个最小元素交换到标记位置
            if (i != minIndex) {
                tmp = a[i];
                a[i] = a[minIndex];
                a[minIndex] = tmp;
            }
        }
        timer = System.currentTimeMillis() - start;
    }

    /**
     * HeapSort 堆排序 升序 用最大堆
     * 它是选择排序的一种。可以利用数组的特点快速定位指定索引的元素。
     * 时间复杂度O(n log n)，空间复杂度O(1)，不稳定
     * 适用场合：数据量较大，且对稳定性无要求，不如用快排或归并，但取前几个最值时可以不用排序完，奇效
     *
     * @param a   an array of Comparable items.
     * @param <T> 实现Comparable接口的类
     */
    public static <T extends Comparable<? super T>> void heapSort(T[] a) {
        long start = System.currentTimeMillis();
        T tmp;
//        从第一个非叶子节点开始下滤，顺序是从下至上，从右至左
        for (int i = a.length / 2 - 1; i >= 0; i--) {
            percDown(a, i, a.length);
        }
//        将堆顶（第k个最大元素）交换至 a.length - k 处
        for (int i = a.length - 1; i > 0; i--) {
            tmp = a[i];
            a[i] = a[0];
            a[0] = tmp;
//            换上来的元素再下滤下去，第k+1个最大值在堆顶的左或右子树，或堆顶本身
//            长度为i，不再包括已排好的元素
            percDown(a, 0, i);
        }
        timer = System.currentTimeMillis() - start;
    }

    /**
     * percolate down 最大堆的下滤操作
     *
     * @param a      an array of Comparable items.
     * @param i      传入的元素索引
     * @param length 堆的大小
     * @param <T>    实现Comparable接口的类
     */
    private static <T extends Comparable<? super T>> void percDown(T[] a, int i, int length) {
//        暂存当前元素
        T tmp = a[i];
//        左子树为2 * i + 1，右子树为2 * i + 2
//        找到存放有更大元素的子树作为下滤口
        for (int child = 2 * i + 1; child < length; child = 2 * child + 1) {
//            如果存在右子树（即不是堆的最后一个元素）并且右子树的元素比左子树更大，则选择右子树作为下滤口
            if (child != length - 1 && a[child].compareTo(a[child + 1]) < 0) {
                child++;
            }
//            如果下滤口的元素比父节点的元素更大，则往父节点插入，否则下滤到底，整个循环结束
            if (tmp.compareTo(a[child]) < 0) {
                a[i] = a[child];
                i = child;
            } else {
                break;
            }
        }
//        将暂存元素取出放在最后找到的底，可能没进行下滤，底就是它自身的位置
        a[i] = tmp;
    }

    /**
     * BubbleSort 冒泡排序
     * 重复地走访过要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来
     * 时间复杂度O(n^2)，空间复杂度O(1)，稳定
     * 适用场合：数据量量不大，对稳定性有要求，且数据基本有序的情况下
     *
     * @param a   an array of Comparable items.
     * @param <T> 实现Comparable接口的类
     */
    public static <T extends Comparable<? super T>> void bubbleSort(T[] a) {
        long start = System.currentTimeMillis();
//        外层循环，i来控制内层循环的深度，每轮将第k个最大元素冒泡至a.length - k的位置后，内循环不再触碰这些位置
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j].compareTo(a[j + 1]) > 0) {
                    T tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
        timer = System.currentTimeMillis() - start;
    }

    /**
     * 将T tmp声明在循环外的冒泡排序，证明多次循环中局部变量的分配内存和释放内存是很大的开销
     *
     * @param a   an array of Comparable items.
     * @param <T> 实现Comparable接口的类
     */
    public static <T extends Comparable<? super T>> void bubbleSort2(T[] a) {
        long start = System.currentTimeMillis();
//        将tmp的声明放在循环外，只需分配一次内存，释放一次内存
        T tmp;
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j].compareTo(a[j + 1]) > 0) {
                    tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
        timer = System.currentTimeMillis() - start;
    }

    /**
     * QuickSort 快速排序的驱动程序
     *
     * @param a   an array of Comparable items.
     * @param <T> 实现Comparable接口的类
     */
    public static <T extends Comparable<? super T>> void quickSort(T[] a) {
        long start = System.currentTimeMillis();
        quickSort(a, 0, a.length - 1);
        timer = System.currentTimeMillis() - start;
    }

    /**
     * QuickSort 快速排序
     * 是对冒泡排序的一种改进。通过一趟排序将要排序的数据分割成独立的两部分，
     * 其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对
     * 这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
     * 时间复杂度O(n log n)，空间复杂度O(n log n)，不稳定，选好枢纽元是使坏情况发生的关键
     * 截断范围选10比较合适，进入截断范围改用小规模高效率算法对小片段排序
     * 适用场合：数值范围较大，相同值的概率较小，数据量大且不考虑稳定性的情况，数值远大于数据量时威力更大
     *
     * @param a     an array of Comparable items.
     * @param <T>   实现Comparable接口的类
     * @param left  左起始位置
     * @param right 右起始位置
     */
    private static <T extends Comparable<? super T>> void quickSort(T[] a, int left, int right) {
//        进入截断值范围内，该用插入排序算法处理
        final int cutoff = 10;
        if (left + cutoff <= right) {
            T tmp;
//            pivot枢纽元，调用三数中值分割法产生，放在left - 1位置
            T pivot = median3(a, left, right);
            int i = left;
            int j = right - 1;
            for (; ; ) {
//                左标i跃过小于枢纽元的元素到达大于枢纽元的元素位置，右标j反之
                while (a[++i].compareTo(pivot) <= 0 && i < right) ;
                while (a[--j].compareTo(pivot) >= 0 && j > left) ;
//                如果游标碰撞，说明分组完成，结束循环，否则交换两个错位元素
                if (i >= j) break;
                tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            }
//            将枢纽元放回中间位置
            tmp = a[i];
            a[i] = a[right - 1];
            a[right - 1] = tmp;
//            左右分区进行递归
            quickSort(a, left, i - 1);
            quickSort(a, i + 1, right);
        } else {
            int j;
            T tmp;
//            直接插入排序，操作数组的left到right段
            for (int i = left + 1; i <= right; i++) {
                if (a[i].compareTo(a[i - 1]) < 0) {
                    tmp = a[i];
//                    j要大于left而不是0
                    for (j = i; j > left && tmp.compareTo(a[j - 1]) < 0; j--) {
                        a[j] = a[j - 1];
                    }
                    a[j] = tmp;
                }
            }
        }
    }

    /**
     * Median3 三数中值分割法
     * 选出左端、右端和中心位置上的三个元素的中值作为枢纽元
     *
     * @param a     an array of Comparable items.
     * @param left  左起始位置
     * @param right 右起始位置
     * @param <T>   实现Comparable接口的类
     * @return 返回pivot枢纽元
     */
    private static <T extends Comparable<? super T>> T median3(T[] a, int left, int right) {
        int center = (left + right) / 2;
        T tmp;
        if (a[center].compareTo(a[left]) < 0) {
            tmp = a[center];
            a[center] = a[left];
            a[left] = tmp;
        }
        if (a[left].compareTo(a[right]) > 0) {
            tmp = a[right];
            a[right] = a[left];
            a[left] = tmp;
        }
        if (a[right].compareTo(a[center]) < 0) {
            tmp = a[right];
            a[right] = a[center];
            a[center] = tmp;
        }
//        a[left]已经确认过比a[center]大，所以直接排在a[center]后面
        tmp = a[center];
        a[center] = a[right - 1];
        a[right - 1] = tmp;
        return a[right - 1];
    }

    /**
     * MergeSort 归并排序的驱动程序
     *
     * @param a   an array of Comparable items.
     * @param <T> 实现Comparable接口的类
     */
    public static <T extends Comparable<? super T>> void mergeSort(T[] a) {
        long start = System.currentTimeMillis();
//        泛型不能直接实例化，可以new一个实现了接口的数组，再强制转型
        T[] tmpA = (T[]) new Comparable[a.length];
        mergeSort(a, tmpA, 0, a.length - 1);
        timer = System.currentTimeMillis() - start;
    }

    /**
     * MergeSort 归并排序 比较次数最少，Arrays.sort()调用该算法
     * 时间复杂度O(n log n)，空间复杂度O(n)，稳定！！！
     * 适用场合：数据规模较大，对稳定性有要求，一般用于总体无序，但是各子项相对有序的数列
     *
     * @param a     an array of Comparable items.
     * @param tmpA  存放归并结果的临时数组
     * @param left  the left-most index of the subarray
     * @param right the right-most index of the subarray
     * @param <T>   实现Comparable接口的类
     */
    private static <T extends Comparable<? super T>> void mergeSort(T[] a, T[] tmpA, int left, int right) {
        if (left < right) {
//            分
            int center = (right + left) / 2;
            mergeSort(a, tmpA, left, center);
            mergeSort(a, tmpA, center + 1, right);
//            治
            merge(a, tmpA, left, center + 1, right);
        }
    }

    /**
     * merge方法，归并操作
     *
     * @param a        an array of Comparable items.
     * @param tmpA     存放归并结果的临时数组
     * @param leftPos  the left-most index of the subarray
     * @param rightPos the index of the start of the second half
     * @param rightEnd the right-most index of the subarray
     * @param <T>      实现Comparable接口的类
     */
    private static <T extends Comparable<? super T>> void merge(T[] a, T[] tmpA, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;
//        两组升序序列比较把小的先放进临时数组里，直到有一组取完
        while (leftPos <= leftEnd && rightPos <= rightEnd) {
//            想要保持排序稳定性，这里要大于等于
            if (a[leftPos].compareTo(a[rightPos]) <= 0) {
                tmpA[tmpPos++] = a[leftPos++];
            } else {
                tmpA[tmpPos++] = a[rightPos++];
            }
        }
//        左数组剩余的元素复制到临时数组后面
        while (leftPos <= leftEnd) {
            tmpA[tmpPos++] = a[leftPos++];
        }
//        右数组剩余的元素复制到临时数组后面
        while (rightPos <= rightEnd) {
            tmpA[tmpPos++] = a[rightPos++];
        }
//        起始位置注意不能用leftPos，因为leftPos已经改变了
        System.arraycopy(tmpA, rightEnd - numElements + 1, a, rightEnd - numElements + 1, numElements);
    }

    /**
     * countingRadixSort 计数基数排序 线性时间复杂度
     * 基数排序（radix sort）属于“分配式排序”（distribution sort），又称“桶子法”（bucket sort）
     * 最高位优先法 MSD 和最低位优先 LSD，d个关键码，n个数据，r是基数
     * LSD的基数排序适用于位数小的数列，如果位数多的话，使用MSD的效率会比较好。MSD的方式与LSD相反，
     * 是由高位数为基底开始进行分配，但在分配之后并不马上合并回一个数组中，而是在每个“桶子”中建立“子桶”，
     * 将每个桶子中的数值按照下一数位的值分配到“子桶”中。在进行完最低位数的分配后再合并回单一的数组中。
     * 时间复杂度O(d(r+n))，空间复杂度O(rd+n)，稳定
     * 适用场合：数值都是非负整数，且范围较小的情况，尤其适用于256位的ASCII码，用作字符串的字典顺序排序
     *
     * @param a 数组
     * @param d 关键码个数
     */
    public static void radixSort(Integer[] a, int d) {
        long start = System.currentTimeMillis();
//        10进制
        final int bucketNum = 10;
//        计数标记
        int counter = 0;
//        10^(位数-1)
        int num = 1;
        int[][] bucket = new int[bucketNum][a.length];
//        存放顺序的数组，数组order[i]用来表示该位是i的数的个数
        int[] order = new int[bucketNum];
        for (int n = 1; n <= d; num *= bucketNum, n++) {
            for (int i = 0; i < a.length; i++) {
//                最小优先法LSD，remainder余数
                int rem = ((a[i] / num) % bucketNum);
                bucket[rem][order[rem]] = a[i];
                order[rem]++;
            }
            for (int i = 0; i < bucketNum; i++) {
                if (order[i] != 0)
                    for (int j = 0; j < order[i]; j++) {
                        a[counter] = bucket[i][j];
                        counter++;
                    }
                order[i] = 0;
            }
            counter = 0;
        }
        timer = System.currentTimeMillis() - start;
    }

    /**
     * 外部排序 用了归并算法的思路
     * 在主存中最大限度地创建三块等长的空间，将辅存中的数据分割成该长度的小片段，第一次导入主存时，将两个小片段各自排序
     * 然后两个小片段就可以归并了，第三块空间存放结果，分两次向辅存输回结果。之后数据一边进来，一边归并，一边传输结果回去
     */

    /**
     * print 输出结果
     *
     * @param sortResult 文件对象
     * @param aa         二维数组
     */
    public static void print(PrintWriter sortResult, Integer[][] aa) {
//去掉注释可用，建议数据规模很大时，不要序列输出，把拖时间的三大低效率去掉
//        sortResult.println("原始序列：");
//        for (Integer i : aa[0]) {
//            sortResult.write(i + " ");
//        }
//        insertionSort(aa[0]);
//        sortResult.println("\n\n直接插入排序");
//        printResult(sortResult, aa[0]);

        shellSort(aa[1]);
        sortResult.println("\n\n希尔排序");
        printResult(sortResult, aa[1]);

//        selectionSort(aa[2]);
//        sortResult.println("\n\n直接选择排序");
//        printResult(sortResult, aa[2]);

        heapSort(aa[3]);
        sortResult.println("\n\n堆排序");
        printResult(sortResult, aa[3]);

//        bubbleSort(aa[4]);
//        sortResult.println("\n\n冒泡排序");
//        printResult(sortResult, aa[4]);
//
//        bubbleSort2(aa[5]);
//        sortResult.println("\n\n优化的冒泡排序");
//        printResult(sortResult, aa[5]);

        quickSort(aa[6]);
        sortResult.println("\n\n快速排序");
        printResult(sortResult, aa[6]);

        mergeSort(aa[7]);
        sortResult.println("\n\n归并排序");
        printResult(sortResult, aa[7]);

        radixSort(aa[8], 6);
        sortResult.println("\n\n基数排序");
        printResult(sortResult, aa[8]);

//        Arrays.sort()方法
        long start = System.currentTimeMillis();
        Arrays.sort(aa[9]);
        timer = System.currentTimeMillis() - start;
        System.out.println(timer);
    }

    /**
     * printResult print的子函数
     *
     * @param sortResult 文件对象
     * @param a          数组
     */
    private static void printResult(PrintWriter sortResult, Integer[] a) {
//        sortResult.write("升序序列:");
//        for (Integer i : a) {
//            sortResult.write(i + " ");
//        }
        sortResult.println("\n耗时：" + timer);
    }
}