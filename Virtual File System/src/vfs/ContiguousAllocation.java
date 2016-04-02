package vfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ContiguousAllocation extends Disk {
	
	//  file 	start 	# of blocks
	ArrayList<Pair<String, Pair <Integer ,Integer>>> directory ;
	
	public ContiguousAllocation(int numOfBlocks) throws IOException {
		super(numOfBlocks);
		directory = new ArrayList<>();
	}
	
	
	@Override
	public void CFile(String filePath ,int fileSize) {
		
		ArrayList<String> path = new ArrayList<>();
		
		path.addAll(Arrays.asList(filePath.split("/")));
		
		FileModel filemodel = new FileModel(path.get(path.size()-1),filePath , fileSize);
		

		double blocksPerFile = Math.ceil(fileSize/1024.0) ; 
		
		System.err.println(numOfBlocks +" "+blocksPerFile);
		if(blocksPerFile > numOfBlocks){
			
			System.out.println("Creation failed, No enough space.");
		}
		if(tree.createFile(path, filemodel)){
		//	System.err.println("Created");
			int lastAllocatedBlock = freeSpaceManager.lastIndexOf("1");
			int allocationIndex = -1;
			
			if(lastAllocatedBlock == -1){
				
				allocationIndex = 0;
			}
			else{
				
				int currentIndex = 0, currentCounter = 0;
				int maxIndex = Integer.MIN_VALUE, maxCounter = 0;
				
				if(freeSpaceManager.charAt(0) == '0'){
					currentIndex = 0;
					currentCounter = 1;
				}
				
				for(int i = 1; i < freeSpaceManager.length(); i++){
					
					if(freeSpaceManager.charAt(i -1) == '1' && freeSpaceManager.charAt(i) == '0'){
						
						if(currentCounter > maxCounter){
							
							maxIndex = currentIndex;
							maxCounter = currentCounter;
						}
						
						currentIndex = i;
						currentCounter = 1;
						
					}
					else if(freeSpaceManager.charAt(i) == '0'){
						
						currentCounter++;
					}
					
					
				}
				
				if (blocksPerFile > maxCounter){
					
					if(blocksPerFile < diskSize - lastAllocatedBlock){
						
						allocationIndex = lastAllocatedBlock + 1;
						
					}
					else
					{
						System.out.println("Creation failed, No enough space.");
						tree.deleteFile(path);
					}
				}
				else{
					
					allocationIndex = maxIndex;
				}
				
				
			}
			

			
			for(int i = allocationIndex; i < allocationIndex + blocksPerFile; i++ ){
				
				freeSpaceManager.setCharAt(i, '1');
			}
			
			Pair<Integer, Integer> temp = new Pair<Integer, Integer>(allocationIndex, (int)blocksPerFile);
			directory.add(new Pair<String, Pair<Integer,Integer>>(filePath, temp));
			
		}
		else{
			System.out.println("Creation failed");
		}
		
		
	}
	
	
	

	@Override
	public void DFile(String filePath) {
		
		ArrayList<String> path = new ArrayList<>();
		if(tree.deleteFile(path)){
			
		
		for(int i =0 ;i<directory.size();i++ ){
			//to delete all the children 
			 if(directory.get(i).first.startsWith(filePath)){
                 directory.get(i).first = "";
                 
                 int allocationIndex = directory.get(i).second.first;
                 int blocksPerFile = directory.get(i).second.second;
                 
                 for(int j = allocationIndex; j < blocksPerFile + allocationIndex; j++){
                	 
                	freeSpaceManager.setCharAt(j, '0');
                 }
                 
                 //directory.remove(i);
                 //i--;
             }
		}
		
		}
		else{
			System.out.println("Error");
		}
		
	}
	
	
	@Override
	public void DFolder(String folderPath) {
		
		ArrayList<String> path = new ArrayList<>();
		if(tree.deleteFolder(path)){
		for(int i =0 ;i<directory.size();i++ ){
			//to delete all the children 
			 if(directory.get(i).first.startsWith(folderPath)){
                 directory.get(i).first = "";
                 
                 int allocationIndex = directory.get(i).second.first;
                 int blocksPerFile = directory.get(i).second.second;
                 
                 for(int j = allocationIndex; j < blocksPerFile + allocationIndex; j++){
                	 
                	freeSpaceManager.setCharAt(j, '0');
                 }
                 
                 //directory.remove(i);
                 //i--;
             }
		}
		
		}
		else{
			System.out.println("Error");
		}
		
	}
	
	
	
	@Override
	public String toString (){
		return directory.toString();
		
	}

}