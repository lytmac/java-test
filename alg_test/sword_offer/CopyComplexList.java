package sword_offer;


import java.util.HashMap;
import java.util.Map;

public class CopyComplexList {


    private static class ListNode {
        int data;
        ListNode next;
        ListNode random;

        public ListNode(int data, ListNode next, ListNode random) {
            this.data = data;
            this.next = next;
            this.random = random;
        }

        public static ListNode copyNew(ListNode node) {
            return new ListNode(node.data, node.next, null);
        }
    }

    private static ListNode copy(ListNode origin) {
        if(origin == null)
            return null;

        ListNode newHeader = null;

        Map<ListNode, ListNode> relationMap = new HashMap<>();

        ListNode cur = origin;  //原有链表上的游标
        ListNode newPre = null; //新链表上标识上一个节点


        while(cur != null) { //先遍历一遍链表，填充data和next,以及新老节点的映射关系
            //重构新节点和前序节点的指向关系
            ListNode newCur = ListNode.copyNew(cur);

            if(newPre != null) {
                newPre.next = newCur;
            } else {
                newHeader = newCur;
            }

            newPre = newCur;
            relationMap.put(cur, newCur);
            cur = cur.next;

        }

        cur = origin;
        ListNode newCur = newHeader;
        while(cur != null) { //第二遍遍历填充random指针
            newCur.random = relationMap.get(cur.random);

            cur = cur.next;
            newCur = newCur.next;
        }

        return newHeader;
    }

    public static void main(String[] args) {
        //copy();
    }
}
