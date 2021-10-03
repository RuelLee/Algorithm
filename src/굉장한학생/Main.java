/*
 Author : Ruel
 Problem : Baekjoon 2336번 굉장한 학생
 Problem address : https://www.acmicpc.net/problem/2336
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 굉장한학생;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] segmentTree;
    static final int MAX = 500_001;
    static int[][] testRanks;

    public static void main(String[] args) throws IOException {
        // 우와 정말 이런 문제 푸는 사람들은 어떤 사람일까라는 생각이 들었다
        // 세 시험의 결과가 주어질 때, 모든 시험에 대해 나보다 성적이 앞서는 사람이 존재하는가를 구하는 문제였다
        // 한사람씩 일일이 구하기엔 학생 수가 최대 50만까지 주어질 수 있어서 안된다
        // 따라서 푸는 방법이 세그먼트 트리를 이용하는 방법이었다. 트리의 값을 최대 등수 값으로 초기화해준다.
        // 제일 먼저 첫번째 시험을 성적 내림차순으로 세그먼트 트리에 값을 넣어줄 것이다.
        // 따라서 세그먼트 트리에서 값이 찾아진다는 거 자체가 나보다 첫번째 시험에서 성적이 우수한 사람이라는 뜻이다.
        // 세그먼트 트리의 위치는 2차 시험에서의 성적 등수이고, 값은 3차 시험의 성적 등수를 넣어줄 것이다. 등수는 낮을 수록 높은 등수이니 낮은 값으로 갱신해준다.
        // 만약 1차 시험에서 3등, 2차 시험에서 5등, 3차 시험에서 8등인 사람이 있다고 하자
        // 이 사람은 3번째 순서로 세그먼트 트리에 값을 넣을 것이고,
        // 그리고 그 때, 2차 시험 등수인 5보다 작은 값, 1 ~ 4등 까지의 등수를 검색한다
        // 그러면 2차 시험의 1 ~ 4 등들이 획득한 3차 시험 성적의 가장 높은 등수가 나올 것이다. -> 값이 나온다는 거 자체가 자신보다 1차, 2차 시험 둘 다에서 나보다 높은 성적을 취득한 사람이 있다는 뜻이다.
        // 마지막으로 나온 값은 나보다 1차, 2차 시험 성적에서 우수한 성적을 기록한 사람들 중 3차 시험에서의 최고 성적이다.
        // 만약 이 값이 나의 3차 성적인 8등보다 큰 등수라면 나보다 세 시험에서 모두 우수한 성적을 기록한 사람은 없는 것이 된다.
        // 개념 자체를 이해하는데 많은 시간이 걸린 문제이다. 어렵다!

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        segmentTree = new int[(int) Math.pow(2, (int) Math.ceil(Math.log(n) / Math.log(2)) + 1) + 1];
        Arrays.fill(segmentTree, MAX);
        testRanks = new int[n + 1][3];

        StringTokenizer firstRanks = new StringTokenizer(br.readLine());
        StringTokenizer secondRanks = new StringTokenizer(br.readLine());
        StringTokenizer thirdRanks = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            testRanks[i + 1][0] = Integer.parseInt(firstRanks.nextToken());     // 시험 성적으로 해당 학생의 번호를 넣어준다.
            testRanks[Integer.parseInt(secondRanks.nextToken())][1] = i + 1;        // 해당 학생의 번호에 2차 시험의 등수를 넣어준다.
            testRanks[Integer.parseInt(thirdRanks.nextToken())][2] = i + 1;         // 해당 학생의 번호에 3차 시험의 등수를 넣어준다.
        }

        int counter = 0;
        for (int i = 1; i < testRanks.length; i++) {
            inputValue(testRanks[testRanks[i][0]][1], testRanks[testRanks[i][0]][2], 1, n, 1);      // 세그먼트 트리에 값을 넣고,

            // 1등부터 2차의 시험의 내 등수까지의 값을 찾아내자. 이는 1차 시험에서 내 성적 이상, 2차 시험에서 내 성적 이상이라는 의미를 띈다.
            // 이렇게 나온 값은 두 시험 모두 내 성적 이상을 취득한 사람 중 3차 시험에서의 최고 성적인 사람의 등수가 된다.
            int highestRanksThanIInSecondAndThird = getValue(1, testRanks[testRanks[i][0]][1], 1, n, 1);

            // 이 값이 나랑 같거나 크다면 세 시험 모두 내 성적보다 좋은 사람은 없다는 것을 의미한다.(중복되는 등수는 없다고 했으므로)
            if (highestRanksThanIInSecondAndThird >= testRanks[testRanks[i][0]][2])
                counter++;
        }
        System.out.println(counter);
    }

    static int getValue(int targetStart, int targetEnd, int start, int end, int loc) {      // 세그먼트 트리에서 2차 시험의 등수에 따른 3차 시험 최고 등수를 가져오는 메소드.
        if (targetStart == start && targetEnd == end)
            return segmentTree[loc];

        int middle = (start + end) / 2;
        if (targetStart > middle)
            return getValue(targetStart, targetEnd, middle + 1, end, loc * 2 + 1);
        else if (targetEnd <= middle)
            return getValue(targetStart, targetEnd, start, middle, loc * 2);
        else {
            return Math.min(getValue(targetStart, middle, start, middle, loc * 2),
                    getValue(middle + 1, targetEnd, middle + 1, end, loc * 2 + 1));
        }
    }

    static void inputValue(int target, int value, int start, int end, int loc) {        // 세그먼트 트리에 값을 넣는 메소드
        if (start >= end) {     // 최종 목적지에 도달.
            segmentTree[loc] = value;
            return;
        }

        int middle = (start + end) / 2;
        if (target > middle)
            inputValue(target, value, middle + 1, end, loc * 2 + 1);
        else
            inputValue(target, value, start, middle, loc * 2);
        segmentTree[loc] = Math.min(segmentTree[loc], value); // 최종 목적지에 도달하기 전까지는 이번에 들어가는 값이 최소값인지 확인하고 갱신해주자.
    }
}