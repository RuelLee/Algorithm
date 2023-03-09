/*
 Author : Ruel
 Problem : Baekjoon 3780번 네트워크 연결
 Problem address : https://www.acmicpc.net/problem/3780
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3780_네트워크연결;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] centers, distances;

    public static void main(String[] args) throws IOException {
        // n개의 기업이 주어지고 각 기업은 독립적인 망을 갖고 있다.
        // 각 기업의 서버를 네트워크로 연결하고자 한다
        // 1. 클러스터 A의 기존 센터 I를 골라
        // 2. 클러스터 B의 기업 J를 고른다. J는 센터가 아닐 수 있다.
        // 3. I와 J를 연결한다. 라인의 길이는 |I - J|(mod 1000)이다.
        // 4. A, B는 하나의 클러스터로 합쳐지며, 이 클러스터는 B 센터에 의해 제공된다.
        // 이 때 다음의 쿼리들을 처리하라
        // 1. E I - 기업 I와 센터까지의 거리를 출력한다
        // 2. I I J - 센터 I를 기업 J에 연결한다
        //
        // 분리 집합 문제
        // 각자의 센터를 찾아갈 때, 센터까지의 거리도 함께 갱신해주자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // n개의 기업
            int n = Integer.parseInt(br.readLine());

            // 각 기업의 센터
            // 처음에는 자기 자신.
            centers = new int[n + 1];
            for (int i = 0; i < centers.length; i++)
                centers[i] = i;
            // 각 기업에서 센터까지의 거리
            distances = new int[n + 1];

            String input = br.readLine();
            while (!input.equals("O")) {
                StringTokenizer st = new StringTokenizer(input);
                // E라면 해당 기업의 센터까지의 거리를 출력한다.
                if (st.nextToken().equals("E")) {
                    int corp = Integer.parseInt(st.nextToken());
                    // centers[corp]에 기록된 센터가 최신값이 아닐 수 있으므로
                    // findCenter() 메소드를 호출하여 센터와 거리를 갱신하고
                    findCenter(corp);
                    // 답안에 기록
                    sb.append(distances[corp]).append("\n");
                } else {        // I일 경우 센터 i와 기업 j를 연결한다
                    int i = Integer.parseInt(st.nextToken());
                    int j = Integer.parseInt(st.nextToken());
                    union(i, j);
                }
                // 다음 줄 처리
                input = br.readLine();
            }
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
    
    // 유니온 메소드
    // 항상 a는 센터이고, a는 항상 b의 센터에 연결되므로
    // ranks를 연산 최적화할 수 없다.
    static void union(int a, int b) {
        // b의 센터를 찾아
        int cb = findCenter(b);

        // a의 센터에 cb를 기록한다.
        centers[a] = cb;
        // a에서 b의 거리와 b에서 센터까지의 거리를 더한 값을
        // distances[a]에 기록한다.
        distances[a] = Math.abs(a - b) % 1000 + distances[b];
    }
    
    // n의 센터를 찾는 메소드
    static int findCenter(int n) {
        // 자기 자신이라면 n값 반환.
        if (centers[n] == n)
            return n;

        // 그렇지 않다면 원래 갖고 있던 값을 잠시 기록해두고
        int center = centers[n];
        // 새로운 센터를 찾아 값을 centers[n]에 기록한다.
        centers[n] = findCenter(centers[n]);
        // 그 후, 원래 갖고 있던 n -> center까지의 거리값인 distances[n]에
        // center -> centers[n]까지의 거리를 더해준다.
        // 다시 말해 기존 센터까지의 거리에서 새로운 센터까지의 거리를 더해
        // 새로운 n의 센터까지의 거리값을 distances[n]에 기록한다.
        distances[n] += distances[center];
        // 그리고 n의 센터값 반환.
        return centers[n];
    }
}