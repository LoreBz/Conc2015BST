/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataStrucutres.InternalNode;
import dataStrucutres.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author lorenzo
 */
class BTreePrinter {

    public static <T extends Comparable<?>> void printNode(Node<Integer, Object> root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<Node<Integer, Object>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes)) {
            return;
        }

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<Node<Integer, Object>> newNodes = new ArrayList<Node<Integer, Object>>();
        for (Node<Integer, Object> node : nodes) {
            if (node != null) {
                System.out.print(node);
                if (node instanceof InternalNode) {
                    newNodes.add(((InternalNode<Integer, Object>) node).left.get());
                    newNodes.add(((InternalNode<Integer, Object>) node).right.get());
                }

            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                Node node = nodes.get(j);
                if (node == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }
                if (node instanceof InternalNode) {
                    if (((InternalNode) node).left.get() != null) {
                        System.out.print("/");
                    } else {
                        BTreePrinter.printWhitespaces(1);
                    }

                    BTreePrinter.printWhitespaces(i + i - 1);

                    if (((InternalNode) node).right.get() != null) {
                        System.out.print("\\");
                    } else {
                        BTreePrinter.printWhitespaces(1);
                    }
                }
                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    private static <T extends Comparable<?>> int maxLevel(Node<Integer, Object> node) {
        if (node == null || !(node instanceof InternalNode)) {
            return 0;
        }
        return Math.max(BTreePrinter.maxLevel((Node<Integer, Object>) ((InternalNode) node).left.get()), BTreePrinter.maxLevel((Node<Integer, Object>) ((InternalNode) node).right.get())) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null) {
                return false;
            }
        }

        return true;
    }

}
