package dev.kenuki.dijkstrafx.backend;

import dev.kenuki.dijkstrafx.backend.algorithm.BFS;
import dev.kenuki.dijkstrafx.util.Block;
import dev.kenuki.dijkstrafx.util.Cell;

import java.util.concurrent.CountDownLatch;

public class Engine {
    private CountDownLatch latch;
    private Block[][] maze;
    private Cell start;
    private Cell finish;
    public Engine(Block[][] maze, Cell start, Cell finish){
        latch = new CountDownLatch(1);
        this.maze = maze;
        this.start = start;
        this.finish = finish;
    }
    public void launch() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BFS bfs = new BFS();
                try {
                    bfs.shortestPath(latch, maze, start, finish);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void nextIteration() {
        latch.countDown();
    }
}
