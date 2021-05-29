package 후보키;

import java.util.*;

public class Solution {
    static int check;
    static Queue<Integer> answer;

    public static void main(String[] args) {
        // 튜플이 주어질 때 최소의 칼럼으로 후보키들을 선택할 때의 가짓수
        // 순열로 선택할 칼럼을 선택. 그리고 각 튜플에 대해 선택된 칼럼으로 된 하나의 문자열을 만든 후, HashSet에 저장.
        // HashSet에 중복이 발견되지 않는다면 이는 후보키. answer 에 저장.
        // 선택된 후보키에 대해 추가적인 칼럼을 탐색할 필요는 없으므로 return.
        // 선택된 후보키들 중에 다른 후보키에 칼럼이 추가된 형태인 최소성을 만족하지 못하는 후보키가 있을 수 있다. ex) 1,3,4 컬럼으로 선택된 후보키와 3,4 컬럼으로 선택된 후보키.
        // 이에 대해 중복 검사를 통해 최소성을 만족하는 후보키만 남겨준다.

        String[][] relation = {{"100", "ryan", "music", "2"}, {"200", "apeach", "math", "2"}, {"300", "tube", "computer", "3"}, {"400", "con", "computer", "4"}, {"500", "muzi", "music", "3"}, {"600", "apeach", "music", "2"}};

        check = 0;
        answer = new LinkedList<>();

        combination(relation, 0);
        minimize();
        System.out.println(answer.size());
    }

    static void combination(String[][] relation, int turn) {    // 순열로 후보키의 후보들을 뽑는다.
        if (turn > relation[0].length)
            return;

        if (check != 0) {   // 하나 이상의 컬럼이 선택된 조건에서 유일성이 성립하는가 검사한다.
            HashSet<String> hashSet = new HashSet<>();
            StringBuilder sb;
            boolean distinguishable = true;
            for (String[] rel : relation) {
                sb = new StringBuilder();
                for (int i = 0; i < rel.length; i++) {
                    if ((check & 1 << i) == 1 << i)
                        sb.append(rel[i]).append('-');
                }
                if (hashSet.contains(sb.toString())) {
                    distinguishable = false;
                    break;
                }
                hashSet.add(sb.toString());
            }
            if (distinguishable) {
                answer.offer(check);
                return;
            }
        }

        check = check | (1 << turn);
        combination(relation, turn + 1);
        check = check & ~(1 << turn);
        combination(relation, turn + 1);
    }

    static void minimize() {    // 작성된 answer 후보키 군에서, 다른 후보키에 대해 최소성을 만족하는지 검사하고 제거한다.
        Queue<Integer> temp = new LinkedList<>();

        while (!answer.isEmpty()) {
            int current = answer.poll();

            boolean isMin = true;
            for (int i : answer) {
                if ((i & current) == current)   // 다른 후보키가 자신 + 다른 컬럼으로 만들어져있다면 다른 후보키를 제거.
                    answer.remove(i);
                else if ((i & current) == i) {  // 자신이 다른 후보키 + 다른 컬럼으로 만들어져있다면 자신을 제거.
                    isMin = false;
                    break;
                }
            }
            if (isMin)      // 다른 후보키를 모두 둘러봐도 자신이 최소성을 만족한다면 남겨둠.
                temp.offer(current);
        }
        answer.addAll(temp);
    }
}