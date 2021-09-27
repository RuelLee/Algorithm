/*
 Author : Ruel
 Problem : Baekjoon 3653번 영화 수집
 Problem address : https://www.acmicpc.net/problem/3653
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 영화수집;

import java.util.Scanner;

public class Main {
    static int[] fenwickTree;
    static int[] movieLocation;
    static int topCounter;

    public static void main(String[] args) {
        // 세그먼트 트리 문제
        // 처음 문제를 봤을 때는 왜 세그먼트 트리로 풀어야할지 감이 잡히지 않는다
        // 하지만 고민하다보면 자신보다 위에 있는 책들을 세기 위해 누적합을 이용하면 될 것이라는 생각이 닿고, 세그먼트 트리를 활용할 수 있다는 생각이 든다
        // 총 n개의 영화가 있고, m번 영화를 보므로,
        // fenwick 트리로 0 ~  m번까지는 본 영화가 놓여질 공간, m+1 ~ n + m 까지는 현재 책이 놓여있다고 생각하면 된다
        // m + 1 ~ 는 모두 값을 1을 채워놓아 영화가 있음을 표시한다
        // 그 후, 해당 영화를 볼 경우, 누적합을 이용해, 해당 영화보다 위에 놓여있는 영화의 개수를 구한다.
        // 그 후 fenwick 트리의 값을 0으로 바꿔주고, m번부터 1번까지 해당 영화를 위치에 두고, 값을 1로 채워놓는다.
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            fenwickTree = new int[n + m + 1];       // 0번은 안 쓰는 공간, 1 ~ m 번은 본 영화가 놓일 공간, m+1 ~ n + m 까지는 현재 영화가 놓여있는 공간
            movieLocation = new int[n + 1];         // 해당 영화가 펜윅 트리 어디에 놓여있는지 표시한다.
            topCounter = m;         // 현재 최상단에 있는 책의 위치를 가르킨다.

            init(n, m);         // 테스트케이스에 따라 n, m이 바뀌므로 펜윅트리와 책 위치를 초기화해준다.
            for (int i = 0; i < m; i++) {
                int movieNum = sc.nextInt();
                sb.append(getMoviesOnTheMovie(movieLocation[movieNum])).append(" ");        // 해당 책의 위에 있는 영화의 개수를 구하고
                seeMovie(movieNum);     // 해당 위치의 영화를 빼 최상단에 둔다.
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static void init(int n, int m) {
        for (int i = 0; i < n; i++) {
            inputValue(i + m + 1, 1);       // i+m+1 ~ n+m 까지 1을 채워넣는다.
            movieLocation[i + 1] = i + m + 1;       // 해당 책의 위치를 기록
        }
    }

    static void seeMovie(int movieNum) {
        inputValue(movieLocation[movieNum], -1);        // 해당 위치의 영화를 빼내고
        inputValue(topCounter, 1);      // 최상단에 영화를 놓는다.
        movieLocation[movieNum] = topCounter--;     // 그리고 해당 영화의 위치를 기록
    }

    static void inputValue(int idx, int value) {        // 펜윅트리에 값을 넣는 메소드
        while (idx < fenwickTree.length) {
            fenwickTree[idx] += value;
            idx += (idx & -idx);
        }
    }

    static int getMoviesOnTheMovie(int idx) {       // 해당 idx보다 위에 있는 영화들의 개수를 구한다.
        idx--;
        int sum = 0;
        while (idx > 0) {
            sum += fenwickTree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }
}