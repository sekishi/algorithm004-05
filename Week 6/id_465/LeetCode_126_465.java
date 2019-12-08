package leetcode.jacf.tech;

import java.util.*;
import java.util.LinkedList;

/**
 * 126. 单词接龙 II
 * https://leetcode-cn.com/problems/word-ladder-ii/
 * <p>
 * Copied from windliang
 * TODO: digest
 *
 * @author jacf
 */
public class WordLadderII {
    class Solution {
        public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
            List<List<String>> ans = new ArrayList<>();
            // 如果不含有结束单词，直接结束，不然后边会造成死循环
            if (!wordList.contains(endWord)) {
                return ans;
            }
            bfs(beginWord, endWord, wordList, ans);
            return ans;
        }

        private void bfs(String beginWord, String endWord, List<String> wordList, List<List<String>> ans) {
            Queue<List<String>> queue = new LinkedList<>();
            List<String> path = new ArrayList<>();
            path.add(beginWord);
            queue.offer(path);
            boolean isFound = false;
            Set<String> dict = new HashSet<>(wordList);
            Set<String> visited = new HashSet<>();
            visited.add(beginWord);
            while (!queue.isEmpty()) {
                int size = queue.size();
                Set<String> subVisited = new HashSet<>();
                for (int j = 0; j < size; j++) {
                    List<String> p = queue.poll();
                    //得到当前路径的末尾单词
                    String temp = p.get(p.size() - 1);
                    // 一次性得到所有的下一个的节点
                    ArrayList<String> neighbors = getNeighbors(temp, dict);
                    for (String neighbor : neighbors) {
                        //只考虑之前没有出现过的单词
                        if (!visited.contains(neighbor)) {
                            //到达结束单词
                            if (neighbor.equals(endWord)) {
                                isFound = true;
                                p.add(neighbor);
                                ans.add(new ArrayList<String>(p));
                                p.remove(p.size() - 1);
                            }
                            //加入当前单词
                            p.add(neighbor);
                            queue.offer(new ArrayList<String>(p));
                            p.remove(p.size() - 1);
                            subVisited.add(neighbor);
                        }
                    }
                }
                visited.addAll(subVisited);
                if (isFound) {
                    break;
                }
            }
        }

        private ArrayList<String> getNeighbors(String node, Set<String> dict) {
            ArrayList<String> res = new ArrayList<String>();
            char[] chs = node.toCharArray();
            for (char ch = 'a'; ch <= 'z'; ch++) {
                for (int i = 0; i < chs.length; i++) {
                    if (chs[i] == ch) {
                        continue;
                    }
                    char old_ch = chs[i];
                    chs[i] = ch;
                    if (dict.contains(String.valueOf(chs))) {
                        res.add(String.valueOf(chs));
                    }
                    chs[i] = old_ch;
                }

            }
            return res;
        }

    }
}
