/*
 Author : Ruel
 Problem : Baekjoon 17406번 배열 돌리기 4
 Problem address : https://www.acmicpc.net/problem/17406
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 배열돌리기4;

import java.util.*;

class Rotation {
    int r;
    int c;
    int s;

    public Rotation(int r, int c, int s) {
        this.r = r;
        this.c = c;
        this.s = s;
    }
}

public class Main {
    public static void main(String[] args) {
        // 구현문제
        // 어려운 알고리즘을 쓴다거나 하는 문제는 아니었지만 여러가지를 복합적으로 해야했다
        // 1. 좌표와 s가 주어질 때 배열을 시계방향으로 한칸씩 돌릴 수 있도록 구현한다.
        // 2. 배열의 각 행에서 열들의 합을 구하고, 이 합들 중 최소값을 배열 값이라고 하자
        // 3. 배열의 회전에 순서가 정해져있지 않다. 따라서 모든 순서로 배열을 회전시켜보고 이 때 배열의 값이 최소가 되는 값을 가져와야한다.(= 순열)
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();

        int[][] array = new int[n][m];      // 원래 배열의 입력을 받는다.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                array[i][j] = sc.nextInt();
        }

        List<Rotation> list = new ArrayList<>();        // 배열을 회전시키는 경우들을 입력 받는다.
        for (int i = 0; i < k; i++)
            list.add(new Rotation(sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt()));

        int answer = dfs(0, list, 0, array);        // 순열로 배열을 회전시키고, 그 중 최소 배열 값을 가져온다.
        System.out.println(answer);
    }

    static int[][] copyArray(int[][] array) {       // 원래 배열에 변경이 생기지 않도록, 복사해서 사용하자.
        int[][] temp = new int[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++)
                temp[i][j] = array[i][j];
        }
        return temp;
    }

    static int dfs(int turn, List<Rotation> list, int bitmask, int[][] array) {
        if (turn >= list.size())        // 모든 회전을 한번씩 사용했다면 배열 값을 구해 리턴하자.
            return calcMatrixValue(array);

        int minValue = Integer.MAX_VALUE;       // 현재 가지에서 최소 배열 값을 저장해두자.
        for (int i = 0; i < list.size(); i++) {
            if ((bitmask & (1 << i)) == 0) {        // 만약 i번째 회전을 사용하지 않았다면
                int[][] temp = copyArray(array);        // 배열을 복사해두고,
                rotate(list.get(i).r, list.get(i).c, list.get(i).s, temp);      // i번째 회전 요소를 사용해서 회전시키자.
                minValue = Math.min(minValue, dfs(turn + 1, list, (bitmask | (1 << i)), temp));     // bitmask에 i번째 회전을 사용했다고 표시하고 다음 턴으로 넘기자.
            }
        }
        return minValue;        // 자신이 할 수 있는 회전을 모두 사용했다면, 그 중 최소 배열 값인 minValue를 리턴해주자.
    }

    static int calcMatrixValue(int[][] array) {     // 배열 값을 구하는 메소드.
        int minValue = Integer.MAX_VALUE;
        for (int[] ints : array) {
            int sum = 0;
            for (int j = 0; j < ints.length; j++)
                sum += ints[j];
            minValue = Math.min(minValue, sum);
        }
        return minValue;
    }

    static void rotate(int r, int c, int s, int[][] array) {            // 배열을 회전시켜주는 메소드.
        for (int i = 1; i <= s; i++) {      // s값에 따라 몇 개의 둘레가 회전할지 바뀐다.
            int first = array[r - i][c - i];        // 해당하는 둘레의 가장 왼쪽, 가장 위쪽 값을 첫값으로 정하고 그 값을 저장해두자.

            for (int j = r - i; j < r + i; j++)     // 왼쪽 줄에 해당한다. 아래 값을 위로 올려주자.
                array[j][c - i] = array[j + 1][c - i];
            for (int j = c - i; j < c + i; j++)     // 아랫 줄에 해당한다. 오른쪽 값을 왼쪽으로 옮겨주자.
                array[r + i][j] = array[r + i][j + 1];
            for (int j = r + i; j > r - i; j--)     // 오른쪽 줄에 해당한다. 윗값을 아래쪽으로 내려주자.
                array[j][c + i] = array[j - 1][c + i];
            for (int j = c + i; j > c - i + 1; j--)     // 윗 줄에 해당한다. 왼쪽 값을 오른쪽으로 옮겨주자.
                array[r - i][j] = array[r - i][j - 1];

            array[r - i][c - i + 1] = first;        // 첫 위치로 잡았던 곳에는 원래의 첫 값이 아니라 바로 아랫값이 들어있다. 따라서 처음 따로 빼두었던 first 값을 array[r - i][c - i + 1]에 넣어주자.
        }
    }
}