package me.mikusugar.node2vec;

import com.ansj.vec.Learn;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author mikusugar
 * @version 1.0, 2023/8/3 17:06
 * @description
 */
public class Node2VecLearn
{
    /*
    超参数p，控制遍历的返回前一个节点的概率。
    */
    private double p = 1;

    /*
    超参数q，控制节点继续向前行的概率。
     */
    private double q = 1;

    /*
    每条路线上节点的数量
    */
    private int walkLength = 20;

    /*
    从头到尾反复遍历次数
    */
    private int numWalks = 10;

    /**
     * 训练多少个特征
     */
    private int layerSize = 242;

    /**
     * 上下文窗口大小
     */
    private int window = 5;

    private double sample = 1e-3;

    private double alpha = 0.025;

    private final Learn learn;

    public Node2VecLearn(double p, double q, int walkLength, int numWalks, int layerSize, int window, double sample,
            double alpha)
    {
        this.p = p;
        this.q = q;
        this.walkLength = walkLength;
        this.numWalks = numWalks;
        this.layerSize = layerSize;
        this.window = window;
        this.sample = sample;
        this.alpha = alpha;
        this.learn = new Learn(false, layerSize, window, alpha, sample);
        check();
    }

    private void check()
    {
        if (this.window > this.walkLength)
        {
            throw new IllegalArgumentException("window参数必须小于walkLength");
        }
    }

    public Node2VecLearn()
    {
        this.learn = new Learn(false, layerSize, window, alpha, sample);
    }

    public void lean(Graph graph)
    {
        check();
        final RandomWalk randomWalk = new RandomWalk(p, q, walkLength, numWalks, graph);
        final List<int[]> simulateWalks = randomWalk.simulateWalks();
        learn.learnData(simulateWalks);
    }

    public void saveMode(String path) throws IOException
    {
        learn.saveModel(new File(path));
    }

    public void setP(double p)
    {
        this.p = p;
    }

    public void setQ(double q)
    {
        this.q = q;
    }

    public void setWalkLength(int walkLength)
    {
        this.walkLength = walkLength;
    }

    public void setNumWalks(int numWalks)
    {
        this.numWalks = numWalks;
    }

    public void setLayerSize(int layerSize)
    {
        this.layerSize = layerSize;
        this.learn.setLayerSize(layerSize);
    }

    public void setWindow(int window)
    {
        this.window = window;
        this.learn.setWindow(window);
    }

    public void setSample(double sample)
    {
        this.sample = sample;
        this.learn.setSample(sample);
    }

    public void setAlpha(double alpha)
    {
        this.alpha = alpha;
        this.learn.setAlpha(alpha);
    }
}