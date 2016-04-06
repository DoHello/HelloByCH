package com.derbysoft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.derbysoft.TreeNode;
import com.derbysoft.entity.sys.SYS_Module;

public class TreeHelper {

	private TreeNode root;
	private List<TreeNode> tempNodeList;
	private boolean isValidTree = true;
	
	public TreeHelper() {
		
	}
	
	public TreeHelper(List<TreeNode> treeNodeList) {
		tempNodeList = treeNodeList;
		generateTree();
	}
	
	public static TreeNode getTreeNodeById(TreeNode tree, String id) {
		if (tree == null)
			return null;
		TreeNode treeNode = tree.findTreeNodeById(id);
		return treeNode;
	}

	/** generate a tree from the given treeNode or entity list */
	public void generateTree() {
		HashMap<String, TreeNode> nodeMap = putNodesIntoMap();
		putChildIntoParent(nodeMap);
	}
	
	
	/**
	 * put all the treeNodes into a hash table by its id as the key
	 * 
	 * @return hashmap that contains the treenodes
	 */
	protected HashMap<String, TreeNode> putNodesIntoMap() {
		int maxId = Integer.MAX_VALUE;
		HashMap<String, TreeNode> nodeMap = new HashMap<String, TreeNode>();
		Iterator<TreeNode> it = tempNodeList.iterator();
		while (it.hasNext()) {
			TreeNode treeNode = (TreeNode) it.next();
			int id = Integer.valueOf(treeNode.getSelfId());
			if (id < maxId) {
				maxId = id;
				this.root = treeNode;
			}
			String keyId = String.valueOf(id);
			
			nodeMap.put(keyId, treeNode);
			// System.out.println("keyId: " +keyId);
		}
		return nodeMap;
	}
	
	/**
	 * set the parent nodes point to the child nodes
	 * 
	 * @param nodeMap
	 * a hashmap that contains all the treenodes by its id as the key
	 */
	protected void putChildIntoParent(HashMap<String, TreeNode> nodeMap) {
		Iterator<TreeNode> it = nodeMap.values().iterator();
		while (it.hasNext()) {
			TreeNode treeNode = (TreeNode) it.next();
			String parentId = treeNode.getParentId();
			String parentKeyId = parentId;
			if (nodeMap.containsKey(parentKeyId)) {
				TreeNode parentNode = (TreeNode) nodeMap.get(parentKeyId);
				treeNode.setParentNode(parentNode);
				if (parentNode == null) {
					this.isValidTree = false;
					return;
				} else {
					parentNode.addChildNode(treeNode);
					// System.out.println("childId: " +treeNode.getSelfId()+" parentId: "+parentNode.getSelfId());
				}
			}
		}
	}
	
	/** initialize the tempNodeList property */
	protected void initTempNodeList() {
		if (this.tempNodeList == null) {
			this.tempNodeList = new ArrayList<TreeNode>();
		}
	}
	
	/** add a tree node to the tempNodeList */
	public void addTreeNode(TreeNode treeNode) {
		initTempNodeList();
		this.tempNodeList.add(treeNode);
	}
	
	/**
	 * insert a tree node to the tree generated already
	 * 
	 * @return show the insert operation is ok or not
	 */
	public boolean insertTreeNode(TreeNode treeNode) {
		boolean insertFlag = root.insertJuniorNode(treeNode);
		return insertFlag;
	}

	public static List<TreeNode> changeEnititiesToTreeNodes(List<SYS_Module> entityList) {
		SYS_Module moduleEntity = new SYS_Module();
		List<TreeNode> tempNodeList = new ArrayList<TreeNode>();
		TreeNode treeNode;
		
		Iterator<SYS_Module> it = entityList.iterator();
		while (it.hasNext()) {
			moduleEntity = (SYS_Module) it.next();
			treeNode = new TreeNode();
			treeNode.setObj(moduleEntity);
			treeNode.setParentId(moduleEntity.getParentID());
			treeNode.setSelfId(moduleEntity.getModuleID());
			treeNode.setNodeName(moduleEntity.getModuleName());
			treeNode.setShowIdx(Double.valueOf(moduleEntity.getShowIndex()));
			tempNodeList.add(treeNode);
		}
		return tempNodeList;
	}

	public boolean isValidTree() {
		return this.isValidTree;
	}
	
	public TreeNode getRoot() {
		return root;
	}
	
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
	public List<TreeNode> getTempNodeList() {
		return tempNodeList;
	}
	
	public void setTempNodeList(List<TreeNode> tempNodeList) {
		this.tempNodeList = tempNodeList;
	}

}
