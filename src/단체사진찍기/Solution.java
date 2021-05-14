package 단체사진찍기;

public class Solution {
    static char[] people = {'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T'};
    static boolean[] check;
    static int answer;

    public static void main(String[] args) {
        // 순열로 각 사람들을 줄 세운 뒤,
        // 각 조건이 일치하는지 여부를 따져서 조건을 모두 만족한다면 counter 를 증가시켜주자.
        int n = 2;
        String[] data = {"N~F=0", "R~T>2"};

        check = new boolean[people.length];
        answer = 0;

        permutation(data, 0, 0, "");
        System.out.println(answer);
    }

    static void permutation(String[] data, int cycle, int selected, String row) {   // 순열
        if (selected == people.length) {    // 모두 선택이 끝나면
            for (String s : data) {     // 모든 조건들이 만족하는지 확인.
                if (!parsingData(s, row))
                    return;
            }
            answer++;
            return;
        } else if (cycle >= people.length)  // 모두 선택되지 않았지만, 사람 수 만큼의 재귀를 반복했지만 줄을 모두 서지 않았다면, 이 줄이 완성된 경우에 다른 재귀에서의 경우와 같을 수 있으므로  끝낸다.
            return;

        for (int i = 0; i < people.length; i++) {
            if (!check[i]) {
                check[i] = true;
                permutation(data, cycle + 1, selected + 1, row + people[i]);
                check[i] = false;
            }
        }
    }

    static boolean parsingData(String s, String row) {      // row 형태로 줄을 섰을 때, s 조건을 만족하는지 체크한다.
        char a = s.charAt(0);
        char b = s.charAt(2);
        char sign = s.charAt(3);
        int value = s.charAt(4) - '0';

        if (sign == '>') {
            return locationGap(row, a, b) > value + 1;
        } else if (sign == '=') {
            return locationGap(row, a, b) == value + 1;
        } else if (sign == '<') {
            return locationGap(row, a, b) < value + 1;
        }
        return false;
    }

    static int locationGap(String row, char a, char b) {    // row 가 주어졌을 때 a,b의 위치 차이를 구한다.
        int aLoc = -1;
        int bLoc = -1;

        for (int i = 0; i < row.length(); i++) {
            if (row.charAt(i) == a)
                aLoc = i;
            else if (row.charAt(i) == b)
                bLoc = i;
        }
        if (aLoc == -1 || bLoc == -1)
            return -1;
        return Math.abs(aLoc - bLoc);
    }
}