package eg.edu.alexu.csd.filestructure.graphs;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Graph implements IGraph {

	private ArrayList<Point>[] Vertices;
	private static int paths[];
	private boolean[] visited;
	private int vers, Edges;

	public void readGraph(File file) {
		Scanner Input = null;
		try {
			Input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (Input.hasNext())
			vers = Input.nextInt();
		else
			throw new RuntimeException("You should write the number of vertices");
		if (Input.hasNext())
			Edges = Input.nextInt();
		else
			throw new RuntimeException("You should write the number of edges");
		Input.nextLine();
		Vertices = new ArrayList[vers];
		visited = new boolean[vers];
		paths = new int[vers];
		for (int i = 0; i < size(); i++)
			Vertices[i] = new ArrayList<Point>();
		for (int i = 0; i < Edges; i++) {
			int from, to, weight;
			if (Input.hasNext())
				from = Input.nextInt();
			else
				throw new RuntimeException("You should write the starting node");
			if (Input.hasNext())
				to = Input.nextInt();
			else
				throw new RuntimeException("You should write the ending node");
			if (Input.hasNext())
				weight = Input.nextInt();
			else
				throw new RuntimeException("You should write the weight of the edge");
			Point temp = new Point(to, weight);
			Vertices[from].add(temp);
			if (Input.hasNextLine())
				Input.nextLine();
		}
	}

	public int size() {
		return vers;
	}

	public ArrayList<Integer> getVertices() {
		ArrayList<Integer> vertices = new ArrayList<>();
		for (int i = 0; i < size(); i++)
			vertices.add(i);
		return vertices;
	}

	public ArrayList<Integer> getNeighbors(int v) {
		ArrayList<Integer> neighbors = new ArrayList<>();
		for (int i = 0; i < Vertices[v].size(); i++)
			neighbors.add((int) Vertices[v].get(i).getX());
		return neighbors;
	}

	public void runDijkstra(int src, int[] distances) {
		for(int i = 0 ; i < size() ; i++)
			distances[i] = Integer.MAX_VALUE;
		distances[src] = 0;
		PriorityQueue<Point> queue = new PriorityQueue<Point>();
		int min = Integer.MAX_VALUE, nodeIndex = 0;
		visited[src] = true;
		for (int i = 0; i < Vertices[src].size(); i++) {
			if (min > (int) Vertices[src].get(i).getY() && !visited[(int) Vertices[src].get(i).getX()]) {
				min = (int) Vertices[src].get(i).getY();
				nodeIndex = i;
			}
		}
		if (distances[src]
				+ (int) Vertices[src].get(nodeIndex).getY() < distances[(int) Vertices[src].get(nodeIndex).getX()])
			distances[(int) Vertices[src].get(nodeIndex).getX()] = distances[src]
					+ (int) Vertices[src].get(nodeIndex).getY();
		if (!visited[(int) Vertices[src].get(nodeIndex).getX()])
			runDijkstra((int) Vertices[src].get(nodeIndex).getX(), distances);
		else
			return;
	}

	public ArrayList<Integer> getDijkstraProcessedOrder() {
		ArrayList<Integer> List = new ArrayList<>();
		for (int i = 0; i < paths.length; i++)
			List.add(paths[i]);
		return List;
	}

	public boolean runBellmanFord(int src, int[] distances) {
		return false;
	}

	public static void main(String Args[]) {
		IGraph s = new Graph();
		File file = new File("C:\\Users\\Wesam Yakout\\Desktop\\New Text Document.txt");
		s.readGraph(file);
		s.runDijkstra(0, paths);
		for (int i = 0; i < paths.length; i++)
			System.out.print(s.getDijkstraProcessedOrder().get(i) + " ");
	}

}
