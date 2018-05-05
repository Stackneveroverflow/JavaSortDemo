import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Main
 *
 * @author Macer
 * @version V1.1
 * @date 2018/05/05 16:09
 */
public class Main {
    public static void main(String[] args) {
        Integer[] a = new Integer[10000000];
        Random rand = new Random(System.currentTimeMillis());
        // 伪随机数填充数组
        for (int i = 0; i < a.length; i++) {
            a[i] = rand.nextInt(99999999);
        }
        //        复制多组相同的原始伪随机序列
        Integer[][] aa = new Integer[10][a.length];
        for (int i = 0; i < aa.length; i++) {
//            System.arraycopy方法是native方法，比clone()更高效，Arrays.copyOf调用该方法
//            传入参数（源数组，源数组起始位置，目的数组，目的数组起始位置，复制长度）都是浅拷贝
            System.arraycopy(a, 0, aa[i], 0, a.length);
        }

        try (
                PrintWriter sortResult = new PrintWriter(new FileOutputStream("SortResult.txt"));
        ) {
            SortDemo.print(sortResult, aa);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
