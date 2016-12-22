package org.webdriver.patatiumappui.utils;

import java.io.*;

public class FileManger {

	/**
	 * 递归删除文件
	 * @param file
	 */
	private void deleteFile(File file)
	{
		if (file.isDirectory()) {
			File[] files=file.listFiles();
			//递归条用删除方法
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}
		}
		file.delete();
	}
	/**
	 * 删除文件夹和密码
	 * @param workspaceRootPath 文件目录
	 */
	public  void clearFile(String workspaceRootPath)
	{
		File file=new File(workspaceRootPath);
		if (file.exists()) {
			deleteFile(file);
		}

	}

    /**
     *
     * @param path 写入文件目标路径
     * @param encode 写入文件编码
     * @param append 是否文本追加模式
     * @param content 写入文本
     */
	public void writeWithEncode(String path,String encode,boolean append,String content)
	{
		File file=new File(path);
		if (file.exists())
		{
			file.delete();
		}
		try {
			file.createNewFile();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter( new FileOutputStream(file,append),encode));
            bufferedWriter.write(content);
            bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    /**
     *
     * @param file 写入文件文件
     * @param encode 写入文件编码
     * @param append 是否文本追加模式
     * @param content 写入文本
     */
    public void writeWithEncode(File file,String encode,boolean append,String content)
    {
        try {
            file.createNewFile();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter( new FileOutputStream(file,append),encode));
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}


}
