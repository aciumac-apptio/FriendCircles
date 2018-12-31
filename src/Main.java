import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        List<String> list = new ArrayList<>();
        list.add("YYNN");
        list.add("YYYN");
        list.add("NYYN");
        list.add("NNNY");
        System.out.println(friendCircles(list));

        System.out.println(isPalindrome("atta"));
        System.out.println(isPalindrome("attta"));
        System.out.println(isPalindrome("abc"));

        System.out.println("Expect 4 Actual " + getScore("attract"));
        System.out.println("Expect 15 Actual " + getScore("acdapmpomp"));
    }

    public static int getScore(String s) {
        // Write your code here
        int maxScore = Integer.MIN_VALUE;
        Set<String> subsequenceSet = new HashSet<>();
        Map<String, int[]> palindromesToIndices = new HashMap<>();

        // Find all the subsequences
        int subsequenceLength = 1;
        while (subsequenceLength < s.length()) {
            for (int i = 0; i <= s.length() - subsequenceLength ; i++) {
                for (int j = i + 1; j < s.length(); j++) {
                    String subs = s.substring(i, i+subsequenceLength-1) + s.substring(j, j+1);
                    if (isPalindrome(subs)) {
                        int[] range = new int[2];
                        if (palindromesToIndices.containsKey(subs)) {
                            range = palindromesToIndices.get(subs);
                            if (range[1] - range[0] > j - i) {
                                if (subsequenceLength > 1) {
                                    range[0] = i;
                                } else {
                                    range[0] = j;
                                }
                                range[1] = j;
                            }
                        } else {
                            if (subsequenceLength > 1) {
                                range[0] = i;
                            } else {
                                range[0] = j;
                            }
                            range[1] = j;
                        }


                        /*for (int k = i; k < i + subsequenceLength - 1; k++) {
                            set.add(k);
                        }
                        set.add(j);*/
                        palindromesToIndices.put(subs, range);
                        //subsequenceSet.add(subs);
                    }
                }
            }
            subsequenceLength++;
        }

// Calculate all max(a) * max(b) and return maximum value
        List<String> list = new ArrayList<>();
        list.addAll(palindromesToIndices.keySet());

        for (String subsequence1 : list) {
            for (String subsequence2 : palindromesToIndices.keySet()) {
                if (!subsequence2.equals(subsequence1) && !overlap(palindromesToIndices, subsequence1, subsequence2)) {
                    int max = subsequence1.length() * subsequence2.length();
                    if (max > maxScore) {
                        maxScore = max;
                    }
                }
            }
        }

        return maxScore;
    }

    private static boolean overlap(Map<String, int[]> map, String subsequence1, String subsequence2) {
        /*Set<Integer> set1 = new HashSet<>(map.get(subsequence1));
        Set<Integer> set2 = new HashSet<>(map.get(subsequence2));

        set1.retainAll(set2);
        return !set1.isEmpty();     */

        int[] range1 = map.get(subsequence1);
        int[] range2 = map.get(subsequence2);

        return range1[0] < range2[1] || range2[0] < range1[1];
    }


    private static boolean isPalindrome(String s) {
        int[] values = new int[26];

        for (int i = 0; i < s.length() / 2; i++) {
            values[s.charAt(i) - 'a'] = values[s.charAt(i) - 'a'] + 1;
        }

        for (int i = s.length() - 1; i >= s.length() - s.length() / 2; i--) {
            values[s.charAt(i) - 'a'] = values[s.charAt(i) - 'a'] - 1;
        }

        for (int val : values) {
            if (val != 0) {
                return false;
            }
        }
        return true;
    }


    public static int friendCircles(List<String> friends) {
        int[] classmates = new int[friends.size()];
        for (int i = 0; i < classmates.length; i++) {
            classmates[i] = i;
        }

        for (int k = 0; k < friends.size(); k++) {
            String line = friends.get(k);
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == 'Y' && i != k && !connected(classmates, i, k)) {
                    unite(classmates, i, k);
                }
            }
        }

        Set<Integer> set = new HashSet<>();
        for (int val : classmates) {
            set.add(val);
        }

        return set.size();
    }

    public static void unite(int[] arr, int p, int q) {
        int pid = arr[p];
        int qid = arr[q];

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == pid) {
                arr[i] = qid;
            }
        }
    }

    public static boolean connected (int[] arr, int p, int q) {
        return arr[p] == arr[q];
    }

    static String[] braces(String[] values) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> closingToOpen = new HashMap<>();
        closingToOpen.put('}', '{');
        closingToOpen.put(']', '[');
        closingToOpen.put(')', '(');

        String[] answer = new String[values.length];
        for (int k = 0; k < values.length; k++) {
            String line = values[k];
            stack = new Stack<>();
            for (int i = 0; i < line.length(); i++) {
                if (!closingToOpen.containsKey(line.charAt(i))) {
                    stack.push(line.charAt(i));
                } else if (!stack.isEmpty() && closingToOpen.get(line.charAt(i)) == stack.peek()) {
                    stack.pop();
                } else {
                    answer[k] = "NO";
                    break;
                }
            }

            if (answer[k] == null) {
                if (stack.isEmpty()) {
                    answer[k] = "YES";
                } else {
                    answer[k] = "NO";
                }
            }

        }

        return answer;
    }
}
