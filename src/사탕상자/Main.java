/*
 Author : Ruel
 Problem : Baekjoon 2243번 사탕상자
 Problem address : https://www.acmicpc.net/problem/2243
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 사탕상자;

import java.util.Scanner;

public class Main {
    static int[] fenwickTree;
    static final int varietyTastes = 1_000_001;

    public static void main(String[] args) {
        // 세그먼트 트리 + 이분탐색 문제!
        // 펜윅트리 덕분에 코드는 간단하게 나왔지만, 두 개의 알고리즘이 섞인 문제!
        // 펜윅 트리를 이용해서 해당 맛까지의 몇 개의 사탕이 있는지 기록해둔다
        // 그리고 그 몇 번째를 찾는 데까지 이분탐색을 이용한다!
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        fenwickTree = new int[varietyTastes];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int a = sc.nextInt();
            if (a == 1) {
                int nth = sc.nextInt();
                int taste = findNthCandy(nth, 1, varietyTastes);
                sb.append(taste).append("\n");
                inputCandy(taste, -1);
            } else if (a == 2)
                inputCandy(sc.nextInt(), sc.nextInt());
        }
        System.out.println(sb);
    }

    // 펜윅 트리에 저장된 사탕의 개수를 이분 탐색하여 nth번째 사탕의 맛을 알려주는 메소드
    static int findNthCandy(int nth, int start, int end) {
        while (start < end) {
            int middle = (start + end) / 2;
            if (getCandyQuantity(middle) < nth)
                start = middle + 1;
            else
                end = middle;
        }
        return start;       // nth 사탕이 속한 맛
    }

    static void inputCandy(int taste, int quantity) {   // 사탕을 넣거나 빼는 메소드
        while (taste < fenwickTree.length) {        // 펜윅 트리기 때문에 이후의 값들에게 영향을 미친다
            fenwickTree[taste] += quantity;
            taste += (taste & -taste);
        }
    }

    static int getCandyQuantity(int taste) {        // 해당하는 맛 까지의 누적된 사탕의 개수를 알려줄 메소드
        int sum = 0;
        while (taste > 0) {
            sum += fenwickTree[taste];
            taste -= (taste & -taste);
        }
        return sum;
    }
}