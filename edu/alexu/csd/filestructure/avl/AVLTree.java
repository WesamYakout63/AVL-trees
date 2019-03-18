package eg.edu.alexu.csd.filestructure.avl;

import java.awt.print.Printable;

public class AVLTree implements IAVLTree {

	private node root = new node();

	public static void print(node n) {
		if (n != null) {
			System.out.print("Node :" + n.getValue() + " ");
			if (n.getParent() != null)
				System.out.print("Parent :" + n.getParent().getValue() + " ");
			if (n.getLeftChild() != null)
				System.out.print("LeftChild :" + n.getLeftChild().getValue() + " ");
			if (n.getRightChild() != null)
				System.out.print("RightChild :" + n.getRightChild().getValue());
			System.out.println();
			print((node) n.getLeftChild());
			print((node) n.getRightChild());
		}
	}

	private node find(Comparable key) {
		node n = new node();
		n = root;
		while (n != null) {
			if (key.compareTo(n.getValue()) == 0)
				break;
			else {
				if (key.compareTo(n.getValue()) >= 1)
					n = (node) n.getRightChild();
				else
					n = (node) n.getLeftChild();
			}
		}
		return n;
	}

	public int nodesNumber(node n) {
		if (n == null)
			return 0;
		else {
			return nodesNumber((node) n.getLeftChild()) + nodesNumber((node) n.getRightChild()) + 1;
		}
	}

	public void insert(Comparable key) {
		node n = root;
		if (root.getValue() == null) {
			n.setValue(key);
			root = n;
		} else {
			while (n.getLeftChild() != null || n.getRightChild() != null) {
				if (key.compareTo(n.getValue()) >= 1) {
					if (n.getRightChild() != null)
						n = (node) n.getRightChild();
					else
						break;
				} else {
					if (n.getLeftChild() != null)
						n = (node) n.getLeftChild();
					else
						break;
				}
			}
			node x = new node();
			x.setValue(key);
			if (key.compareTo(n.getValue()) >= 1) {
				x.setParent(n);
				n.setRightChild(x);
			} else {
				x.setParent(n);
				n.setLeftChild(x);
			}
		}
		while (n != root) {
			n = balance(n);
			n = n.getParent();
		}
		root = balance(n);
	}

	public boolean delete(Comparable key) {
		if (search(key)) {
			node n = new node();
			n = find(key);
			if (root.getRightChild() == null && root.getLeftChild() == null)
				root = null;
			else {
				if (n.getLeftChild() != null && n.getRightChild() != null) {
					Comparable temp = n.getValue();
					n.setValue(n.getRightChild().getValue());
					n.getRightChild().setValue(temp);
					n = (node) n.getRightChild();
					while (n.getLeftChild() != null) {
						Comparable temp1 = n.getValue();
						n.setValue(n.getLeftChild().getValue());
						n.getLeftChild().setValue(temp1);
						n = (node) n.getLeftChild();
					}
				}
				if (n.getLeftChild() == null && n.getRightChild() == null) {
					if (n.getParent().getLeftChild() == n)
						n.getParent().setLeftChild(null);
					else
						n.getParent().setRightChild(null);
				} else if (n.getLeftChild() == null || n.getRightChild() == null) {
					node temp = new node();
					temp = (node) n.getLeftChild() == null ? (node) n.getRightChild() : (node) n.getLeftChild();
					if (n.getParent().getLeftChild() == n) {
						temp.setParent(n.getParent());
						n.getParent().setLeftChild(temp);
					} else {
						temp.setParent(n.getParent());
						n.getParent().setRightChild(temp);
					}
				}
				n = n.getParent();
				while (n != root) {
					n = balance(n);
					n = n.getParent();
				}
				root = balance(n);
			}
			return true;
		} else
			return false;
	}

	public boolean search(Comparable key) {
		if (find(key) == null)
			return false;
		else
			return true;
	}

