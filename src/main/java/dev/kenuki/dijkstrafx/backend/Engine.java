package dev.kenuki.dijkstrafx.backend;

import dev.kenuki.dijkstrafx.backend.algorithm.BFS;
import dev.kenuki.dijkstrafx.util.Block;
import dev.kenuki.dijkstrafx.util.Cell;

public class Engine {
    private final Object lock;
    private final Block[][] maze;
    private final Cell start;
    private final Cell finish;

    public Engine(Block[][] maze, Cell start, Cell finish){
        lock = new Object();
        this.maze = maze;
        this.start = start;
        this.finish = finish;
    }
    public void launch() {
        Thread thread = new Thread(() -> {
            BFS bfs = new BFS();
            try {
                bfs.shortestPath(lock, maze, start, finish);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }
    public void nextIteration() {
        synchronized (lock) {
            lock.notify();
        }

    }
}
