package eg.edu.alexu.csd.filestructure.avl;

public class node implements INode {
	
	private Comparable value = null;
	private node leftChild = null , rightChild = null , parent = null;
	
	public INode getLeftChild() {
		return leftChild;
	}

	public INode getRightChild() {
		return rightChild;
	}

	public Comparable getValue() {
		return value;
	}

	public void setValue(Comparable value) {
		this.value = value;
	}
	
	public void setLeftChild(node n){
		this.leftChild = n;
	}

	public void setRightChild(node n){
		this.rightChild = n;
	}
	
	public void setParent(node n){
		this.parent = n;
	}
	
	public node getParent(){
		return parent;
	}
}
