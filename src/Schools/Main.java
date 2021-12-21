/*
 Author : Ruel
 Problem : Baekjoon 11848번 Schools
 Problem address : https://www.acmicpc.net/problem/11848
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Schools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class City {
    int music;
    int sports;

    public City(int music, int sports) {
        this.music = music;
        this.sports = sports;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // State에 지을 수 있는 음악 학교와, 스포츠 학교의 수가 주어진다
        // 각 city에 음악을 배우길 원하는 학생과 스포츠를 배우길 원하는 학생의 수가 주어질 때
        // 어느 city들에 학교를 설립하는 것이 만족시키는 학생의 수를 최대로 할 수 있는지 찾고, 그 때의 만족시키는 학생 수를 구하라
        // 그리디 문제
        // 그리디 문제는 구현하고보면 코드량은 많지 않지만 그 방법을 생각하기까지가 시간이 오래 걸리는 것 같다.
        // 각 도시에 각 학생들 간의 차이에 따라서 정렬을 한다.
        // 음악을 배우길 원하는 학생 수와 스포츠를 배우길 원하는 학생 수 간의 차이가 큰 것이 먼저 오도록.(역순으로는 반대 학생이 비교적 더 많은 쪽으로 정렬된다)
        // 그 후 첫번째부터 음악을 원하는 학생 수를 더해나가며, 더한 city의 수가 m을 벗어날 경우, 가장 작은 학생 수에 해당하는 도시의 학생 수만큼을 빼가며 이를 기록한다
        // 위 과정이 끝나면, 반대로 끝에서부터 스포츠를 원하는 학생을 더해나가며, 더한 city의 수가 s를 벗어날 경우, 가장 작은 수에 해당하는 도시의 학생 수만큼 빼가며 이를 기록한다.
        // 위 과정이 끝났다면 우리가 정렬했던 도시의 순서에 따라서
        // 기록한 위치를 i라 했을 때, i에 해당하는 기록값은 0 ~ i까지 도시들 중 m이하의 도시를 선택하여 얻을 수 있는 최대 만족할 수 있는 학생 수가 되고,
        // 스포츠는 (n - 1) ~ i까지의 도시들 중 s이하의 도시를 선택하여 얻을 수 있는 최대 만족할 수 있는 학생의 수가 된다.
        // 그리고 그 두 학교를 동시에 설립했을 때를 찾기 위해서는, 0 ~ i번째에서 음악학교를, (i + 1) ~ (n - 1)까지에선 스포츠학교를 선택했을 때의 학생 수 중 최대값을 찾으면 된다.
        // 이 때 찾기 시작하는 초기값으로는 음악 학교만을 선택했을 때의 최대값인 기록값의 (n - 1)번과 스포츠 학교만을 선택했을 때의 최대값인 기록값 0번 중 큰 값으로 세팅해주자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        City[] cities = new City[n];
        for (int i = 0; i < cities.length; i++) {
            st = new StringTokenizer(br.readLine());
            cities[i] = new City(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        // 각 도시에 음악을 원하는 학생 수 - 스포츠를 원하는 학생 수로 정렬해준다
        // 음악을 원하는 학생이 스포츠를 원하는 학생보다 상대적으로 많은 도시가 우선적으로 정렬된다.
        Arrays.sort(cities, (o1, o2) -> Integer.compare(o2.music - o2.sports, o1.music - o1.sports));
        long[] maxMusicFromZero = new long[n];      // 0 ~ i번까지의 도시에서 m개 이하를 선택하여 최대로 만족시킬 수 있는 학생 수가 기록된다.
        long sum = 0;
        PriorityQueue<Integer> students = new PriorityQueue<>();
        for (int i = 0; i < cities.length; i++) {
            sum += cities[i].music;     // i번 도시를 선택해서 더해주고
            students.offer(cities[i].music);        // 우선순위큐에 넣어준다.
            while (!students.isEmpty() && students.size() > m)     // 만약 도시의 수가 m개를 넘었다면
                sum -= students.poll();     // 그 만큼의 학생 수를 적은 순으로 빼준다.
            maxMusicFromZero[i] = sum;      // 그 후 그 값을 maxMusicFromZero에 기록해주자.
        }

        long[] maxSportsFromN = new long[n];        // (n - 1) ~ i번까지의 도시에서 s개 이하를 선택하여 최대로 만족시킬 수 있는 학생 수가 기록된다.
        sum = 0;
        students.clear();
        for (int i = cities.length - 1; i >= 0; i--) {
            sum += cities[i].sports;            // i번째 도시의 학생 수를 더해주고
            students.offer(cities[i].sports);       // 우선순위큐에 넣어준다.
            while (!students.isEmpty() && students.size() > s)     // 도시의 수가 s개를 넘었다면
                sum -= students.poll();     // 해당하는 도시의 학생 수를 적은 순으로 빼준다.
            maxSportsFromN[i] = sum;            // 그리고 그 때의 값을 maxSportsFromN에 기록한다.
        }

        // 만족시킬 수 있는 최대값의 초기값은 음악학교만을 선택했을 때의 최대값인 maxMusicFromZero[n - 1]값과
        // 스포츠 학교만을 선택했을 때의 최대값인 maxSportsFromN[0] 중 큰 값을 넣어주자.
        long max = Math.max(maxMusicFromZero[n - 1], maxSportsFromN[0]);
        // (0 ~ i)번까지의 음악 학교를 선택하고, ((i + 1) ~ (n - 1))까지의 스포츠 학교를 선택했을 때의 최대값을 찾아가며, 최대 만족시킬 수 있는 학생 수가 갱신되는지 확인한다.
        for (int i = 0; i < cities.length - 1; i++)
            max = Math.max(max, maxMusicFromZero[i] + maxSportsFromN[i + 1]);
        System.out.println(max);
    }
}