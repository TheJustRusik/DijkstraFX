package dev.kenuki.dijkstrafx.backend.algorithms.implementation;

import dev.kenuki.dijkstrafx.backend.algorithms.Algorithm;
import dev.kenuki.dijkstrafx.util.Block;
import dev.kenuki.dijkstrafx.util.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BFS extends Algorithm {
    @Override
    public void shortestPath(Object lock, Block[][] maze, Cell start, Cell end) {
        assert (maze != null && start != null && end != null);

        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Cell[][] parent = new Cell[rows][cols];
        LinkedList<Cell> queue = new LinkedList<>();

        visited[start.y][start.x] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            if (current.x == end.x && current.y == end.y) {
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (isValid(nx, ny, rows, cols) && maze[ny][nx] != Block.WALL && !visited[ny][nx]) {
                    visited[ny][nx] = true;
                    //WAIT HERE
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if(maze[ny][nx] != Block.START && maze[ny][nx] != Block.FINISH )
                        maze[ny][nx] = Block.CHECKED;
                    parent[ny][nx] = current;

                    queue.add(new Cell(nx, ny));
                }
            }
        }

        List<Cell> path = new ArrayList<>();
        for (Cell at = end; at != null; at = parent[at.y][at.x]) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(maze[at.y][at.x] != Block.START && maze[at.y][at.x] != Block.FINISH )
                maze[at.y][at.x] = Block.ROAD;

            path.add(at);
        }
        Collections.reverse(path);
        System.out.println("END");
    }


}
