package com.shtoone.glhnt.treenode;

public class FileBean
{
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;

	public String getTag() {
		return tag;
	}

	@TreeNodeTag
	private String tag;

	public int get_id() {
		return _id;
	}

	public FileBean(int _id, int parentId, String name,String tag)
	{
		super();
		this._id = _id;

		this.parentId = parentId;
		this.name = name;
		this.tag = tag;
	}

}
