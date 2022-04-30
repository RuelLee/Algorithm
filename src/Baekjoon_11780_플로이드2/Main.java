/*
 Author : Ruel
 Problem : Baekjoon 11780번 플로이드 2
 Problem address : https://www.acmicpc.net/problem/11780
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11780_플로이드2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시가 있다
        // 그리고 a도시에서 출발해 b도시에 도착하는 비용 c의 m개의 버스가 주어진다
        // 각 도시에서 다른 도시로 가는 최소 비용을 모두 출력하고
        // 그 때 거쳐가는 도시의 개수와 도시를 출력하라
        //
        // 플로이드 와샬 알고리즘 문제
        // 인데, 거쳐가는 도시에 대해서는 기록을 남겨 출력해줘야한다
        // 출발 도시 - > 도착 도시 만큼의 리스트를 만들어 기록해주자
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        // 인접 행렬
        int[][] adjMatrix = new int[n + 1][n + 1];
        StringTokenizer st;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // a에서 b로 가는 버스가 아직 입력된 것이 없거나
            // 더 저렴한 비용의 버스가 들어온다면
            // adjMatrix[a][b] 에 c값을 넣음.
            if (adjMatrix[a][b] == 0 || adjMatrix[a][b] > c)
                adjMatrix[a][b] = c;
        }

        // start -> end로 가는 최소 경로를 저장할 리스트
        List<Integer>[][] minRoute = new List[n + 1][n + 1];
        for (int i = 0; i < minRoute.length; i++) {
            for (int j = 0; j < minRoute[i].length; j++) {
                minRoute[i][j] = new ArrayList<>();
                minRoute[i][j].add(i);
                minRoute[i][j].add(j);
            }
        }

        for (int via = 1; via < adjMatrix.length; via++) {
            for (int start = 1; start < adjMatrix.length; start++) {
                // 시작지과 경유지가 같을 경유 건너뜀.
                if (via == start)
                    continue;
                for (int end = 1; end < adjMatrix.length; end++) {
                    // 경유지와 시작지, 경유지와 도착지가 같을 경우 건너뛴다.
                    // 마찬가지로 시작지에서 경유지로 가는 버스가 없거나 경유지에서 도착지로 가는 버스가 없을 때도 건너 뛴다.
                    if (end == via || end == start || adjMatrix[start][via] == 0 || adjMatrix[via][end] == 0)
                        continue;

                    // 인접 행렬에서 현재 start -> end로 가는 비용이 없거나
                    // 새로운 최소 비용이 등장했을 때에
                    if (adjMatrix[start][end] == 0 || adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end]) {
                        // 최소 비용 갱신
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];

                        // 최소 경로를 다시 만들어준다
                        minRoute[start][end].clear();
                        // start -> via 까지 가는 경로를 기록하고
                        minRoute[start][end].addAll(minRoute[start][via]);
                        // via -> end까지의 경로를 기록한다.(via에 도착해서 via에서 출발하므로, via가 두번 적히는 걸 막기 위해 1부터 시작)
                        for (int i = 1; i < minRoute[via][end].size(); i++)
                            minRoute[start][end].add(minRoute[via][end].get(i));
                    }
                }
            }
        }

        StringBuilder adjPrint = new StringBuilder();
        StringBuilder minRoutePrint = new StringBuilder();
        for (int i = 1; i < adjMatrix.length; i++) {
            for (int j = 1; j < adjMatrix[i].length; j++) {
                // 인접 행렬 기록
                adjPrint.append(adjMatrix[i][j]).append(" ");
                if (adjMatrix[i][j] != 0) {     // 최소 경로가 존재할 경우
                    // 거치는 도시의 개수
                    minRoutePrint.append(minRoute[i][j].size()).append(" ");
                    // 거치는 도시들
                    for (int k = 0; k < minRoute[i][j].size(); k++)
                        minRoutePrint.append(minRoute[i][j].get(k)).append(" ");
                } else      // 최소 경로가 존재하지 않는다면 0을 출력.
                    minRoutePrint.append(0);
                minRoutePrint.append("\n");
            }
            adjPrint.append("\n");
        }

        // 결과 출력
        System.out.print(adjPrint);
        System.out.print(minRoutePrint);
    }
}