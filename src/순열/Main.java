/*
 Author : Ruel
 Problem : Baekjoon 1849번 순열
 Problem address : https://www.acmicpc.net/problem/1849
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 순열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // 먼저 작은 숫자 순서대로, 원래 순열에서 자신보다 큰 숫자의 개수가 주어졌을 때
        // 이를 원래 순열로 복원하는 방법을 먼저 생각해야한다.
        // 자신보다 '큰' 숫자들에 대한 개수가 주어지는 것이기 때문에
        // 작은 숫자 먼저 자리에 배치하더라도 뒤에 나오는 숫자에 대해서는 영향을 미치지 않는다.
        // 따라서 작은 숫자부터 순서대로, '현재 비어 있는 위치에 자신보다 큰 숫자의 개수 + 1번째 위치'에 숫자를 놓으면 된다
        // 비어 있는 공간은 자연적으로 자신보다 큰 숫자가 채우게 될 것이다.
        // 그러면 현재 위치가 비어있는 몇번째 위치인가에 대한 고민을 해야한다
        // 우리는 이에 대한 세그먼트 트리라는 좋은 알고리즘을 알고 있다!
        // 채워지는 위치에 세그먼트 트리에 1을 넣는다.
        // 그리고 해당 위치에서 비어있는 몇번째인지는, 자신의 위치 - 자신보다 앞 위치의 숫자의 개수를 해주면 된다!
        // 또한 시간을 줄이기 위해서 위치를 검색할 때는 이분탐색을 활용한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        fenwickTree = new int[n];
        int[] answer = new int[n];

        for (int i = 0; i < n; i++) {
            int loc = findOrder(Integer.parseInt(br.readLine()));
            answer[loc - 1] = i + 1;
            checkLoc(loc);
        }
        StringBuilder sb = new StringBuilder();
        for (int an : answer)
            sb.append(an).append("\n");
        System.out.println(sb);
    }

    static int findOrder(int order) {       // 이분 탐색으로 위치를 찾는다.
        int start = 1;
        int end = fenwickTree.length - 1;
        while (start <= end) {
            int middle = (start + end) / 2;
            // 이미 숫자가 들어있는 경우, 바로 앞에 비었는 공간과 같은 값을 갖게 된다.
            // 따라서 같은 값을 가진 값 중에 가장 앞에 것을 선택해야한다. 따라서 middle -1
            if (order <= getEmptyOrder(middle))
                end = middle - 1;
            else
                start = middle + 1;
        }
        return start;
    }

    static int getEmptyOrder(int loc) {     // 위치에 앞에 몇개의 빈공간이 있는지 반환한다.
        int idx = loc;
        int sum = 0;
        while (idx > 0) {       // 자신의 위치부터 1번까지 이미 자리한 숫자들의 개수를 센다.
            sum += fenwickTree[idx];
            idx -= (idx & -idx);
        }
        // 현재 위치에서 앞에 들어있는 숫자의 개수를 빼면, 자신이 몇번째 빈공간인지 나온다.
        // 원하는 것은 자신이 몇번째인가가 아니라, 자신 앞에 몇 개가 있느냐이므로, -1을 해준다.
        return loc - sum - 1;
    }

    static void checkLoc(int loc) {     // loc 위치에 숫자가 있다고 체크해준다.
        while (loc < fenwickTree.length) {
            fenwickTree[loc]++;
            loc += (loc & -loc);
        }
    }
}