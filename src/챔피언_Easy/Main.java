/*
 Author : Ruel
 Problem : Baekjoon 21319번 챔피언 (Easy)
 Problem address : https://www.acmicpc.net/problem/21319
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 챔피언_Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] players;
    static int[] samePowerStart;
    static int[] samePowerEnd;

    public static void main(String[] args) throws IOException {
        // n명의 선수의 실력이 비내림차순으로 정렬되어 주어진다
        // 각 선수는 인접한 선수와만 겨룰 수 있고, 실력이 높은 선수가 승리한다.
        // 또한 승리한 선수는 실력이 1 증가한다. 만약 실력이 같을 경우 경기는 취소된다.
        // 최종적으로 우승이 가능한 선수의 번호를 출력하라.
        // 비내림차순이 무엇인가 살펴보았다. 오름차순이되 같은 값을 허용하는 오름차순이라 보면 되는 것 같았다.
        // 먼저 각 선수들은 인접한 선수들과만 겨룰 수 있고, 비내림차순으로 정렬되어있기 때문에, 자신보다 왼쪽 인접한 선수가 자신과 같은 실력이라면 이 선수는 우승할 수 없다.(실력을 증가시킬 수 없기 때문)
        // 따라서 같은 실력을 가진 선수들을 묶음으로 생각할 필요가 있다.
        // 같은 실력을 가진 선수들 중 가장 먼저 나오는 선수는 우승할 가능성이 있고(자신보다 왼쪽에 있는 선수들을 이김으로써 실력을 높일 수 있기 때문에) 나머지 같은 실력의 선수들을 우승할 가능성이 없다.
        // 또한 a라는 실력을 가진 선수들 중 가장 먼저 나오는 선수가 우승이 불가능하다면, a보다 작은 실력을 가진 선수들 또한 우승이 불가능하다.(경기를 통해 실력을 높이더라도 불가능)
        // 선수의 수가 20만으로 많기 때문에 이분탐색으로 범위를 줄여나가되, mid의 값으로 나오는 선수와 같은 실력을 가진 선수 중 가장 앞번호 선수로 계산을 하고,
        // 해당 선수가 우승이 불가능할 경우, end 범위를 같은 실력의 가장 앞번호 선수로 줄여주고,
        // 해당 선수가 우승이 가능할 경우, start 범위를 같은 실력을 가진 뒷번호 선수+1 번으로 줄여나가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        players = new int[n + 1];
        samePowerStart = new int[n + 1];        // 같은 실력을 가진 선수 중 가장 앞번호를 저장할 것이다.
        samePowerEnd = new int[n + 1];          // 같은 실력을 가진 선수 중 가장 뒷번호를 저장할 것이다.
        for (int i = 1; i < n + 1; i++) {
            players[i] = Integer.parseInt(st.nextToken());
            if (players[i] == players[i - 1]) {     // 앞 선수와 실력이 같다면
                samePowerStart[i] = i - 1;      // samePowerStart[i]에 앞번호를 넣어주고
                samePowerEnd[i - 1] = i;        // samePowerEnd[i-1]에 내 번호를 넣어준다.
            } else      // 다르다면
                samePowerStart[i] = i;      // samePowerStart[i]에는 내 번호를 넣어준다.
            samePowerEnd[i] = i;        // samePowerEnd[i]에는 항상 내 번호를 넣어준다(뒷 번호 선수의 실력을 아직 모르므로)
        }

        int start = 1;
        int end = n;
        while (start < end) {
            int mid = findSamePowerStart((start + end) / 2);        // mid 값으로 나온 선수와 같은 실력을 갖는 가장 앞 선수를 가져온다.
            if (canBeWinner(mid))       // 우승이 가능하다면
                end = mid;      // end를 mid값으로 넣어주고,
            else        // 우승이 불가능하다면, 같은 실력을 갖는 가장 뒷번호 선수 + 1 값을 start에 넣어준다.
                start = findSamePowerEnd(mid) + 1;
        }

        StringBuilder sb = new StringBuilder();
        // start가 n의 범위를 넘어버렸다면, 우승이 가능한 선수가 없는 경우.
        if (start == n + 1)
            sb.append(-1);
        else {      // 그렇지 않다면
            // start보다 큰 값을 갖는 선수들을 살펴보며
            // 같은 실력을 갖는 맨 앞 선수만 우승 가능성이 있는 선수이다.
            for (int i = start; i < players.length; i++) {
                if (players[i] == players[i - 1])       // 앞 선수와 같은 실력을 갖는다면 우승할 수 없다.
                    continue;
                sb.append(i).append(" ");
            }
        }
        System.out.println(sb);
    }

    static boolean canBeWinner(int idx) {
        int power = players[idx] + idx - 1;     // 해당 선수가 앞 선수들과 대전하며 얻을 수 있는 최대 실력
        int opponent = idx + 1;     // 자신의 뒷 선수들과 겨루기 시작.
        // 자신의 실력이 높다면 실력을 하나 증가시켜가며 더 뒷 선수들과 겨루기 시작.
        while (opponent < players.length && power > players[opponent]) {
            opponent++;
            power++;
        }
        // 마지막 선수까지 이기는 게 성공했다면 true, 아니라면 false
        return opponent == players.length;
    }

    static int findSamePowerStart(int idx) {        // 같은 실력을 갖는 가장 앞 번호 선수를 찾는다.
        if (samePowerStart[idx] == idx)
            return idx;
        return samePowerStart[idx] = findSamePowerStart(samePowerStart[idx]);
    }

    static int findSamePowerEnd(int idx) {          // 같은 실력을 갖는 가장 뒷 번호 선수를 찾는다.
        if (samePowerEnd[idx] == idx)
            return idx;
        return samePowerEnd[idx] = findSamePowerEnd(samePowerEnd[idx]);
    }
}