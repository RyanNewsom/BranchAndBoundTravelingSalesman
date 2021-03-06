package ex17.RyanNewsomKyleFrisbie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Testing for the Knapsack class
 */
public class KnapsackTest {
    private ArrayList<Item> mItemsWeCouldUse = new ArrayList<>();
    private ArrayList<Node> mTestNodes = new ArrayList<>();
    private int mMaxWeightPossible = 16;
    private Knapsack mKnapsack;

    @Before
    public void setUp() throws Exception {
        mItemsWeCouldUse.add(new Item(1,40,2));
        mItemsWeCouldUse.add(new Item(2,30,5));
        mItemsWeCouldUse.add(new Item(3,50,10));
        mItemsWeCouldUse.add(new Item(4,10,5));

        mKnapsack = new Knapsack(mMaxWeightPossible, mItemsWeCouldUse);

        mTestNodes.add(new Node(40, 98));
        mTestNodes.add(new Node(100, 115));
        mTestNodes.add(new Node(60, 70));
        mTestNodes.add(new Node(0, 82));
    }

    @Test
    public void testDetermineOptimalItemsForKnapsackProblem() throws Exception {
        ArrayList<Item> itemsUsed = new ArrayList<>();
        itemsUsed.add(new Item(1, 40, 2));
        itemsUsed.add(new Item(3, 50, 10));

        ArrayList<Item> itemsNotAvailableForUse = new ArrayList<>();
        itemsNotAvailableForUse.add(new Item(2, 30, 5));
        itemsNotAvailableForUse.add(new Item(4, 10, 5));

        double maximumPossibleProfit = 90.0;
        int actualProfit = 90;
        int actualWeight = 12;


        Node optimalExpected = new Node(itemsUsed, itemsNotAvailableForUse,
        maximumPossibleProfit, actualProfit, actualWeight);

        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(1, 40, 2));
        items.add(new Item(2, 30, 5));
        items.add(new Item(3, 50, 10));
        items.add(new Item(4, 10, 5));

        mKnapsack = new Knapsack(16, items);
        Node optimalActual = mKnapsack.determineOptimalStrategyForKnapsackProblem();

        Assert.assertTrue(optimalExpected.equals(optimalActual));
    }

    @Test
    public void testCalculateHighestPossibleProfit() throws Exception {
        Node node = new Node();
        double result;
        result = mKnapsack.calculateHighestPossibleProfit(node);

        assertEquals(115, result, 0);
    }

    @Test
    public void testSeeWhoToExploreNext() throws Exception {
        mKnapsack.mPossibleNodesForExploration = mTestNodes;
        double result = mKnapsack.seeWhoToExploreNext().getMaximumPossibleProfit();
        assertEquals(115, result, 0);
    }

    @Test
    public void createRightNode() throws Exception {
        // base case
        Node rootNode = new Node();
        rootNode.setMaximumPossibleProfit(mKnapsack.calculateHighestPossibleProfit(rootNode));
        Node rightChild = mKnapsack.createRightNode(rootNode);
        assertEquals(115, rightChild.getMaximumPossibleProfit(), 0);
        assertEquals(40, rightChild.getActualProfit(), 0);
        assertEquals(2, rightChild.getActualWeight(), 0);
    }

    @Test
    public void createLeftNode() throws Exception {
        // base case
        Node rootNode = new Node();
        rootNode.setMaximumPossibleProfit(mKnapsack.calculateHighestPossibleProfit(rootNode));
        Node leftChild = mKnapsack.createLeftNode(rootNode);
        assertEquals(82, leftChild.getMaximumPossibleProfit(), 0);
        assertEquals(0, leftChild.getActualProfit(), 0);
        assertEquals(0, leftChild.getActualWeight(), 0);
    }

    @Test
    public void testCreateChildren() throws Exception {
        Node node = new Node();

        mKnapsack.createChildren(node);

        assertNotNull(node.getLeftChild());
        assertNotNull(node.getRightChild());
    }

    @Test
    public void testPruneNodes() throws Exception {
        int expectedMaximumPossibleProfit = 115;
        mKnapsack.mPossibleNodesForExploration = mTestNodes;
        mKnapsack.pruneNodes();
        assertTrue((mKnapsack.mPossibleNodesForExploration.size() == 1) &&
                (mKnapsack.mPossibleNodesForExploration.get(0).getMaximumPossibleProfit() == expectedMaximumPossibleProfit));
    }
}