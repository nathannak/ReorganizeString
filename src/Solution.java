import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Solution {
    public String reorganizeString(String S) {
        int buffer = S.length()%2 == 0 ? S.length()/2 : S.length()/2 + 1;
        Map<Character, Integer> map = new HashMap<>();
        char[] chars = S.toCharArray();

        for (char c : chars) {
            map.put(c, map.getOrDefault(c, 0) + 1);
            if (map.get(c) > buffer) {
                return "";
            }
        }

        return createString(map);
    }

    private String createString(Map<Character, Integer> map) {

        //max PQ
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);

        //map each char to number of occurrences in map
        for (char c : map.keySet()) {
            pq.add(new int[] {c, map.get(c)});
        }

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {

            //get top element of PQ
            int[] first = pq.poll();

            //if first time or last char of string is not the char we are having now [since same char should not be adjacent] do the followi ng
            if (sb.length() == 0 || first[0] != sb.charAt(sb.length() - 1)) {

                //add char to SB
                sb.append((char) first[0]);

                //deduce one from number of char occurrences and add to PQ for further investigation if count is not zero
                if (--first[1] > 0) {
                    pq.add(first);
                }
            }

            //this means if the current character is the same as the last one,
            // then we need to get another one from PQ to avoid duplication
            // and do not forget to put the current one and the last one back to our PQ back if their counts are not zero..
            else {
                int[] second = pq.poll();
                sb.append((char) second[0]);
                if (--second[1] > 0) {
                    pq.add(second);
                }
                pq.add(first);
            }
        }

        return sb.toString();
    }
}