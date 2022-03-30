/*
 Author : Ruel
 Problem : Baekjoon 3176번 도로 네트워크
 Problem address : https://www.acmicpc.net/problem/3176
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3176_도로네트워크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Road {
    int end;
    int distance;

    public Road(int end, int distance) {
        this.end = end;
        this.distance = distance;
    }
}

public class Main {
    static List<List<Road>> child;
    static int[] ranks;
    static int[][][] sparseArray;

    public static void main(String[] args) throws IOException {
        // n개의 도시와 n-1개의 도로가 주어진다
        // 각각의 도시 쌍에는 두 도시를 연결하는 유일한 경로가 존재한다
        // k개의 도시 쌍이 주어질 때 두 도시를 연결하는 경로에서 가장 짧은 도로와 가장 긴 도로의 길이는?
        //
        // 희소배열문제
        // '두 도시를 연결하는 유일한 경로' = 그래프 형태가 아니라 트리 형태가 된다는 의미다
        // 따라서 희소배열을 통해 두 도시를 연결하는 도로를 빠르게 찾을 수 있다
        // 희소 배열에 자신의 2^n번째 조상 뿐만 아니라 최소 길이 도로와 최대 길이 도로도 같이 저장
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        init(n);
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());       // a 도시와
            int b = Integer.parseInt(st.nextToken());       // b 도시 간에
            int dis = Integer.parseInt(st.nextToken());     // dis 길이의 도로가 존재.

            child.get(a).add(new Road(b, dis));
            child.get(b).add(new Road(a, dis));
        }
        setRootAndFillSparseArray(1);

        int k = Integer.parseInt(br.readLine());        // k개의 쿼리
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());       // a 도시와
            int b = Integer.parseInt(st.nextToken());       // b 도시 간의 최소, 최대 길이의 도시는?

            int[] answer = findMinMaxRoad(a, b);        // answer[0]은 최소, answer[1]은 최대 길이 도로
            sb.append(answer[0]).append(" ").append(answer[1]).append("\n");
        }
        System.out.print(sb);
    }

    static int[] findMinMaxRoad(int a, int b) {     // a와 b 간의 최소, 최대 길이 도로를 차는다.
        int[] answer = new int[]{Integer.MAX_VALUE, 0};
        while (ranks[a] != ranks[b]) {      // 서로 간의 차수가 다르다면,
            // 두 차수 간의 차이를 희소 배열로 줄인다
            // 2의 배수로 저장되어있기 때문에, 가장 많이 건너 뛸 수 있는 차이를 계산한다.
            // 계산된 diff 만큼 차수를 줄여준다.
            int diff = (int) (Math.log(Math.abs(ranks[a] - ranks[b])) / Math.log(2));

            // a의 차수를 줄일 때
            // 최소, 최대 길이 도시를 고려한다.
            if (ranks[a] > ranks[b]) {
                answer[0] = Math.min(answer[0], sparseArray[a][diff][1]);
                answer[1] = Math.max(answer[1], sparseArray[a][diff][2]);
                a = sparseArray[a][diff][0];
            } else {        // b의 차수를 줄일 때.
                answer[0] = Math.min(answer[0], sparseArray[b][diff][1]);
                answer[1] = Math.max(answer[1], sparseArray[b][diff][2]);
                b = sparseArray[b][diff][0];
            }
        }

        // 차수는 동일해졌지만, 아직 경로로써 서로 만나지 않았다면(= a와 b가 같아질 때까지)
        while (a != b) {
            // 다시 희소배열을 활용하여 같은 조상을 같도록 해준다
            // 부모가 같아지기 직전 값으로 희소배열을 이용하여 차이를 줄여준다
            // 최소 0을 건너뛰도록 하여 마지막에는 a와 b가 같아진다.
            int jump = 0;
            while (sparseArray[a][jump + 1][0] != sparseArray[b][jump + 1][0])
                jump++;

            // a와 b 모두 건너뛰고 있는 중이므로 최소, 최대 길이 도로는 모두 고려한다.
            answer[0] = Math.min(answer[0], Math.min(sparseArray[a][jump][1], sparseArray[b][jump][1]));
            answer[1] = Math.max(answer[1], Math.max(sparseArray[a][jump][2], sparseArray[b][jump][2]));
            a = sparseArray[a][jump][0];
            b = sparseArray[b][jump][0];
        }
        return answer;
    }

    // n 도시를 부모 노드로 생각하고 트리 형태로 만든다.
    static void setRootAndFillSparseArray(int n) {
        // 자신의 차수를 통해, 희소 배열의 공간을 어디까지 채울 지 정할 수 있다.
        int sparseLength = (int) (Math.log(ranks[n]) / Math.log(2)) + 1;
        // 희소 배열에 내용을 채운다.
        for (int i = 1; i < sparseLength; i++) {
            // 2^i번째 부모 번호
            sparseArray[n][i][0] = sparseArray[sparseArray[n][i - 1][0]][i - 1][0];
            // n -> 2^i 부모까지의 도로들 중 최소 길이
            sparseArray[n][i][1] = Math.min(sparseArray[n][i - 1][1], sparseArray[sparseArray[n][i - 1][0]][i - 1][1]);
            // n -> 2^i 부모까지의 도로들 중 최대 길이
            sparseArray[n][i][2] = Math.max(sparseArray[n][i - 1][2], sparseArray[sparseArray[n][i - 1][0]][i - 1][2]);
        }

        // 자신의 자식 노드들에게 재귀적으로 메소드를 보낸다.
        for (Road r : child.get(n)) {
            // 아직 부모 노드가 설정이 안되어있다면 미방문 노드
            if (sparseArray[r.end][0][0] == 0) {
                // 차수는 n보다 하나 증가
                ranks[r.end] = ranks[n] + 1;
                // 부모 노드는 n
                sparseArray[r.end][0][0] = n;
                // 최소, 최대 길이 도로는 n -> r.end를 잇는 도로 하나 뿐이므로, 둘다 이 도로의 길이로 초기화
                sparseArray[r.end][0][1] = sparseArray[r.end][0][2] = r.distance;
                // 재귀적으로 함수 콜
                setRootAndFillSparseArray(r.end);
            }
        }
    }

    static void init(int n) {
        child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());
        ranks = new int[n + 1];
        sparseArray = new int[n + 1][(int) Math.ceil(Math.log(n - 1) / Math.log(2)) + 1][3];
    }
}