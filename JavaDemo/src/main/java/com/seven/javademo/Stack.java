package com.seven.javademo;

import android.util.Log;

public class Stack {
    public Node stackTop;
    public Node stackBottom;

    public Stack(Node stackTop, Node stackBottom) {
        this.stackTop = stackTop;
        this.stackBottom = stackBottom;
    }

    public Stack() {
    }

    /**
     * 进栈
     *
     * @param stack 栈
     * @param value 要进栈的元素
     */
    public static void pushStack(Stack stack, int value) {

        // 封装数据成节点
        Node newNode = new Node(value);

        Log.i("Seven","traverse:stackTop" + stack.stackTop.data);
        Log.i("Seven","traverse:stackBottom" + stack.stackBottom.data);
        // 栈顶本来指向的节点交由新节点来指向
        newNode.next = stack.stackTop;

        // 栈顶指针指向新节点
        stack.stackTop = newNode;
        Log.i("Seven","traverse:stackTop" + stack.stackTop.data);
        Log.i("Seven","traverse:stackTop.next" + stack.stackTop.next.data);
        Log.i("Seven","traverse:stackBottom" + stack.stackBottom.data);

    }

    /**
     * 遍历栈(只要栈顶指针不指向栈底指针，就一直输出)
     *
     * @param stack
     */
    public static void traverse(Stack stack) {
        Node stackTop = stack.stackTop;

        Log.i("Seven","traverse:stackTop" + stack.stackTop.data);
        Log.i("Seven","traverse:stackBottom" + stack.stackBottom.data);

        while (stackTop != stack.stackBottom) {

            Log.i("Seven","traverse:" + stackTop.data);

            stackTop = stackTop.next;
        }

    }

    /**
     * 判断该栈是否为空
     *
     * @param stack
     */
    public static boolean isEmpty(Stack stack) {
        if (stack.stackTop == stack.stackBottom) {
            System.out.println("isEmpty---->该栈为空");
            return true;
        } else {
            System.out.println("isEmpty---->该栈不为空");
            return false;
        }

    }

    /**
     * 出栈(将栈顶的指针指向下一个节点)
     * @param stack
     */
    public static void popStack(Stack stack) {

        // 栈不为空才能出栈
        if (!isEmpty(stack)) {

            //栈顶元素
            Node top = stack.stackTop;

            // 栈顶指针指向下一个节点
            stack.stackTop = top.next;

            System.out.println("popStack---->出栈的元素是：" + top.data);

        }
    }
}
