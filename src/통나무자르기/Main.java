/*
 Author : Ruel
 Problem : Baekjoon 1114번 통나무 자르기
 Problem address : https://www.acmicpc.net/problem/1114
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 통나무자르기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static int l, k, c;
    static int[] cutPoints;

    public static void main(String[] args) throws IOException {
        // l길이의 통나무와 자를 수 있는 k개의 지점 그리고 자를 수 있는 횟수 c가 주어진다
        // 자른 조각들 중 최대의 길이가 가장 짧게하도록 자르고 싶다
        // 이 때의 가장 긴 조각의 길이와 첫번째로 자르는 지점의 위치를 출력하라
        // l의 길이가 10억으로 매우 길 수 있으므로, 이분 탐색을 통해 풀어야한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        l = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        HashSet<Integer> hashSet = new HashSet<>();     // 중복된 지점이 있을 수 있으므로 해쉬셋으로 받아 중복을 제거하고
        hashSet.add(0);
        hashSet.add(l);
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++)
            hashSet.add(Integer.parseInt(st.nextToken()));
        // 스트림을 사용해, 정렬하고 배열로 바꿔주자.
        cutPoints = hashSet.stream().sorted().mapToInt(value -> value).toArray();

        // 이분 탐색을 통해 가능한 범위를 좁혀준다.
        int start = 1;
        int end = l;
        int[] answer = new int[2];
        while (start < end) {
            int mid = (start + end) / 2;
            // mid 길이 이하로 자르는 것이 가능한지 계산한다.
            int[] candidate = isPossible(mid);
            if (candidate[0] > mid)
                // mid보다 긴 길이로 답이 나온다면, 불가능.
                // start 를 mid + 1로 늘려주자.
                start = mid + 1;
            else {
                // 가능한 경우에는 end = mid로 바꿔주고
                // 지금 이 값을 answer에 저장해두자.
                end = mid;
                answer = candidate;
            }
        }
        System.out.println(answer[0] + " " + answer[1]);
    }

    static int[] isPossible(int n) {        // n 길이 이하의 조각들로 자르는 것이 가능한지 시도한다.
        int[] answer = {0, l};      // answer[0]는 최대 조각의 길이, answer[1]은 첫번째 재단 지점.
        int cut = c;        // 자를 수 있는 횟수

        // 뒤에서부터 잘라오기 시작한다
        // 최종적으로 answer[1]에는 가능한 앞 부분의 재단 지점이 남아있을 것이다.
        for (int i = cutPoints.length - 2; i > 0; i--) {
            if (cut == 0 || answer[1] - cutPoints[i] > n)       // 재단 횟수를 다 사용했거나, 마지막 지점부터 현 지점까지의 길이가 이미 n을 넘어버리는 경우에는 중지.
                break;

            // 다음 재단 지점에서는 n보다 긴 조각이 생기지만, 현 재단 지점에서는 n보다 작은 조각으로 만들 수 있는 재단 지점을 찾는다
            // 그 후 해당 재단 지점에서 조각들로 나눈다고 생각한다.
            if (answer[1] - cutPoints[i - 1] > n && answer[1] - cutPoints[i] <= n && cut > 0) {
                answer[0] = Math.max(answer[0], answer[1] - cutPoints[i]);
                answer[1] = cutPoints[i];
                cut--;
            }
        }
        // 0부터 마지막 재단 위치까지의 길이가 n보다 작거나 같고 아직 재단 횟수가 남아있다면
        // 첫번째 재단 지점을 무조건 자른다.
        if (answer[1] <= n && cut > 0)
            answer[1] = cutPoints[1];
        // 자른 지점으로부터 뒷부분의 길이만 answer[0]에 갱신해왔기 때문에
        // 마지막으로는 0위치로부터 마지막 재단 위치까지의 길이가 answer[0]보다 큰지 확인하고 갱신한다.
        answer[0] = Math.max(answer[0], answer[1]);
        return answer;
    }
}