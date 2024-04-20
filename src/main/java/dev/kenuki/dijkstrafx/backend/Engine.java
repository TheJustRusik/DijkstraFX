package dev.kenuki.dijkstrafx.backend;

import dev.kenuki.dijkstrafx.backend.algorithms.Algorithm;
import dev.kenuki.dijkstrafx.backend.algorithms.implementation.BFS;
import dev.kenuki.dijkstrafx.util.AlgoTypes;
import dev.kenuki.dijkstrafx.util.Block;
import dev.kenuki.dijkstrafx.util.Cell;

public class Engine {
    private final Object lock;
    private final Block[][] maze;
    private final Cell start;
    private final Cell finish;
    private final Algorithm algorithm;
    private boolean hasIteration;

    public Engine(Block[][] maze, Cell start, Cell finish, AlgoTypes algoTypes){
        hasIteration = false;
        lock = new Object();
        this.maze = maze;
        this.start = start;
        this.finish = finish;
        algorithm = new BFS();//TODO: implement a*, greedy, dijkstra and ability to choose them in future
    }
    public void launch() {
        Thread thread = new Thread(() -> {
            try {
                hasIteration = true;
                algorithm.shortestPath(lock, maze, start, finish);
                hasIteration = false;
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
    public boolean hasNextIteration() {
        return hasIteration;
    }
}