	public static node balance(node n) {
		if (!(length(n.getRightChild()) - length(n.getLeftChild()) <= 1
				&& length(n.getRightChild()) - length(n.getLeftChild()) >= -1))
			if (length(n.getRightChild()) - length(n.getLeftChild()) > 1) {
				node childr = (node) n.getRightChild();
				if (length(childr.getRightChild()) - length(childr.getLeftChild()) > 0) { // RR
					node temp = (node) childr.getLeftChild();
					childr.setParent(n.getParent());
					if (n.getParent() != null) {
						if (n.getParent().getLeftChild() == n)
							n.getParent().setLeftChild(childr);
						else
							n.getParent().setRightChild(childr);
					}
					n.setParent(childr);
					childr.setLeftChild((node) n);
					if (temp != null)
						temp.setParent(n);
					n.setRightChild(temp);
					n = childr;
				} else { // RL
					node childrl = (node) childr.getLeftChild();
					node temp1 = (node) childrl.getRightChild();
					node temp2 = (node) childrl.getLeftChild();
					childrl.setParent(n.getParent());
					if (n.getParent() != null) {
						if (n.getParent().getLeftChild() == n)
							n.getParent().setLeftChild(childrl);
						else
							n.getParent().setRightChild(childrl);
					}
					n.setParent(childrl);
					childr.setParent(childrl);
					childrl.setRightChild(childr);
					childrl.setLeftChild(n);
					if (temp1 != null)
						temp1.setParent(childr);
					childr.setLeftChild(temp1);
					if (temp2 != null)
						temp2.setParent(n);
					n.setRightChild(temp2);
					n = childrl;
				}
			} else if (length(n.getRightChild()) - length(n.getLeftChild()) < -1) {
				node childl = (node) n.getLeftChild();
				if (length(childl.getRightChild()) - length(childl.getLeftChild()) > 0) { // LR
					node childlr = (node) childl.getRightChild();
					node temp1 = (node) childlr.getRightChild();
					node temp2 = (node) childlr.getLeftChild();
					childlr.setParent(n.getParent());
					if (n.getParent() != null) {
						if (n.getParent().getLeftChild() == n)
							n.getParent().setLeftChild(childlr);
						else
							n.getParent().setRightChild(childlr);
					}
					n.setParent(childlr);
					childl.setParent(childlr);
					childlr.setRightChild(n);
					childlr.setLeftChild(childl);
					if (temp1 != null)
						temp1.setParent(n);
					n.setLeftChild(temp1);
					if (temp2 != null)
						temp2.setParent(childl);
					childl.setRightChild(temp2);
					n = childlr;
				} else { // LL
					node temp = (node) childl.getRightChild();
					childl.setParent(n.getParent());
					if (n.getParent() != null) {
						if (n.getParent().getLeftChild() == n)
							n.getParent().setLeftChild(childl);
						else
							n.getParent().setRightChild(childl);
					}
					n.setParent(childl);
					childl.setRightChild(n);
					if (temp != null)
						temp.setParent(n);
					n.setLeftChild(temp);
					n = childl;
				}
			}
		return n;
	}

	private static int length(INode n) {
		if (n == null)
			return -1;
		else
			return length(n.getLeftChild()) >= length(n.getRightChild()) ? length(n.getLeftChild()) + 1
					: length(n.getRightChild()) + 1;
	}

	public int height() {
		return length(root) + 1;
	}

	public INode getTree() {
		return root;
	}

	public static void main(String[] args) {
		AVLTree T1 = new AVLTree();
		/*
		 * T1.insert(57); print(root); System.out.println(); T1.insert(26);
		 * print(root); System.out.println(); T1.insert(25); print(root);
		 * System.out.println(); T1.insert(3); print(root);
		 * System.out.println(); T1.insert(38); print(root);
		 * System.out.println(); T1.insert(37); print(root);
		 * System.out.println(); T1.insert(47); print(root);
		 * System.out.println(); T1.insert(72); print(root);
		 * System.out.println(); T1.insert(63); print(root);
		 * System.out.println(); T1.insert(94); print(root);
		 * System.out.println(); T1.insert(78); print(root);
		 * System.out.println(); T1.insert(1); print(root);
		 * System.out.println(); T1.insert(30); print(root);
		 * System.out.println(); T1.insert(32); print(root);
		 * System.out.println(); T1.insert(35); print(root);
		 * System.out.println(); T1.delete(26); print(root);
		 * System.out.println();
		 */
		/*
		 * T1.delete(57); print(root); System.out.println();
		 */
		int[] input = { 13, 8, 5, 9, 4, 6, 12, 2, 1, 3 };
		for (int i = 0; i < input.length; i++) {
			T1.insert(input[i]);
			System.out.println(T1.height());
			// T1.print(root);
			// System.out.println();
		}
		print((node) T1.getTree());
		int[] deleteOrder = {4, 2, 12, 9, 13, 5, 3, 1, 6 };
		for (int i = 0; i < deleteOrder.length; i++) {
			System.out.println(T1.delete(deleteOrder[i]));
			T1.print((node) T1.getTree());
			System.out.println();
		}
	}
}
