package other;

/*
打印成 M 型：
   012345678
-----------------
0 |  3   7
1 | 2 4 6 8
2 |1   5   9

平面图形 （使用二位数组解决）
 */
public class Test {

    public static void main(String[] args) {
        int num = 23;
        int height = num / 4 + 1; // M 型有 4 条边，所以除以 4。但为什么是加 1 而不是向上取整呢？
        int width = num;
        int[][] a = new int[height][width];

        int x = height - 1;
        int y = 0;
        boolean order = true;

        for (int i = 1; i <= width; i++) {
            a[x][y] = i;
            y++;

            if (order) {
                x++;
            } else {
                x--;
            }

            if (x < 0) {
                x += 2;
                order = true;
            }
            if (x > height - 1) {
                x -= 2;
                order = false;
            }
        }

        int digits = digits(num);
        for (int k = 0; k < a.length; k++) {
            for (int l = 0; l < a[k].length; l++) {
                if (a[k][l] == 0) {
                    System.out.printf("%" + digits + "s", ' ');
                } else {
                    System.out.printf("%" + digits + "d", a[k][l]);
                }
            }
            System.out.println();
        }
    }

    private static int digits(int num) {
        int cnt = 0;
        while (num != 0) {
            cnt++;
            num /= 10;
        }
        return cnt;
    }
}
