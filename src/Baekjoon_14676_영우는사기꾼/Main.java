/*
 Author : Ruel
 Problem : Baekjoon 14676번 영우는 사기꾼?
 Problem address : https://www.acmicpc.net/problem/14676
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14676_영우는사기꾼;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] inDegree;
    static List<List<Integer>> nextConstructions;
    static int[] built;

    public static void main(String[] args) throws IOException {
        // n개의 건물, m개의 선행 건물, k개의 게임 정보가 주어진다.
        // 선행 건물은 x y -> x 건물을 건설해야 y 건물을 건설할 수 있음.
        // 게임 정보 1 a -> a 건물을 건설함, 2 a -> a 건물을 파괴함.
        // 각 건물들을 중복 건설이 가능하다.
        // 선행 건물들이 없는데 후속 건물을 짓거나, 없는 건물을 파괴한 경우 Lier!,
        // 올바르게 이루어진 경우 King-God-Emperor 를 출력하자.
        //
        // 위상 정렬에 대한 문제
        // 명령들을 따라가며 위상 정렬 결과를 토대로 치트키를 사용했는지 체크하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 진입차수
        inDegree = new int[n + 1];
        // 선행 건물들.
        nextConstructions = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            nextConstructions.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int former = Integer.parseInt(st.nextToken());
            int latter = Integer.parseInt(st.nextToken());

            // 선행 건물을 지을 경우, 진입차수를 낮춰줄 후속 건물들을 기록한다.
            nextConstructions.get(former).add(latter);
            // 후속 건물의 진입차수 높여줌.
            inDegree[latter]++;
        }

        // 건설된 건물의 개수를 센다.
        built = new int[n + 1];
        // 치트키 사용 유무
        boolean fairPlayed = true;
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            int target = Integer.parseInt(st.nextToken());

            // 건설 명령일 경우.
            if (order == 1) {
                // 진입 차수가 0보다 크다면 선행 건물들이 안 지어진 경우.
                // 치트키 사용으로 판단하고 종료.
                if (inDegree[target] > 0) {
                    fairPlayed = false;
                    break;
                }

                // 이번 건설을 통해 target 건물이 한 개가 된다면
                if (built[target]++ == 0) {
                    // 후속 건물들의 진입 차수를 낮춰준다
                    for (int next : nextConstructions.get(target))
                        inDegree[next]--;
                }

            // 파괴 명령
            } else if (order == 2) {
                // 건설된 건물이 없다면, 불가능한 경우.
                // 치트키 사용 체크 후 종료.
                if (built[target] == 0) {
                    fairPlayed = false;
                    break;
                }

                // 만약 이번 파괴를 통해 target 건물이 없게 된다면
                if (--built[target] == 0) {
                    // 후속 건물들의 진입차수를 높여준다.
                    for (int next : nextConstructions.get(target))
                        inDegree[next]++;
                }
            }
        }
        // fairPlayed를 통해 치트키 사용 여부에 따라 다른 결과를 출력한다.
        System.out.println(fairPlayed ? "King-God-Emperor" : "Lier!");
    }
}