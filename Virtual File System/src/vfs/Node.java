package vfs;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

	private T data ;
    private ArrayList<Node<FolderModel>> folders ;
    private ArrayList<Node<FileModel>> files ;
    private Node<FileModel> parent;
    
    
   public Node(T otherData) {
	   data = otherData;
	   folders= new ArrayList<>();
	   files = new ArrayList<>();
   }
   public Node() {
	   folders= new ArrayList<>();
	   files = new ArrayList<>();
	   data = null;
}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public ArrayList<Node<FolderModel>> getFolders() {
		return folders;
	}
	public void setFolders(ArrayList<Node<FolderModel>> folders) {
		this.folders = folders;
	}
	public ArrayList<Node<FileModel>> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<Node<FileModel>> files) {
		this.files = files;
	}
	public Node<FileModel> getParent() {
		return parent;
	}
	public void setParent(Node<FileModel> parent) {
		this.parent = parent;
	}

    

}
