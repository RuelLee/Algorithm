/*
 Author : Ruel
 Problem : Baekjoon 1017번 소수 쌍
 Problem address : https://www.acmicpc.net/problem/1017
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1017_소수쌍;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static boolean[] primeNums;
    static int[] nums;
    static List<List<Integer>> possiblePair;

    public static void main(String[] args) throws IOException {
        // 짝수 n만큼의 수가 주어지고, 이들을 합이 소수가 되도록 쌍으로 만들고 싶다.
        // 불가능하다면 -1, 가능하다면 첫번째 주어지는 수와 페어가 되는 수들을 출력하라.
        // 1 4 7 10 11 12이라면
        // 1-4, 7-10, 11-12
        // 1-10, 4-7, 11-12 식과 같이 가능하므로 정답은 4 10이다
        // 소수 판별과 이분 매칭을 통해 풀 수 있는 문제이다.
        // 소수에 대해서는 에라토스테네스의 체(소수의 배수들은 비소수로 표시해두는 것)를 사용해 구하자.
        calcPrimeNumber();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        nums = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < nums.length; i++)
            nums[i] = Integer.parseInt(st.nextToken());

        possiblePair = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            possiblePair.add(new ArrayList<>());
        for (int i = 1; i < nums.length - 1; i++) {     // 첫번째 수부터
            for (int j = i + 1; j < nums.length; j++) {     // 뒷 순서의 수들과 비교하면서
                if (primeNums[nums[i] + nums[j]]) {     // 합이 소수일 경우, 서로의 가능한 짝으로 표시해둔다.
                    possiblePair.get(i).add(j);
                    possiblePair.get(j).add(i);
                }
            }
        }

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < possiblePair.get(1).size(); i++) {      // 첫번째 수와 페어가 가능한 수들을 하나씩 검증한다.
            int[] pair = new int[n + 1];        // 순서에 따른 짝을 표시해둘 공간
            boolean[] visited = new boolean[n + 1];     // 방문 체크
            visited[1] = true;          // 첫번째 수와 가능한 수들을 모두 검증해봐야하므로, 1번째 수와
            visited[possiblePair.get(1).get(i)] = true;     // 가능한 페어 중 i번째 수를 방문 체크를 해놔 고정시킨다.
            pair[1] = possiblePair.get(1).get(i);       // 서로에게 페어로 등록
            pair[possiblePair.get(1).get(i)] = 1;
            boolean fail = false;
            for (int j = 2; j < n + 1; j++) {
                // 두번째 수부터 페어를 연결시켜준다
                // 페어가 형성되는 게 불가능하다면 종료.
                if (pair[j] == 0 && !bipartiteMatch(j, visited.clone(), pair)) {
                    fail = true;
                    break;
                }
            }
            // 실패로 끝났다면, 건너뛰고, 가능하다면 첫번째 수와 페어가 된 수를 우선 순위 큐에 담아주자.
            if (!fail)
                priorityQueue.offer(nums[pair[1]]);
        }
        StringBuilder sb = new StringBuilder();
        if (priorityQueue.isEmpty())        // 우선 순위 큐가 비어있다면 가능한 가짓수가 없는 경우. -1 출력.
            sb.append(-1);
        while (!priorityQueue.isEmpty())        // 우선 순위 큐가 빌 때까지, 수를 뽑아내며 기록해주자.
            sb.append(priorityQueue.poll()).append(" ");
        // 결과 출력
        System.out.println(sb);
    }

    static boolean bipartiteMatch(int n, boolean[] visited, int[] pair) {       // 이분 매칭
        visited[n] = true;  // 방문 체크
        for (int partner : possiblePair.get(n)) {       // 가능한 partner는
            // partner에 짝이 없거나, partner에 할당된 짝이 다른 수와 페어를 이루는게 가능하다면
            // n과 partner를 페어로 묶어준다.
            if (pair[partner] == 0 || (!visited[pair[partner]] && bipartiteMatch(pair[partner], visited, pair))) {
                pair[partner] = n;
                pair[n] = partner;
                return true;
            }
        }
        return false;
    }

    static void calcPrimeNumber() {     // 에라토스테네스의 체
        primeNums = new boolean[2000];  // 최대 1000까지의 수가 중복없이 주어지므로 최대 수는 1999
        Arrays.fill(primeNums, true);
        primeNums[0] = primeNums[1] = false;
        for (int i = 2; i < primeNums.length / 2 + 1; i++) {        // 2부터
            if (!primeNums[i])      // 소수가 아니라면 해당 수의 배수들은 이미 비소수로 표시됐을 것이다.
                continue;
            for (int j = 2; i * j < primeNums.length; j++)      // 소수라면 해당 수의 배수들은 비소수로 표시.
                primeNums[i * j] = false;
        }
    }
}