package securityGeo;

import java.io.File;
import java.util.List;

class ShpFilesData {

	public File file;
	public String shptypeString;
	public String shpcontentList;
	public int[] information;
	


	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the shptypeString
	 */
	public String getShptypeString() {
		return shptypeString;
	}

	/**
	 * @param shptypeString the shptypeString to set
	 */
	public void setShptypeString(String shptypeString) {
		this.shptypeString = shptypeString;
	}

	
	/**
	 * @return the shpcontentList
	 */
	public String getShpcontentList() {
		return shpcontentList;
	}

	/**
	 * @param shpcontentList the shpcontentList to set
	 */
	public void setShpcontentList(String shpcontentList) {
		this.shpcontentList = shpcontentList;
	}

	/**
	 * @return the information
	 */
	public int[] getInformation() {
		return information;
	}

	/**
	 * @param information the information to set
	 */
	public void setInformation(int[] information) {
		this.information = information;
	}

	public void setInformation(String[] infor){
		this.information = new int[4];
		this.information[0] = Integer.parseInt(infor[0]);
		this.information[1] = Integer.parseInt(infor[1]);
		this.information[2] = Integer.parseInt(infor[2]);
		this.information[3] = Integer.parseInt(infor[3]);
		
	}
	/**
	 * @param information the information to set
	 */
	public void setInformation(int times, int locals, int weights) {
		int[] infor = new int[4];
		infor[0] = times;
		infor[1] = locals;
		infor[2] = weights;
		infor[3] = times * locals * weights;
		this.information = infor;
	}

}
