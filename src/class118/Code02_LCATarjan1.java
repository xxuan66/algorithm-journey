package class118;

// LCA问题Tarjan算法解法
// 测试链接 : https://www.luogu.com.cn/problem/P3379
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_LCATarjan1 {

	public static int MAXN = 500001;

	public static int[] treeHead = new int[MAXN];

	public static int[] treeNext = new int[MAXN << 1];

	public static int[] treeTo = new int[MAXN << 1];

	public static int tcnt;

	public static int[] queryHead = new int[MAXN];

	public static int[] queryNext = new int[MAXN << 1];

	public static int[] queryTo = new int[MAXN << 1];

	public static int[] queryIndex = new int[MAXN << 1];

	public static int qcnt;

	public static boolean[] visited = new boolean[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] ans = new int[MAXN];

	public static void build(int n) {
		tcnt = qcnt = 1;
		Arrays.fill(treeHead, 1, n + 1, 0);
		Arrays.fill(queryHead, 1, n + 1, 0);
		Arrays.fill(visited, 1, n + 1, false);
		for (int i = 1; i <= n; i++) {
			father[i] = i;
		}
	}

	public static void addEdge(int u, int v) {
		treeNext[tcnt] = treeHead[u];
		treeTo[tcnt] = v;
		treeHead[u] = tcnt++;
	}

	public static void addQuery(int u, int v, int i) {
		queryNext[qcnt] = queryHead[u];
		queryTo[qcnt] = v;
		queryIndex[qcnt] = i;
		queryHead[u] = qcnt++;
	}

	// 并查集找代表节点递归版
	// 一般来说都这么写，不过本题附加的测试数据设计的很毒
	// c++能通过，java这么写就会因为递归太深而爆栈
	// 都能通过的写法参考本节课Code02_LCATarjan2文件
	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	// Tarjan算法递归版
	// 一般来说都这么写，不过本题附加的测试数据设计的很毒
	// c++能通过，java这么写就会因为递归太深而爆栈
	// 都能通过的写法参考本节课Code02_LCATarjan2文件
	public static void tarjan(int u, int f) {
		visited[u] = true;
		for (int e = treeHead[u], v; e != 0; e = treeNext[e]) {
			v = treeTo[e];
			if (v != f) {
				tarjan(v, u);
			}
		}
		for (int e = queryHead[u], v; e != 0; e = queryNext[e]) {
			v = queryTo[e];
			if (visited[v]) {
				ans[queryIndex[e]] = find(v);
			}
		}
		father[u] = f;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		in.nextToken();
		int root = (int) in.nval;
		build(n);
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1, u, v; i <= m; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addQuery(u, v, i);
			addQuery(v, u, i);
		}
		tarjan(root, 0);
		for (int i = 1; i <= m; i++) {
			out.println(ans[i]);
		}
		out.flush();
		out.close();
		br.close();
	}

}
